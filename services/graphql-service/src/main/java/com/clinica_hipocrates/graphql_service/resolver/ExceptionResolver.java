package com.clinica_hipocrates.graphql_service.resolver;

import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @GraphQlExceptionHandler
    protected GraphQLError handleNotFound(ResourceNotFoundException ex) {
        return GraphQLError.newError().message(ex.getMessage()).errorType(ErrorType.NOT_FOUND).build();
    }
}
