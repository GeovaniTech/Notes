package managedBean;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.note.IKeepNoteSbean;
import to.TONote;
import utils.MessageUtil;
import utils.StringUtil;

@Named(MBNote.MANAGED_BEAN_NAME)	
@ViewScoped
public class MBNote extends AbstractMBean {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBNote";
	
	private String noteId;
	private TONote note;

	@EJB
	private IKeepNoteSbean noteSbean;
	
	/**
	 * Will try to find the note based on the user url, if does not exits, a new note will be created
	 */
	public void loadNoteFromURL() {
		if (StringUtil.isNotNull(this.getNoteId())) {
			TONote note = this.getNoteSbean().findNoteById(this.getNoteId());
			
			boolean isNewNote = note == null;
			
			if (isNewNote) {
				this.initNewNote();
			} else {
				this.setNote(note);
			}
			
			this.getMBNoteSecret().init(this.getNote().getId(), isNewNote);
		}
	}
	
	/**
	 * Save the note actual note clicking in the button
	 */
	public void saveNote() {
		try {
			if (this.getNote() != null) {
				this.getNoteSbean().change(this.getNote());
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_saving"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	/*
	 * Clear the content of the textEditor
	 */
	public void clear() {
		this.getNote().setDescription(null);
	}
	
	/**
	 * Copy the content of the textEditor removing all of the blank spaces.
	 */
	public void copy() {
		String textWithoutSpace = this.getNote().getDescription().replaceAll("(?i)<br\\s*/?>", "")
												                 .replaceAll("(?i)</p>", "<br>")
												                 .replaceAll("(?i)<p.*?>", "");
		
		PrimeFaces.current().executeScript("copy(\"" + textWithoutSpace + "\")");
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("text_copied"), FacesMessage.SEVERITY_INFO);
	}
	
	public boolean isButtonsEnabled() {
		return !(this.getNote().getDescription() == null);
	}
	
	public void initNewNote() {
		this.setNote(new TONote());
		this.getNote().setId(this.getNoteId());
		this.getNoteSbean().save(this.getNote());
	}

	private MBNoteSecret getMBNoteSecret() {
		return this.getMBean(MBNoteSecret.MANAGED_BEAN_NAME);
	}
	
	public TONote getNote() {
		return note;
	}

	public void setNote(TONote note) {
		this.note = note;
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
