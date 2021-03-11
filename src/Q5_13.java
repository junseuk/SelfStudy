import java.util.Scanner;
public class Q5_13 {
	public static void main(String args[]) {
		String[] words = { "television", "computer", "mouse", "phone" };
		Scanner scanner = new Scanner(System.in);
		
		for(int i=0;i<words.length;i++) {
			char[] question = words[i].toCharArray();
			for (int j = 0;j<question.length;j++) {
 				int randomindex = (int) (Math.random()*question.length);
 				char tmp = question[j];
 				question[j] = question[randomindex];
 				question[randomindex] = tmp;
			}
			System.out.printf("Q%d. %s의 정답을 입력하세요.>", i+1, new String(question));
			String answer = scanner.nextLine();
			
			if(words[i].equals(answer.trim())) System.out.printf("맞았습니다.%n%n");
			
			else System.out.printf("틀렸습니다.%n%n"); 
		} 
		scanner.close();
	}
}
