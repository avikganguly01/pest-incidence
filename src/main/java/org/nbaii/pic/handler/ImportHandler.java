package org.nbaii.pic.handler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.nbaii.pic.data.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImportHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportHandler.class);
	private final Sheet sheet;
	private LinkedHashMap<String, String> metadata;
	private DatabaseHelper helper;
	
	public ImportHandler(Workbook workbook) {
		Sheet firstSheet = workbook.getSheetAt(0);
		this.sheet = firstSheet;
		metadata = new LinkedHashMap<String,String>();
		helper = new DatabaseHelper();
	}
	
	public Result preprocess() {
		Result result = new Result();
		try{
			setMetaData();
		
		} catch(Exception e) {
			result.addError(e.getMessage());
		}
		return result;
	}
	
	private void setMetaData() {
		Row firstRow = sheet.getRow(0);
		int noOfColumns = firstRow.getLastCellNum();
		for (int i=0; i < noOfColumns; i++) {
			String columnLabel = firstRow.getCell(i).getStringCellValue();
			metadata.put(columnLabel.replaceAll("[. ]","_"), "double");
		}
		
		for (int j=0; j< sheet.getLastRowNum() + 1; j++) {
	        Row row = sheet.getRow(j);
	    	String columnLabel = "";
	    	if(row.getRowNum() == 0)
	    		columnLabel = row.getCell();
	        Cell cell = row.getCell(0); 
	    }
	}
	
	public Result save() {
		Result result = new Result();
		Statement statement = null;
		try{
		statement = helper.createStatement();
		dropExistingAndCreateNew(statement);
		
		}catch (Exception e) {
			result.addError(e.getMessage());
		}finally {
			helper.closeStatementAndConnection(statement);
		}
		return result;
	}
	
	private void dropExistingAndCreateNew(Statement statement) throws SQLException {
		String dropTable = "DROP TABLE IF EXISTS training ";
		String createTableStatement = "CREATE TABLE IF NOT EXISTS training(";
		String createTableLastPart = ")";
		for(String columnName : metadata) {
			if(!columnName.equals(metadata.get(metadata.size()-1))) {
			    createTableStatement += columnName + " double NOT NULL,";
			}
			else {
				createTableStatement += columnName + " double NOT NULL";
			}
		}
		createTableStatement += createTableLastPart;
		logger.info(createTableStatement);
		statement.executeUpdate(dropTable);
		statement.execute(createTableStatement);
	}
	
	
}
