package weka.web.handler;

import java.util.ArrayList;
import java.util.Enumeration;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.web.data.DatabaseHelper;

public class DecisionTreeHandler {
	
private DatabaseHelper helper;
	
	public DecisionTreeHandler() {
		helper = new DatabaseHelper();
	}

	public String getTree() {
		Result result = new Result();
		InstanceQuery query;
		J48 tree = new J48();
		try {
			query = new InstanceQuery();
			query.setUsername("root");
			 query.setPassword("mysql");
			 query.setQuery("select * from training");
			 Instances data = query.retrieveInstances();
			 Enumeration<Attribute> attrs = data.enumerateAttributes();
			 ArrayList<Integer> includeIndices = new ArrayList<> ();
			 Attribute classAttribute = null;
			 while(attrs.hasMoreElements()) {
				 classAttribute = attrs.nextElement();
				 includeIndices.add(classAttribute.index());
			 }
			 includeIndices.remove(0);
			 Integer[] inclIndices = new Integer[includeIndices.size()];
			 inclIndices = includeIndices.toArray(inclIndices);
			 int[] incIndices = new int[includeIndices.size()];
			 for(int i=0; i<inclIndices.length; i++) {
				 incIndices[i] = inclIndices[i];
			 }
			 Remove remove = new Remove();
			 remove.setAttributeIndicesArray(incIndices);
			 remove.setInvertSelection(true);
			 remove.setInputFormat(data);
			 Instances filteredData = Filter.useFilter(data, remove);
			 attrs = filteredData.enumerateAttributes();
			 while(attrs.hasMoreElements()) {
				 classAttribute = attrs.nextElement();
			 }
			 filteredData.setClass(classAttribute);
			 tree.setBinarySplits(true);
			 tree.setReducedErrorPruning(true);
			 tree.setSubtreeRaising(true);
			 tree.setUseLaplace(true);
			 tree.setUseMDLcorrection(true);
			 tree.buildClassifier(filteredData);
			 System.out.println(tree.graph());
			 System.out.println(tree.toString());
			 System.out.println();
			 return tree.graph();
//			 final ClassifierTree gr = tree.getGraph();
//			 getJson(gr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
