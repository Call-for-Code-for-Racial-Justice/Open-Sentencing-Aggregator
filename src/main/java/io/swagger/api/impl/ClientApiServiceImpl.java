package io.swagger.api.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import application.model.ClientModel;
import com.google.gson.Gson;
import com.ibm.cloud.cloudant.v1.model.DocumentResult;
import com.ibm.cloud.cloudant.v1.model.FindResult;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.ClientApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.Client;
import io.swagger.model.ClientResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-08-12T16:49:15.342+02:00")
public class ClientApiServiceImpl extends ClientApiService {
	private ClientModel am = null;

	public ClientApiServiceImpl() {
		String serviceName = "AGGREGATOR_DB";
		am = new ClientModel(serviceName, "outcarcerate-client");
	}

	@Override
	public Response addClient(Client body, SecurityContext securityContext) throws NotFoundException {
		System.out.println("addClient");
		DocumentResult resp = am.save(body);
		if (resp.getError() == null) {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
		} else {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
		}
	}

	@Override
	public Response deleteClientById(String clientId, SecurityContext securityContext) throws NotFoundException {
		DocumentResult resp = am.delete(clientId);
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
			FindResult findResponse = am.getAllClientsOfAttorney(attorneyId);

			Gson gson = new Gson();
			List<Client> clients = findResponse.getDocs()
					.stream()
					.map(doc->gson.fromJson(doc.toString(), Client.class))
					.collect(Collectors.toList());

			ClientResponse cr = new ClientResponse();
			cr.setCode(200);
			cr.setSuccess(true);
			cr.setClients(clientId != null ? clients.stream().filter(i -> i.getId().equalsIgnoreCase(clientId))
					.collect(Collectors.toList()) : clients);
			cr.setWarning(findResponse.getWarning());
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
