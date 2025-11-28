
//import model.grouping.StudentGroup;
import model.module.Module;
import model.module.Programme;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import model.user.Admin;
import persistence.PersistenceManager;

public class ULTimetable {

    private static final String dataDir = "persistence";

    public static void main(String[] args) {

        //On cold boot: Load everything!
        PersistenceManager pm = new PersistenceManager(dataDir);
        pm.load();

        //testCreateData();

        pm.save();
    }

    static void testCreateData() {
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

        for (Student st : s) {
            p[0].addStudent(st);
        }

        p[0].addLeader(l[0]);
        p[0].addLeader(l[1]);
        p[0].addLeader(l[2]);

        PersistenceManager.addProgramme(p);
        PersistenceManager.addModule(m);
        PersistenceManager.addLeader(l);
        PersistenceManager.addStudent(s);
    }
}