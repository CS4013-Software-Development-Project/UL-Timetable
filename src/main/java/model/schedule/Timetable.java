package model.schedule;

import model.module.Session;
import persistence.AbstractPersistable;

import java.util.List;

public class Timetable extends AbstractPersistable {
    //6x10 rigid array because we don't want to change it
    //but its also at the same time easy to change with editing the respective enums!
    private final Session[][] grid = new Session[Day.values().length][Period.values().length];

    public Session getSession(Day day, Period period) {
        return this.grid[day.ordinal()][period.ordinal()];
    }

    public void setSession(Day day, Period period, Session session) {
        this.grid[day.ordinal()][period.ordinal()] = session;
    }

    public void clear(Day day, Period period) {
        this.grid[day.ordinal()][period.ordinal()] = null;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();

        for (Session[] day : this.grid) {
            for (Session ts : day) {
                if(ts == null)
                    line.append("null").append("|");
                else
                    line.append(ts.getUUID()).append("|");
            }
            line.append("|"); // two || for the end of the day array
        }

        return line.toString();
    }

    //Timetable does not have a deserialize function.
    //Due to being entirely a referential type, it must be constructed manually.
}
