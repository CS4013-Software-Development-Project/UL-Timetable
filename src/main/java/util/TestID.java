package util;

public class TestID {
    static int counter = 0;
    public static String getID() {
        return "" + counter++;
    }
}
