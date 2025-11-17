package model.module;

import model.user.Leader;

import java.util.ArrayList;
import java.util.List;

public class Module {
    String moduleCode;
    String moduleName;

    Leader leader;
    List<Session> sessions;

    public Module(String moduleCode, String moduleName, Leader leader) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.leader = leader;
        this.sessions = new ArrayList<>();
    }

    public Module(String moduleCode, String moduleName, Leader leader, List<Session> sessions) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.leader = leader;
        this.sessions = sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }
}