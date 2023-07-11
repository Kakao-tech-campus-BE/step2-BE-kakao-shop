package com.example.kakaoshop._core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private static final String PASSWORD_PATTERN =
			"^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	public static boolean validatePassword(String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
