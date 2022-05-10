package com.shoeshop.application.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GlobalUtils {
	public static final Logger log = LoggerFactory.getLogger(GlobalUtils.class);
	static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public static boolean matchPassword(String hashPassFromDB, String userEnteredPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		
//		String hashPassword = bCryptPasswordEncoder.encode(hashPassFromDB);
		return bCryptPasswordEncoder.matches(userEnteredPassword, hashPassFromDB);
	}
	public static String hashPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
				
		String base64Hash = bCryptPasswordEncoder.encode(password);
		return base64Hash;
	}
}
