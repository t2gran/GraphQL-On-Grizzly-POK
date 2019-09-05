package org.tiger.graphqlbeta.api;

class TranslationNo extends Translation {

    // Common/Shared fields
    @Override public String id() { return "id"; }
    @Override public String name() { return "navn"; }

    // Query
    @Override public String queryType() { return "Query"; }
    @Override public String allBooks() { return "boker"; }
    @Override public String bookById() { return "bok"; }

    // Book
    @Override public String bookType() { return "Bok"; }
    @Override public String pagesCount() { return "sider"; }
    @Override public String author() { return "forfatter"; }

    // Author
    @Override public String authorType() { return "Forfatter"; }
    @Override public String firstName() { return "fornavn"; }
    @Override public String lastName() { return "etternavn"; }
}
