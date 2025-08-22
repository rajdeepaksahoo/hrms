package org.vrnda.hrms.configs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.vrnda.hrms.service.AppUsersMstService;
import org.vrnda.hrms.service.resultset.AppUsersMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;

@Component
public class VrndaHRMSAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	AppUsersMstService appUsersMstService;

	private List<GrantedAuthority> authorityList = new ArrayList<>();

	public VrndaHRMSAuthenticationProvider() {
		authorityList.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return "ROLE_TRUSTED_CLIENT";
			}
		});
		authorityList.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return "ROLE_CLIENT";
			}
		});
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String password = authentication.getCredentials().toString();
		String username = authentication.getName();
		if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
			Long userId = appUsersMstService.getAppUserIdByUserName(username);
			if (userId != null) {
				AppUsersMstResultSet authenticationResultSet = appUsersMstService.doLogin(username, password);
				if (authenticationResultSet != null) {
					if (!authenticationResultSet.getStatus()) {
						throw new UsernameNotFoundException(authenticationResultSet.getMessageDescription());
					} else if (authenticationResultSet.getAppUsersMstEntity() != null && Character.compare('Y',
							authenticationResultSet.getAppUsersMstEntity().getFirstLogin()) == 0) {
						throw new InsufficientAuthenticationException("First_Login");
					}
				}
			} else {
				throw new UsernameNotFoundException("User does not exist.");
			}
			authorityList.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return "ROLE_USER";
				}
			});
			return new UsernamePasswordAuthenticationToken(username, password, authorityList);
		} else {
			throw new InsufficientAuthenticationException("Invalid credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
