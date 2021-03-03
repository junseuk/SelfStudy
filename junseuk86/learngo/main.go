package main

import (
	"fmt"
	"strings"
)

func main() {
	const name string = "nico"
	//	name2 := "nininco"
	//	var nameds string = "nico"
	fmt.Println(multiple(2, 2))
	totalLength, upperName := lenAndUpper("nico")
	fmt.Println(totalLength, upperName)
	repeatme("sdfs", "sdfds", "sdfsdfs")
	total := superAdd(1, 2, 3, 4, 5, 6, 7)
	fmt.Println(total)
	namess := [5]string{"a", "b", "c"}
	namesss := []string{"d", "e"}      //slice
	namesss = append(namesss, "dfsdf") //to update the slice
	fmt.Println(namess)
	fmt.Println(namesss)

	jun := map[string]string{"name": "jun", "age": "23"}
	for key, value := range jun {
		fmt.Println(key, value)
	}
}

func multiple(a, b int) int {
	return a * b
}

//naked return and defer
func lenAndUpper(name string) (length int, upper string) {
	defer fmt.Println("I'm done!")
	length = len(name)
	upper = strings.ToUpper(name)
	return
}

//multiple arguments
func repeatme(words ...string) {
	fmt.Println(words)
}

func superAdd(numbers ...int) int {
	total := 0
	for _, number := range numbers {
		fmt.Println(number)
		total += number
	}
	return total
}
