package model.grouping;

import model.user.Student;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;
import util.SaveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Subgroup represents a group of Students that attends a Session together.
 */
public class Subgroup extends AbstractPersistable {
    /**
     * The ID of this subgroup.
     */
    String id;
    /**
     * The list of students this subgroup consists of.
     */
    List<Student> students;

    private Subgroup() {}
    /**
     * Creates a new instance of Subgroup.
     * @param id The String ID representing this Subgroup.
     */
    public Subgroup(String id) {
        this.id = id;
        this.students = new ArrayList<>();
    }

    /**
     * @return  id The String ID representing this Subgroup.
     */
    public String getId() {
        return id;
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
        line.append(SaveUtil.fastList(this.students));

        return line.toString();
    }

    public static Subgroup deserialize(String[] tokens) {
        Subgroup subgroup = new Subgroup();
        subgroup.setUUID(tokens[0]);

        subgroup.id = tokens[1];

        return subgroup;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        // Temporary?
        if (tokens.length != 3) { tokens = new String[] {tokens[0], tokens[1], "null"}; }
        this.students = SaveUtil.queryList(tokens[2], PersistenceManager.students);
    }

    @Override
    public void resolveDependencies() {
        for (Student student : this.students) {
            if (student != null && !PersistenceManager.students.containsKey(student.getUUID())) {
                student.resolveDependencies();
                PersistenceManager.students.put(student.getUUID(), student);
            }
        }
    }
}