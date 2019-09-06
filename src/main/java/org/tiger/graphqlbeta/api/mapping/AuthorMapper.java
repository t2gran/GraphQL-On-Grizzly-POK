package org.tiger.graphqlbeta.api.mapping;

import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.ModelTerminologyMapping;
import org.tiger.graphqlbeta.model.Auther;


import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.*;

public class AuthorMapper extends AbstractBinder<Auther> {

    public AuthorMapper(ModelTerminologyMapping.Translation translation) {
        super(translation);
    }

    public TypeRuntimeWiring.Builder authorFetcher(TypeRuntimeWiring.Builder builder) {
        useBuilder(builder);

        bind(ID, Auther::getId);
        bind(FIRST_NAME, Auther::getFirstName);
        bind(LAST_NAME, Auther::getLastName);

        return builder;
    }
}
