package persistence;

/**
 * Base implementation of {@link IPersistable} that handles UUID
 * generation and provides empty hook methods for reference
 * resolution.
 */
public abstract class AbstractPersistable implements IPersistable {
    /** The unique identifier for this object. */
    private String uuid = ID.getID();

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void resolveReferences(String[] tokens) {}

    @Override
    public void resolveDependencies() {}
}
