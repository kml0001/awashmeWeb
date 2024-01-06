package cu.edu.cujae.backend.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import cu.edu.cujae.backend.core.dto.UserDto;
import cu.edu.cujae.backend.core.service.UserServiceImp;

public class TokenRoleAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserServiceImp userService;
    
    
    
    
    private static final Logger logger = LoggerFactory.getLogger(TokenRoleAuthenticationFilter.class);
    
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

			String tokenHeader = request.getHeader("Authorization");
			
			if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
				String token = tokenHeader.substring(7);
			
				if(tokenProvider.validateToken(token)){
					String username = tokenProvider.getUserDtoFromToken(token).getUsername();
					
					System.out.print(username + " <------------------------");
					
					UserDetails userDetails = UserPrincipal.create( userService.getUserByUsername(username));
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
				
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
    }
}
