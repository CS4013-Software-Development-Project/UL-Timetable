package model.user;

import model.module.Session;
import model.module.Programme;
import persistence.PersistenceManager;
import util.SaveUtil;

import java.util.List;

/**
 * Represents the {@link Student} user. It implements secure login logic from {@link User}.
 */
public class Student extends User {
    Programme programme;
    List<Session> sessions;

    /**
     * Creates a new Student.
     */
    public Student(String username, String password) {
        super(username, password);
    }

    /**
     * Creates a new Student.
     */
    public Student(String username, String passwordHash, Programme programme, List<Session> sessions) {
        super(username, "");
        this.setPasswordHash(passwordHash);

        this.programme = programme;
        this.sessions = sessions;

        this.programme.addStudent(this);
        for (Session session : sessions) {
            session.getSubgroup().addStudent(this);
        }
    }

    /**
     * Gets the {@link Programme} this {@link Student} is currently enrolled in.
     * @return the {@link Programme} this {@link Student} is currently enrolled in.
     */
    public Programme getProgramme() {
        return this.programme;
    }

    /**
     * Set the {@link Programme} this {@link Student} should now be enrolled in.
     * @param programme the new {@link Programme}.
     */
    public void setProgramme(Programme programme) {
        this.programme = programme;
        if (programme.getSubgroup() != null && !programme.getSubgroup().getStudents().contains(this))
            programme.addStudent(this);
    }

    // TODO: Account for group rework
    /**
     * Returns the sessions this user is part of.
     * @return the sessions this user is in.
     */
    public List<Session> getSessions() {
        return this.sessions;
    }

    /**
     * Add this user to a session.
     * @param session a new session this user should belong to.
     */
    public void addSession(Session session) {
        if (!this.sessions.contains(session))
            this.sessions.add(session);
        if (!session.getSubgroup().getStudents().contains(this))
            session.getSubgroup().addStudent(this);
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");
        if(this.programme != null)
            line.append(this.programme.getUUID()).append(",");
        else
            line.append("null").append(",");
        line.append(SaveUtil.fastList(this.sessions));

        return line.toString();
    }

    public static Student deserialize(String[] tokens) {
        Student student = new Student(tokens[1], "");
        student.setUUID(tokens[0]);

        student.setPasswordHash(tokens[2]);

        return student;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        // Temporary?
        if (tokens.length != 5) { tokens = new String[] {tokens[0], tokens[1], tokens[2], tokens[3], "null"}; }
        this.programme = PersistenceManager.programmes.get(tokens[3]);
        this.sessions = SaveUtil.queryList(tokens[4], PersistenceManager.sessions);
    }

    @Override
    public void resolveDependencies() {

        if (this.programme != null && !PersistenceManager.programmes.containsKey(programme.getUUID())) {
            this.programme.resolveDependencies();
            PersistenceManager.programmes.put(programme.getUUID(), this.programme);
        }

        if (this.sessions == null || this.sessions.isEmpty())
            return;
        for (Session session : this.sessions) {
            if (session != null && !PersistenceManager.sessions.containsKey(session.getUUID())) {
                session.resolveDependencies();
                PersistenceManager.sessions.put(session.getUUID(), session);
            }
        }
    }
}