//// Needs to be transitioned elsewhere as sessions now handle differentiating groups via their SessionType property.
//
//package model.grouping;
//
//import model.module.Programme;
//import model.user.Student;
//import persistence.AbstractPersistable;
//import util.SaveUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * StudentGroup represents a group of students assigned to a Programme. They may not necessarily all attend the same sessions.
// */
//public class StudentGroup extends AbstractPersistable {
//    String id;
//    Programme programme;
//
//    List<Student> students;
//
//    List<Subgroup> lectureGroups;
//    List<Subgroup> labGroups;
//    List<Subgroup> tutorialGroups;
//
//    private StudentGroup() {}
//    /**
//     * Creates a new instance of StudentGroup.
//     * @param id The String ID representing this StudentGroup.
//     * @param programme The Programme this StudentGroup belongs to.
//     */
//    public StudentGroup(String id, Programme programme) {
//        this.id = id;
//        this.programme = programme;
//
//        this.students = new ArrayList<>();
//    }
//
//    /**
//     * Gets the ID of this StudentGroup.
//     * @return The ID of this StudentGroup.
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * Add a Student to this StudentGroup.
//     * @param student The Student to be added to this StudentGroup.
//     */
//    public void addStudent(Student student) {
//        if (students.contains(student))
//            return;
//        this.students.add(student);
//        if (student.getStudentGroup() != null && !student.getStudentGroup().getUUID().equals(this.getUUID()))
//            student.setStudentGroup(this);
//    }
//
//    /**
//     * Remove a Student from this StudentGroup.
//     * @param student The Student to be removed from this StudentGroup.
//     */
//    public void removeStudent(Student student) {
//        this.students.remove(student);
//        student.setStudentGroup(null);
//    }
//
//    public void setStudentList(List<Student> students) {
//        this.students = students;
//        for (Student student : students) {
//            if (student.getStudentGroup() == null || !student.getStudentGroup().getUUID().equals(this.getUUID()))
//                student.setStudentGroup(this);
//        }
//    }
//
//    public List<Student> getStudentList() {
//        return students;
//    }
//
//    public void setProgramme(Programme programme) {
//        if (this.programme != null && programme.getUUID().equals(this.programme.getUUID()))
//            return;
//        this.programme = programme;
//        programme.setStudentGroup(this);
//    }
//
//    public void generateSubGroups(int lectureLimit, int labLimit, int tutorialLimit) {
//        int studentCount = this.students.size();
//        this.lectureGroups = new ArrayList<>();
//        int loopCount = 1;
//        while (studentCount >= 1) {
//            Subgroup lectureGroup = new Subgroup("Lec " + loopCount, this);
//            this.lectureGroups.add(lectureGroup);
//            for (int i = 0; i < lectureLimit && i < this.students.size(); i++) {
//                lectureGroup.addStudent(this.students.get(i));
//                studentCount--;
//            }
//            loopCount++;
//        }
//
//        studentCount = this.students.size();
//        this.labGroups = new ArrayList<>();
//        loopCount = 1;
//        while (studentCount >= 1) {
//            Subgroup lectureGroup = new Subgroup("Lab " + loopCount, this);
//            this.labGroups.add(lectureGroup);
//            for (int i = 0; i < labLimit && i < this.students.size(); i++) {
//                lectureGroup.addStudent(this.students.get(i));
//                studentCount--;
//            }
//            loopCount++;
//        }
//
//        studentCount = this.students.size();
//        this.tutorialGroups = new ArrayList<>();
//        loopCount = 1;
//        while (studentCount >= 1) {
//            Subgroup lectureGroup = new Subgroup("Lab " + loopCount, this);
//            this.tutorialGroups.add(lectureGroup);
//            for (int i = 0; i < tutorialLimit && i < this.students.size(); i++) {
//                lectureGroup.addStudent(this.students.get(i));
//                studentCount--;
//            }
//            loopCount++;
//        }
//    }
//
//    public void setLectureGroups(List<Subgroup> lectureGroups) {
//        if (lectureGroups == null || lectureGroups.isEmpty())
//            return;
//        this.lectureGroups = lectureGroups;
//        for (Subgroup subgroup : lectureGroups) {
//            if (subgroup != null)
//                subgroup.setParentGroup(this);
//        }
//    }
//
//    public List<Subgroup> getLectureGroups() {
//        return lectureGroups;
//    }
//
//    public void setLabGroups(List<Subgroup> labGroups) {
//        if (labGroups == null || labGroups.isEmpty())
//            return;
//        this.labGroups = labGroups;
//        for (Subgroup subgroup : labGroups) {
//            if (subgroup != null)
//                subgroup.setParentGroup(this);
//        }
//    }
//
//    public List<Subgroup> getLabGroups() {
//        return labGroups;
//    }
//
//    public void setTutorialGroups(List<Subgroup> tutorialGroups) {
//        if (tutorialGroups == null || tutorialGroups.isEmpty())
//            return;
//        this.tutorialGroups = tutorialGroups;
//        for (Subgroup subgroup : tutorialGroups) {
//            if (subgroup != null)
//                subgroup.setParentGroup(this);
//        }
//    }
//
//    public List<Subgroup> getTutorialGroups() {
//        return tutorialGroups;
//    }
//
//    public List<Subgroup> getAllSubgroups() {
//        List<Subgroup> subgroups = new ArrayList<>();
//        subgroups.addAll(this.lectureGroups);
//        subgroups.addAll(this.labGroups);
//        subgroups.addAll(this.tutorialGroups);
//        return subgroups;
//    }
//
//    public Subgroup[]  getAllSubgroupsArray() {
//        return getAllSubgroups().toArray(new Subgroup[0]);
//    }
//
//    public String serialize() {
//        StringBuilder line = new StringBuilder();
//        line.append(this.getUUID()).append(",");
//        line.append(this.getId()).append(",");
//        line.append(this.programme.getUUID()).append(",");
//
//        line.append(SaveUtil.fastList(this.students)).append(",");
//        line.append(SaveUtil.fastList(this.lectureGroups)).append(",");
//        line.append(SaveUtil.fastList(this.labGroups)).append(",");
//        line.append(SaveUtil.fastList(this.tutorialGroups)).append(",");
//
//        return line.toString();
//    }
//
//    public static StudentGroup deserialize(String line) {
//        String[] tokens = line.split(",");
//        StudentGroup group = new StudentGroup();
//        group.setUUID(tokens[0]);
//        group.id = tokens[1];
//
//        return group;
//    }
//}