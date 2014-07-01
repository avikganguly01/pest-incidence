package weka.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import weka.web.handler.ImportHandler;
import weka.web.handler.Result;

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
		   if(result.isSuccess())
		   	   return Response.status(200).entity("Status : Success").build();
		   else
			   return Response.status(500).entity("Error : " + result.getErrors().get(0)).build();
	}
	
	
	@GET
	@Produces("application/vnd.ms-excel")
	public Response getFile() {
		 
		final InputStream is = this.getClass().getClassLoader().getResourceAsStream("Soyabean.xls");
		StreamingOutput stream = new StreamingOutput() {
			@Override public void write(OutputStream output) throws IOException {
				try {  
					IOUtils.copy(is, output);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					is.close();
					output.close();
				}
			  } 
		};
		return Response.ok(stream, "application/vnd.ms-excel")
			.header("content-disposition", "attachment; filename = Soyabean.xls").build();
	}

}
