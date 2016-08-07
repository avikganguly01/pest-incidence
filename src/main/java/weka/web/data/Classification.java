package weka.web.data;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class Classification {

	private Double minRange;
	private Double maxRange;
	private String assignedClass;
	
	@JsonCreator
	public Classification(@JsonProperty("minRange") Double minRange,
			@JsonProperty("maxRange") Double maxRange,
			@JsonProperty("assignedClass") String assignedClass) {
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.assignedClass = assignedClass;
	}
	
	public Double getMinRange() {
		return minRange;
	}

	public void setMinRange(Double minRange) {
		this.minRange = minRange;
	}

	public Double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}

	public String getAssignedClass() {
		return assignedClass;
	}

	public void setAssignedClass(String assignedClass) {
		this.assignedClass = assignedClass;
	}

		
}
