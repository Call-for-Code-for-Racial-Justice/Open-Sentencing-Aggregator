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

import io.swagger.model.Attorney;

public class AttorneyModel {
  private CloudantClient client = null;
  private Database db = null;

  public AttorneyModel(String url, String apiKey, String database) {
    System.out.println(url);
    CloudantClient client = null;
    try {
      client = ClientBuilder
          .url(new URL(url))
          .iamApiKey(apiKey).build();
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

  public Attorney read(String id) {
    Attorney attorney = db.find(Attorney.class, id);
    return attorney;
  }

  public QueryResult<Attorney> getAll() {
    QueryResult<Attorney> qr = db.query(new QueryBuilder(gt("_id", 0)).fields("name").build(), Attorney.class);
    return qr;
  }
}

/*
 * // Get a Database instance to interact with, but don't create it if it
 * doesn't already exist Database db = client.database("example_db", false);
 * 
 * // A Java type that can be serialized to JSON public class ExampleDocument {
 * private String _id = "example_id"; private String _rev = null; private
 * boolean isExample;
 * 
 * public ExampleDocument(boolean isExample) { this.isExample = isExample; }
 * 
 * public String toString() { return "{ id: " + _id + ",\nrev: " + _rev +
 * ",\nisExample: " + isExample + "\n}"; } }
 * 
 * // Create an ExampleDocument and save it in the database db.save(new
 * ExampleDocument(true)); System.out.println("You have inserted the document");
 * 
 * // Get an ExampleDocument out of the database and deserialize the JSON into a
 * Java type ExampleDocument doc = db.find(ExampleDocument.class,"example_id");
 * System.out.println(doc);
 */