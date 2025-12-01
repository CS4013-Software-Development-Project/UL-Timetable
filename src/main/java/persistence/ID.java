package persistence;

/**
 * The ID class is used to keep a running counter using for unique IDs.
 * They grow predictably from zero, which gives many benefits for example
 * having a running total of serialized objects.
 *
 * @author Kuba Rodak (24436755)
 */
public class ID {
    /**
     * Internal counter for generating IDs.
     */
    private static int counter = 0;

    /**
     * Returns the next available ID as a String. Auto-increments the counter.
     * @return a unique ID.
     */
    public static String getID() {
        return "" + counter++;
    }

    /**
     * Manually sets the counter to a specified value. Use when loading data
     * from storage to align new IDs and avoid collisions.
     * @param counter the new value to create IDs from.
     */
    public static void setCounter(int counter) {
        ID.counter = counter;
    }
}
