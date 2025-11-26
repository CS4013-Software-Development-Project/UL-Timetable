package model.module;

import model.grouping.Subgroup;
import model.user.Student;
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

    Subgroup students;

    /**
     * Creates a new instance of Module.
     * @param moduleCode The module code assigned to this Module.
     * @param moduleName The full name representing this Module.
     * @param students Subgroup of students taking this Module.
     */
    public Module(String moduleCode, String moduleName, Subgroup students) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.sessions = new ArrayList<>();
        this.students = students;
    }

    /**
     * Creates a new instance of Module.
     * @param moduleCode The module code assigned to this Module.
     * @param moduleName The full name representing this Module.
     * @param students Subgroup of students taking this Module.
     * @param sessions The list of Sessions of this Module.
     */
    public Module(String moduleCode, String moduleName, Subgroup students, List<Session> sessions) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.sessions = sessions;
        this.students = students;
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

    @Override
    public String serialize() {
        String line = "";

        line += this.getUUID() + ",";
        line += this.moduleCode + ",";
        line += this.moduleName;

        return line;
    }

    public static Module deserialize(String line) {
        String[] tokens = line.split(",");
        // TODO: fix serialization for new properties
        // Module m = new Module(tokens[1], tokens[2]);
        m.setUUID(tokens[0]);
        return m;
    }
}