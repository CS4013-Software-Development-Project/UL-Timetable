package model.grouping;

import model.module.Session;
import model.user.Student;
import persistence.AbstractPersistable;
import util.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Subgroup represents a group of Students that attends a Session together.
 */
public class Subgroup extends AbstractPersistable {
    String id;

    List<Student> students;

    public Subgroup() {}
    /**
     * Creates a new instance of Subgroup.
     * @param id The String ID representing this Subgroup.
     */
    public Subgroup(String id) {
        this.id = id;
        this.students = new ArrayList<Student>();
    }
    /**
     * Creates a new instance of Subgroup.
     * @param id The String ID representing this Subgroup.
     * @param students List of Student(s) in this SubGroup
     */
    public Subgroup(String id, List<Student> students) {
        this.id = id;
        this.students = students;
    }

    /**
     * Splits students into a list of ceil(number_of_students/maxStudentsPerGroup) Subgroups.
     * @param maxStudentsPerGroup Maximum amount of students to have in each group.
     */
    public List<Subgroup> splitGroup(int maxStudentsPerGroup) {return null;}

    /**
     * Adds a Student to this Subgroup.
     * @param student The Student to add to this Subgroup.
     */
    public void addStudent(Student student) {
        students.add(student);
    }
    /**
     * Removes a Student from this Subgroup.
     * @param student The Student to add to this Subgroup.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Gets the list of Students currently assigned to this Subgroup.
     * @return The list of Students currently assigned to this Subgroup.
     */
    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.id).append(",");

        line.append(SaveUtil.fastList(this.students)).append(",");

        return line.toString();
    }

    public static Subgroup deserialize(String line) {
        String[] tokens = line.split(",");
        Subgroup subgroup = new Subgroup();
        subgroup.setUUID(tokens[0]);
        subgroup.id = tokens[1];

        return subgroup;
    }
}