package com.steve.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;




public class UserPrincipal implements UserDetails{

	private static final long serialVersionUID = 1L;
	private User user; 
	
	public UserPrincipal(User user ) { 
		this.user = user ; 
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>(); 
	
		this.user.convertPermissionsToList().forEach(p-> {
			GrantedAuthority authority = new SimpleGrantedAuthority(p) ; 
			authorities.add(authority);
		});
		
		//list of roles 
		this.user.convertRolesToList().forEach(r-> {
			GrantedAuthority authority = new SimpleGrantedAuthority(r) ; 
			authorities.add(authority);
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.isActive() == true ;
	}

}
