package com.crud.tasks;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @RequestMapping(method = RequestMethod.GET, path = "add")
    public int add(@RequestParam Integer a, @RequestParam Integer b){
        return a+b;
    }

    @RequestMapping(method = RequestMethod.GET, path = "subtract/{a}/{b}")
    public int subtract(@PathVariable Integer a, @PathVariable Integer b){
        return a-b;
    }

    @RequestMapping(method = RequestMethod.POST, path = "book")
    public Book book(@RequestBody Book book){
        book.setId(123L);

        return book;
    }

    static class Book{
        private Long id;

        private String title;

        private Boolean isBooked;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Boolean getBooked() {
            return isBooked;
        }

        public void setBooked(Boolean booked) {
            isBooked = booked;
        }
    }

}
