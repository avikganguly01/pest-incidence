package weka.web.handler;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import weka.web.data.DatabaseHelper;
import weka.web.data.RawAttribute;


public class RawAttributeHandler {

//	private static final Logger logger = LoggerFactory.getLogger(RawAttributeHandler.class);
	private DatabaseHelper helper;
	private ResultSet columns = null;
	
	public RawAttributeHandler() {
		helper = new DatabaseHelper();
	}
	
	public Result retrieveAttributes() {
		Result result = new Result();
		try{
		    DatabaseMetaData meta = helper.getMetaData();
		    columns = meta.getColumns(null, null, "training", null);
		    helper.closeConnection();
		} catch(Exception e) {
			result.addError(e.getMessage());
		}
		return result;
	}
	
	public ArrayList<RawAttribute> getAttributes(boolean onlyNominal) {
		ArrayList<RawAttribute> attributes = new ArrayList<RawAttribute> ();
		ArrayList<String> options = new ArrayList<String>();
		RawAttribute attribute;
		try {
			while (columns.next()) {
				  attribute = null;
				  String columnType = columns.getString("TYPE_NAME");
				  if(columnType.equals("DOUBLE"))
					  columnType = "Numeric";
				  else if(columnType.equals("VARCHAR"))
					  columnType = "Nominal";
				  if(onlyNominal && columnType.equals("Numeric"))
					  continue;
				  String columnName = columns.getString("COLUMN_NAME");
				  options = getCategoriesForAttribute(columnName);
				  attribute = new RawAttribute(columnName, columnType, options);
				  attributes.add(attribute);
			}
			helper.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attributes;
	}
	
	private ArrayList<String> getCategoriesForAttribute(String columnName) {
		ArrayList<String> categories = new ArrayList<String>();
		Statement statement = helper.createStatement(true);
		String selectDistinct = "select distinct(" + columnName +") from `training`";
		ResultSet options;
		try {
			options = statement.executeQuery(selectDistinct);
			while(options.next()) {
				categories.add(options.getString(columnName));
			}
			helper.closeStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
}
