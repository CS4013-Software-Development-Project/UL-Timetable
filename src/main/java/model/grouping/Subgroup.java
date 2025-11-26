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

    StudentGroup parentGroup;
    Session session;
    List<Student> students;

    private Subgroup() {}
    /**
     * Creates a new instance of Subgroup.
     * @param id The String ID representing this Subgroup.
     * @param parentGroup The StudentGroup this Subgroup belongs to.
     */
    public Subgroup(String id, StudentGroup parentGroup) {
        this.id = id;
        this.parentGroup = parentGroup;
        this.students = new ArrayList<>();
    }

    /**
     * Sets the Session that this Subgroup attends.
     * @param session The Session that this Subgroup attends.
     */
    public void setSession(Session session) {
        this.session = session;
        session.setSubgroup(this);
    }

    /**
     * Gets the Session that this Subgroup attends.
     */
    public Session getSession() {
        return this.session;
    }

    /**
     * Adds a Student to this Subgroup.
     * @param student The Student to add to this Subgroup.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Gets the list of Students currently assigned to this Subgroup.
     * @return The list of Students currently assigned to this Subgroup.
     */
    public List<Student> getStudents() {
        return students;
    }

    public void setParentGroup(StudentGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");

        line.append(this.id).append(",");
        line.append(this.parentGroup.getUUID()).append(",");
        line.append(this.session.getUUID()).append(",");

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