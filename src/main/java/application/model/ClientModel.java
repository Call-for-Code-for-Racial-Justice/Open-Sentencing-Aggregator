package application.model;

import java.net.MalformedURLException;
import java.net.URL;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;

import static com.cloudant.client.api.query.EmptyExpression.empty;
import static com.cloudant.client.api.query.Expression.eq;
import static com.cloudant.client.api.query.Expression.gt;
import static com.cloudant.client.api.query.Operation.and;

import io.swagger.model.Client;

public class ClientModel {
	private Database db = null;

	public ClientModel(String url, String apiKey, String database) {
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

	public Response save(Client client) {
		Response resp = db.save(client);
		return resp;
	}

	public Response delete(String id) {
		Client client = db.find(Client.class, id);
		Response resp = db.remove(client);
		return resp;
	}

	public Client read(String id) {
		Client client = db.find(Client.class, id);
		return client;
	}

	public QueryResult<Client> getAllClientsOfAttorney(String attorneyId) {
		QueryResult<Client> qr = db.query(new QueryBuilder(eq("attorneyId", attorneyId)).build(), Client.class);
		return qr;
	}
}
