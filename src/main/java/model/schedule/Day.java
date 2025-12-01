package model.schedule;

/**
 * Represents all the days scheduling can occur on.
 * By using this enum instead of Date or LocalTime, we
 * can more easily add/remove dates.
 */
public enum Day {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday;

    @Override
    public String toString() {
        switch (this) {
            case Monday:
                return "Monday";
            case Tuesday:
                return "Tuesday";
            case Wednesday:
                return "Wednesday";
            case Thursday:
                return "Thursday";
            case Friday:
                return "Friday";
            case Saturday:
                return "Saturday";
        }
        return "";
    }

    public static int first() {
        return Day.values()[0].ordinal();
    }

    public static int last() {
        return Day.values().length - 1;
    }
}
