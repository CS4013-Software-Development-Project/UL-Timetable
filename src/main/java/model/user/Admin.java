package model.user;

import model.module.Programme;

/**
 * The Admin can appoint and remove Leaders from Programmes.
 */
public class Admin extends User {

    private Admin() { super(); }

    public Admin(String username, String password) {
        super(username, password);
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

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");
        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");

        return line.toString();
    }

    public static Admin deserialize(String line) {
        String[] tokens = line.split(",");
        Admin admin = new Admin();

        admin.setUUID(tokens[0]);
        admin.setUsername(tokens[1]);
        admin.setPasswordHash(tokens[2]);

        return admin;
    }
}