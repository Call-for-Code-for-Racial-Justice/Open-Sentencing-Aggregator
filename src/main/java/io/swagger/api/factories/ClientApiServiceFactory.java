package io.swagger.api.factories;

import io.swagger.api.ClientApiService;
import io.swagger.api.impl.ClientApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-09-24T19:29:11.667Z")
public class ClientApiServiceFactory {
    private final static ClientApiService service = new ClientApiServiceImpl();

    public static ClientApiService getClientApi() {
        return service;
    }
}
