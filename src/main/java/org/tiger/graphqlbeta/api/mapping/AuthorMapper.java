package org.tiger.graphqlbeta.api.mapping;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.Translation;
import org.tiger.graphqlbeta.model.Auther;

public class AuthorMapper {
    private Translation i;

    public AuthorMapper(Translation i) {
        this.i = i;
    }

    public TypeRuntimeWiring.Builder authorFetcher(TypeRuntimeWiring.Builder builder) {
        return builder
                .dataFetcher(i.id(), e -> auther(e).getId())
                .dataFetcher(i.firstName(), e -> auther(e).getFirstName())
                .dataFetcher(i.lastName(), e -> auther(e).getLastName())
                ;
    }

    private static Auther auther(DataFetchingEnvironment e) { return ((Auther)e.getSource()); }
}
