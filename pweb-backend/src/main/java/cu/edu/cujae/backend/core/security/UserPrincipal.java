package cu.edu.cujae.backend.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cu.edu.cujae.backend.core.dto.RoleDto;
import cu.edu.cujae.backend.core.dto.UserDto;


public class UserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String firstname;
    private String lastname;
    private String mail;
    private String passwd;
	//private List<RoleDto> roleList;
    private Collection<? extends GrantedAuthority> authorities;
	private String username;

    public UserPrincipal(int id, String firstname, String lastname, String mail, String passwd, Collection<? extends GrantedAuthority> authorities ,List<RoleDto> roleList) {
    	this.id = String.valueOf(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.passwd = passwd;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserDto user) {
    	List<GrantedAuthority> authorities;
    	try {
    		Collection<String> roleNames = user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList());
    		authorities = AuthorityUtils.createAuthorityList(roleNames.toArray(new String[0]));
		} catch (Exception e) {
			authorities = Collections.
	                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
			
		}
    	return new UserPrincipal(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMail(),
                user.getPasswd(),
                authorities,
                user.getRoles()
        );
    }

    public static UserPrincipal create(UserDto user, Map<String, Object> attributes) {
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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
