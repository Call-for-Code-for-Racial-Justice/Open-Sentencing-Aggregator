package application.rest.v1;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;


@Path("v1/cases")
public class Cases {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCaseById(@PathParam("id") String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        list.add("getCaseById");
        return Response.ok(list.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCasesList() {
        return Response.ok("getCasesList").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCase() {
        return Response.ok("Case created").build();
    }
}
