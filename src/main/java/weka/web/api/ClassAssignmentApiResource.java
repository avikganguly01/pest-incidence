package weka.web.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import weka.web.data.Classification;
import weka.web.handler.ClassificationHandler;
import weka.web.handler.Result;

@Path("/algorithm")
public class ClassAssignmentApiResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response assigningClasses(ArrayList<Classification> classifiers) {
		ClassificationHandler handler = new ClassificationHandler();
		Result result = handler.assignClasses(classifiers);
		return Response.status(200).entity("Status : Success").build();
	}

}
