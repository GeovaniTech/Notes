package to;

import java.io.Serializable;
import java.util.Date;

public class TONote implements Serializable {
	private static final long serialVersionUID = -4808383928943704518L;
	
	private String id;
	private String description;
	private Date creationDate;
	
	// Getters and setters
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
