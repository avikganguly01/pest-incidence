package weka.web.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import weka.web.handler.DecisionTreeHandler;
import weka.web.handler.Result;

@Path("/decisiontree")
public class DecisionTreeApiResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDecisionTree() {
		   DecisionTreeHandler handler = new DecisionTreeHandler();
		   Result result = handler.getTree();
		   return Response.status(202).entity(result).build();
	}

}
