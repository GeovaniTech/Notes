package managedBean.home;

import java.util.Date;
import java.util.List;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import keep.note.IKeepNoteSbean;
import to.note.TONote;
import utils.MessageUtil;
import utils.StringUtil;

@Named(MBHome.MANAGED_BEAN_NAME)
@SessionScoped
public class MBHome extends AbstractMBean {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBHome";
	
	private TONote note;
	private List<TONote> notes;
	
	@EJB
	private IKeepNoteSbean noteSbean;
	
	@PostConstruct
	public void init() {
		this.updateNotes();
	}
	
	public void savePolling() {
		try {
			if (this.getNote() != null && (StringUtil.isNotNull(this.getNote().getTitle())
					|| StringUtil.isNotNull(this.getNote().getDescription()))) {
				
				if(this.getNote().getId() == null) {
					this.getNote().setCreationUser(this.getClientSession().getEmail());
					this.getNote().setCreationDate(new Date());
					this.getNoteSbean().save(this.getNote());
				} else {
					this.getNote().setChangeUser(this.getClientSession().getEmail());
					this.getNote().setChangeDate(new Date());
					this.getNoteSbean().change(this.getNote());
				}
				
				this.updateNotes();
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_saving"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	private void updateNotes() {
		this.setNotes(this.getNoteSbean().getNotes());
	}
	
	public void initNewNote() {
		this.setNote(new TONote());
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
}
