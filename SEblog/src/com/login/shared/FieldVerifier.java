package com.login.shared;

public class FieldVerifier {
	public static boolean isValidPassword(String pwd) {
		if(pwd==null)
			return false;
		return pwd.length() >= 6 && pwd.length() <= 10;
	}
}
