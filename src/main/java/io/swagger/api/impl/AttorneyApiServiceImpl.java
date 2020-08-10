package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.Attorney;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import application.model.AttorneyModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-09T16:09:16.040+02:00")
public class AttorneyApiServiceImpl extends AttorneyApiService {
    @ConfigProperty(name="AGGREGATOR_DB_URL")
    public String databaseUrl;
    @ConfigProperty(name="AGGREGATOR_DB_IAM_KEY")
    public String databaseIamKey;

    @Override
    public Response addAttorney(Attorney body, SecurityContext securityContext) throws NotFoundException {
        System.out.println(body.toString());
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response deleteAttorney(String attorneyId, SecurityContext securityContext) throws NotFoundException {
        System.out.println("Deleting attorney: " + attorneyId);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getAttorneyById(String attorneyId, SecurityContext securityContext) throws NotFoundException {
        System.out.println("Getting attorney: " + attorneyId);
        AttorneyModel am = new AttorneyModel(databaseUrl, databaseIamKey, "outcarcerate");

        Attorney attorney = am.read(attorneyId);
        System.out.println("Attorney: " + attorney.toString());
        return Response.ok().entity(attorney).build();
    }
}
