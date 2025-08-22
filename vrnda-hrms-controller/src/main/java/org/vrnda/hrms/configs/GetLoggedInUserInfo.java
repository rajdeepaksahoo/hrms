package org.vrnda.hrms.configs;

import org.springframework.security.core.context.SecurityContextHolder;

public class GetLoggedInUserInfo {

	public String getUserId(){
		String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return userName;
	}
}
