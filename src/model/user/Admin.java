package model.user;

import model.module.Programme;

public class Admin extends User {

    public Admin(String username, String passwordHash) {
        super(username, passwordHash);
    }

    public void appointLeader(Leader leader, Programme programme) {
        programme.addLeader(leader);
    }

    public void removeLeader(Leader leader, Programme programme) {
        programme.removeLeader(leader);
    }
}