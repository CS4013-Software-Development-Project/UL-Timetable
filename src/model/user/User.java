package model.user;

/**
 * Represents a generic system user. This class is intended to be extended by more specific user types.
 */
public class User {
    String username;
    String passwordHash;

    /**
     * Constructs a new User with the username and password.
     * @param username The username String.
     * @param passwordHash the hashed password for authentication.
     */
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the username of this user.
     * @return the username string
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Authenticates the user by validating the provided username and password hash.
     * @param username The username of the attempted authentication.
     * @param password The password of the auth attempt.
     * @return true if the credentials match; false otherwise.
     */
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.passwordHash.equals(password);
    }
}