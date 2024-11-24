package keep.note;

import jakarta.ejb.Local;
import to.TONote;

@Local
public interface IKeepNoteSbean {
	public void save(TONote note);
	public void change(TONote note);
	public TONote findNoteById(String id);
}
