/**
 * 
 */
package com.sample.tnfigueiredo.util;

import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * A Wrapper for a {@link UserDetails} and return its information as a {@link ClientDetails} object.
 * 
 * @author tnfigueiredo
 *
 */
public class ClientDetailsUserWrapper {
	
	static final String RESOURCE_ID = "resource_id";
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String SCOPE_ADMIN = "ADMIN";
	static final String SCOPE_USER = "USER";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	
	private UserDetails user;
	private BaseClientDetails baseClientDetails;
	
	public ClientDetailsUserWrapper(UserDetails user) {
		this.user = user;
		this.baseClientDetails = new BaseClientDetails();
		loadUserInfo();
	}
	
	public void refreshUserInfo(UserDetails user) {
		this.user = user;
		loadUserInfo();
	}
	
	public ClientDetails getClientDetails() {
		return this.baseClientDetails;
	}
	
	private void loadUserInfo() {
		this.baseClientDetails.setClientId(this.user.getUsername());
		this.baseClientDetails.setClientSecret(this.user.getPassword());
		this.baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN) );
		this.baseClientDetails.setScope(Arrays.asList(SCOPE_ADMIN, SCOPE_USER));
		this.baseClientDetails.setResourceIds(Arrays.asList(RESOURCE_ID));
		this.baseClientDetails.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
		this.baseClientDetails.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
	}

}
