package model.module;

import model.user.Leader;

import java.util.ArrayList;
import java.util.List;

/**
 * Module represents a subject that is part of a Programme.
 */
public class Module {
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
    }

    /**
     * Adds a Session to this Module.
     * @param session The Session to add to this Module.
     */
    public void addSession(Session session) {
        this.sessions.add(session);
    }

    /**
     * Gets a list of Sessions of this Module.
     * @return The list of Sessions of this Module.
     */
    public List<Session> getSessions() {
        return sessions;
    }
}