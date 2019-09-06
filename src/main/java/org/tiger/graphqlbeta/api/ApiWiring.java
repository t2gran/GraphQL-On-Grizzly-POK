package org.tiger.graphqlbeta.api;

import graphql.schema.idl.RuntimeWiring;
import org.tiger.graphqlbeta.api.mapping.AuthorMapper;
import org.tiger.graphqlbeta.api.mapping.BookMapper;
import org.tiger.graphqlbeta.api.mapping.QueryMapper;
import org.tiger.graphqlbeta.db.Repository;

import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.AUTHOR_TYPE;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.BOOK_TYPE;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.QUERY_TYPE;

public class ApiWiring {
    private ModelTerminologyMapping.Translation translation;

    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final QueryMapper queryMapper;

    public ApiWiring(boolean useNo, Repository data) {
        this.translation = ModelTerminologyMapping.translation(useNo);
        this.authorMapper = new AuthorMapper(translation);
        this.bookMapper = new BookMapper(translation);
        this.queryMapper = new QueryMapper(translation, data);
    }

    public RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(translation.id(QUERY_TYPE), queryMapper::queryFetcher)
                .type(translation.id(BOOK_TYPE), bookMapper::bookFetcher)
                .type(translation.id(AUTHOR_TYPE), authorMapper::authorFetcher)
                .build();
    }
}
