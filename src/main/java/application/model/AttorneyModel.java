package application.model;

import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.*;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.google.common.collect.Iterables;
import io.swagger.model.Attorney;
import io.swagger.model.ModelCase;
import java.util.ArrayList;
import java.util.List;

public class AttorneyModel {
	private String database = null;
	private Cloudant service = null;
	private ModelHelper modelHelper = null;

	public AttorneyModel(String serviceName, String database) {
		this.database = database;
		this.modelHelper = new ModelHelper();
		try {
			service = Cloudant.newInstance(serviceName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// New Constructor added to run Unit tests
	public AttorneyModel(Cloudant service, ModelHelper modelHelper) {
		this.service = service;
		this.modelHelper = modelHelper;
	}

	public DocumentResult save(Attorney attorney) {
		Gson gson = new Gson();
		DocumentResult resp = null;
		if(attorney != null) {
			Document attorneyDocument = new Document();
			attorneyDocument.setProperties(gson.fromJson(gson.toJson(attorney), Map.class));
			PostDocumentOptions createDocumentOptions = modelHelper.getPostDocumentOptions(attorneyDocument, database);
			resp = service
					.postDocument(createDocumentOptions)
					.execute()
					.getResult();
		}
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

	public Attorney getAttorney(String id) {
		Gson gson = new Gson();
		GetDocumentOptions getDocOptions = modelHelper.getGetDocumentOptions(id, database);
		Document attorneyDocument = service
				.getDocument(getDocOptions)
				.execute()
				.getResult();
		Attorney attorney = attorneyDocument != null ? gson.fromJson(attorneyDocument.toString(),Attorney.class): null;

		return attorney;
	}

	public String getUuid() {
		return Iterables.getOnlyElement(service.getUuids().execute().getResult().getUuids());
	}

	public DocumentResult addCaseToAttorney(String id, ModelCase modelCase) {
		Attorney attorney = getAttorney(id);
		if (attorney.getCases() == null)
			attorney.setCases(new ArrayList<>());

		attorney.getCases().add(modelCase);
		DocumentResult response = save(attorney);
		return response;
	}

	public DocumentResult deleteCaseFromAttorney(String id, String caseId) {
		Attorney attorney = getAttorney(id);
		attorney.getCases().removeIf(i -> i.getId().equalsIgnoreCase(caseId));
		DocumentResult response = save(attorney);
		return response;
	}

	public List<ModelCase> getCasesForAttorney(String id) {
		Attorney attorney = getAttorney(id);
		return attorney.getCases();
	}
	
	public List<ModelCase> getCasesByIdForAttorney(String attroneyId, String caseId) {
		Attorney attorney = getAttorney(attroneyId);
		return attorney.getCases().stream().filter(i -> i.getId()
				.equalsIgnoreCase(caseId)).collect(Collectors.toList());
	}

	public FindResult getAll() {
		Map<String, Object> selector = Collections.singletonMap(
				"_id", Collections.singletonMap("$ne", ""));
		PostFindOptions findOptions = modelHelper.getFindOptions(selector, database);
		FindResult response =
				service.postFind(findOptions).execute()
						.getResult();

		System.out.println(response);

		return response;
	}
}
