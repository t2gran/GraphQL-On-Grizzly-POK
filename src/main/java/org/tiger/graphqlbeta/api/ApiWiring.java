package org.tiger.graphqlbeta.api;

import graphql.schema.idl.RuntimeWiring;
import org.tiger.graphqlbeta.api.mapping.AuthorMapper;
import org.tiger.graphqlbeta.api.mapping.BookMapper;
import org.tiger.graphqlbeta.db.Repository;

public class ApiWiring {
    private static Translation DEFAULT = new Translation();
    private static Translation NO = new TranslationNo();

    private Translation i;
    private Repository data;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public ApiWiring(boolean useNo, Repository data) {
        this.i = useNo ? NO : DEFAULT;
        this.data = data;
        this.authorMapper = new AuthorMapper(i);
        this.bookMapper = new BookMapper(i);
    }

    public RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(i.queryType(), builder -> builder
                        .dataFetcher(i.allBooks(), it -> data.getBooks())
                        .dataFetcher(i.bookById(), it -> data.getBook(it.getArgument(i.id())))
                )
                .type(i.bookType(), bookMapper::bookFetcher)
                .type(i.authorType(), authorMapper::authorFetcher)
                .build();
    }
}
