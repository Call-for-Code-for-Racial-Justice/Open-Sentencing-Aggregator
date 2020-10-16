package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.CaseApiService;
import io.swagger.api.factories.CaseApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.CaseResponse;
import io.swagger.model.ModelCase;

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

@Path("/case")

@io.swagger.annotations.Api(description = "the case API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2020-10-13T17:17:47.836Z")
public class CaseApi {
	private final CaseApiService delegate;

	public CaseApi(@Context ServletConfig servletContext) {
		CaseApiService delegate = null;

		if (servletContext != null) {
			String implClass = servletContext.getInitParameter("CaseApi.implementation");
			if (implClass != null && !"".equals(implClass.trim())) {
				try {
					delegate = (CaseApiService) Class.forName(implClass).newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		if (delegate == null) {
			delegate = CaseApiServiceFactory.getCaseApi();
		}

		this.delegate = delegate;
	}

	@POST

	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Add a new case to an attorney", notes = "", response = Void.class, tags = {
			"case", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
	public Response addCase(
			@ApiParam(value = "Enter the attorney id for which the case to be added", required = true) @QueryParam("attorneyId") String attorneyId,
			@ApiParam(value = "", required = true) ModelCase body, @Context SecurityContext securityContext)
			throws NotFoundException {
		return delegate.addCase(attorneyId, body, securityContext);
	}

	@DELETE
	@Path("/{attorneyId}/{caseId}")

	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Delete case by ID", notes = "Delete a single case", response = Void.class, tags = {
			"case", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "Case not found", response = Void.class) })
	public Response deleteCaseById(
			@ApiParam(value = "ID of attorney", required = true) @PathParam("attorneyId") String attorneyId,
			@ApiParam(value = "ID of case to delete", required = true) @PathParam("caseId") String caseId,
			@Context SecurityContext securityContext) throws NotFoundException {
		return delegate.deleteCaseById(attorneyId, caseId, securityContext);
	}

	@GET
	@Path("/{attorneyId}/{caseId}")

	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Get all cases for attorney", notes = "Returns a single case", response = CaseResponse.class, tags = {
			"case", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = CaseResponse.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "Case not found", response = Void.class) })
	public Response getCase(
			@ApiParam(value = "ID of attorney", required = true) @PathParam("attorneyId") String attorneyId,
			@ApiParam(value = "ID of case", required = true) @PathParam("caseId") String caseId,
			@Context SecurityContext securityContext) throws NotFoundException {
		return delegate.getCase(attorneyId, caseId, securityContext);
	}

	@GET
	@Path("/{attorneyId}")

	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "Get all cases for attorney", notes = "Returns a single case", response = CaseResponse.class, tags = {
			"case", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = CaseResponse.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "Case not found", response = Void.class) })
	public Response getCaseById(
			@ApiParam(value = "ID of attorney", required = true) @PathParam("attorneyId") String attorneyId,
			@Context SecurityContext securityContext) throws NotFoundException {
		return delegate.getCaseById(attorneyId, securityContext);
	}

	@GET
	@Path("/report/{attorneyId}/{caseId}")

	@io.swagger.annotations.ApiOperation(value = "Get report for the case", notes = "Returns a single case", response = CaseReport.class, tags = {
			"case", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = CaseReport.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "Case not found", response = Void.class) })
	public Response getCaseReport(
			@ApiParam(value = "ID of attorney", required = true) @PathParam("attorneyId") String attorneyId,
			@ApiParam(value = "ID of case", required = true) @PathParam("caseId") String caseId,
			@Context SecurityContext securityContext) throws NotFoundException {
		return delegate.getCaseReport(attorneyId, caseId, securityContext);
	}

}
