package tests;

import model.grouping.Subgroup;
import model.module.Module;
import model.module.Programme;
import model.room.Room;
import model.room.RoomType;
import model.user.Leader;
import model.user.Student;
import persistence.PersistenceManager;

import org.junit.Test;
import util.SaveUtil;

/**
 * Class for testing persistence logic by repeatedly loading/unloading and saving data.
 *
 * @author Kuba Rodak (24436755)
 */
public class PersistenceTest {

    @Test
    public void testLoad() {
        PersistenceManager pm = new PersistenceManager("persistence");
        pm.load();
    }

    @Test
    public void testSaveData() {
        PersistenceManager pm = new PersistenceManager("persistence");

        Room[] r = {
                new Room("A1 - Lecture", 200, RoomType.LectureHall),
                new Room("B1 - Lecture", 200, RoomType.LectureHall),
                new Room("C1 - Lecture", 200, RoomType.LectureHall),
                new Room("D1 - Lecture", 200, RoomType.LectureHall),
                new Room("E1 - Lecture", 200, RoomType.LectureHall),

                new Room("A2 - Lab", 5, RoomType.Lab),
                new Room("B2 - Lab", 5, RoomType.Lab),
                new Room("C2 - Lab", 3, RoomType.Lab),
                new Room("D2 - Lab", 3, RoomType.Lab),
                new Room("E2 - Lab", 3, RoomType.Lab),
        };

        Student[] s = {
                new Student("Kuba", "welcome"),
                new Student("Leo", "welcome"),
                new Student("Willow", "welcome"),
                new Student("Finn", "welcome"),
                new Student("Caoimhe", "welcome"),
                new Student("Adam", "welcome"),
                new Student("Pedro", "welcome"),
                new Student("Lucretia", "welcome"),
                new Student("Misha", "welcome"),
                new Student("Cian", "welcome"),
                new Student("Hershel", "welcome"),
                new Student("Pirak", "welcome"),
                new Student("Shay", "welcome"),
        };

        Programme[] p = {
                new Programme("LM121 Computer Science Common Entry"),
                new Programme("LMWhatever ARM Dev"),
                new Programme("LM121-1 Computer Systems"),
                new Programme("LM121-2 Computer Game Development"),
                new Programme("LM121-3 Cybersecurity"),
        };

        Module[] m = {
                new Module("CS4004", "SOFTWARE TESTING AND INSPECTION"),
                new Module("CS4013", "OBJECT ORIENTED DEVELOPMENT"),
                new Module("CS4023", "OPERATING SYSTEMS"),
                new Module("CS4178", "SOFTWARE REQUIREMENTS AND MODELLING"),
                new Module("CS4416", "DATABASE SYSTEMS"),
        };

        Leader[] l = {
                new Leader("Faeq", "welcome"),
                new Leader("Michael", "welcome"),
                new Leader("Emil Vassev", "welcome"),
                new Leader("Fazilat Hojaji", "welcome"),
                new Leader("Nikola Nikolev", "welcome"),
        };

        Subgroup subgroup = new Subgroup("Group 1");

        for (Student st : s) {
            p[0].addStudent(st);
            subgroup.addStudent(st);
        }

        p[0].addLeader(l[0]);
        p[0].addLeader(l[1]);
        p[0].addLeader(l[2]);

        PersistenceManager.addRoom(r);
        PersistenceManager.addProgramme(p);
        PersistenceManager.addModule(m);
        PersistenceManager.addLeader(l);
        PersistenceManager.addStudent(s);

        pm.save();
    }

    //RUN THIS ONE TO REGENERATE & RESERIALIZE ALL DATA
    @Test
    public void regenerateData() {
        //delete persistence store first
        try {
            SaveUtil.deleteRecursive("persistence/");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        testSaveData();
    }

}
