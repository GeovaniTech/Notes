package managedBean;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.note.IKeepNoteSbean;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.StringUtil;

@Named(MBNoteSecret.MANAGED_BEAN_NAME)
@ViewScoped
public class MBNoteSecret extends AbstractMBean {

	private static final long serialVersionUID = -8695362522827287702L;
	public static final String MANAGED_BEAN_NAME = "MBNoteSecret";

	private String token;
	private String userSecret;
	private String noteId;

	@EJB
	private IKeepNoteSbean noteSbean;
	
	public void init(String noteId, boolean createToken) {
		this.setNoteId(noteId);
		
		if (createToken) {
			this.setToken(JWTUtil.generateToken(MANAGED_BEAN_NAME, noteId));
		}
		
		if (hasSecret()) {
			PrimeFaces.current().executeScript("PF('dialogValidateSecret').show();");
		}
	}
	
	/**
	 * Verify if token is still valid to set a secret for Note.
	 * 
	 * @return
	 */
	public boolean isValidToken() {
		return StringUtil.isNotNull(this.getToken()) && StringUtil.isNotNull(JWTUtil.getValueFromToken(MANAGED_BEAN_NAME, this.getToken()));
	}

	/**
	 * Verify if exists a secret for the Note.
	 * 
	 * @return
	 */
	public boolean hasSecret() {
		return StringUtil.isNotNull(this.getNoteSbean().getSecretFromNote(this.getNoteId()));
	}
	
	/**
	 * Verify if it is possible to see Note. If Note requires secret, 
	 * then it compares user secret with Note's secret.
	 * 
	 * @return
	 */
	public boolean isAccessValid() {
		String noteSecret = this.getNoteSbean().getSecretFromNote(this.getNoteId());
		
		if (StringUtil.isNull(noteSecret)) {
			return true;
		}	
		
		if (StringUtil.isNull(this.getUserSecret())) {
			return false;
		}
		
		return EncryptionUtil.encryptTextSHA(this.getUserSecret()).equals(noteSecret);
	}
	
	/*
	 * Validate if the secret and token are valid, and then try to save it.
	 */
	public void createSecret() {
		try {
			if (!this.isValidToken()) {
				throw new Exception(this.getLabel("msg_time_expired_secret"));
			}	
			
			if (StringUtil.isNull(this.getUserSecret())) {
				throw new Exception(this.getLabel("msg_insert_a_valid_secret"));
			}
			
			this.getNoteSbean().createSecret(EncryptionUtil.encryptTextSHA(this.getUserSecret()), this.getNoteId());
			
			PrimeFaces.current().executeScript("PF('dialogCreateSecret').hide();");
			PrimeFaces.current().ajax().update("growl");
			addMessage("msg_secret_set_with_sucess", FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			showMessageError(e);
		}
	}

	/**
	 * Validate if the user secret equals the Note secret, if it does, then user
	 * can see the Note.
	 */
	public void onConfirmSecret() {
		try {
			if (this.isAccessValid()) {
				PrimeFaces.current().executeScript("PF('dialogValidateSecret').hide();");
				PrimeFaces.current().executeScript("updateScreen();");
				return;
			}
			
			addMessage("msg_invalid_secret", FacesMessage.SEVERITY_ERROR);
		} catch (Exception e) {
			showMessageError(e);
		}

	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getUserSecret() {
		return userSecret;
	}

	public void setUserSecret(String userSecret) {
		this.userSecret = userSecret;
	}
}
