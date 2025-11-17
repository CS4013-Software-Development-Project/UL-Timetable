package model.module;

import model.grouping.Subgroup;
import model.schedule.Timeslot;
import model.user.Leader;

public class Session {
    Module module;
    Timeslot timeslot;

    Subgroup groupAttending;
    Leader leader;

    SessionType sessionType;

    public Session(Module module, Timeslot timeslot, SessionType sessionType) {
        this.module = module;
        this.timeslot = timeslot;
        this.sessionType = sessionType;
    }

    public Session(Module module, Timeslot timeslot, SessionType sessionType,  Leader leader, Subgroup groupAttending) {
        this.module = module;
        this.timeslot = timeslot;
        this.sessionType = sessionType;
        this.leader = leader;
        this.groupAttending = groupAttending;
    }

    public void setSubgroup(Subgroup subgroup) {
        this.groupAttending = subgroup;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void assignTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }
}