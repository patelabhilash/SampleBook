package com.encryptor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Introducer {

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static byte[] encryptFile(String key, String initVector, String sourcefilepath,
			String encryptedziplocation) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), key.getBytes(), 64, 128);
			SecretKey tmp = skf.generateSecret(spec);

			SecretKeySpec skeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			System.out.println("--Encryption Started--");
			byte[] encrypted = cipher.doFinal(readFile(sourcefilepath));
			System.out.println("--Encryption Ended--");
			writeFile(encryptedziplocation, encrypted);// file path to be modified
			return encrypted;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptFile(String key, String initVector, String encryptedziplocation,
			String zippedlocation) {
		try {

			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(key.toCharArray(), key.getBytes(), 64, 128);
			SecretKey tmp = skf.generateSecret(spec);

			SecretKeySpec skeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			System.out.println("--Decryption Started--");
			byte[] original = cipher.doFinal(readFile(encryptedziplocation));
			System.out.println("--Decryption Ended--");
			writeFile(zippedlocation, original);

			return original;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte[] readFile(String filePath) {
		byte[] ans = null;
		try {
			ans = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public static String writeFile(String filePath, byte[] value) {
		Path path = null;
		try {
			path = Files.write(Paths.get(filePath), value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path.toString();
	}

	public static String zipit(String key, String initVector, String sourcepath, String encryptedziplocation) {
		String zippedlocation = Paths.get(sourcepath, "..\\~protected.zip").normalize().toString();
		try {
			ZipFile zipfile = new ZipFile(zippedlocation);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			parameters.setPassword(key);
			zipfile.addFile(new File(sourcepath), parameters);
			encryptFile(key, initVector, zippedlocation, encryptedziplocation);
			Files.delete(Paths.get(zippedlocation));
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zippedlocation;
	}

	public static String unzipit(String key, String initVector, String encryptedziplocation, String unzippedlocation) {
		String zippedlocation = Paths.get(unzippedlocation, ".\\~protected.zip").normalize().toString();
		decryptFile(key, initVector, encryptedziplocation, zippedlocation);
		ZipFile zipfile;
		try {
			zipfile = new ZipFile(zippedlocation);
			if (zipfile.isEncrypted()) {
				zipfile.setPassword(key);
			}
			zipfile.extractAll(unzippedlocation);
			Files.delete(Paths.get(zippedlocation));
			return unzippedlocation;
		} catch (ZipException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// needs to separate into two projects
		String initVector = "RandomInitVector"; // 16 bytes IV
		String key = "Bar12345Bar123456"; // 128 bit key

		String sourcepathforenc = "D:\\rough\\EncryptionTest\\New folder\\Text2.txt";
		String encryptedziplocation = "D:\\rough\\EncryptionTest\\New folder\\encfile";
		String unzippedlocation = "D:\\rough\\EncryptionTest\\New folder\\solveded";

		zipit(key, initVector, sourcepathforenc, encryptedziplocation);
		unzipit(key, initVector, encryptedziplocation, unzippedlocation);

	}
}
