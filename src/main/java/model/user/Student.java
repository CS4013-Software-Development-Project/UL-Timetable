package model.user;

import model.grouping.Subgroup;
import model.module.Programme;
import persistence.AbstractPersistable;
import persistence.PersistenceManager;
import util.SaveUtil;

import java.util.List;

/**
 * The Student
 */
public class Student extends User {
    Programme programme;
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
    public Student(String username, String passwordHash, Programme programme, List<Subgroup> subgroups) {
        super(username, "");
        this.setPasswordHash(passwordHash);

        this.programme = programme;
        this.subgroups = subgroups;

        this.programme.addStudent(this);
        for (Subgroup subgroup : subgroups) {
            subgroup.addStudent(this);
        }
    }

    public Programme getProgramme() {
        return this.programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
        if (programme.getSubgroup() != null && !programme.getSubgroup().getStudents().contains(this))
            programme.addStudent(this);
    }

    // TODO: Account for group rework
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
        line.append(SaveUtil.fastList(this.subgroups));

        return line.toString();
    }

    public static Student deserialize(String[] tokens) {
        Student student = new Student(tokens[1], "");
        student.setUUID(tokens[0]);

        student.setPasswordHash(tokens[2]);

        return student;
    }

    @Override
    public void resolveReferences(String[] tokens) {
        this.programme = PersistenceManager.programmes.get(tokens[3]);
        this.subgroups = SaveUtil.queryList(tokens[4], PersistenceManager.subgroups);
    }

    @Override
    public void resolveDependencies() {

        if (this.programme != null && !PersistenceManager.programmes.containsKey(programme.getUUID())) {
            this.programme.resolveDependencies();
            PersistenceManager.programmes.put(programme.getUUID(), this.programme);
        }

        for (Subgroup subgroup : this.subgroups) {
            if (subgroup != null && !PersistenceManager.subgroups.containsKey(subgroup.getUUID())) {
                subgroup.resolveDependencies();
                PersistenceManager.subgroups.put(subgroup.getUUID(), subgroup);
            }
        }
    }
}