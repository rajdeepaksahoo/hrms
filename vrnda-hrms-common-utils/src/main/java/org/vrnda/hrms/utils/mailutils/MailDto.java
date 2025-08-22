package org.vrnda.hrms.utils.mailutils;

import java.util.List;

import lombok.Data;

@Data
public class MailDto {

	private String senderMail;

	private String userName;

	private String recieverMail;

	private String password;

	private String ccMail;

	private String subject;

	private String message;

	private String senderName;

	private String recieverName;
	
	private List<String> recipients;
	private List<String> ccList;
	private List<String> bccList;
	private String body;
	private Boolean isHtml;
	private String attachmentPath;


}
