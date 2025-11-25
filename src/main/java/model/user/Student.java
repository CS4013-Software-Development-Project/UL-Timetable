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
        super(username, "");
        this.setPasswordHash(passwordHash);

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

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");
        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");
        line.append(this.programme.getUUID()).append(",");
        line.append(this.studentGroup.getUUID()).append(",");
        line.append(this.subgroup.getUUID()).append(",");

        return line.toString();
    }

    public static Student deserialize(String line) {
        String[] tokens = line.split(",");
        Student student = new Student(tokens[1], "");
        student.setPasswordHash(tokens[2]);
        student.setUUID(tokens[0]);

        return student;
    }
}