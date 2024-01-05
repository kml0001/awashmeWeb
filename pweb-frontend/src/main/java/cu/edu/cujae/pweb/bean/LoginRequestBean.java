package cu.edu.cujae.pweb.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import cu.edu.cujae.pweb.dto.UserAuthenticatedDto;
import cu.edu.cujae.pweb.service.AuthService;
import cu.edu.cujae.pweb.utils.JsfUtils;
import cu.edu.pweb.security.UserPrincipal;

@Component
@ManagedBean
@ViewScoped
public class LoginRequestBean {
	
	private String username;
	private String password;

	 @Autowired
	private AuthService authService;
	 
	 
	public LoginRequestBean() {
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		try {
			
			UserAuthenticatedDto userAuthenticated = authService.login(username, password);
			
			UserDetails userDetails = UserPrincipal.create(userAuthenticated);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (Exception e) {
	        JsfUtils.addMessageFromBundle("securityMessages", FacesMessage.SEVERITY_INFO, "message_invalid_credentials");
	        return null;
		}
		return "login";
	}
	protected HttpServletRequest getRequest() {
	    return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}
	
	protected FacesContext getFacesContext() {
	    return FacesContext.getCurrentInstance();
	}
}
