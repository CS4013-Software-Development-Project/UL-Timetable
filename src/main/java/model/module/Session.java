package model.module;

import model.grouping.Subgroup;
import model.schedule.Timeslot;
import model.user.Leader;
import persistence.AbstractPersistable;

/**
 * A Session represents the performance of a Module during a Timeslot. It is attended by a Subgroup of Students, and is conducted by a Leader.
 */
public class Session {
    Module module;
    Timeslot timeslot;

    Subgroup groupAttending;
    Leader leader;

    SessionType sessionType;

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
    }

    /**
     * Creates a new Session.
     * @param module The Module this Session belongs to.
     * @param timeslot The Timeslot this Session takes place during.
     * @param sessionType The type of Session. Is this a Lecture, Lab, or Tutorial session?
     * @param leader The Leader conducting this Session.
     * @param groupAttending The Subgroup of Students set to attend this Session.
     */
    public Session(Module module, Timeslot timeslot, SessionType sessionType,  Leader leader, Subgroup groupAttending) {
        this.module = module;
        this.timeslot = timeslot;
        this.sessionType = sessionType;
        this.leader = leader;
        this.groupAttending = groupAttending;
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
}