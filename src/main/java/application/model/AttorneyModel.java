package application.model;

import static com.cloudant.client.api.query.Expression.ne;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;

import io.swagger.model.Attorney;
import io.swagger.model.CaseReport;
import io.swagger.model.ModelCase;

public class AttorneyModel {
//  private CloudantClient client = null;
	private Database db = null;

	public AttorneyModel(String url, String apiKey, String database) {
		System.out.println(url);
		CloudantClient client = null;
		try {
			client = ClientBuilder.url(new URL(url)).iamApiKey(apiKey).build();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Server Version: " + client.serverVersion());
		db = client.database(database, false);
	}

	public Response save(Attorney attorney) {
		Response resp = db.save(attorney);
		return resp;
	}
	
	public Response delete(String id) {
		Attorney attorney = db.find(Attorney.class, id);
		Response resp = db.remove(attorney);
		return resp;
	}

	public Attorney read(String id) {
		Attorney attorney = db.find(Attorney.class, id);
		return attorney;
	}

	public Response addCaseToAttorney(String id, ModelCase modelCase) {
		Attorney attorney = db.find(Attorney.class, id);

		if (attorney.getCases() == null)
			attorney.setCases(new ArrayList<ModelCase>());

		attorney.getCases().add(modelCase);
		Response response = db.update(attorney);
		return response;
	}

	public Response deleteCaseFromAttorney(String id, String caseId) {
		Attorney attorney = db.find(Attorney.class, id);
		attorney.getCases().removeIf(i -> i.getId().equalsIgnoreCase(caseId));
		Response response = db.update(attorney);
		return response;
	}

	public List<ModelCase> getCasesForAttorney(String id) {
		Attorney attorney = db.find(Attorney.class, id);
		return attorney.getCases();
	}
	
	public List<ModelCase> getCasesByIdForAttorney(String attroneyId, String caseId) {
		Attorney attorney = db.find(Attorney.class, attroneyId);
		return attorney.getCases().stream().filter(i -> i.getId()
				.equalsIgnoreCase(caseId)).collect(Collectors.toList());
	}

	public QueryResult<Attorney> getAll() {
		QueryResult<Attorney> qr = db.query(new QueryBuilder(ne("_id", "")).fields("username", "_id", "_rev").build(),
				Attorney.class);
		return qr;
	}
	
	
}
