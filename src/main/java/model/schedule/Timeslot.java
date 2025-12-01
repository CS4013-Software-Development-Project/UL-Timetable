package model.schedule;

import model.room.Room;
import model.user.Student;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;
import util.SaveUtil;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * The Timeslot represents the availability of a specific Room during a specific Time.
 */
public class Timeslot extends AbstractPersistable {
    private Day day;
    private Period period;
    private Room room;

    private Timeslot() {}
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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.day).append(",");
        line.append(this.period).append(",");
        line.append(this.room.getUUID());

        return line.toString();
    }

    public static Timeslot deserialize(String[] tokens) {
        Timeslot timeslot = new Timeslot();
        timeslot.setUUID(tokens[0]);

        timeslot.day = Day.values()[Integer.parseInt(tokens[1])];
        timeslot.period = Period.values()[Integer.parseInt(tokens[2])];

        return timeslot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(day.toString());
        sb.append(" ");
        sb.append(period.toString());
        sb.append(" ");
        sb.append(room.getroomNumber());
        sb.append("|");

        return sb.toString();
    }

    @Override
    public void resolveReferences(String[] tokens) {
        this.room = PersistenceManager.rooms.get(tokens[3]);
    }

    @Override
    public void resolveDependencies() {
        if (this.room != null && !PersistenceManager.rooms.containsKey(this.room.getUUID())) {
            room.resolveDependencies();
            PersistenceManager.rooms.put(this.room.getUUID(), this.room);
        }
    }
}