package org.vrnda.hrms.configs;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HrmsUserDetailsService implements UserDetailsService {

	private static Logger logger = LogManager.getLogger(HrmsUserDetailsService.class.getName());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object clientPrincipal = authentication.getPrincipal();
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return "ROLE_TRUSTED_CLIENT";
			}
		});
		return new User(username,"",authorityList);
		//throw new UsernameNotFoundException("Unauthorized client_id or username not found: " + username);
	}
}