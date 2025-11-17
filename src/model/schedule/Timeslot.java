package model.schedule;

import model.room.Room;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Timeslot {
    DayOfWeek day;
    LocalTime startTime;
    int hoursDuration;
    Room room;

    public Timeslot(DayOfWeek day, LocalTime startTime, int hoursDuration, Room room) {
        this.day = day;
        this.startTime = startTime;
        this.hoursDuration = hoursDuration;
        this.room = room;
    }
}