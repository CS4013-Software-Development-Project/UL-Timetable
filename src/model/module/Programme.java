package model.module;
import model.grouping.StudentGroup;
import model.user.Leader;

import java.util.ArrayList;
import java.util.List;

public class Programme {
    String name;
    List<Module> modules;
    List<Leader> leaders;
    StudentGroup studentGroup;

    public Programme(String name, List<Module> modules, StudentGroup studentGroup) {
        this.name = name;
        this.modules = modules;
        this.studentGroup = studentGroup;
        this.leaders = new ArrayList<>();
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void addLeader(Leader leader) {
        this.leaders.add(leader);
    }

    public void removeLeader(Leader leader) {
        this.leaders.remove(leader);
    }
}