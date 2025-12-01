package model.schedule;

/**
 * Represents an hour that a session can start on. By using
 * an enum, we have better control over the time limits on
 * when and when not can sessions be scheduled.
 */
public enum Period {
    H09(9),
    H10(10),
    H11(11),
    H12(12),
    H13(13),
    H14(14),
    H15(15),
    H16(16),
    H17(17),
    H18(18);

    public final int hour;
    Period(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        if (this ==  H09) {
            return "09:00";
        }
        else {
            return this.hour + ":00";
        }
    }

    public static int first() {
        return Period.values()[0].ordinal();
    }

    public static int last() {
        return Period.values().length - 1;
    }
}
