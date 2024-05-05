package br.com.notes.api.notes.service;

import java.util.List;

import br.com.notes.api.common.abstracts.AbstractKeep;
import br.com.notes.api.notes.model.Note;
import br.com.notes.api.notes.model.TONoteRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class KeepNoteSbean extends AbstractKeep<Note, TONoteRestModel>{
	private static final long serialVersionUID = 1L;
	
	public KeepNoteSbean() {
		super(Note.class, TONoteRestModel.class);
	} 
	
	@SuppressWarnings("unchecked")
	public List<TONoteRestModel> list()  {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT N FROM Note N ");
		Query query = this.getEntityManager().createQuery(sql.toString(), Note.class);
		query.getResultList();
		
		return this.convertModelResults(query.getResultList());
	}
	
	public void add(List<TONoteRestModel> notes) {
		for(TONoteRestModel note : notes) {
			Note model = this.convertToModel(note);

			Note noteDatabase = this.getEntityManager().find(Note.class, model.getId());
			
			if(noteDatabase == null) {
				this.getEntityManager().persist(model);
			} else {
				this.getEntityManager().merge(model);
			}
		}
	}
	
	public void remove(TONoteRestModel note) {
		Note model = this.convertToModel(note);
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}
}
