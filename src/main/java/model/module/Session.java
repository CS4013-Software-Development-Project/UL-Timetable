package model.module;

import model.grouping.Subgroup;
import model.schedule.Timeslot;
import model.user.Leader;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;

/**
 * A Session represents the performance of a Module during a Timeslot.
 * It is attended by a Subgroup of Students, and is conducted by a Leader.
 * A Session has a SessionType which delegates it as Lecture, Lab, or Tutorial.
 */
public class Session extends AbstractPersistable {
    Module module;
    Timeslot timeslot;

    Subgroup groupAttending;
    Leader leader;

    SessionType sessionType;

    private Session() {}
    /**
     * Creates a new Session.
     * @param module The Module this Session belongs to.
     * @param timeslot The Timeslot this Session takes place during.
     * @param sessionType The type of Session. Is this a Lecture, Lab, or Tutorial session?
     */
    public Session(Module module, Timeslot timeslot, SessionType sessionType) {
        this.module = module;
        this.timeslot = timeslot;
        this.sessionType = sessionType;

        module.addSession(this);
    }

    /**
     * Sets the Subgroup which will attend this Session.
     * @param subgroup The Subgroup in question.
     */
    public void setSubgroup(Subgroup subgroup) {
        this.groupAttending = subgroup;
    }

    /**
     * Sets the Leader that will conduct this Session.
     * @param leader The Leader that will conduct this Session.
     */
    public void setLeader(Leader leader) {
        this.leader = leader;

    }

    /**
     * Assigns a Timeslot to this Session.
     * @param timeslot The Timeslot to assign to this Session.
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Assigns a Module to this Session.
     * @param module The Module to assign to this Session.
     */
    public void setModule(Module module) {
        this.module = module;
        if (!module.getSessions().contains(this))
            module.addSession(this);
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(module.getUUID()).append(",");
        line.append(timeslot.getUUID()).append(",");
        line.append(groupAttending.getUUID()).append(",");
        line.append(leader.getUUID()).append(",");
        line.append(this.sessionType.ordinal());

        return line.toString();
    }

    public static Session deserialize(String[] tokens) {
        Session session = new Session();
        session.setUUID(tokens[0]);

        session.sessionType = SessionType.values()[Integer.parseInt(tokens[5])];

        return session;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        this.module = PersistenceManager.modules.get(tokens[1]);
        this.timeslot = PersistenceManager.timeslots.get(tokens[2]);
        this.groupAttending = PersistenceManager.subgroups.get(tokens[3]);
        this.leader = PersistenceManager.leaders.get(tokens[4]);
    }

    @Override
    public void resolveDependencies() {
        if (this.module != null && !PersistenceManager.modules.containsKey(this.module.getUUID())) {
            module.resolveDependencies();
            PersistenceManager.modules.put(this.module.getUUID(), this.module);
        }
        if (this.timeslot != null && !PersistenceManager.timeslots.containsKey(this.timeslot.getUUID())) {
            timeslot.resolveDependencies();
            PersistenceManager.timeslots.put(this.timeslot.getUUID(), this.timeslot);
        }
        if (this.groupAttending != null && !PersistenceManager.subgroups.containsKey(this.groupAttending.getUUID())) {
            groupAttending.resolveDependencies();
            PersistenceManager.subgroups.put(this.groupAttending.getUUID(), this.groupAttending);
        }
        if (this.leader != null && !PersistenceManager.leaders.containsKey(this.leader.getUUID())) {
            leader.resolveDependencies();
            PersistenceManager.leaders.put(this.leader.getUUID(), this.leader);
        }
    }
}