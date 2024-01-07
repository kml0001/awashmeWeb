package cu.edu.cujae.backend.core.util;


import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import cu.edu.cujae.backend.core.security.UserPrincipal;



public class CurrentUserUtils {
	
	public CurrentUserUtils() {
		super();
	}

	public static String getUsername() {
		String username = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
			username =  principal.getUsername();
		}
		return username;
	}
	
	public static List<String> getUserRole() {
		List<String> roles = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
			roles =  principal.getRoleList();
		}
		return roles;
	}
	
	public static String getEmail() {
		String email = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
			email = principal.getEmail();
		}
		return email;
	}
	
	public static String getFullName() {
		String fullName = "Invitado";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			fullName = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLastname();
		}
		return fullName;
	}
	
	public static int getUserId() {
		int userId = -1;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    	UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
			userId = Integer.valueOf(principal.getId());
		}
		return userId;
	}

	
}
