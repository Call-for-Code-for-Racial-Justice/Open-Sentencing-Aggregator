package io.swagger.api.impl;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import application.model.AttorneyModel;
import com.ibm.cloud.cloudant.v1.model.DocumentResult;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.CaseApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.CaseResponse;
import io.swagger.model.ModelCase;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-13T17:17:47.836Z")
public class CaseApiServiceImpl extends CaseApiService {

    private AttorneyModel am = null;

	public CaseApiServiceImpl() {
		String serviceName = "AGGREGATOR_DB";
		am = new AttorneyModel(serviceName, "outcarcerate-attorney");
	}

	@Override
	public Response addCase(@NotNull String attorneyId, ModelCase body, SecurityContext securityContext)
			throws NotFoundException {
		System.out.println("addCase");

        String id = body.getId();
        if (id == null || id.trim().isEmpty()) {
            body.setId(am.getUuid());
        }

        DocumentResult resp = am.addCaseToAttorney(attorneyId, body);
		if (resp.getError() == null) {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
		} else {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
		}
	}

	@Override
	public Response deleteCaseById(String attorneyId, String caseId, SecurityContext securityContext)
			throws NotFoundException {
		DocumentResult resp = am.deleteCaseFromAttorney(attorneyId, caseId);
		if (resp.getError() == null) {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, resp.getId())).build();
		} else {
			return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, resp.getError())).build();
		}
	}

    @Override
    public Response getCase(String attorneyId, String caseId, SecurityContext securityContext)
            throws NotFoundException {
        System.out.println("getCase");
        List<ModelCase> qr = am.getCasesByIdForAttorney(attorneyId, caseId);
        CaseResponse ar = new CaseResponse();
        ar.setCode(200);
        ar.setSuccess(true);
        ar.setClients(qr);
        return Response.ok().entity(ar).build();
    }

	@Override
	public Response getCaseById(String attorneyId, SecurityContext securityContext) throws NotFoundException {
		System.out.println("getCaseById");
		List<ModelCase> qr = am.getCasesForAttorney(attorneyId);
		CaseResponse ar = new CaseResponse();
		ar.setCode(200);
		ar.setSuccess(true);
		ar.setClients(qr);
		return Response.ok().entity(ar).build();

    }

    @Override
    public Response getCaseReport(String attorneyId, String caseId, SecurityContext securityContext) throws NotFoundException {
        // TODO Implementation to be added based on report-generator outcome
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
