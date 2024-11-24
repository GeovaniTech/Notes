package keep.note;

import java.util.List;

import jakarta.ejb.Local;
import to.note.TONote;

@Local
public interface IKeepNoteSbean {
	public void save(TONote note);
	public void change(TONote note);
	public List<TONote> getNotes();
	public TONote findNoteById(String id);
}
