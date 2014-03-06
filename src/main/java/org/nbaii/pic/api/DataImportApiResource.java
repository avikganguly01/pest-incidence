package org.nbaii.pic.api;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.nbaii.pic.data.Track;
import org.nbaii.pic.handler.ImportHandler;
import org.nbaii.pic.handler.Result;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


@Path("/import")
public class DataImportApiResource {
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws IOException,SQLException,ClassNotFoundException {
		
		   Workbook workbook = new HSSFWorkbook(fileInputStream);
		   
           ImportHandler handler = new ImportHandler(workbook);
		   Result result = handler.preprocess();
		   if(result.isSuccess()) {
			   result = handler.save();
		   }
           return Response.status(200).entity("{Status : Success}").build();
 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {
		 
		Track track = new Track();
		track.setTitle("Enter Sandman");
		track.setSinger("Metallica");
 
		return track;
	}

}
