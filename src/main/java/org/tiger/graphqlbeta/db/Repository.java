package org.tiger.graphqlbeta.db;

import org.tiger.graphqlbeta.model.Auther;
import org.tiger.graphqlbeta.model.Book;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Book> books = new ArrayList<>();

    {
        Auther a = new Auther("1", "Arthur", "Doyle");
        Auther b = new Auther("2", "Benjamin", "Foile");

        books.add(new Book("1", "Mysteries I", 2, "22-33", a));
        books.add(new Book("2", "Mysteries II", 23, "44-33", a));
        books.add(new Book("3", "Mysteries III", 234, "55-33", b));
        books.add(new Book("4", "Mysteries IV", 2345, "23-67", b));
        books.add(new Book("5", "Mysteries V", 23456, "52-73", b));
    }

    public List<Book> getBooks() {
        return books;
    }
    public Book getBook(String id) {
        return books.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }
}
