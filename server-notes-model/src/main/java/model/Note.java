package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Note {
	@Id
	private String id;
	private String title;
	private String description;
	
	// Getters and Setters
	public String getTitle() {
		return title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
