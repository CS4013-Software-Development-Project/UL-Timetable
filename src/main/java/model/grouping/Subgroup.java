package model.grouping;

import model.module.Session;
import model.user.Student;
import java.util.List;

/**
 * Subgroup represents a group of Students that attends a Session together.
 */
public class Subgroup {
    String id;

    StudentGroup parentGroup;
    Session session;
    List<Student> students;

    /**
     * Creates a new instance of Subgroup.
     * @param id The String ID representing this Subgroup.
     * @param parentGroup The StudentGroup this Subgroup belongs to.
     */
    public Subgroup(String id, StudentGroup parentGroup) {
        this.id = id;
        this.parentGroup = parentGroup;
    }

    /**
     * Sets the Session that this Subgroup attends.
     * @param session The Session that this Subgroup attends.
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Gets the Session that this Subgroup attends.
     */
    public void getSession() {
        this.session = session;
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
}