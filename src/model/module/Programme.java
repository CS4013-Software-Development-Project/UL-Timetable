package model.module;
import model.grouping.StudentGroup;
import model.user.Leader;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Programme course at the University. Is composed of Modules and may have assigned Leaders.
 */
public class Programme {
    String name;
    List<Module> modules;
    List<Leader> leaders;
    StudentGroup studentGroup;

    /**
     * Creates a new instance of Programme.
     * @param name The localized name representing this Programme.
     * @param modules The list of Modules this Programme is composed of.
     * @param studentGroup The StudentGroup that is taking this Programme.
     */
    public Programme(String name, List<Module> modules, StudentGroup studentGroup) {
        this.name = name;
        this.modules = modules;
        this.studentGroup = studentGroup;
        this.leaders = new ArrayList<>();
    }

    /**
     * Adds a Module to this Programme.
     * @param module The Module to add to this Programme.
     */
    public void addModule(Module module) {
        this.modules.add(module);
    }

    /**
     * Gets a list of Module this Programme is composed of.
     * @return The list of Module this Programme is composed of.
     */
    public List<Module> getModules() {
        return this.modules;
    }

    /**
     * Adds a Leader to this Programme, allowing them to become available to conduct Sessions.
     * @param leader The Leader to be added.
     */
    public void addLeader(Leader leader) {
        this.leaders.add(leader);
    }

    /**
     * Removes a Leader from this Programme.
     * @param leader The Leader to be removed.
     */
    public void removeLeader(Leader leader) {
        this.leaders.remove(leader);
    }
}