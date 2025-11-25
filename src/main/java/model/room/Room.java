package model.room;

import persistence.AbstractPersistable;

/**
 * Represents the capabilities of a physical Room where Timeslots become available in.
 */
public class Room extends AbstractPersistable {
    private String roomNumber;
    private int capacity;
    private RoomType roomType;

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

    public void setroomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the Room capacity.
     * @return The Room capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the Room Type.
     * @return The Room Type.
     */
    public RoomType getRoomType() {
        return this.roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        return "";
    }

    public static Room deserialize(String line) {
        String[] tokens = line.split(",");
        Room room = new Room(
                tokens[1],
                Integer.parseInt(tokens[2]),
                RoomType.values()[Integer.parseInt(tokens[2])]
        );

        room.setUUID(tokens[0]);

        return room;
    }
}
