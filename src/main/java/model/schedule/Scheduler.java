package model.schedule;

import model.module.Session;
import model.module.Module;
import model.module.SessionType;
import model.room.Room;
import model.room.RoomType;
import model.user.Student;
import persistence.PersistenceManager;

import java.util.*;

/**
 * This behavioral service creates and schedules the TimeTable.
 * @author Finn
 * @author Kuba Rodak
 */
public class Scheduler {

    private final List<Timeslot> timeslots = new ArrayList<>();
    private final Timetable timetable = new Timetable();

    static Timeslot[][] timeslotGrid;




    public static void populateRoomTimeslots() {
        Scheduler.timeslotGrid = new Timeslot[Timetable.days][Timetable.periods];

        for (Timeslot timeslot : PersistenceManager.timeslots.values()) {
            if(timeslot != null)
                timeslotGrid[timeslot.getDay().ordinal()][timeslot.getPeriod().ordinal()] = timeslot;
        }

        for (int day = Day.first(); day <= Day.last(); day++) {
            for (int time = Period.first(); time <= Period.last(); time++) {
                for (Room room : PersistenceManager.rooms.values()) {
                    //timeslot exists here?
                    if (room != null && timeslotGrid[day][time] == null) {
                        Timeslot ts = new Timeslot(Day.values()[day], Period.values()[time], room);
                        timeslotGrid[day][time] = ts; //potentially optional?
                        //add timeslot to store
                        PersistenceManager.addTimeslot(ts);
                    }
                }
            }
        }
    }

    public static Timetable scheduleStudent(Student student) {
        return scheduleStudent(student, 3, 1, 1);
    }
    public static Timetable scheduleStudent(Student student, int totalLectures, int totalLabs, int totalTutorials) {
        //first get student's module list
        List<Module> ms = student.getProgramme().getModules();

        boolean[][] alreadyScheduled = new boolean[Timetable.days][Timetable.periods];

        for(Module m : ms) {
            //lectures
            for(int i = 0; i < totalLectures; i++) {
                //first, check if module already has a session (that isnt scheduled here yet)
                for(Session session : m.getSessions()) {
                    if (session != null &&
                            session.getTimeslot() != null &&
                            session.getTimeslot().getRoom().getCapacity() >
                                    session.getSubgroup().getStudents().size() &&
                            !alreadyScheduled
                                    [session.getTimeslot().getDay().ordinal()]
                                    [session.getTimeslot().getPeriod().ordinal()] &&
                            session.getSessionType() == SessionType.Lecture
                    ) {
                        //phew! all checks passed.
                        alreadyScheduled
                                [session.getTimeslot().getDay().ordinal()]
                                [session.getTimeslot().getPeriod().ordinal()] = true;
                        student.addSession(session);
                    } else {
                        //make another session for this module
                        Session newSession = new Session(m,
                                getNextAvailableTimeslot(RoomType.LectureHall),
                                SessionType.Lecture);
                        student.addSession(newSession);
                        PersistenceManager.addSession(newSession);
                    }
                }
            }
            //labs
            for(int i = 0; i < totalLabs; i++) {
                for(Session session : m.getSessions()) {
                    if (session != null &&
                            session.getTimeslot() != null &&
                            session.getTimeslot().getRoom().getCapacity() >
                                    session.getSubgroup().getStudents().size() &&
                            !alreadyScheduled
                                    [session.getTimeslot().getDay().ordinal()]
                                    [session.getTimeslot().getPeriod().ordinal()] &&
                            session.getSessionType() == SessionType.Lab
                    ) {
                        //phew! all checks passed.
                        alreadyScheduled
                                [session.getTimeslot().getDay().ordinal()]
                                [session.getTimeslot().getPeriod().ordinal()] = true;
                        student.addSession(session);
                    } else {
                        //make another session for this module
                        Session newSession = new Session(m,
                                getNextAvailableTimeslot(RoomType.Lab),
                                SessionType.Lab);
                        student.addSession(newSession);
                        PersistenceManager.addSession(newSession);
                    }
                }
            }
            //tutorials
            for(int i = 0; i < totalTutorials; i++) {
                for(Session session : m.getSessions()) {
                    if (session != null &&
                            session.getTimeslot() != null &&
                            session.getTimeslot().getRoom().getCapacity() >
                                    session.getSubgroup().getStudents().size() &&
                            !alreadyScheduled
                                    [session.getTimeslot().getDay().ordinal()]
                                    [session.getTimeslot().getPeriod().ordinal()] &&
                            session.getSessionType() == SessionType.Tutorial
                    ) {
                        //phew! all checks passed.
                        alreadyScheduled
                                [session.getTimeslot().getDay().ordinal()]
                                [session.getTimeslot().getPeriod().ordinal()] = true;
                        student.addSession(session);
                    } else {
                        //make another session for this module
                        Session newSession = new Session(m,
                                getNextAvailableTimeslot(RoomType.Lab),
                                SessionType.Tutorial);
                        student.addSession(newSession);
                        PersistenceManager.addSession(newSession);
                    }
                }
            }
        }

        return Scheduler.getTimetableFromStudent(student);
    }

    private static Timeslot getNextAvailableTimeslot(RoomType roomType) {
        Session[][] sessionGrid = new Session[Timetable.days][Timetable.periods];
        //first get session grid
        for (Session s : PersistenceManager.sessions.values()) {
            if (s.getTimeslot() != null) {
                int day = s.getTimeslot().getDay().ordinal();
                int period = s.getTimeslot().getPeriod().ordinal();
                sessionGrid[day][period] = s;
            }
        }

        for (Day day : Day.values()) {
            for (Period period : Period.values()) {
                if (sessionGrid[day.ordinal()][period.ordinal()] == null)
                    return Scheduler.timeslotGrid[day.ordinal()][period.ordinal()];
            }
        }

        return null;
    }

    public static Timetable getTimetableFromStudent(Student student) {
        Timetable timetable = new Timetable();

        for(Session session : student.getSessions()) {
            if (session == null || session.getTimeslot() == null)
                continue;

            timetable.setSession(session.getTimeslot().getDay(), session.getTimeslot().getPeriod(), session);
        }

        return timetable;
    }




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
        timetable.clearSession(day, period);
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
