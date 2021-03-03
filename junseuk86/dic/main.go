package main

import (
	"fmt"

	"github.com/junseuk86/dic/dic"
)

func main() {
	dic := dic.Dictionary{}
	err := dic.Add("hello", "greeting")
	if err != nil {
		fmt.Println(err)
	}
	definition, err := dic.Search("hello")
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(definition)
	}
	err2 := dic.Add("hello", "greeting")
	if err2 != nil {
		fmt.Println(err2)
	}
	err3 := dic.Update("hello", "hihi")
	if err3 != nil {
		fmt.Println(err3)
	} else {
		def, _ := dic.Search("hello")
		fmt.Println(def)
	}

	err4 := dic.Delete("hello")
	if err4 != nil {
		fmt.Println(err4)
	}
	definition2, err5 := dic.Search("hello")
	if err5 != nil {
		fmt.Println(err5)
	} else {
		fmt.Println(definition2)
	}
}
