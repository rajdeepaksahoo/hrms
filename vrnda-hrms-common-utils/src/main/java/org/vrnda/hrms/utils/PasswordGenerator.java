package org.vrnda.hrms.utils;

import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.codec.Hex;

public class PasswordGenerator {

	private static PasswordGenerator instance = null;

	private PasswordGenerator() {
	}

	public static synchronized PasswordGenerator getInstance() {
		if (instance == null) {
			instance = new PasswordGenerator();
		}
		return instance;
	}

	public static String getGeneratePassword() {
		String dCase = "abcdefghijklmnopqrstuvwxyz";
		String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String intChar = "0123456789";
		char[] generatedPassword = new char[12];
		char c = 'A';
		for (int i = 0; i < 12; i++) {
			switch (i) {
			case 0:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 1:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			case 2:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 3:
				c = intChar.charAt((int) (Math.random() * 10));
				break;
			case 4:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 5:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			case 6:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 7:
				c = intChar.charAt((int) (Math.random() * 10));
				break;
			case 8:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 9:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			case 10:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 11:
				c = intChar.charAt((int) (Math.random() * 10));
				break;
			}
			generatedPassword[i] = (char) c;
		}
		return new StringBuffer(new String(generatedPassword)).toString();
	}

	public String getGeneratePasswordWithSpecailChar() {
		String dCase = "abcdefghijklmnopqrstuvwxyz";
		String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String intChar = "0123456789";
		String specialChar = "~!@#$%^&()*";
		char[] generatedPassword = new char[10];
		char c = 'A';
		for (int i = 0; i < 10; i++) {
			switch (i) {
			case 0:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 1:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			case 2:
				c = specialChar.charAt((int) (Math.random() * 11));
				break;
			case 3:
				c = intChar.charAt((int) (Math.random() * 10));
				break;
			case 4:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 5:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			case 6:
				c = specialChar.charAt((int) (Math.random() * 11));
				break;
			case 7:
				c = intChar.charAt((int) (Math.random() * 10));
				break;
			case 8:
				c = dCase.charAt((int) (Math.random() * 26));
				break;
			case 9:
				c = uCase.charAt((int) (Math.random() * 26));
				break;
			}
			generatedPassword[i] = (char) c;
		}
		return new StringBuffer(new String(generatedPassword)).toString();
	}

	public String getSHA512(String inputPassword) {

		// Hash the password using SHA-512
		byte[] passwordBytes = inputPassword.getBytes();
		byte[] sha512Hash = Sha512DigestUtils.sha(passwordBytes);

		// Convert the hash to a hexadecimal string
		String sha512HashHex = new String(Hex.encode(sha512Hash));

		return sha512HashHex;

	}
}