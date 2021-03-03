package main

import (
	"encoding/csv"
	"fmt"
	"log"
	"net/http"
	"os"
	"strconv"
	"strings"

	"github.com/PuerkitoBio/goquery"
)

var baseURL string = "https://kr.indeed.com/jobs?q=python&l=%EA%B2%BD%EA%B8%B0%EB%8F%84+%EC%84%B1%EB%82%A8"

type extractedJob struct {
	id       string
	title    string
	rating   string
	location string
	summary  string
}

func main() {
	c := make(chan []extractedJob)
	var jobs []extractedJob
	totalpage := getPages()
	for i := 0; i < totalpage; i++ {
		go getPage(i, c)
	}
	for i := 0; i < totalpage; i++ {
		extractedJob := <-c
		jobs = append(jobs, extractedJob...)
	}
	writeJob(jobs)
}

func writeJob(jobs []extractedJob) {
	file, err := os.Create("jobs.csv")
	checkerr(err)
	w := csv.NewWriter(file)
	defer w.Flush()
	headers := []string{"ID", "Title", "Rating", "Location", "Summary"}
	wErr := w.Write(headers)
	checkerr(wErr)
	for _, job := range jobs {
		jobSlice := []string{"https://kr.indeed.com/viewjob?jk=" + job.id, job.title, job.rating, job.location, job.summary}
		jwErr := w.Write(jobSlice)
		checkerr(jwErr)
	}
	fmt.Println("DONE!")
}

func getPage(page int, mainc chan<- []extractedJob) {
	var jobs []extractedJob
	c := make(chan extractedJob)
	pageURL := baseURL + "&start=" + strconv.Itoa(page*10)
	res, err := http.Get(pageURL)
	checkerr(err)
	checkcode(res)
	defer res.Body.Close()
	doc, err := goquery.NewDocumentFromReader(res.Body)
	checkerr(err)
	searchCards := doc.Find(".jobsearch-SerpJobCard")
	searchCards.Each(func(i int, card *goquery.Selection) {
		go extractJob(card, c)
	})
	for i := 0; i < searchCards.Length(); i++ {
		job := <-c
		jobs = append(jobs, job)
	}
	mainc <- jobs
}

func extractJob(card *goquery.Selection, c chan<- extractedJob) {
	id, _ := card.Attr("data-jk")
	title := cleanString(card.Find(".title>a").Text())
	location := cleanString(card.Find(".sjcl").Text())
	rating := cleanString(card.Find(".ratingsContent").Text())
	summary := cleanString(card.Find(".summary").Text())
	c <- extractedJob{id: id, title: title, location: location, summary: summary, rating: rating}
}

func getPages() int {
	numpages := 0
	res, err := http.Get(baseURL)
	checkerr(err)
	checkcode(res)
	defer res.Body.Close()
	doc, err := goquery.NewDocumentFromReader(res.Body)
	checkerr(err)
	doc.Find(".pagination").Each(func(i int, s *goquery.Selection) {
		numpages = s.Find("a").Length()
	})
	return numpages
}

func checkerr(err error) {
	if err != nil {
		log.Fatalln(err)
	}
}

func checkcode(res *http.Response) {
	if res.StatusCode != 200 {
		log.Fatalln(res.StatusCode)
	}
}

func cleanString(text string) string {
	return strings.Join(strings.Fields(strings.TrimSpace(text)), " ")
}
