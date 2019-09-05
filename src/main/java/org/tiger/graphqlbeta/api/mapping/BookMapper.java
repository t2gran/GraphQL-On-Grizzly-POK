package org.tiger.graphqlbeta.api.mapping;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.Translation;
import org.tiger.graphqlbeta.model.Auther;
import org.tiger.graphqlbeta.model.Book;

import java.util.function.UnaryOperator;

public class BookMapper {
    private Translation i;

    public BookMapper(Translation i) {
        this.i = i;
    }

    public TypeRuntimeWiring.Builder bookFetcher(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher(i.id(), e -> book(e).getId())
                .dataFetcher(i.name(), e -> book(e).getName())
                .dataFetcher(i.pagesCount(), e -> book(e).getPages())
                .dataFetcher(i.author(), e -> book(e).getAuthor())
                ;
    }

    private static Book book(DataFetchingEnvironment e) { return ((Book)e.getSource()); }
}
