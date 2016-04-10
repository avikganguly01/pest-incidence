package weka.web.handler;

import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CheckPerformer {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckPerformer.class);
	
	private int noOfColumns;
	private LinkedHashMap<String, String> metadata;
	
	public CheckPerformer(int noOfColumns) {
		metadata = new LinkedHashMap<String,String>();
		this.noOfColumns = noOfColumns;
	}
	
	public void checkHeaderLengths(Row row)throws Exception {
		for (int i=0; i < noOfColumns; i++) {
			String columnLabel = row.getCell(i).getStringCellValue();
			if(columnLabel.length() > 20)
				throw new Exception("Header length too big.");
		}
	}
	
	public Sheet filterOutRowsWithNull(Sheet sheet) {
		int noOfRows = getNumberOfRows(sheet);
		outerloop:
		for(int rowNo = 1; rowNo < noOfRows; rowNo++) {
			Row row = sheet.getRow(rowNo);
			Cell firstCell = row.getCell(0); Cell secondCell = row.getCell(1);
			if((firstCell == null || firstCell.getCellType() == Cell.CELL_TYPE_BLANK) && (secondCell == null || secondCell.getCellType() == Cell.CELL_TYPE_BLANK))
				break;
			for(int cellNo = 0; cellNo < noOfColumns; cellNo++) {
				Cell cell = row.getCell(cellNo);
				if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					logger.info(rowNo +"-"+cellNo);
					sheet.removeRow(row);
					int rowIndex = row.getRowNum();
			        int lastRowNum = sheet.getLastRowNum();
			        if (rowIndex >= 0 && rowIndex < lastRowNum)
			            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
			        rowNo--;
			        rowIndex--;
			        if(rowIndex==lastRowNum){
			            Row removingRow=sheet.getRow(rowIndex);
			            if(removingRow!=null){
			                sheet.removeRow(removingRow);
			                break outerloop;
			            }
			        }
			        break;
				}
			}
		}
		
		return sheet;
	}
	
	public LinkedHashMap<String,String> checkColumnHomogeneity(Sheet sheet)throws Exception {
		saveActualDataType(sheet);
		int noOfRows = getNumberOfRows(sheet);
		int colIndex = 0;
		for(String columnLabel : metadata.keySet()) {
			String dataType = metadata.get(columnLabel);
			int columnType = -1;
			if(dataType.equals("double"))
				columnType = 0;
			else if(dataType.equals("string"))
				columnType = 1;
			for(int rowNo = 1; rowNo < noOfRows; rowNo++) {
				Row row = sheet.getRow(rowNo);
				if(row.getCell(colIndex).getCellType() == 2)
					throw new Exception("Column " + columnLabel + "contains formula cells. Copy the entire column, select the column, press Shift+F10 and press v to paste values only.");
				if(row.getCell(colIndex).getCellType() != columnType)
					throw new Exception("Column " + columnLabel + " has non-uniform data-type. Check for values such as X or - present in that column.");
				}
			colIndex++;
		}
		return metadata;
	}
	
	private void saveActualDataType(Sheet sheet)throws Exception {
		Row firstRow = sheet.getRow(0);
		Row secondRow = sheet.getRow(1);
		for (int i=0; i < noOfColumns; i++) {
			String columnLabel = firstRow.getCell(i).getStringCellValue().replaceAll("[^a-zA-Z0-9\\s]", "_").replace(" ", "_");
			int columnType = secondRow.getCell(i).getCellType();
			if(columnType == 0)
			   metadata.put(columnLabel, "double");
			else if(columnType == 1)
			   metadata.put(columnLabel, "string");
			else if(columnType == 2)
				throw new Exception("Column " + (i + 1) + "contains formula cells. Copy the entire column, select the column, press Shift+F10 and press v to paste values only.");
		}
	}
	
	private Integer getNumberOfRows(Sheet sheet) {
        Integer noOfEntries = 1;
        // getLastRowNum and getPhysicalNumberOfRows showing false values
        // sometimes
           while (sheet.getRow(noOfEntries) !=null && sheet.getRow(noOfEntries).getCell(0) != null) {
               noOfEntries++;
           }
        	
        return noOfEntries;
    }

}
