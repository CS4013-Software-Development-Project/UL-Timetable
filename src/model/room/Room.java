package model.room;

public class Room {
    String id;
    int capacity;
    RoomType roomType;

    public Room(String id, int capacity, RoomType roomType) {
        this.id = id;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    public String getId() {
        return this.id;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public RoomType getRoomType() {
        return this.roomType;
    }
}
