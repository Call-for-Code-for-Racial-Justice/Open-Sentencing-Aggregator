package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.Attorney;
import io.swagger.model.AttorneyResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-09-24T19:29:11.667Z")
public abstract class AttorneyApiService {
    public abstract Response addAttorney(Attorney body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getAllAttorneys(SecurityContext securityContext) throws NotFoundException;
    public abstract Response getAttorneyById(String attorneyId,SecurityContext securityContext) throws NotFoundException;
}
