/**
 * 
 */
package com.sample.tnfigueiredo.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import com.sample.tnfigueiredo.util.ClientDetailsUserWrapper;

/**
 * Service representation for validation of Resource Owner user
 * 
 * @author tnfigueiredo
 *
 */
@Service(value = "clientDetailService")
public class ClientDetailServiceImpl implements ClientDetailsService {

	/*
	 * Service for recovering user information for authentication.
	 * 
	 */
	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		
		UserDetails user = this.userDetailsService.loadUserByUsername(clientId);
		
		if(user == null) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
		
		ClientDetailsUserWrapper clientDetailsUserWrapper = new ClientDetailsUserWrapper(user);
		
		return clientDetailsUserWrapper.getClientDetails();
	}

}
