package model.user;

public class User {
    String username;
    String passwordHash;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.passwordHash.equals(password);
    }
}