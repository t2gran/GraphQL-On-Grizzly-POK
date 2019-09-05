package org.tiger.graphqlbeta.model;

public class Book {
        private String id;
        private String name;
        private int pages = 0;
        private String isbn;
        private Auther author;

    public Book(String id, String name, int pages, String isbn, Auther author) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.isbn = isbn;
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

    public String getIsbn() {
        return isbn;
    }

    public Auther getAuthor() {
        return author;
    }
}
