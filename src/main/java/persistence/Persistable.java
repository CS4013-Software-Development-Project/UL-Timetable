package persistence;

public interface Persistable {
    String getUUID();
    void setUUID(String uuid);

    String serialize();
}