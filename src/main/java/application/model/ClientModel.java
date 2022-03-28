package application.model;

import java.util.Collections;
import java.util.Map;
import com.google.gson.Gson;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.*;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import io.swagger.model.Client;

public class ClientModel {
	private String database = null;
	private Cloudant service = null;
	private ModelHelper modelHelper = null;

	public ClientModel(String serviceName, String database) {
		this.database = database;
		modelHelper = new ModelHelper();
		try {
			service = Cloudant.newInstance(serviceName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DocumentResult save(Client client) {
		Gson gson = new Gson();
		Document clientDocument = new Document();
		clientDocument.setProperties(gson.fromJson(gson.toJson(client), Map.class));
		PostDocumentOptions createDocumentOptions = modelHelper.getPostDocumentOptions(clientDocument, database);
		DocumentResult resp = service
				.postDocument(createDocumentOptions)
				.execute()
				.getResult();

		return resp;
	}

	public DocumentResult delete(String id) {
		DocumentResult deleteDocumentResponse = null;
		try {
			// Set the options to get the document out of the database if it exists
			GetDocumentOptions documentInfoOptions = modelHelper.getGetDocumentOptions(id,database);
			// Try to get the document if it previously existed in the database
			Document document = service
					.getDocument(documentInfoOptions)
					.execute()
					.getResult();

			// Delete the document from the database
			DeleteDocumentOptions deleteDocumentOptions =
					modelHelper.getDeleteDocumentOptions(id, document.getRev(), database);

			deleteDocumentResponse = service
					.deleteDocument(deleteDocumentOptions)
					.execute()
					.getResult();
			if (deleteDocumentResponse.isOk()) {
				System.out.println("You have deleted the document.");
			}
		} catch (NotFoundException nfe) {
			System.out.println("Cannot delete because document was not found " + nfe);
		}
		return deleteDocumentResponse;
	}

	public Client read(String id) {
		Gson gson = new Gson();
		GetDocumentOptions getDocOptions = modelHelper.getGetDocumentOptions(id, database);
		Document clientDocument = service
				.getDocument(getDocOptions)
				.execute()
				.getResult();
		Client client = clientDocument != null ? gson.fromJson(clientDocument.toString(),Client.class): null;

		return client;
	}

	public FindResult getAllClientsOfAttorney(String attorneyId) {
		Map<String, Object> selector = Collections.singletonMap(
				"_id", Collections.singletonMap("$eq", attorneyId));

		PostFindOptions findOptions = modelHelper.getFindOptions(selector,database);

		FindResult response =
				service.postFind(findOptions).execute()
						.getResult();
		System.out.println(response);

		return response;
	}

}
