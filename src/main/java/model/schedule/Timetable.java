package model.schedule;

import java.util.List;

public class Timetable {
    //6x10 rigid array because we dont want to change it
    //but its also at the same time easy to change with editing the respective enums!
    private final Timeslot[][] grid = new Timeslot[Day.values().length][Period.values().length];

    public Timeslot getTimeslot(Day day, Period period) {
        return this.grid[day.ordinal()][period.ordinal()];
    }

    public void setTimeslot(Day day, Period period, Timeslot timeslot) {
        this.grid[day.ordinal()][period.ordinal()] = timeslot;
    }

    public void clear(Day day, Period period) {
        this.grid[day.ordinal()][period.ordinal()] = null;
    }

}
