package org.tiger.graphqlbeta.api.binding;

import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.ModelTerminologyMapping;
import org.tiger.graphqlbeta.model.Auther;

import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.FIRST_NAME;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.ID;
import static org.tiger.graphqlbeta.api.ModelTerminologyMapping.LAST_NAME;

public class AuthorBinder extends AbstractBinder<Auther> {

    public AuthorBinder(ModelTerminologyMapping.Translation translation) {
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
