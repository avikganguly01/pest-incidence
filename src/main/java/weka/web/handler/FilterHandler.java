package weka.web.handler;

import java.sql.Statement;

import weka.web.data.DatabaseHelper;
import weka.web.data.Filter;

public class FilterHandler {
	
	private DatabaseHelper helper;
	
	public FilterHandler() {
		helper = new DatabaseHelper();
	}

	public Result filter(Filter filter) {
		Result result = new Result();
		Statement deleteStatement = null;
		String deleteQuery = "delete from `training` where ";
		try{
			deleteStatement = helper.createStatement(true);
			deleteQuery += filter.getName() + "=\"" + filter.getCategory() + "\"";
			deleteStatement.executeUpdate(deleteQuery);
		}catch (Exception e) {
			result.addError(e.getMessage());
		}finally {
			helper.closeStatement(deleteStatement);
			helper.closeConnection();
		}
		return result;
	}
}
