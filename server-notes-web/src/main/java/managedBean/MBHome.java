package managedBean;

import java.io.IOException;

import abstracts.AbstractMBean;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named(MBHome.MANAGED_BEAN_NAME)
@ViewScoped
public class MBHome extends AbstractMBean {

	private static final long serialVersionUID = -7977619792071655879L;
	public static final String MANAGED_BEAN_NAME = "MBHome";
	
	private String url;
	
	public void redirectTo() {
		int firstBar = this.getUrl().indexOf('/');
		
		if (firstBar != -1) {
			this.setUrl(this.getUrl().substring(0, firstBar));
		}
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
			ec.redirect(this.getUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
