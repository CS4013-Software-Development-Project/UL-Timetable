package model.user;

import model.grouping.StudentGroup;
import model.grouping.Subgroup;
import model.module.Programme;

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