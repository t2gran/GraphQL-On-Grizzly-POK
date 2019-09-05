package org.tiger.graphqlbeta.api;

public class Book {
        private String id;
        private String name;
        private int pages = 0;
        private Auther author;

    public Book(String id, String name, int pages, Auther author) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.author = author;
    }

    public Book(String name, int pages, Auther author) {
        this.name = name;
        this.pages = pages;
        this.author = author;
    }

    public Book() { }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPages() {
        return pages;
    }

    public Auther getAuthor() {
        return author;
    }
}
