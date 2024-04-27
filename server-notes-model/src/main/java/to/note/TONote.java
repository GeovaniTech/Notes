package to.note;

import abstracts.AbstractTOObject;

public class TONote extends AbstractTOObject {
	private static final long serialVersionUID = -4808383928943704518L;
	
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
}
