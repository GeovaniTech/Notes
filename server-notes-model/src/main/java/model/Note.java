package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Note {
	@Id
	private String id;
	private String description;
	
	// Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
