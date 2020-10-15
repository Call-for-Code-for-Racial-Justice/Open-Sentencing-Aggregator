package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.CaseResponse;
import io.swagger.model.ModelCase;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-13T17:17:47.836Z")
public abstract class CaseApiService {
    public abstract Response addCase( @NotNull String attorneyId,ModelCase body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response deleteCaseById(String attorneyId,String caseId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getCase(String attorneyId,String caseId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getCaseById(String attorneyId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getCaseReport(String attorneyId,String caseId,SecurityContext securityContext) throws NotFoundException;
}
