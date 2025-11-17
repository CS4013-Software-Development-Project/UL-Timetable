package model.user;

import model.module.Programme;

import java.util.ArrayList;
import java.util.List;

public class Leader extends User {
    List<Programme> ledProgrammes;

    public Leader(String username, String passwordHash) {
        super(username, passwordHash);
        this.ledProgrammes = new ArrayList<Programme>();
    }

    public void addLedProgramme(Programme programme) {
        this.ledProgrammes.add(programme);
    }

    public void removeLedProgramme(Programme programme) {
        this.ledProgrammes.remove(programme);
    }

    public List<Programme> getLedProgrammes() {
        return this.ledProgrammes;
    }
}