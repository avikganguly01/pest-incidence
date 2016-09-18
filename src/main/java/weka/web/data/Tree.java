package weka.web.data;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class Tree {
	
	private String tree;
	
	@JsonCreator
	public Tree(@JsonProperty("tree")  String tree) {
		this.tree = tree;
	}
	
	public String getTree() {
		return this.tree;
	}

}
