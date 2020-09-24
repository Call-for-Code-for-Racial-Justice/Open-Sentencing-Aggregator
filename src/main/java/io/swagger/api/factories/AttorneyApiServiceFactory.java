package io.swagger.api.factories;

import io.swagger.api.AttorneyApiService;
import io.swagger.api.impl.AttorneyApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-09-24T19:29:11.667Z")
public class AttorneyApiServiceFactory {
    private final static AttorneyApiService service = new AttorneyApiServiceImpl();

    public static AttorneyApiService getAttorneyApi() {
        return service;
    }
}
