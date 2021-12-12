package com.steve.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalService implements UserDetailsService{

	private UserRepository userRepository ; 
	
	public UserPrincipalService(UserRepository userRepository ){
		this.userRepository = userRepository ; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username) ;
		if(user == null ) {
			throw new UsernameNotFoundException(username);
		}
		UserPrincipal userPrincipal = new UserPrincipal(user) ;  	
		return userPrincipal;
	}

}
