package model.schedule;

import model.module.Session;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;

public class Timetable extends AbstractPersistable {
    //6x10 rigid array because we don't want to change it
    //but its also at the same time easy to change with editing the respective enums!
    private static final int days = Day.values().length;
    private static final int periods = Period.values().length;

    private Session[][] grid = new Session[days][periods];

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

            for (Session session : day) {
                if (session == null)
                    line.append("null").append("|");
                else
                    line.append(session.getUUID()).append("|");
            }

            // two || for the end of the day array
            line.append("|");
        }

        return line.toString();
    }

    public static Timetable deserialize(String[] tokens) {
        Timetable timetable = new Timetable();
        timetable.setUUID(tokens[0]);

        return timetable;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        String encoded = tokens[1];  // the serialized grid string

        // First split into days
        String[] dayStrings = encoded.split("\\|\\|");
        int days = Timetable.days;
        int slots = Timetable.periods;

        this.grid = new Session[days][slots];

        for (int d = 0; d < days; d++) {
            if (dayStrings[d].isEmpty()) {
                continue;
            }

            String[] items = dayStrings[d].split("\\|");

            for (int s = 0; s < slots; s++) {
                String token = items[s];

                if (token.equals("null"))
                    this.grid[d][s] = null;
                else
                    this.grid[d][s] = PersistenceManager.sessions.get(token);
            }
        }
    }
}
