package com.example.c_quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Hashes passwords with a per-user random salt (SHA-256) so credentials are
 * never stored in plaintext in SharedPreferences.
 */
public final class PasswordHasher {

    private PasswordHasher() {
        // utility class
    }

    /** Generates a new random salt, hex-encoded. */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return toHex(salt);
    }

    /** Returns the SHA-256 hash of salt + password, hex-encoded. */
    public static String hash(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(fromHex(salt));
            return toHex(digest.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is guaranteed to be available on every Android runtime
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    /** Constant-time comparison of two hex-encoded hashes. */
    public static boolean matches(String password, String salt, String expectedHash) {
        return MessageDigest.isEqual(
                hash(password, salt).getBytes(),
                expectedHash.getBytes());
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
