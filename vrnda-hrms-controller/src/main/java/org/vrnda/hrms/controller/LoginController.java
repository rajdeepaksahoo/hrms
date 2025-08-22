package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private DefaultTokenServices tokenServices;

	@PostMapping("/logout/revoke")
	public boolean revokeToken() {
		final OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		final String token = tokenStore.getAccessToken(auth).getValue();
		boolean response = tokenServices.revokeToken(token);
		return response;
	}

}
