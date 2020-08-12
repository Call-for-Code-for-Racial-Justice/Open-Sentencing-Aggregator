package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.Client;
import io.swagger.model.ClientFilter;
import io.swagger.model.ClientResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import application.model.ClientModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-12T16:49:15.342+02:00")
public class ClientApiServiceImpl extends ClientApiService {
    private ClientModel am = null;

    public ClientApiServiceImpl() {
        String databaseUrl = System.getenv("AGGREGATOR_DB_URL");
        String databaseIamKey = System.getenv("AGGREGATOR_DB_IAM_KEY");
        am = new ClientModel(databaseUrl, databaseIamKey, "outcarcerate-client");
    }

    @Override
    public Response addClient(Client body, SecurityContext securityContext) throws NotFoundException {
        System.out.println("addClient");
        com.cloudant.client.api.model.Response resp = am.save(body);
        if(resp.getError() == null) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
        }
    }
    @Override
    public Response getClientById(String clientId, SecurityContext securityContext) throws NotFoundException {
        System.out.println("getAllAttorneys");
        QueryResult<Client> qr = am.getAll();
        ClientResponse cr = new ClientResponse();
        cr.setCode(200);
        cr.setSuccess(true);
        cr.setAttorney(qr.getDocs());
        cr.setWarning(qr.getWarning());

        return Response.ok().entity(cr).build();
    }
    @Override
    public Response getClients(ClientFilter body, SecurityContext securityContext) throws NotFoundException {
        System.out.println("getClientsFiltered");
        System.out.println("Attorney Id: " + body.getAttorneyId());
        Client client = am.read(body.getAttorneyId());
        System.out.println("Client: " + client.toString());
        return Response.ok().entity(client).build();
    }
}
