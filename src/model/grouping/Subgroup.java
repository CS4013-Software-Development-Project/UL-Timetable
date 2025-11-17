package model.grouping;

import model.module.Session;
import model.user.Student;
import java.util.List;

public class Subgroup {
    String id;

    StudentGroup parentGroup;
    Session session;
    List<Student> students;

    public Subgroup(String id, StudentGroup parentGroup) {
        this.id = id;
        this.parentGroup = parentGroup;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void getSession(Session session) {
        this.session = session;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }
}