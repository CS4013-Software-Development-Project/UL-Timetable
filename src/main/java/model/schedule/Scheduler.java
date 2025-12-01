package model.schedule;

import model.module.Session;
import model.room.Room;

import java.util.*;

/**
 * This behavioral service creates and schedules the TimeTable.
 * @author Finn
 */
public class Scheduler {

    private final List<Timeslot> timeslots = new ArrayList<>();
    private final Timetable timetable = new Timetable();

    public Scheduler() {}

    /**
     * Adds a timeslot to the scheduler.
     */
    public void addTimeslot(Timeslot timeslot) {
        timeslots.add(timeslot);
    }

    /**
     * Finds a timeslot object that matches the given time and room.
     */
    private Timeslot findTimeslot(Day day, Period period, Room room) {
        String key = day.toString() + " " + period.toString() + " " + room.getroomNumber() + "|";
        for (Timeslot t : timeslots) {
            if (t.toString().equals(key)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Schedules a session into a specific timeslot.
     */
    public boolean schedule(Session session, Day day, Period period, Room room) {
        Timeslot slot = findTimeslot(day, period, room);
        // If the slot doesn't exist.
        if (slot == null) {
            return false;
        }
        // If the slot is already occupied.
        if (timetable.getSession(day, period) != null) {
            return false;
        }

        timetable.setSession(day, period, session);
        return true;
    }

    /**
     * Unschedules a session from a specific day or period.
     */
    public void unschedule(Day day, Period period) {
        timetable.clear(day, period);
    }

    /**
     * Checks to see if a room is free.
     */
    public boolean isRoomFree(Room room, Day day, Period period) {
        Timeslot slot = findTimeslot(day, period, room);
        // If there is no timeslot available.
        if (slot == null) {
            return false;
        }
        return timetable.getSession(day, period) == null;
    }

    /**
     * Retrieves all scheduled sessions mapped to the timeslots that they occupy.
     */
    public Map<Timeslot, Session> getScheduledSessions() {
        Map<Timeslot, Session> map = new HashMap<>();

        for (Timeslot t : timeslots) {
            // Reconstructs a day, period, or room from its toString() method.
            String[] parts = t.toString().split(" ");
            Day day = Day.valueOf(parts[0]);
            Period period = Arrays.stream(Period.values())
                    .filter(p -> p.toString().equals(parts[1]))
                    .findFirst()
                    .orElse(null);

            if (period == null) continue;

            Session s = timetable.getSession(day, period);
            if (s != null) {
                map.put(t, s);
            }
        }

        return map;
    }

    /**
     * Gets the Timetable this Scheduler created.
     * @return the created timetable.
     */
    public Timetable getTimetable() {
        return timetable;
    }
}
