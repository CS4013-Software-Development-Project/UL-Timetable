package model.schedule;

import model.room.Room;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * The Timeslot represents the availability of a specific Room during a specific Time.
 */
public class Timeslot {
    DayOfWeek day;
    LocalTime startTime;
    int hoursDuration;
    Room room;

    /**
     * Creates a new Timeslot.
     * @param day The DayOfWeek this Timeslot takes place on.
     * @param startTime The LocalTime start time of this Timeslot.
     * @param hoursDuration The length of this timeslot, in hours.
     * @param room The Room this Timeslot references.
     */
    public Timeslot(DayOfWeek day, LocalTime startTime, int hoursDuration, Room room) {
        this.day = day;
        this.startTime = startTime;
        this.hoursDuration = hoursDuration;
        this.room = room;
    }
}