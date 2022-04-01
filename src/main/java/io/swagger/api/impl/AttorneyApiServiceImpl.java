package io.swagger.api.impl;

import com.google.gson.Gson;
import com.ibm.cloud.cloudant.v1.model.DocumentResult;
import com.ibm.cloud.cloudant.v1.model.FindResult;
import io.swagger.api.*;
import io.swagger.model.*;
import io.swagger.model.Attorney;
import io.swagger.model.AttorneyResponse;
import java.util.*;
import io.swagger.api.NotFoundException;
import java.util.stream.Collectors;
import application.model.AttorneyModel;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-11T08:44:06.780+02:00")
public class AttorneyApiServiceImpl extends AttorneyApiService {
    private AttorneyModel am = null;

    public AttorneyApiServiceImpl() {
        String serviceName = "AGGREGATOR_DB";
        am = new AttorneyModel(serviceName, "outcarcerate-attorney");
    }

    @Override
    public Response addAttorney(Attorney body, SecurityContext securityContext) throws NotFoundException {
        System.out.println("addAttorney");
        DocumentResult resp = am.save(body);
        if(resp.getError() == null) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
        }
    }

    @Override
    public Response getAllAttorneys(SecurityContext securityContext) throws NotFoundException {
        System.out.println("getAllAttorneys");
        FindResult findResponse = am.getAll();
        Gson gson = new Gson();
        List<Attorney> attorneys = findResponse.getDocs()
                .stream()
                .map(doc->gson.fromJson(doc.toString(), Attorney.class))
                .collect(Collectors.toList());

        AttorneyResponse ar = new AttorneyResponse();
        ar.setCode(200);
        ar.setSuccess(true);
        ar.setAttorney(attorneys);
        ar.setWarning(findResponse.getWarning());

        return Response.ok().entity(ar).build();
    }

    @Override
    public Response getAttorneyById(String attorneyId, SecurityContext securityContext) throws NotFoundException {
        System.out.println("getAttorneyById");
        System.out.println("Getting attorney: " + attorneyId);
        Attorney attorney = am.getAttorney(attorneyId);
        System.out.println("Attorney: " + attorney.toString());
        return Response.ok().entity(attorney).build();
    }

    @Override
    public Response deleteAttorneyById(String attorneyId, SecurityContext securityContext) throws NotFoundException {
        DocumentResult resp = am.delete(attorneyId);
        if(resp.getError() == null) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
        }
        
    }
}
