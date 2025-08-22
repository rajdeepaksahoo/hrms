package org.vrnda.hrms.utils.mailutils;

import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MailUtilityServiceImpl implements MailUtilityService {

	public static void leaveApplicationMail(MailDto mailDto) {

		// Declare recipient's & sender's e-mail id.
		String sendrmailid = mailDto.getSenderMail();
		String destmailid = mailDto.getRecieverMail();
		String body = String.join(System.lineSeparator(), "Hi " + mailDto.getRecieverName() + ",", mailDto.getMessage(),
				"Regards", mailDto.getSenderName());
		// Mention user name and password as per your configuration
		final String uname = mailDto.getSenderMail();
		final String pwd = mailDto.getPassword();
		// We are using relay.jangosmtp.net for sending emails
		String smtphost = "smtp.gmail.com";
		// Set properties and their values
		Properties propvls = new Properties();
		propvls.put("mail.smtp.auth", "true");
		propvls.put("mail.smtp.starttls.enable", "true");
		propvls.put("mail.smtp.host", "smtp.gmail.com");
		propvls.put("mail.smtp.port", "587");
		// Create a Session object & authenticate uid and pwd
		Session sessionobj = Session.getInstance(propvls, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(uname, pwd);
			}
		});

		try {
			// Create MimeMessage object & set values
			Message messageobj = new MimeMessage(sessionobj);
			messageobj.setFrom(new InternetAddress(sendrmailid));
			messageobj.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destmailid));
			messageobj.setSubject(mailDto.getSubject());
//		   messageobj.addRecipient(RecipientType.BCC, new InternetAddress("your@email.com"));
			messageobj.addRecipient(RecipientType.CC, new InternetAddress(mailDto.getCcMail()));
			messageobj.setText(body);
			// Now send the message
			Transport.send(messageobj);
			copyIntoSent(sessionobj, messageobj, mailDto.getSenderMail(), mailDto.getPassword());
		} catch (MessagingException exp) {
			throw new RuntimeException(exp);
		}
	}

	private static void copyIntoSent(final Session session, final Message msg, final String userName,
									 final String passWord) throws MessagingException {
		final Store store = session.getStore("imap");
		// Gmail IMAP server
		store.connect("imap.gmail.com", 993, userName, passWord);

		// Access the "[Gmail]/Sent Mail" folder
		final Folder sentFolder = store.getFolder("[Gmail]/Sent Mail");

		if (!sentFolder.exists()) {
			throw new MessagingException("Gmail Sent folder does not exist.");
		}

		sentFolder.open(Folder.READ_WRITE);
		sentFolder.appendMessages(new Message[]{msg});
		sentFolder.close(false);
		store.close();
	}


	@Override
	public Boolean sendGenericEmial(MailDto mailDto) {

		Boolean isSent = false;
		try {
			Session session = authentication();
			if (session != null) {
				MimeMessage message = new MimeMessage(session);
				message.setSubject(mailDto.getSubject());
				message.setContent(mailDto.getBody(), "text/html; charset=utf-8");
				message.setFrom(new InternetAddress("dev.sbmail@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(mailDto.getRecipients().stream().collect(Collectors.joining(","))));
				Transport.send(message);
			}
			isSent = true;
		} catch (Exception e) {
			log.error("Sending e-mail error: {}  " + e.getMessage());
		}
		return isSent;

	}

	private Session authentication() {
		final String user = "dev.sbmail@gmail.com";
		final String password = "hnea deut sobh bpqj";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");

		Properties propvls = new Properties();
		propvls.put("mail.smtp.auth", "true");
		propvls.put("mail.smtp.starttls.enable", "true");
		propvls.put("mail.smtp.host", "smtp.gmail.com");
		propvls.put("mail.smtp.port", "587");

		Session session = null;
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		return session;
	}

}