package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.AttorneyApiService;
import io.swagger.api.factories.AttorneyApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Attorney;
import io.swagger.model.AttorneyResponse;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/attorney")


@io.swagger.annotations.Api(description = "the attorney API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-13T17:17:47.836Z")
public class AttorneyApi  {
   private final AttorneyApiService delegate;

   public AttorneyApi(@Context ServletConfig servletContext) {
      AttorneyApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("AttorneyApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (AttorneyApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = AttorneyApiServiceFactory.getAttorneyApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a new attorney", notes = "", response = Void.class, tags={ "attorney", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response addAttorney(@ApiParam(value = "" ,required=true) Attorney body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.addAttorney(body,securityContext);
    }
    @DELETE
    @Path("/{attorneyId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete attorney by ID", notes = "Delete a single attorney", response = Void.class, tags={ "attorney", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Attorney not found", response = Void.class) })
    public Response deleteAttorneyById(@ApiParam(value = "ID of attorney to delete",required=true) @PathParam("attorneyId") String attorneyId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteAttorneyById(attorneyId,securityContext);
    }
    @GET
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get a list of attorneys", notes = "Returns a all attorneys", response = AttorneyResponse.class, tags={ "attorney", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = AttorneyResponse.class) })
    public Response getAllAttorneys(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAllAttorneys(securityContext);
    }
    @GET
    @Path("/{attorneyId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find attorney by ID", notes = "Returns a single attorney", response = AttorneyResponse.class, tags={ "attorney", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = AttorneyResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Attorney not found", response = Void.class) })
    public Response getAttorneyById(@ApiParam(value = "ID of attorney to return",required=true) @PathParam("attorneyId") String attorneyId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAttorneyById(attorneyId,securityContext);
    }
}
