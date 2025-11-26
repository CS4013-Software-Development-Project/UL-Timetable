package model.module;
import model.grouping.StudentGroup;
import model.user.Leader;
import model.user.Student;
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
    StudentGroup studentGroup;

    /**
     * Creates a new instance of Programme.
     * @param name The localized name representing this Programme.
     */
    public Programme(String name) {
        this.name = name;
        this.modules = new ArrayList<>();
        this.leaders = new ArrayList<>();

        this.studentGroup = new StudentGroup(this.name + " Group 1", this);
    }

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
        for (Student s : studentGroup.getStudentList()) {
            s.setProgramme(this);
        }

        this.leaders = new ArrayList<>();
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
     * Adds a Student to the StudentGroup associated with this Programme.
     * @param student The Student to be added to the StudentGroup associated with this Programme.
     */
    public void addStudent(Student student) {
        this.studentGroup.addStudent(student);
        student.setProgramme(this);
        student.setStudentGroup(this.studentGroup);
    }

    /**
     * Removes a Student from the StudentGroup associated with this Programme.
     * @param student The Student to be removed from the StudentGroup associated with this Programme.
     */
    public void removeStudent(Student student) {
        if (this.studentGroup.getStudentList().contains(student))
        {
            this.studentGroup.removeStudent(student);
            student.setProgramme(null);
            student.setStudentGroup(null);
        }
    }

    /**
     * Gets the StudentGroup taking this Programme.
     * @return The StudentGroup taking this Programme.
     */
    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }

    /**
     * Sets the StudentGroup taking this Programme.
     * @param studentGroup The StudentGroup taking this Programme.
     */
    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
        if (studentGroup.getStudentList() != null)
            for (Student s : studentGroup.getStudentList()) {
                s.setProgramme(this);
                s.setStudentGroup(studentGroup);
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

        line.append(studentGroup.getUUID());

        return line.toString();
    }

    public static Programme deserialize(String line) {
        String[] tokens = line.split(",");

        Programme p = new Programme(tokens[1]);
        p.setUUID(tokens[0]);

        return p;
    }
}