package com.example.qlbh.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class BookList {
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
