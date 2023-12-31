package cu.edu.cujae.backend.core.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.UserServiceImp;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImp userService;

    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDto user = null;
		user = userService.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found.");
		}

        return UserPrincipal.create(user);
    }

}