package main.java.model.user;

import main.java.model.module.Programme;

import java.util.ArrayList;
import java.util.List;

/**
 * The Leader has a list of Programmes that they lead.
 */
public class Leader extends User {
    List<Programme> ledProgrammes;

    public Leader(String username, String passwordHash) {
        super(username, passwordHash);
        this.ledProgrammes = new ArrayList<Programme>();
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
}