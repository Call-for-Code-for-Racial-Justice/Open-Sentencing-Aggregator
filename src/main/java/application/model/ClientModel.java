package application.model;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.QueryResult;
import io.swagger.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static com.cloudant.client.api.query.Expression.eq;

public class ClientModel {
    private Database db;
    private static final Logger LOG = LoggerFactory.getLogger(ClientModel.class);

    public ClientModel(String url, String apiKey, String database) {
        LOG.debug(url);
        CloudantClient client = null;
        try {
            client = ClientBuilder.url(new URL(url)).iamApiKey(apiKey).build();
        } catch (MalformedURLException e) {
            LOG.error("Malformed url: {}", url, e);
        }

        LOG.debug("Server Version: {}", client.serverVersion());
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
