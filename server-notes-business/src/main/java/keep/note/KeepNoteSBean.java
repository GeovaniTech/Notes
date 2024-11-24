package keep.note;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Note;
import to.TONote;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepNoteSBean extends AbstractKeep<Note, TONote> implements IKeepNoteSbean, IKeepNoteRemoteSBean {
	public KeepNoteSBean() {
		super(Note.class, TONote.class);
	}
		
	@Override
	public void save(TONote note) {
		Note model = this.convertToModel(note);
		
		this.getEntityManager().persist(model);
		note.setId(model.getId());
	}
	
	@Override
	public void change(TONote note) {
		Note model = this.convertToModel(note);
		this.getEntityManager().merge(model);
	}

	@Override
	public TONote findNoteById(String id) {
		return this.convertToDTO(this.getEntityManager().find(Note.class, id));
	}
}
