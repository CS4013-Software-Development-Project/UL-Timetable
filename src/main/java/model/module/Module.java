package model.module;

import persistence.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

/**
 * Module represents a subject that is part of a Programme.
 */
public class Module extends AbstractPersistable {
    String moduleCode;
    String moduleName;

    List<Session> sessions;

    /**
     * Creates a new instance of Module.
     * @param moduleCode The module code assigned to this Module.
     * @param moduleName The full name representing this Module.
     */
    public Module(String moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.sessions = new ArrayList<>();
    }

    /**
     * Creates a new instance of Module.
     * @param moduleCode The module code assigned to this Module.
     * @param moduleName The full name representing this Module.
     * @param sessions The list of Sessions of this Module.
     */
    public Module(String moduleCode, String moduleName, List<Session> sessions) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.sessions = sessions;

        for (Session session : sessions){
            session.setModule(this);
        }
    }

    /**
     * Adds a Session to this Module.
     * @param session The Session to add to this Module.
     */
    public void addSession(Session session) {
        this.sessions.add(session);
        session.setModule(this);
    }

    /**
     * Gets a list of Sessions of this Module.
     * @return The list of Sessions of this Module.
     */
    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();

        line.append(this.getUUID()).append(",");
        line.append(this.moduleCode).append(",");
        line.append(this.moduleName);

        return line.toString();
    }

    public static Module deserialize(String line) {
        String[] tokens = line.split(",");
        Module m = new Module(tokens[1], tokens[2]);
        m.setUUID(tokens[0]);
        return m;
    }
}