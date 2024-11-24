package managedBean.home;

import java.util.List;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.note.IKeepNoteSbean;
import to.note.TONote;
import utils.MessageUtil;
import utils.StringUtil;

@Named(MBHome.MANAGED_BEAN_NAME)	
@ViewScoped
public class MBHome extends AbstractMBean {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBHome";
	
	private String noteId;
	private TONote note;
	
	private List<TONote> notes;

	@EJB
	private IKeepNoteSbean noteSbean;
	
	/**
	 * Will try to find the note based on the user url, if does not exits, a new note will be created
	 */
	public void loadNoteFromURL() {
		if (StringUtil.isNotNull(this.getNoteId())) {
			TONote note = this.getNoteSbean().findNoteById(this.getNoteId());
			
			if (note == null) {
				this.initNewNote();
				return;
			}
			
			this.setNote(note);
		}
	}
	
	/**
	 * Save the note actual note clicking in the button or automatically after
	 * each 30 seconds by polling.
	 */
	public void saveNote() {
		try {
			if (this.getNote() != null && (StringUtil.isNotNull(this.getNote().getTitle())
					|| StringUtil.isNotNull(this.getNote().getDescription()))) {
				
				this.getNoteSbean().change(this.getNote());

				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_saving"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void initNewNote() {
		this.setNote(new TONote());
		this.getNote().setId(this.getNoteId());
		this.getNoteSbean().save(this.getNote());
	}
	
	public void cancelNote() {
		this.setNote(null);
	}

	public TONote getNote() {
		return note;
	}

	public void setNote(TONote note) {
		this.note = note;
	}

	public List<TONote> getNotes() {
		return notes;
	}

	public void setNotes(List<TONote> notes) {
		this.notes = notes;
	}

	public IKeepNoteSbean getNoteSbean() {
		return noteSbean;
	}

	public void setNoteSbean(IKeepNoteSbean noteSbean) {
		this.noteSbean = noteSbean;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
}
