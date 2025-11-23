package main.java.model.user;

import main.java.model.grouping.StudentGroup;
import main.java.model.grouping.Subgroup;
import main.java.model.module.Programme;

/**
 * The Student
 */
public class Student extends User {
    Programme programme;
    StudentGroup studentGroup;
    Subgroup subgroup;

    public Student(String username, String passwordHash, Programme programme, StudentGroup studentGroup, Subgroup subgroup) {
        super(username, passwordHash);

        this.programme = programme;
        this.studentGroup = studentGroup;
        this.subgroup = subgroup;
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }

    public Subgroup getSubgroup() {
        return this.subgroup;
    }
}