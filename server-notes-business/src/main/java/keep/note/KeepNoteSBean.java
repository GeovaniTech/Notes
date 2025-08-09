package keep.note;

import java.util.StringJoiner;

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
		model.setSecret(this.getSecretFromNote(model.getId()));
		
		this.getEntityManager().persist(model);
		note.setId(model.getId());
	}
	
	@Override
	public void change(TONote note) {
		Note model = this.convertToModel(note);
		model.setSecret(this.getSecretFromNote(model.getId()));
		this.getEntityManager().merge(model);
	}

	@Override
	public TONote findNoteById(String id) {
		return this.convertToDTO(this.getEntityManager().find(Note.class, id));
	}

	@Override
	public String getSecretFromNote(String id) {
		StringJoiner sql = new StringJoiner(BREAK_LINE);
		
		sql.add(" SELECT N.secret FROM ")
			.add(Note.class.getSimpleName()).add(" N ")
			.add(" WHERE N.id = :pIdNote ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), String.class);
		query.setParameter("pIdNote", id);
		
		try {
			return (String) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void createSecret(String secret, String noteId) {
		StringJoiner sql = new StringJoiner(BREAK_LINE);
		
		sql.add(" UPDATE ")
			.add(Note.class.getSimpleName()).add(" N ")
			.add(" SET N.secret = :pSecret ")
			.add(" WHERE N.id = :pIdNote ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pIdNote", noteId);
		query.setParameter("pSecret", secret);
		
		query.executeUpdate();
	}
}
