/**
 * 
 */
package com.sample.tnfigueiredo.service.impl;

import java.util.Arrays;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * Service representation for validation of Resource Owner user
 * 
 * @author tnfigueiredo
 *
 */
@Service(value = "clientDetailService")
public class ClientDetailServiceImpl implements ClientDetailsService {

	static final String CLIEN_ID = "myuserapp";
	static final String CLIENT_SECRET = "myusersecret";
	static final String RESOURCE_ID = "resource_id";
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String SCOPE_ADMIN = "ADMIN";
	static final String SCOPE_USER = "USER";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.ClientDetailsService#loadClientByClientId(java.lang.String)
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		
		if(!clientId.equals("myuserapp")) {
			throw new NoSuchClientException("No client found with id = " + clientId);
		}
		
		BaseClientDetails baseClientDetails = new BaseClientDetails();
		baseClientDetails.setClientId(clientId);
		baseClientDetails.setClientSecret("{noop}"+CLIENT_SECRET);
		baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN) );
		baseClientDetails.setScope(Arrays.asList(SCOPE_ADMIN, SCOPE_USER));
		baseClientDetails.setResourceIds(Arrays.asList(RESOURCE_ID));
		baseClientDetails.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
		baseClientDetails.setRefreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
		return baseClientDetails;
	}

}
