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

var baseURL string = "https://syllabus.sfc.keio.ac.jp/courses?button=&locale=en&page="
var secondbaseURL string = "&search%5Bobjective%5D=&search%5Bsemester%5D=&search%5Bsub_semester%5D=&search%5Bsummary%5D=&search%5Bteacher_name%5D=&search%5Btitle%5D=&search%5Byear%5D=2021"

type extractedCourse struct {
	url         string
	title       string
	department  string
	field       string
	unit        string
	professor   string
	classformat string
	period      string
	language    string
}

func main() {
	c := make(chan []extractedCourse)
	var courses []extractedCourse
	for i := 1; i < 57; i++ {
		go getPage(i, c)
	}
	for i := 1; i < 57; i++ {
		extractedCourse := <-c
		courses = append(courses, extractedCourse...)
	}
	writeCourse(courses)
}

func writeCourse(courses []extractedCourse) {
	file, err := os.Create("courses.csv")
	checkerr(err)
	w := csv.NewWriter(file)
	defer w.Flush()
	headers := []string{"URL", "Title", "Department", "Field", "Credit", "Professor", "Class Format", "Period", "Language"}
	wErr := w.Write(headers)
	checkerr(wErr)
	for _, course := range courses {
		courseSlice := []string{"https://syllabus.sfc.keio.ac.jp/" + course.url,
			course.title,
			course.department,
			course.field,
			course.unit,
			course.professor,
			course.classformat,
			course.period,
			course.language}
		cwErr := w.Write(courseSlice)
		checkerr(cwErr)
	}
	fmt.Println("DONE!")
}

func getPage(page int, mainc chan<- []extractedCourse) {
	var courses []extractedCourse
	c := make(chan extractedCourse)
	pageURL := baseURL + strconv.Itoa(page) + secondbaseURL
	res, err := http.Get(pageURL)
	checkerr(err)
	checkcode(res)
	defer res.Body.Close()
	doc, err := goquery.NewDocumentFromReader(res.Body)
	checkerr(err)
	searchEachCourse := doc.Find(".result").Find("li")
	searchEachCourse.Each(func(i int, card *goquery.Selection) {
		go extractCourse(card, c)
	})
	for i := 0; i < searchEachCourse.Length(); i++ {
		course := <-c
		courses = append(courses, course)
	}
	mainc <- courses
}

func extractCourse(card *goquery.Selection, c chan<- extractedCourse) {
	url, _ := card.Find(".detail-btn").Attr("href")
	title := cleanString(card.Find("h2").Text())

	extractDetailofCourse := card.Find("dt")
	//numdd := card.Find("dd").Length()
	var detail [7]string
	//detail := make([]string, numdd)
	extractDetailofCourse.Each(func(i int, detailcard *goquery.Selection) {
		switch cleanString(detailcard.Text()) {
		case "Department Name":
			detail[0] = cleanString(detailcard.Next().Text())
		case "Field (Undergraduate)":
			detail[1] = cleanString(detailcard.Next().Text())
		case "Unit":
			detail[2] = cleanString(detailcard.Next().Text())
		case "Lecture Name":
			detail[3] = cleanString(detailcard.Next().Text())
		case "Class Format":
			detail[4] = cleanString(detailcard.Next().Text())
		case "Day of Week・Period":
			detail[5] = cleanString(detailcard.Next().Text())
		case "Language":
			detail[6] = cleanString(detailcard.Next().Text())
		}
	})
	department := detail[0]
	field := detail[1]
	unit := detail[2]
	professor := detail[3]
	classformat := detail[4]
	period := detail[5]
	language := detail[6]
	c <- extractedCourse{
		url:         url,
		title:       title,
		department:  department,
		field:       field,
		unit:        unit,
		professor:   professor,
		classformat: classformat,
		period:      period,
		language:    language}
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
