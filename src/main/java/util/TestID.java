package util;

public class TestID {
    static int counter = 0;
    public static String getID() {
        return "" + counter++;
    }

    public static void setCounter(int counter) {
        TestID.counter = counter;
    }
    //TODO: Make the counter update to highest ID in persistence manager
}
