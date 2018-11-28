/**
 * 
 */
package com.sample.tnfigueiredo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author tnfigueiredo
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	
	static final String CLIEN_ID = "myuserapp";
	static final String CLIENT_SECRET = "myusersecret";
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
	static final String REFRESH_TOKEN = "refresh_token";
	static final String IMPLICIT = "implicit";
	static final String SCOPE_ADMIN = "ADMIN";
	static final String SCOPE_USER = "USER";
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/*
	 * Configuration to encode JWT access token
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("mykeyforsecret");
		return converter;
	}
	
	/*
	 * Setting enconding configuration to access token
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	/*
	 * Setting configuration to add InMemory Client Credentials for getting access token 
	 * 
	 *(non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.inMemory()
			.withClient(CLIEN_ID)
			.secret("{noop}"+CLIENT_SECRET) //avoinding encription for example case
			.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
			.scopes(SCOPE_ADMIN, SCOPE_USER)
			.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
			refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}
	
	/*
	 * Overide configurations to use access token as a JWT access token
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
				.authenticationManager(authenticationManager)
				.accessTokenConverter(accessTokenConverter());
	}

}
