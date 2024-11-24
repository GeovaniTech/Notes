package to;

import java.io.Serializable;

public class TONote implements Serializable {
	private static final long serialVersionUID = -4808383928943704518L;
	
	private String id;
	private String title;
	private String description;
	
	// Getters and setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
