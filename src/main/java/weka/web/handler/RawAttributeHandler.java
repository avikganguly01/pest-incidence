package weka.web.handler;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public ArrayList<RawAttribute> getAttributes(boolean onlyNominal, boolean expectingFirstColumn) {
		ArrayList<RawAttribute> attributes = new ArrayList<RawAttribute> ();
		ArrayList<String> options = null;
		RawAttribute attribute;
		try {
			int i = 0;
			while (columns.next()) {
				  if(i == 0) {
					  i++;
					  if(!expectingFirstColumn)
						  continue;
				  }
				  attribute = null;
				  String columnType = columns.getString("TYPE_NAME");
				  String columnName = columns.getString("COLUMN_NAME");
				  if(columnType.equals("DOUBLE")) {
					  columnType = "Numeric";
					  options = new ArrayList<String>();
				  } else if(columnType.equals("VARCHAR")) {
					  columnType = "Nominal";
					  options = getCategoriesForAttribute(columnName);
				  }
				  if(onlyNominal && columnType.equals("Numeric"))
					  continue;
				  attribute = new RawAttribute(columnName, columnType, options);
				  attributes.add(attribute);
			}
			helper.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attributes;
	}
	
	public Result removeAttributes(final List<RawAttribute> attributes) {
		Result result = new Result();
		Statement alterStatement = null;
		try {
			alterStatement = helper.createStatement(true);
			StringBuffer removeStatement = new StringBuffer("alter table training ");
			for(RawAttribute attr : attributes) {
				removeStatement.append(" drop column " + attr.getAttributeName() + ",");
			}
			removeStatement.append(";");
			final String removalStatement = removeStatement.toString().replace(",;", ";");
			alterStatement.execute(removalStatement);
		} catch (Exception e) {
			result.addError(e.getMessage());
		} finally {
			helper.closeStatement(alterStatement);
			helper.closeConnection();
		}
		return result;
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
