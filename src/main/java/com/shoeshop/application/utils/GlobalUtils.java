package com.shoeshop.application.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalUtils {
	public static final Logger log = LoggerFactory.getLogger(GlobalUtils.class);

	public static boolean matchPassword(String hashPassFromDB, String userEnteredPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String base64Hash = hashPassword(userEnteredPassword);
		return base64Hash.equals(hashPassFromDB);
	}
	public static String hashPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = secureRandom.generateSeed(12);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 10, 512);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
		String base64Hash = Base64.getMimeEncoder().encodeToString(hash);
		return base64Hash;
	}
}
