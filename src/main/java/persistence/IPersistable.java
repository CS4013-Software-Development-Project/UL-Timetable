package persistence;

public interface IPersistable {
    String getUUID();
    void setUUID(String uuid);

    String serialize();
    void resolveReferences(String[] tokens);
    void resolveDependencies();
}