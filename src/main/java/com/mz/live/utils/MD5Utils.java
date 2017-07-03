package com.mz.live.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String encrypt(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 用户密码加密
	 * 
	 * @author dun
	 * @param password
	 * @return String
	 */
	public static String encryptUserPassword(String password) {
		return encrypt(encrypt(password).substring(0, 16));
	}
}
