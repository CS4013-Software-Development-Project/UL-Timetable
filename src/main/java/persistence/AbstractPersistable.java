package persistence;

import util.TestID;

import java.util.UUID;

public abstract class AbstractPersistable implements IPersistable {
    private String uuid = TestID.getID();//UUID.randomUUID().toString();

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
