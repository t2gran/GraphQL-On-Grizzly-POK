package org.tiger.graphqlbeta.api.mapping;

import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.ModelTerminologyMapping;
import org.tiger.graphqlbeta.db.Repository;

import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.ALL_BOOKS;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.BOOK_BY_ID;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.ID;

public class QueryMapper extends AbstractBinder<Object> {
    private final Repository data;

    public QueryMapper(ModelTerminologyMapping.Translation translation, Repository data) {
        super(translation);
        this.data = data;
    }

    public TypeRuntimeWiring.Builder queryFetcher(TypeRuntimeWiring.Builder builder) {
        useBuilder(builder);

        bind(ALL_BOOKS, data::getBooks);
        bind(BOOK_BY_ID, data::getBook, ID);

        return builder;
    }
}
