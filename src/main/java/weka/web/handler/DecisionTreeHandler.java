package weka.web.handler;

import java.util.Enumeration;

import weka.classifiers.trees.j48.ClassifierTree;
import weka.core.Attribute;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.web.data.DatabaseHelper;
import weka.web.data.MyJ48;

public class DecisionTreeHandler {
	
private DatabaseHelper helper;
	
	public DecisionTreeHandler() {
		helper = new DatabaseHelper();
	}

	public Result getTree() {
		Result result = new Result();
		InstanceQuery query;
		try {
			query = new InstanceQuery();
			query.setUsername("root");
			 query.setPassword("mysql");
			 query.setQuery("select * from training");
			 Instances data = query.retrieveInstances();
			 final Enumeration<Attribute> attrs = data.enumerateAttributes();
			 Attribute attr = null;
			 while(attrs.hasMoreElements()) {
				 attr = attrs.nextElement();
			 }
			 data.setClass(attr);
			 MyJ48 tree = new MyJ48();
			 tree.buildClassifier(data);
			 System.out.println(tree.toString());
			 System.out.println();
//			 final ClassifierTree gr = tree.getGraph();
//			 getJson(gr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
