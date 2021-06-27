#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <pthread.h>
#include <assert.h>
#include <math.h>
#include <unistd.h>
#define NUM_THREADS 5


/*
Each thread targets a certain age range and sort individually
threadAgeStart = each thread's start point of age
threadWorkAmount = each thread's age range of work
printOrder = order of which thread should do printf first
countThread = count how many threads are being processed to set order of each thread
*/
int maxAge;
int threadAgeStart = 0;
int threadWorkAmount;
int printOrder = NUM_THREADS - 1;
int countThread = 0;
pthread_mutex_t master_lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t print_lock = PTHREAD_MUTEX_INITIALIZER;

typedef struct node {
  char name[32];
  char sex;
  int  age;
  struct node *next;
  struct node *prev;;
} node;

node *CreateNode();
node *ReadNodeDate(node *Top);
void *myThread(void *Top);
node *sortList(node *top);
void freeData(node *Top);

//Create a node in a heap memory
node *CreateNode() {
  node *newNode;
  newNode = (node *)malloc(sizeof(node));
  newNode->next = NULL;
  newNode->prev = NULL;
  return newNode;
}

//Read input data and create a linked list
node *ReadNodeDate(node *Top) {
  node *newNode;
  char *cp, *tp;
  char buf[512];
  char name[32];
  char sex;
  int age;
  while (fgets(buf, sizeof(buf), stdin) != NULL) {
    if (Top == NULL) {
      Top = CreateNode();
      newNode = Top;
    } else {
      newNode->next = CreateNode();
      newNode->next->prev = newNode;
      newNode = newNode->next;
    }
    cp = buf;
    tp = cp;
    if((cp = index(tp, ' ')) == NULL) {
      fprintf(stderr, "Input format fail: %s\n", tp);
      exit(-1);
    }
    *cp++ = '\0';
    strcpy(newNode->name, tp);
    newNode->sex = *cp++;
    cp++;
    newNode->age = atoi(cp);
    //Set the maxAge
    if (atoi(cp) > maxAge) maxAge = atoi(cp);
  }
  return (Top);
}

/*
Each thread will copy the linked list whose age is in thread's range of age
And run sortList() individually
When sorting process is done, they will print their own result in order
*/
void *myThread(void *Top) {
  int order;
  int min;
  pthread_mutex_lock(&master_lock);
  order = countThread;
  min = threadAgeStart;
  threadAgeStart += threadWorkAmount;
  countThread++;
  pthread_mutex_unlock(&master_lock);
  node *temp;
  node *originalTop = (node*)Top;
  node *newTop = (node*) NULL;
  node *newNode;
  int max = min + threadWorkAmount;
  //Copy data
  for (temp = originalTop;temp != NULL;temp = temp -> next) {
    if (temp -> age < max && temp -> age >= min) {
      if (newTop == NULL) {
        newTop = CreateNode();
        newNode = newTop;
      } else {
        newNode->next = CreateNode();
        newNode->next->prev = newNode;
        newNode = newNode->next;
      }
      strcpy(newNode -> name, temp -> name);
      newNode -> sex = temp -> sex;
      newNode -> age = temp -> age;
    }
  }
  //Sort data
  newTop = sortList(newTop); 
  //Wait for the turn and print the result
  while(1) {
    if (order == printOrder) {
      pthread_mutex_lock(&print_lock);
      for (newNode = newTop; newNode != NULL; newNode = newNode->next) {
        printf("%s\t%c\t%d\n", newNode->name, newNode->sex, newNode->age);
      }
      printOrder--;
      pthread_mutex_unlock(&print_lock);
      break;
    }
  }
  pthread_exit(NULL);
}

node *sortList(node *top) {
  node *sortp;
  node *cp;
  node *maxp;
  node *newNode;
  int flag;
  maxp = top;

  for(cp = top; cp != NULL; cp = cp->next) {
    if (maxp->age < cp->age) maxp = cp;
  }
  if (top != maxp) {
    maxp->prev->next = maxp->next;
    if (maxp->next) maxp->next->prev = maxp->prev;
    top->prev = maxp;
    maxp->next = top;
    maxp->prev = NULL;
    top = maxp;
  }

  sortp = top;

  while (sortp->next != NULL) {
    maxp = sortp->next;

    flag = 0;
    for (cp = sortp->next; cp != NULL; cp = cp->next) {
      if (cp->age > maxp->age) {
	    maxp = cp;
	    flag++;
      }
    }
    if (!flag) {
      if (sortp->next->next != NULL) {
	      sortp = sortp->next;
	      continue;
      }
      break;
    }

    /*
     * move maxp sortp 
     */
    maxp->prev->next = maxp->next;
    if (maxp->next != NULL) {
      maxp->next->prev = maxp->prev;
    }
    maxp->next = sortp->next;
    maxp->prev=sortp;
    sortp->next = maxp;
    sortp = maxp;
  }

  return(top);
}

void freeData(node* Top) {
  node *Node;
  for (Node = Top; Node != NULL; Node = Node->next) {
    free(Node);
  }
}

int main() {
  node *Top;
  node *newNode;
  pthread_t threads[NUM_THREADS];
  int rc;

  Top = (node *)NULL;
  Top = ReadNodeDate(Top);
  threadWorkAmount = ceil((maxAge+1)/NUM_THREADS);
  //Create threads
  for (int i = 0 ; i < NUM_THREADS ; i++ ) {
    rc = pthread_create(&threads[i], NULL, myThread, (void*) Top); assert(rc == 0);
  }
  //Wait threads
  for (int i = 0 ; i < NUM_THREADS ; i++ ) {
    rc = pthread_join(threads[i], NULL); assert(rc == 0);
  }
  //Free original data
  freeData(Top);
  return 0;
}