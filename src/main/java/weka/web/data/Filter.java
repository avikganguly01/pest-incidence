package weka.web.data;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;


public class Filter {
	
	private String name;
	private String category;
	
	@JsonCreator
	public Filter(@JsonProperty("name") String name,@JsonProperty("category") String category) {
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

}
