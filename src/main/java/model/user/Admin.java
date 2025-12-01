package model.user;

import model.module.Module;
import model.module.Programme;
import persistence.PersistenceManager;

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
        if (!programme.getLeaders().contains(leader))
            programme.addLeader(leader);
        if (!leader.getLedProgrammes().contains(programme))
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

    /**
     * Adds admin to system.
     * @param username String to set username.
     * @param password String to set password.
     */
    public void addAdmin(String username, String password){
        Admin addedAdmin = new Admin(username, password);
        PersistenceManager.addAdmin(addedAdmin);
    }

    /**
     * Adds student to system.
     * @param username String to set username.
     * @param password String to set password.
     */
    public void addStudent(String username, String password){
        Student addedUser = new Student(username, password);
        PersistenceManager.addStudent(addedUser);
    }

    /**
     * Adds leader to system.
     * @param username String to set username.
     * @param password String to set password.
     */
    public void addLeader(String username, String password){
        Leader addedUser = new Leader(username, password);
        PersistenceManager.addLeader(addedUser);
    }

    /**
     * Adds programme to system.
     * @param name String to set programme name.
     */
    public void addProgramme(String name){
        Programme addedProgramme = new Programme(name);
        PersistenceManager.addProgramme(addedProgramme);
    }

    /**
     * Adds module to system.
     * @param moduleCode String to set module code.
     * @param moduleName String to set module name.
     */
    public void addModule(Programme programme, String moduleCode, String moduleName){
        Module addedModule = new Module(moduleCode, moduleName);
        programme.addModule(addedModule);
        PersistenceManager.addModule(addedModule);
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash);

        return line.toString();
    }

    public static Admin deserialize(String[] tokens) {
        Admin admin = new Admin();
        admin.setUUID(tokens[0]);

        admin.setUsername(tokens[1]);
        admin.setPasswordHash(tokens[2]);

        return admin;
    }
}