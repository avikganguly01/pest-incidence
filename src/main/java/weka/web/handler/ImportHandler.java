package weka.web.handler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.web.data.DatabaseHelper;

public class ImportHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportHandler.class);
	
	private Sheet sheet;
	private LinkedHashMap<String, String> metadata;
	private DatabaseHelper helper;
	private int noOfCols;
	
	public ImportHandler(Workbook workbook) {
		Sheet firstSheet = workbook.getSheetAt(0);
		this.sheet = firstSheet;
		metadata = new LinkedHashMap<String,String>();
		helper = new DatabaseHelper();
	}
	
	public Result preprocess() {
		Result result = new Result();
		try{
			noOfCols = 0;
			Row headers = sheet.getRow(0);
			for(int i=0; i < headers.getLastCellNum(); i++) {
				if(headers.getCell(i) != null &&
						headers.getCell(i).getCellType() != Cell.CELL_TYPE_BLANK)
					noOfCols++;
			}
			CheckPerformer checks = new CheckPerformer(noOfCols);
			
			checks.checkHeaderLengths(headers);
			logger.info("A-"+sheet.getLastRowNum());
			sheet = checks.filterOutRowsWithNull(sheet);
			logger.info("B-"+sheet.getLastRowNum());
			metadata = checks.checkColumnHomogeneity(sheet);
		} catch(Exception e) {
			e.printStackTrace();
			result.addError(e.getMessage());
		}
		return result;
	}
	
	public Result save()throws SQLException {
		Result result = new Result();
		Statement createStatement = null;
		Statement insertStatements = null;
		try{
		createStatement = helper.createStatement(true);
		dropExistingAndCreateNew(createStatement);
		insertStatements = helper.createStatement(false);
		addAllEntries(insertStatements);
		}catch (Exception e) {
			result.addError(e.getMessage());
		}finally {
			helper.closeStatement(createStatement);
			helper.closeStatement(insertStatements);
			helper.closeConnection();
		}
		return result;
	}
	
	private void addAllEntries(Statement statement)throws SQLException {
		String entry = "";
		logger.info("C-"+sheet.getLastRowNum());
		for(int rowNo = 1; rowNo <= sheet.getLastRowNum(); rowNo++) {
			entry = new String();
			entry = "insert into `training` values(";
			for(int cellNo = 0; cellNo < noOfCols; cellNo++) {
				Cell cell = sheet.getRow(rowNo).getCell(cellNo);
				if(cell.getCellType() == 0)
					entry += cell.getNumericCellValue() + ",";
				else if(cell.getCellType() == 1)
					entry += "\"" + cell.getStringCellValue() + "\",";
			}
			entry+= ")";
			entry = entry.replace(",)", ")");
			statement.addBatch(entry);
		}
		statement.executeBatch();
		helper.commit();
	}
	
	private void dropExistingAndCreateNew(Statement statement) throws SQLException {
		String dropTable = "DROP TABLE IF EXISTS training";
		String createTableStatement = "CREATE TABLE IF NOT EXISTS `training`(";
		String createTableLastPart = ")";
		for(String columnName : metadata.keySet()) {
			if(metadata.get(columnName).equals("double")) {
			    createTableStatement += columnName + " double NOT NULL,";
			}
			else {
				createTableStatement += columnName + " VARCHAR(50) NOT NULL,";
			}
		}
		createTableStatement += createTableLastPart;
		createTableStatement = createTableStatement.replace(",)", ")");
		statement.executeUpdate(dropTable);
		statement.execute(createTableStatement);
	}
	
	
}
