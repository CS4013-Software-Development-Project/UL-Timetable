package persistence;

import model.grouping.StudentGroup;
import model.grouping.Subgroup;
import model.module.Module;
import model.module.Programme;
import model.module.Session;
import model.room.Room;
import model.schedule.Day;
import model.schedule.Period;
import model.schedule.Timeslot;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersistenceManager {
    String dataDirectory;

    Repository moduleRepo;
    Repository programmeRepo;
    Repository adminRepo;
    Repository leaderRepo;
    Repository studentRepo;
    Repository roomRepo;
    Repository subgroupRepo;
    Repository groupRepo;
    Repository timeslotRepo;
    Repository sessionRepo;
    Repository timetableRepo;


    public PersistenceManager(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        moduleRepo = new Repository(this.dataDirectory + "modules");
        programmeRepo = new Repository(this.dataDirectory + "programmes");
        adminRepo = new Repository(this.dataDirectory + "admins");
        leaderRepo = new Repository(this.dataDirectory + "leaders");
        studentRepo = new Repository(this.dataDirectory + "students");
        roomRepo = new Repository(this.dataDirectory + "rooms");
        subgroupRepo = new Repository(this.dataDirectory + "subgroups");
        groupRepo = new Repository(this.dataDirectory + "groups");
        timeslotRepo = new Repository(this.dataDirectory + "timeslots");
        sessionRepo = new Repository(this.dataDirectory + "sessions");
        timetableRepo = new Repository(this.dataDirectory + "timetables");
    }

    private void load() {
        //Phase 1: Load Modules
        HashMap<String, Module> modules = new HashMap<>();
        for (String line : moduleRepo.readAll()) {
            String[] tokens = line.split(",");
            modules.put(tokens[0], Module.deserialize(line));
        }

        //Phase 2: Load Programmes
        HashMap<String, Programme> programmes = new HashMap<>();
        for (String line : programmeRepo.readAll()) {
            String[] tokens = line.split(",");
            programmes.put(tokens[0], Programme.deserialize(line));
        }

        //Phase 3: Load Admins
        HashMap<String, Admin> admins = new HashMap<>();
        for (String line : adminRepo.readAll()) {
            String[] tokens = line.split(",");
            admins.put(tokens[0], Admin.deserialize(line));
        }

        //Phase 4: Load Leaders
        HashMap<String, Leader> leaders = new HashMap<>();
        for (String line : leaderRepo.readAll()) {
            String[] tokens = line.split(",");
            Leader leader = Leader.deserialize(line);
            //fill in led programme list
            for (String programmeUUID : tokens[3].split("\\|")) {
                leader.addLedProgramme(programmes.get(programmeUUID)); //ooh yeah its all comin togethr
            }
            leaders.put(tokens[0], leader);
        }
        //Phase 4.1: Place Leaders into Programmes
        for (String line : programmeRepo.readAll()) {
            String[] tokens = line.split(",");
            Programme programme = programmes.get(tokens[0]);

            for (String leaderUUID : tokens[3].split("\\|")) {
                programme.addLeader(leaders.get(leaderUUID));
            }
        }

        //Phase 5: Load Students
        HashMap<String, Student> students = new HashMap<>();
        for (String line : studentRepo.readAll()) {
            String[] tokens = line.split(",");
            Student student = Student.deserialize(line);

            student.setProgramme(programmes.get(tokens[3]));

            students.put(tokens[0], student);
        }

        //Phase 6: Load Rooms
        HashMap<String, Room> rooms = new HashMap<>();
        for (String line : roomRepo.readAll()) {
            String[] tokens = line.split(",");
            rooms.put(tokens[0], Room.deserialize(line));
        }

        //Phase 7: Load Subgroups
        HashMap<String, Subgroup> subgroups = new HashMap<>();
        for (String line : subgroupRepo.readAll()) {
            String[] tokens = line.split(",");
            subgroups.put(tokens[0], Subgroup.deserialize(line));
        }

        //Phase 7.1: Fill in Subgroup in Students
        for (String line : studentRepo.readAll())  {
            String[] tokens = line.split(",");
            Student student = students.get(tokens[0]);

            Subgroup subgroup = subgroups.get(tokens[4]);
            if (subgroup == null)
                tokenNotFound(tokens[4]);

            student.setSubgroup(subgroup);
        }

        //Phase 8: Load StudentGroup
        HashMap<String, StudentGroup> studentGroups = new HashMap<>();
        for (String line: groupRepo.readAll()) {
            String[] tokens = line.split(",");
            StudentGroup grp = StudentGroup.deserialize(line);

            Programme programme = programmes.get(tokens[2]);
            if (programme == null)
                tokenNotFound(tokens[2]);

            List<Student> localStudents = new ArrayList<>();
            for(String studentUUID : tokens[3].split("\\|")) {
                Student s = students.get(studentUUID);
                if (s == null)
                    tokenNotFound(studentUUID);
                localStudents.add(students.get(studentUUID));
            }

            List<Subgroup> localLectureSubgroups = new ArrayList<>();
            for(String subgroupUUID : tokens[4].split("\\|")) {
                Subgroup s = subgroups.get(subgroupUUID);
                if (s == null)
                    tokenNotFound(subgroupUUID);
                localLectureSubgroups.add(subgroups.get(subgroupUUID));
            }

            List<Subgroup> localLabSubgroups = new ArrayList<>();
            for(String subgroupUUID : tokens[5].split("\\|")) {
                Subgroup s = subgroups.get(subgroupUUID);
                if (s == null)
                    tokenNotFound(subgroupUUID);
                localLabSubgroups.add(subgroups.get(subgroupUUID));
            }

            List<Subgroup> localTutorialSubgroups = new ArrayList<>();
            for(String subgroupUUID : tokens[6].split("\\|")) {
                Subgroup s = subgroups.get(subgroupUUID);
                if (s == null)
                    tokenNotFound(subgroupUUID);
                localTutorialSubgroups.add(subgroups.get(subgroupUUID));
            }

            grp.setProgramme(programme);
            grp.setStudentList(localStudents);
            grp.setLectureGroups(localLectureSubgroups);
            grp.setLabGroups(localLabSubgroups);
            grp.setTutorialGroups(localTutorialSubgroups);

            studentGroups.put(tokens[0], grp);
        }

        //Phase 8.1: Fill in StudentGroup in Subgroup
        for (String line : subgroupRepo.readAll())  {
            String[] tokens = line.split(",");
            Subgroup subgroup = subgroups.get(tokens[0]);

            StudentGroup grp = studentGroups.get(tokens[2]);
            if (grp == null)
                tokenNotFound(tokens[2]);
            subgroup.setParentGroup(grp);
        }

        //Phase 8.2: Fill in StudentGroup in Students
        for (String line : studentRepo.readAll())  {
            String[] tokens = line.split(",");
            Student student = students.get(tokens[0]);

            StudentGroup grp = studentGroups.get(tokens[4]);
            if (grp == null)
                tokenNotFound(tokens[4]);
            student.setStudentGroup(grp);
        }

        //Phase 8.3: Fill in StudentGroup in Programmes
        for (String line : programmeRepo.readAll())  {
            String[] tokens = line.split(",");
            Programme programme = programmes.get(tokens[0]);

            StudentGroup grp = studentGroups.get(tokens[4]);
            if (grp == null)
                tokenNotFound(tokens[4]);
            programme.setStudentGroup(grp);
        }

        //Phase 9: Timeslots
        HashMap<String, Timeslot> timeslots = new HashMap<>();
        for (String line : timeslotRepo.readAll())  {
            String[] tokens = line.split(",");

            Day day = Day.values()[Integer.parseInt(tokens[1])];
            Period period = Period.values()[Integer.parseInt(tokens[2])];
            Room room = rooms.get(tokens[3]);
            if (room == null)
                tokenNotFound(tokens[3]);

            Timeslot timeslot = new Timeslot(day, period, room);
            timeslot.setUUID(tokens[0]);

            timeslots.put(tokens[0], timeslot);
        }

        //Phase 10: Sessions
        HashMap<String, Session> sessions = new HashMap<>();
        for (String line : sessionRepo.readAll())  {
            String[] tokens = line.split(",");
            Session session = Session.deserialize(line);

            Module m = modules.get(tokens[1]);
            if (m == null)
                tokenNotFound(tokens[1]);
            Timeslot t =  timeslots.get(tokens[2]);
            if (t == null)
                tokenNotFound(tokens[2]);
            Subgroup s  = subgroups.get(tokens[3]);
            if (s == null)
                tokenNotFound(tokens[3]);
            Leader l = leaders.get(tokens[4]);
            if (l == null)
                tokenNotFound(tokens[4]);

            session.setUUID(tokens[0]);
            session.setModule(m);
            session.setTimeslot(t);
            session.setSubgroup(s);
            session.setLeader(l);

            sessions.put(tokens[0], session);
        }

        //Phase 10.1: Fill in Session in Subgroup
        for (String line : subgroupRepo.readAll())  {
            String[] tokens = line.split(",");
            Subgroup subgroup = subgroups.get(tokens[0]);

            Session s = sessions.get(tokens[3]);
            if  (s == null)
                tokenNotFound(tokens[3]);

            subgroup.setSession(s);
        }

    }

    private void tokenNotFound(String token) {
        throw new IllegalArgumentException("Token {" + token + "} not found.");
    }
}
