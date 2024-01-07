package cu.edu.cujae.pweb.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.dto.UserAuthenticatedDto;



public class UserPrincipal implements UserDetails {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String id;
	    private String fullname;
	    private String mail;
	    private String passwd;
	    private Collection<? extends GrantedAuthority> authorities;
	    private String username;
	    private List<RoleDto> roleList;
	    private String token;

	    public UserPrincipal(int id, String fullname, String mail, String passwd, Collection<? extends GrantedAuthority> authorities, String username, List<RoleDto> roleList ,String token) {
	        this.id = String.valueOf(id);
	        this.setFullname(fullname);
	        this.mail = mail;
	        this.passwd = passwd;
	        this.authorities = authorities;
	        this.setUsername(username);
	        this.roleList = roleList;
	        this.token = token;
	    }

	    public static UserPrincipal create(UserAuthenticatedDto user) {
	    	System.out.println("username en user principal: " + user.getUsername());
	    	List<GrantedAuthority> authorities;
	    	try {
	    		Collection<String> roleNames = user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList());
	    		authorities = AuthorityUtils.createAuthorityList(roleNames.toArray(new String[0]));
			} catch (Exception e) {
				e.printStackTrace();
				authorities = Collections.
		                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
				
			}
	    	
	    	
	        UserPrincipal result = new UserPrincipal(
	                Integer.valueOf(user.getId()),
	                user.getFullName(),
	                user.getEmail(),
	                user.getPassword(),
	                authorities,
	                user.getUsername(),
	                user.getRoles(),
	                user.getToken()
	        );
	        System.out.println("token en user principal" + result.getUsername());
	        
	        return result;
	    }

	    public static UserPrincipal create(UserAuthenticatedDto user, Map<String, Object> attributes) {
	        UserPrincipal userPrincipal = UserPrincipal.create(user);
	        return userPrincipal;
	    }

	    public String getId() {
	        return String.valueOf(id);
	    }

	    public String getEmail() {
	        return mail;
	    }

	    @Override
	    public String getPassword() {
	        return passwd;
	    }

	    @Override
	    public String getUsername() {
	        return username;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    public List<RoleDto> getRoleList() {
	        return roleList;
	    }

	    public void setRoleList(List<RoleDto> roleList) {
	        this.roleList = roleList;
	    }
	    
	    public String getToken() {
	    	return token;
	    }
	    public void setToken(String token) {
	    	this.token = token;
	    }

		public String getFullname() {
			return fullname;
		}

		public void setFullname(String fullname) {
			this.fullname = fullname;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
