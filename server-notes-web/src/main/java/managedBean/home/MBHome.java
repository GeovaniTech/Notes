package managedBean.home;

import abstracts.AbstractMBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named(MBHome.MANAGED_BEAN_NAME)
@ViewScoped
public class MBHome extends AbstractMBean {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBHome";
	
}
