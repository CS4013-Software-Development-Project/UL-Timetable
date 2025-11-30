package persistence;

/**
 * @author Kuba Rodak (24436755)
 */
public interface IPersistable {
    String getUUID();
    void setUUID(String uuid);

    String serialize();
    void resolveReferences(String[] tokens);
    void resolveDependencies();
}