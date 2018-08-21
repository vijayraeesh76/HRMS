package com.hrms.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.lang.StringUtils;

public class HashPassword {

	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int ITERATIONS = 10000;
	// salt size at least 32 byte
	private static final int SALT_SIZE = 32;
	private static final int HASH_SIZE = 512;

	public static Map<String, String> getPasswordHashAndSaltHash(String passwordStr) {
		char[] password = passwordStr.toCharArray();
		byte[] hashedPassword = null;
		String passHashAsHexStr = null;
		Map<String, String> hashStrings = new HashMap<String, String>();
		String saltHashAsHexStr = null;
		try {

			// Load Algorithm
			SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);

			// Generate salt hash
			byte[] salt = generateSalt();

			// Generate spec
			PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_SIZE);

			// Hash Password
			hashedPassword = skf.generateSecret(spec).getEncoded();

			// Convert password and salt hash to hexadecimal string
			passHashAsHexStr = byteArrayToHexaString(hashedPassword);
			saltHashAsHexStr = byteArrayToHexaString(salt);

			// Store the hexadecimal strings in map
			hashStrings.put("PasswordHash", passHashAsHexStr);
			hashStrings.put("SaltHash", saltHashAsHexStr);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			System.out.println(ex.getMessage());
		}

		return hashStrings;
	}

	public static Boolean validatePassword(String existingPasswordHash, String saltHash, String newPassword) {
		Boolean passwordMatches = Boolean.FALSE;
		String newPasswordHash = hash(newPassword, hexaStringToBytes(saltHash));
		if (existingPasswordHash.equals(newPasswordHash)) {
			passwordMatches = Boolean.TRUE;
		}

		return passwordMatches;
	}

	private static String hash(String passwordStr, byte[] salt) {
		char[] password = passwordStr.toCharArray();
		byte[] hashedPassword = null;
		String hexHash = null;
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);

			PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_SIZE);

			hashedPassword = skf.generateSecret(spec).getEncoded();
			hexHash = byteArrayToHexaString(hashedPassword);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			System.out.println(ex.getMessage());
		}

		return hexHash;
	}

	private static byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_SIZE];
		random.nextBytes(salt);

		return salt;
	}

	private static String byteArrayToHexaString(final byte[] passwordBytes) {
		StringBuilder passwordString = new StringBuilder();
		for (byte b : passwordBytes) {
			passwordString.append(String.format("%02X", b));
		}
		return passwordString.toString();
	}

	private static byte[] hexaStringToBytes(String passHexaString) {
		int len = passHexaString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(passHexaString.charAt(i), 16) << 4)
					+ Character.digit(passHexaString.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static void main(String args[]){
		String password = "hr";
		String salt = "A871E896AB242BB81AA0C1167A4B95349669096E533379714EC81553007C6C79";
		String passwordHash = hash(password, hexaStringToBytes(salt));
		System.out.println("Password Hash : " + passwordHash);
	}
}
