package io.swagger.api.factories;

import io.swagger.api.ClientApiService;
import io.swagger.api.impl.ClientApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-12T16:49:15.342+02:00")
public class ClientApiServiceFactory {
    private final static ClientApiService service = new ClientApiServiceImpl();

    public static ClientApiService getClientApi() {
        return service;
    }
}
