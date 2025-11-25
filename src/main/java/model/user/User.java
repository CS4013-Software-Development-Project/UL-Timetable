package model.user;

import persistence.AbstractPersistable;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

/**
 * Represents a generic system user. This class is intended to be extended by more specific user types.
 * As this is a parent class that should not be instantiated, it is abstract.
 */
public abstract class User extends AbstractPersistable {
    String username;
    String passwordHash;

    protected User() {}


    public User(String username) {
        this.username = username;
    }
    /**
     * Constructs a new User with the username and password.
     * @param username The username String.
     * @param password the hashed password for authentication.
     */
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    /**
     * Generates a cryptographically-secure salted password.
     * @param password The plain-text password String.
     * @return The cryptographically-secure salted password.
     */
    private String hashPassword(String password) {
        try {
            //generate salt
            byte[] salt = new byte[16];
            SecureRandom sR = new SecureRandom();
            sR.nextBytes(salt);

            return hashPassword(salt, password);
        }
        catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Generates a cryptographically-secure salted password.
     * @param salt The 16-byte hash.
     * @param password The plain-text password String.
     * @return cryptographically-secure salted password.
     */
    private String hashPassword(byte[] salt, String password) {
        try {
            //default params for SHA256
            int iterations = 65536;
            int keyLength = 256;

            //sources:
            //https://www.ibm.com/docs/en/sdk-java-technology/8?topic=classes-secretkeyfactory-class
            //https://download.java.net/java/early_access/jdk26/docs/api/java.base/javax/crypto/SecretKeyFactory.html
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            //Per oracle docs, every JDK implementation MUST support PBKDF2
            //therefore, this catch statement is redundant, and should never trigger.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            //now, return this in salt:hash (in base64 format)
            Base64.Encoder b64 = Base64.getEncoder();
            return b64.encodeToString(salt) + ":" +  b64.encodeToString(hash);
        }
        catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Gets the username of this user.
     * @return The username String.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of this user.
     * @param username The new username String.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Resets this users password.
     * @param newPassword The new plain-text password.
     */
    public void resetPassword(String newPassword) {
        this.passwordHash = hashPassword(newPassword);
    }

    /**
     * Manually set the password hash for this user.
     * @param passwordHash must be in salt:hash string format.
     */
    protected void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Securely authenticates the user by validating the provided password against the stored password hash.
     * @param testPassword The plain-text password String of the auth attempt.
     * @return true if the passwords match; false otherwise.
     */
    public boolean authenticate(String testPassword) {
        String[] parts = this.passwordHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);

        byte[] testHash = Base64.getDecoder().decode(hashPassword(salt, testPassword).split(":")[1]);

        //we need to perform a constant-time equals to secure against timing attacks
        int result = hash.length ^ testHash.length;

        for (int i = 0; i < Math.max(hash.length, testHash.length); i++) {
            byte a = (i < hash.length) ? hash[i] : 0;
            byte b = (i < testHash.length) ? testHash[i] : 0;
            result |= a ^ b;
        }

        return result == 0;
    }
}