package main.java.model.grouping;

import main.java.model.module.Programme;
import main.java.model.user.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentGroup represents a group of students assigned to a Programme. They may not necessarily all attend the same sessions.
 */
public class StudentGroup {
    String id;
    Programme programme;

    List<Student> students;

    List<Subgroup> lectureGroups;
    List<Subgroup> labGroups;
    List<Subgroup> tutorialGroups;

    /**
     * Creates a new instance of StudentGroup.
     * @param id The String ID representing this StudentGroup.
     * @param programme The Programme this StudentGroup belongs to.
     */
    public StudentGroup(String id, Programme programme) {
        this.id = id;
        this.programme = programme;

        this.students = new ArrayList<>();
    }

    /**
     * Gets the ID of this StudentGroup.
     * @return The ID of this StudentGroup.
     */
    public String getId() {
        return id;
    }

    /**
     * Add a Student to this StudentGroup.
     * @param student The Student to be added to this StudentGroup.
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }
}