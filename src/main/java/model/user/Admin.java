package main.java.model.user;

import main.java.model.module.Programme;

/**
 * The Admin can appoint and remove Leaders from Programmes.
 */
public class Admin extends User {

    public Admin(String username, String passwordHash) {
        super(username, passwordHash);
    }

    /**
     * Appoints a Leader to a Programme.
     * @param leader Leader to appoint.
     * @param programme Programme to appoint the Leader to.
     */
    public void appointLeader(Leader leader, Programme programme) {
        programme.addLeader(leader);
        leader.addLedProgramme(programme);
    }

    /**
     * Removes a Leader from a Programme.
     * @param leader Leader to remove.
     * @param programme Programme to remove the Leader from.
     */
    public void removeLeader(Leader leader, Programme programme) {
        programme.removeLeader(leader);
        leader.removeLedProgramme(programme);
    }
}