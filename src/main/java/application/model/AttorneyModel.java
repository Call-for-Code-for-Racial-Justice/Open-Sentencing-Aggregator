package application.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.cloudant.client.api.*;
import com.cloudant.client.api.model.Response;

import io.swagger.model.Attorney;

public class AttorneyModel {
  private CloudantClient client = null;
  private Database db = null;

  public AttorneyModel(String url, String apiKey, String database) {
    CloudantClient client = null;
    try {
      client = ClientBuilder
          .url(new URL("https://72bb7f9c-7ef9-473d-9370-1e0e1cb6b0af-bluemix.cloudantnosqldb.appdomain.cloud"))
          .iamApiKey("pRKSiKC7rdWejvsqpZ8Tiwo6H1ClhaLKAhYxuEq1kxc4").build();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    System.out.println("Server Version: " + client.serverVersion());
    db = client.database(database, false);
  }

  public boolean save(Attorney attorney) {
    Response resp = db.save(attorney);
    return resp.getError() == null;
  }

  public Attorney read(String id) {
    Attorney attorney = db.find(Attorney.class, id);
    return attorney;
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