package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.Attorney;
import io.swagger.model.AttorneyResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import application.model.AttorneyModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.cloudant.client.api.query.QueryResult;

import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-11T08:44:06.780+02:00")
public class AttorneyApiServiceImpl extends AttorneyApiService {
    private AttorneyModel am = null;

    public AttorneyApiServiceImpl() {
        String databaseUrl = System.getenv("AGGREGATOR_DB_URL");
        String databaseIamKey = System.getenv("AGGREGATOR_DB_IAM_KEY");
        am = new AttorneyModel(databaseUrl, databaseIamKey, "outcarcerate-attorney");
    }

    @Override
    public Response addAttorney(Attorney body, SecurityContext securityContext) throws NotFoundException {
        System.out.println("addAttorney");
        com.cloudant.client.api.model.Response resp = am.save(body);
        if(resp.getError() == null) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
        }
    }
    @Override
    public Response getAllAttorneys(SecurityContext securityContext) throws NotFoundException {
        System.out.println("getAllAttorneys");
        QueryResult<Attorney> qr = am.getAll();
        AttorneyResponse ar = new AttorneyResponse();
        ar.setCode(200);
        ar.setSuccess(true);
        ar.setAttorney(qr.getDocs());
        ar.setWarning(qr.getWarning());

        return Response.ok().entity(ar).build();
        //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getAttorneyById(String attorneyId, SecurityContext securityContext) throws NotFoundException {
        System.out.println("getAttorneyById");
        System.out.println("Getting attorney: " + attorneyId);
        Attorney attorney = am.read(attorneyId);
        System.out.println("Attorney: " + attorney.toString());
        return Response.ok().entity(attorney).build();
    }
}
