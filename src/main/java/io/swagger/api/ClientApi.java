package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.ClientApiService;
import io.swagger.api.factories.ClientApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Client;
import io.swagger.model.ClientFilter;
import io.swagger.model.ClientResponse;

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

@Path("/client")


@io.swagger.annotations.Api(description = "the client API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-09-24T19:29:11.667Z")
public class ClientApi  {
   private final ClientApiService delegate;

   public ClientApi(@Context ServletConfig servletContext) {
      ClientApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("ClientApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (ClientApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = ClientApiServiceFactory.getClientApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a new client", notes = "", response = Void.class, tags={ "client", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    public Response addClient(@ApiParam(value = "" ,required=true) Client body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.addClient(body,securityContext);
    }
    @GET
    @Path("/{clientId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find client by ID", notes = "Returns a single client", response = ClientResponse.class, tags={ "client", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = ClientResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Client not found", response = Void.class) })
    public Response getClientById(@ApiParam(value = "ID of client to return",required=true) @PathParam("clientId") String clientId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getClientById(clientId,securityContext);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get a list of clients for an attorney", notes = "Returns all clients of an attorney", response = ClientResponse.class, tags={ "client", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = ClientResponse.class) })
    public Response getClients(@ApiParam(value = "" ,required=true) ClientFilter body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getClients(body,securityContext);
    }
}
