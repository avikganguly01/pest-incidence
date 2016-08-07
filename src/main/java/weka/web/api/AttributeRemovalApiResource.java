package weka.web.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import weka.web.data.RawAttribute;
import weka.web.handler.RawAttributeHandler;
import weka.web.handler.Result;

@Path("/attrremoval")
public class AttributeRemovalApiResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAttributeTypes() {
		   RawAttributeHandler handler = new RawAttributeHandler();
		   Result result = handler.retrieveAttributes();
		   ArrayList<RawAttribute> attributes;
		   if(result.isSuccess()) {
			   attributes = handler.getAttributes(false, false);
			   return Response.status(202).entity(attributes).build();
		   }else
			   return Response.status(500).entity("Error : " + result.getErrors().get(0)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removingColumns(ArrayList<RawAttribute> attributes) {
		RawAttributeHandler handler = new RawAttributeHandler();
		Result result = handler.removeAttributes(attributes);
		return Response.status(200).entity("Status : Success").build();
	}

}
