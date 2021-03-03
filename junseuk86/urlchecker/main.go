package main

import (
	"errors"
	"fmt"
	"net/http"
)

type result struct {
	url    string
	status string
}

var errorRequestFail = errors.New("Request Fail")

func hitUrls(url string, c chan<- result) {
	resp, err := http.Get(url)
	status := "OK"
	if err != nil || resp.StatusCode >= 400 {
		status = "FAILED"
	}
	c <- result{url: url, status: status}
}

func main() {
	results := make(map[string]string)
	urls := []string{
		"https://www.airbnb.com",
		"https://www.google.com",
		"https://www.naver.com",
	}
	c := make(chan result)
	for _, url := range urls {
		go hitUrls(url, c)
	}
	for i := 0; i < len(urls); i++ {
		result := <-c
		results[result.url] = result.status
	}
	for url, status := range results {
		fmt.Println(url, status)
	}
}
