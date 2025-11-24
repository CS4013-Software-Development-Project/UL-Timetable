package model.user;

import model.grouping.StudentGroup;
import model.grouping.Subgroup;
import model.module.Programme;

/**
 * The Student
 */
public class Student extends User {
    Programme programme;
    StudentGroup studentGroup;
    Subgroup subgroup;

    /**
     * Creates a new Student.
     */
    public Student(String username, String password) {
        super(username, password);
    }

    /**
     * Creates a new Student.
     */
    public Student(String username, String passwordHash, Programme programme, StudentGroup studentGroup, Subgroup subgroup) {
        super(username, passwordHash);

        this.programme = programme;
        this.studentGroup = studentGroup;
        this.subgroup = subgroup;
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Subgroup getSubgroup() {
        return this.subgroup;
    }

    public void setSubgroup(Subgroup subgroup) {
        this.subgroup = subgroup;
    }
}