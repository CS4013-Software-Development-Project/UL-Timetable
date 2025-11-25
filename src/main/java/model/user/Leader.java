package model.user;

import model.module.Programme;

import java.util.ArrayList;
import java.util.List;

/**
 * The Leader has a list of Programmes that they lead.
 */
public class Leader extends User {
    List<Programme> ledProgrammes;

    public Leader(String username) {
        super(username);
        this.ledProgrammes = new ArrayList<Programme>();
    }

    public Leader(String username, String password) {
        super(username, password);
        this.ledProgrammes = new ArrayList<Programme>();
    }

    public Leader(String username, String passwordHash, List<Programme> ledProgrammes) {
        super(username, passwordHash);
        this.ledProgrammes = ledProgrammes;
    }

    /**
     * Adds a Programme to the list of Programmes this Leader leads in.
     * @param programme the Programme to add this Leader to.
     */
    public void addLedProgramme(Programme programme) {
        this.ledProgrammes.add(programme);
    }

    /**
     * Removes a Programme from the list of Programmes this Leader leads in.
     * @param programme the Programme to remove.
     */
    public void removeLedProgramme(Programme programme) {
        this.ledProgrammes.remove(programme);
    }

    /**
     * Gets the list of Programmes this Leader leads in.
     * @return The list of Programmes this Leader leads in.
     */
    public List<Programme> getLedProgrammes() {
        return this.ledProgrammes;
    }

    /**
     * Sets the list of Programmes this Leader leads in.
     * @param ledProgrammes The list of Programmes this Leader leads in.
     */
    public void setLedProgrammes(List<Programme> ledProgrammes) {
        this.ledProgrammes = ledProgrammes;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");
        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");

        for (Programme programme : this.ledProgrammes) {
            line.append(programme.getUUID()).append("|");
        }

        return line.toString();
    }

    public static Leader deserialize(String line) {
        String[] tokens = line.split(",");
        Leader leader = new Leader(tokens[1], tokens[2]);
        leader.setUUID(tokens[0]);

        return leader;
    }
}