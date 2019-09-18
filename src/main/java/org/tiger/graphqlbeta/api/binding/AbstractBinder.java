package org.tiger.graphqlbeta.api.binding;

import graphql.schema.idl.TypeRuntimeWiring;
import org.tiger.graphqlbeta.api.ModelTerminologyMapping;

import java.util.function.Function;
import java.util.function.Supplier;

abstract class AbstractBinder<T> {
    final ModelTerminologyMapping.Translation translation;
    private TypeRuntimeWiring.Builder builder;

    AbstractBinder(ModelTerminologyMapping.Translation translation) {
        this.translation = translation;
    }

    void useBuilder(TypeRuntimeWiring.Builder builder) {
        this.builder = builder;
    }


    void bind(ModelTerminologyMapping schemaMapping, Function<T, ?> getFunction) {
        bind(translation.id(schemaMapping), getFunction);
    }

    void bind(String schemaID, Function<T, ?> getFunction) {
        builder.dataFetcher(schemaID, e -> getFunction.apply(e.getSource()));
    }

    void bind(ModelTerminologyMapping identifier, Supplier<?> f) {
        builder.dataFetcher(translation.id(identifier), e -> f.get());
    }

    void bind(ModelTerminologyMapping identifier, Function<String, ?> f, ModelTerminologyMapping arg1) {
        builder.dataFetcher(translation.id(identifier), e -> f.apply(e.getArgument(translation.id(arg1))));
    }
}
