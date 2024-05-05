package br.com.notes.api.notes.model;

import abstracts.AbstractTOObject;

public class TONoteRestModel extends AbstractTOObject {
	private static final long serialVersionUID = -961019840624961196L;
	
	private String title;
	private String description;

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
