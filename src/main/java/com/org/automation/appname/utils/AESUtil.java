package com.org.automation.appname.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.InvalidKeyException;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final String KEY_FILE = "aes.key";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the text to encrypt: ");
            String plainText = scanner.nextLine();
            // Use the input so the variable is not flagged as unused. This utility focuses on key generation/saving;
            // showing the entered text length gives minimal visible feedback without performing encryption here.
            System.out.println("Entered text length: " + (plainText != null ? plainText.length() : 0));

            System.out.println("Enter base64 key (or press Enter to generate a new key): ");
            String base64Key = scanner.nextLine();
            // check if the key is not entered generate a base-64 bit key
            if (base64Key == null || base64Key.trim().isEmpty()) {
                try {
                    base64Key = AESUtil.generateBase64Key();
                    System.out.println("Generated Base64 Key: " + base64Key);
                } catch (NoSuchAlgorithmException | RuntimeException e) {
                    System.err.println("Failed to generate or save AES key: " + e.getMessage());
                    // print a concise error representation instead of raw stack trace
                    System.err.println("Exception: " + e);
                }
            } else {
                System.out.println("Using provided Base64 key.");
            }
            // Note: encryption of plainText is out of scope for this helper; this utility focuses on key generation/saving.
        }
    }

    private static String generateBase64Key() throws NoSuchAlgorithmException {
        // Method to generate a base64 encoded AES key
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        // Try to initialize 256 bits; if not permitted fall back to 128 bits
        try {
            keyGen.init(256);
        } catch (Exception e) {
            // Could be InvalidParameterException if 256-bit not available; fall back to 128
            keyGen.init(128);
        }
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String decrypt(String encryptedValue,String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, ALGORITHM);
        String decryptedValue = "";
        try {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = Base64.getDecoder().decode(encryptedValue);
        decryptedValue = new String(decrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("Decryption failed : NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException : {}", e.getMessage(),e);
        }
        return decryptedValue;
    }
    public static String encrypt(String plainText,String base64Key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, ALGORITHM);
        String encryptedValue = "";
        try {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        encryptedValue = Base64.getEncoder().encodeToString(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
           log.error("Encryption failed : NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |\n" +
                   "                 BadPaddingException " , e);
        }
        return encryptedValue;
    }
}
