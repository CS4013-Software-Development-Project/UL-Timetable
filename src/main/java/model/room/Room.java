package model.room;

/**
 * Represents the capabilities of a physical Room where Timeslots become available in.
 */
public class Room {
    String roomNumber;
    int capacity;
    RoomType roomType;

    /**
     * Instantiates a new Room.
     * @param roomNumber The roomNumber/Room Number etc. of a room.
     * @param capacity The room capacity.
     * @param roomType The type of room this is.
     */
    public Room(String roomNumber, int capacity, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    /**
     * Gets the Room roomNumber.
     * @return The Room roomNumber.
     */
    public String getroomNumber() {
        return this.roomNumber;
    }

    /**
     * Gets the Room capacity.
     * @return The Room capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets the Room Type.
     * @return The Room Type.
     */
    public RoomType getRoomType() {
        return this.roomType;
    }
}
