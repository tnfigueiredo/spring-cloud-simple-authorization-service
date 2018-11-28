/**
 * 
 */
package com.sample.tnfigueiredo.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author tnfigueiredo
 *
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService{

	/*
	 * Mock user credentials for sample service. Avoiding encryption for sample usage.
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		return new org.springframework.security.core.userdetails.User("myuser", "{noop}password", getAuthority());
	}
	
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

}
