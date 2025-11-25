package model.schedule;

import model.room.Room;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * The Timeslot represents the availability of a specific Room during a specific Time.
 */
public class Timeslot {
    private final Day day;
    private final Period period;
    private final Room room;

    /**
     * Creates a new Timeslot.
     * @param day The Day this Timeslot takes place on.
     * @param period The Period of this Timeslot.
     * @param room The Room this Timeslot references.
     */
    public Timeslot(Day day, Period period, Room room) {
        this.day = day;
        this.period = period;
        this.room = room;
    }
}