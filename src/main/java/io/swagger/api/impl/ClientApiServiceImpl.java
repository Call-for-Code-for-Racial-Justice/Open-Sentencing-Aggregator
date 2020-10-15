package io.swagger.api.impl;

import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.cloudant.client.api.query.QueryResult;

import application.model.ClientModel;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.ClientApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.Client;
import io.swagger.model.ClientResponse;

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
		if (resp.getError() == null) {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
		} else {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
		}
	}

	@Override
	public Response deleteClientById(String clientId, SecurityContext securityContext) throws NotFoundException {
		com.cloudant.client.api.model.Response resp = am.delete(clientId);
		if (resp.getError() == null) {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
		} else {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
		}

	}

	@Override
	public Response getClients(String clientId, String attorneyId, SecurityContext securityContext)
			throws NotFoundException {
		// When attorneyId passed
		if (attorneyId != null && !"".equalsIgnoreCase(attorneyId)) {
			QueryResult<Client> qr = am.getAllClientsOfAttorney(attorneyId);
			ClientResponse cr = new ClientResponse();
			cr.setCode(200);
			cr.setSuccess(true);
			cr.setClients(clientId != null ? qr.getDocs().stream().filter(i -> i.getId().equalsIgnoreCase(clientId))
					.collect(Collectors.toList()) : qr.getDocs());
			cr.setWarning(qr.getWarning());
			return Response.ok().entity(cr).build();
		}

		if (clientId != null && !"".equalsIgnoreCase(clientId)) {
			Client client = am.read(clientId);
			System.out.println("Client: " + client.toString());
			return Response.ok().entity(client).build();
		}

		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "No documents found")).build();
	}
}
