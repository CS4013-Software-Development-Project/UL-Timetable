package model.grouping;

import model.module.Programme;
import model.user.Student;
import persistence.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentGroup represents a group of students assigned to a Programme. They may not necessarily all attend the same sessions.
 */
public class StudentGroup extends AbstractPersistable {
    String id;
    Programme programme;

    List<Student> students;

    List<Subgroup> lectureGroups;
    List<Subgroup> labGroups;
    List<Subgroup> tutorialGroups;

    private StudentGroup() {}
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

    /**
     * Remove a Student from this StudentGroup.
     * @param student The Student to be removed from this StudentGroup.
     */
    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public void setStudentList(List<Student> students) {
        this.students = students;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public void setLectureGroups(List<Subgroup> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }

    public void setLabGroups(List<Subgroup> labGroups) {
        this.labGroups = labGroups;
    }

    public void setTutorialGroups(List<Subgroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");
        line.append(this.getId()).append(",");
        line.append(this.programme.getUUID()).append(",");

        for (Student student : this.students) {
            line.append(student.getUUID()).append("|");
        }
        line.append(",");

        for (Subgroup group : this.lectureGroups) {
            line.append(group.getUUID()).append("|");
        }
        line.append(",");

        for (Subgroup group : this.labGroups) {
            line.append(group.getUUID()).append("|");
        }
        line.append(",");

        for (Subgroup group : this.tutorialGroups) {
            line.append(group.getUUID()).append("|");
        }

        return line.toString();
    }

    public static StudentGroup deserialize(String line) {
        String[] tokens = line.split(",");
        StudentGroup group = new StudentGroup();
        group.setUUID(tokens[0]);
        group.id = tokens[1];

        return group;
    }
}