package model.user;

import model.module.Programme;
import model.module.Session;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;
import util.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The Leader has a list of Programmes that they lead.
 */
public class Leader extends User {
    List<Programme> ledProgrammes;

    public Leader(String username, String password) {
        super(username, password);
        this.ledProgrammes = new ArrayList<Programme>();
    }
    public Leader(String username, String passwordHash, List<Programme> ledProgrammes) {
        super(username, passwordHash);
        this.ledProgrammes = ledProgrammes;
        for (Programme p : ledProgrammes) {
            p.addLeader(this);
        }
    }

    /**
     * Adds a Programme to the list of Programmes this Leader leads in.
     * @param programme the Programme to add this Leader to.
     */
    public void addLedProgramme(Programme programme) {
        this.ledProgrammes.add(programme);
        if (!programme.getLeaders().contains(this))
            programme.addLeader(this);
    }

    /**
     * Removes a Programme from the list of Programmes this Leader leads in.
     * @param programme the Programme to remove.
     */
    public void removeLedProgramme(Programme programme) {
        this.ledProgrammes.remove(programme);
        programme.removeLeader(this);
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
        for (Programme p : ledProgrammes) {
            if (!p.getLeaders().contains(this))
                p.addLeader(this);
        }
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");
        line.append(SaveUtil.fastList(this.ledProgrammes));

        return line.toString();
    }

    public static Leader deserialize(String[] tokens) {
        Leader leader = new Leader(tokens[1], tokens[2]);
        leader.setUUID(tokens[0]);

        leader.setPasswordHash(tokens[2]);
        return leader;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        // Temporary?
        if (tokens.length != 4) { tokens = new String[] {tokens[0], tokens[1], tokens[2], "null"}; }
        this.ledProgrammes = SaveUtil.queryList(tokens[3], PersistenceManager.programmes);
    }

    @Override
    public void resolveDependencies() {
        for (Programme programme : this.ledProgrammes) {
            if (programme != null && !PersistenceManager.programmes.containsKey(programme.getUUID())) {
                programme.resolveDependencies();
                PersistenceManager.programmes.put(programme.getUUID(), programme);
            }
        }
    }
}