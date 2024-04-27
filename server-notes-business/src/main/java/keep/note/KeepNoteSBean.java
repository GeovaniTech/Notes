package keep.note;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Note;
import to.note.TONote;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<TONote> getNotes() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT N FROM ").append(Note.class.getSimpleName()).append(" N ");
		sql.append(" WHERE N.creationUser = :email ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", getClientSession().getEmail());
		
		return this.convertModelResults(query.getResultList());
	}
}
