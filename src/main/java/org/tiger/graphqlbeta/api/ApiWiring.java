package org.tiger.graphqlbeta.api;

import graphql.schema.idl.RuntimeWiring;
import org.tiger.graphqlbeta.api.binding.AuthorBinder;
import org.tiger.graphqlbeta.api.binding.BookBinder;
import org.tiger.graphqlbeta.api.binding.QueryBinder;
import org.tiger.graphqlbeta.db.Repository;

import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.AUTHOR_TYPE;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.BOOK_TYPE;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.QUERY_TYPE;

public class ApiWiring {
    private ModelTerminologyMapping.Translation translation;

    private final AuthorBinder authorBinder;
    private final BookBinder bookBinder;
    private final QueryBinder queryBinder;

    public ApiWiring(boolean useNo, Repository data) {
        this.translation = ModelTerminologyMapping.translation(useNo);
        this.authorBinder = new AuthorBinder(translation);
        this.bookBinder = new BookBinder(translation);
        this.queryBinder = new QueryBinder(translation, data);
    }

    public RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(translation.id(QUERY_TYPE), queryBinder::queryFetcher)
                .type(translation.id(BOOK_TYPE), bookBinder::bookFetcher)
                .type(translation.id(AUTHOR_TYPE), authorBinder::authorFetcher)
                .build();
    }
}
