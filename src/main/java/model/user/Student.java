package model.user;

import model.grouping.StudentGroup;
import model.grouping.Subgroup;
import model.module.Programme;
import persistence.AbstractPersistable;
import util.SaveUtil;

import java.util.List;

/**
 * The Student
 */
public class Student extends User {
    Programme programme;
    StudentGroup studentGroup;
    List<Subgroup> subgroups;

    /**
     * Creates a new Student.
     */
    public Student(String username, String password) {
        super(username, password);
    }

    /**
     * Creates a new Student.
     */
    public Student(String username, String passwordHash, Programme programme, StudentGroup studentGroup, List<Subgroup> subgroups) {
        super(username, "");
        this.setPasswordHash(passwordHash);

        this.programme = programme;
        this.studentGroup = studentGroup;
        this.subgroups = subgroups;

        this.programme.addStudent(this);
        this.studentGroup.addStudent(this);
        for (Subgroup subgroup : subgroups) {
            subgroup.addStudent(this);
        }
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
        if (programme.getStudentGroup() != null && !programme.getStudentGroup().getStudentList().contains(this))
            programme.addStudent(this);
    }

    public StudentGroup getStudentGroup() {
        return this.studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
        if (!studentGroup.getStudentList().contains(this))
            studentGroup.addStudent(this);
    }

    public List<Subgroup> getSubgroups() {
        return this.subgroups;
    }

    public void addSubgroup(Subgroup subgroup) {
        if (!this.subgroups.contains(subgroup))
            this.subgroups.add(subgroup);
        if (!subgroup.getStudents().contains(this))
            subgroup.addStudent(this);
    }

    @Override
    public String serialize() {
        StringBuilder line = new StringBuilder();
        line.append(this.getUUID()).append(",");
        line.append(this.getUsername()).append(",");
        line.append(this.passwordHash).append(",");
        line.append(this.programme.getUUID()).append(",");
        line.append(this.studentGroup.getUUID()).append(",");
        line.append(SaveUtil.fastList(this.subgroups));

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