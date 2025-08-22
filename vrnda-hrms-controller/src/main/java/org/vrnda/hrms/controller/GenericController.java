package org.vrnda.hrms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.AppUsersMstService;

@RestController
public abstract class GenericController {

	private static final String INTERNAL_ERROR = "Internal Server Error";
	private static final String UNAUTHORIZED = "Unauthorized access";
	private static final String UNPROCESSABLE = "Couldn't process the request. Please try again later.";

	@Autowired
	protected Environment environment;

	@Autowired
	protected AppUsersMstService appUsersMstService;

	protected ResponseEntity<Message> toResponse(Object object) {
		Message message = new Message.MessageBuilder()
				.data(object)
				.build();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> toSucess(Object object) {
		Message message = new Message.MessageBuilder()
				.data(object)
				.build();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> toSucess(boolean status) {
		Message message = new Message.MessageBuilder()
				.status(status)
				.build();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> toSucess(int identifier, String msg) {
		Message message = new Message.MessageBuilder()
				.identifier(identifier)
				.detailMessage(msg)
				.build();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> toSucessForCreate(boolean status) {
		Message message = new Message.MessageBuilder()
				.status(status)
				.build();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> toSucessForCreate(Object data) {
		Message message = new Message.MessageBuilder()
				.data(data)
				.build();
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(message);
	}

	protected ResponseEntity<Message> to400(String msg) {
		Message message = new Message.MessageBuilder()
				.error(msg)
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	protected ResponseEntity<Message> to401() {
		Message message = new Message.MessageBuilder()
				.error(UNAUTHORIZED)
				.build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}

	protected ResponseEntity<Message> to401(String msg) {
		Message message = new Message.MessageBuilder()
				.error(msg)
				.build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}

	protected ResponseEntity<Message> to404(String msg) {
		Message message = new Message.MessageBuilder()
				.error(msg)
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	protected ResponseEntity<Message> to409(String msg) {
		Message message = new Message.MessageBuilder()
				.error(msg)
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
	}

	protected ResponseEntity<Message> to500() {
		Message message = new Message.MessageBuilder()
				.error(INTERNAL_ERROR)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}

	protected ResponseEntity<Message> toFailedDependency() {

		Message message = new Message.MessageBuilder()
				.error(UNPROCESSABLE)
				.build();
		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(message);
	}

	protected void redirectToUI(HttpServletResponse response,String endpoint) {
		try {
			response.sendRedirect(endpoint);
		} catch (IOException e) {
		}
	}

	protected String getMessageForIdentifier(Class<?> clazz) {
		return environment.getProperty("identifier").replace("*", clazz.getSimpleName());
	}

	protected Long getLoggedInUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String userName = authentication.getName();
			return appUsersMstService.getAppUserIdByUserName(userName);
		}
		return 0l;
	}

	protected String getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String userName = authentication.getName();
			return userName;
//			return appUsersMstService.getAppUserIdByUserName(userName);
		}
		return "";
	}
	
	protected String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return authentication.getName();
		}
		return "Not Found";
	}

}
