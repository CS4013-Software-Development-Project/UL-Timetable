package main.java.model.room;

/**
 * Represents the capabilities of a physical Room where Timeslots become available in.
 */
public class Room {
    String id;
    int capacity;
    RoomType roomType;

    /**
     * Instantiates a new Room.
     * @param id The ID/Room Number etc. of a room.
     * @param capacity The room capacity.
     * @param roomType The type of room this is.
     */
    public Room(String id, int capacity, RoomType roomType) {
        this.id = id;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    /**
     * Gets the Room ID.
     * @return The Room ID.
     */
    public String getId() {
        return this.id;
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
