package model.schedule;

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
}
