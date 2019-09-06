package org.tiger.graphqlbeta.api.mapping;

import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.ModelTerminologyMapping;
import org.tiger.graphqlbeta.model.Book;

import java.util.function.Function;

import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.AUTHOR;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.ID;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.ISBN;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.NAME;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.PAGES_COUNT;

public class BookMapper extends AbstractBinder<Book> {
    public BookMapper(ModelTerminologyMapping.Translation translation) {
        super(translation);
    }

    public TypeRuntimeWiring.Builder bookFetcher(TypeRuntimeWiring.Builder builder) {
        useBuilder(builder);

        bind(ID, Book::getId);
        bind(NAME, Book::getName);
        bind(PAGES_COUNT, Book::getPages);
        bind(AUTHOR, Book::getAuthor);

         if(translation.onlyNo()) {
             bind(ISBN.no, Book::getIsbn);
         }
         return builder;
    }
}
