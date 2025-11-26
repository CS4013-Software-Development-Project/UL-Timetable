package model.module;
// import model.grouping.StudentGroup;
import model.user.Leader;
import model.user.Student;
import model.grouping.Subgroup;
import persistence.AbstractPersistable;
import util.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Programme course at the University. Is composed of Modules and may have assigned Leaders.
 */
public class Programme extends AbstractPersistable {
    String name;
    List<Module> modules;
    List<Leader> leaders;
    Subgroup students;

    /**
     * Creates a new instance of Programme.
     * @param name The localized name representing this Programme.
     */
    public Programme(String name) {
        this.name = name;
        this.modules = new ArrayList<>();
        this.leaders = new ArrayList<>();

        this.students = new Subgroup(this.name + " Group 1");
    }

    /**
     * Creates a new instance of Programme.
     * @param name The localized name representing this Programme.
     * @param modules The list of Modules this Programme is composed of.
     * @param students The Subgroup that is taking this Programme.
     */
    public Programme(String name, List<Module> modules, Subgroup students) {
        this.name = name;
        this.modules = modules;
        this.students = students;
        for (Student s : students.getStudents()) {
            s.setProgramme(this);
        }

        this.leaders = new ArrayList<>();
    }

    /**
     * Gets Programme name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a Module to this Programme.
     * @param module The Module to add to this Programme.
     */
    public void addModule(Module module) {
        if (this.modules.contains(module))
            return;
        this.modules.add(module);
    }

    /**
     * Gets a list of Modules this Programme is composed of.
     * @return The list of Modules this Programme is composed of.
     */
    public List<Module> getModules() {
        return this.modules;
    }

    /**
     * Sets the Module list this Programme is composed of.
     * @param modules The list of Modules this Programme should be composed of.
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * Adds a Module from this Programme.
     * @param module The Module to remove from this Programme.
     */
    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    /**
     * Adds a Student to the Subgroup associated with this Programme.
     * @param student The Student to be added to the Subgroup associated with this Programme.
     */
    public void addStudent(Student student) {
        this.students.addStudent(student);
        student.setProgramme(this);
    }

    /**
     * Removes a Student from the Subgroup associated with this Programme.
     * @param student The Student to be removed from the Subgroup associated with this Programme.
     */
    public void removeStudent(Student student) {
        if (this.students.getStudents().contains(student))
        {
            this.students.removeStudent(student);
            student.setProgramme(null);
        }
    }

    /**
     * Gets the Subgroup taking this Programme.
     * @return The Subgroup taking this Programme.
     */
    public Subgroup getSubgroup() {
        return this.students;
    }

    /**
     * Sets the StudentGroup taking this Programme.
     * @param studentGroup The Subgroup taking this Programme.
     */
    public void setSubgroup(Subgroup studentGroup) {
        this.students = studentGroup;
        if (studentGroup.getStudents() != null)
            for (Student s : studentGroup.getStudents()) {
                s.setProgramme(this);
            }
    }

    /**
     * Adds a Leader to this Programme, allowing them to become available to conduct Sessions.
     * @param leader The Leader to be added.
     */
    public void addLeader(Leader leader) {
        if (this.leaders.contains(leader))
            return;
        this.leaders.add(leader);
        leader.addLedProgramme(this);
    }

    /**
     * Removes a Leader from this Programme.
     * @param leader The Leader to be removed.
     */
    public void removeLeader(Leader leader) {
        this.leaders.remove(leader);
        leader.removeLedProgramme(this);
    }

    /**
     * Gets a list of Leaders leading this Programme.
     * @return The list of Leaders leading this Programme.
     */
    public List<Leader> getLeaders() {
        return leaders;
    }

    /**
     * Sets the list of Leaders leading this Programme.
     * @param leaders The list of Leaders to lead this Programme.
     */
    public void setLeaders(List<Leader> leaders) {
        this.leaders = leaders;
        for (Leader leader : leaders) {
            if(!leader.getLedProgrammes().contains(this))
                leader.addLedProgramme(this);
        }
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.name).append(",");
        line.append(SaveUtil.fastList(this.modules)).append(",");
        line.append(SaveUtil.fastList(this.leaders)).append(",");

        line.append(students.getUUID());

        return line.toString();
    }

    public static Programme deserialize(String line) {
        String[] tokens = line.split(",");

        Programme p = new Programme(tokens[1]);
        p.setUUID(tokens[0]);

        return p;
    }
}