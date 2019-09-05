package org.tiger.graphqlbeta.api;

public class Translation {

    Translation() {}

    // Common/Shared fields
    public String id() { return "id"; }
    public String name() { return "name"; }

    // Query
    public String queryType() { return "Query"; }
    public String allBooks() { return "allBooks"; }
    public String bookById() { return "bookById"; }

    // Book
    public String bookType() { return "Book"; }
    public String pagesCount() { return "pagesCount"; }
    public String author() { return "author"; }

    // Author
    public String authorType() { return "Author"; }
    public String firstName() { return "firstName"; }
    public String lastName() { return "lastName"; }
}
