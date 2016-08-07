package weka.web.handler;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weka.web.data.Classification;
import weka.web.data.DatabaseHelper;
import weka.web.data.RawAttribute;

public class ClassificationHandler {
	
private DatabaseHelper helper;
	
	public ClassificationHandler() {
		helper = new DatabaseHelper();
	}

	public Result assignClasses(List<Classification> classifiers) {
		Result result = new Result();
		Statement alterStatement = null;
		String alterQuery = "alter table `training` add column `classifier` VARCHAR(50) DEFAULT NULL";
		String updateQuery = null;
		RawAttributeHandler handler = new RawAttributeHandler();
		handler.retrieveAttributes();
		final ArrayList<RawAttribute> attrs = handler.getAttributes(false, true);
		try{
			alterStatement = helper.createStatement(true);
			alterStatement.executeUpdate(alterQuery);
			for(Classification classifier : classifiers) {
				updateQuery = "update `training` set classifier = '" + classifier.getAssignedClass() + "' where " + attrs.get(0).getAttributeName()
						+ "> " + classifier.getMinRange() + " and " + attrs.get(0).getAttributeName() + " <= " + classifier.getMaxRange();
				alterStatement.executeUpdate(updateQuery);
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.addError(e.getMessage());
		}finally {
			helper.closeStatement(alterStatement);
			helper.closeConnection();
		}
		return result;
		
	}

}
