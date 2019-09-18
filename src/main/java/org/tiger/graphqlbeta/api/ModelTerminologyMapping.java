package org.tiger.graphqlbeta.api;

public enum ModelTerminologyMapping {
    // Common/Shared fields
    ID("id", "id"),
    NAME("name", "navn"),

    // Query
    QUERY_TYPE("Query", "Query"),
    ALL_BOOKS("allBooks", "boker"),
    BOOK_BY_ID("bookById", "bok"),

    // Book
    BOOK_TYPE("Book", "Bok"),
    PAGES_COUNT("pagesCount", "sider"),
    ISBN("<NOT USED>", "ISBN"),
    AUTHOR("author", "forfatter"),

    // Author
    AUTHOR_TYPE("Author", "Forfatter"),
    FIRST_NAME("firstName", "etternavn"),
    LAST_NAME("lastName", "fornavn")
    ;

    // English translation (default)
    public final String en;

    // Norwegian translation
    public final String no;

    ModelTerminologyMapping(String en, String no) {
        this.en = en;
        this.no = no;
    }

    public static Translation translation(boolean useNo) {
        return useNo ? new NoTranslation() : new DefaultTranslation();
    }

    public interface Translation {
        String id(ModelTerminologyMapping value);
        boolean onlyNo();
    }

    private static class DefaultTranslation implements Translation {
        @Override public String id(ModelTerminologyMapping value) {
            return value.en;
        }
        @Override public boolean onlyNo() { return false; }
    }

    private static class NoTranslation implements Translation {
        @Override public String id(ModelTerminologyMapping value) {
            return value.no;
        }
        @Override public boolean onlyNo() { return true; }
    }
}
