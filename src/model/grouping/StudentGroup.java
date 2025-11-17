package model.grouping;

import model.module.Programme;
import model.user.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup {
    String id;
    Programme programme;

    List<Student> students;

    List<Subgroup> lectureGroups;
    List<Subgroup> labGroups;
    List<Subgroup> tutorialGroups;

    public StudentGroup(String id, Programme programme) {
        this.id = id;
        this.programme = programme;

        this.students = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}