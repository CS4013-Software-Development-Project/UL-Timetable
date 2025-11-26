package persistence;

import model.grouping.*;
import model.module.*;
import model.room.*;
import model.schedule.*;
import model.user.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PersistenceManager {
    String dataDirectory;

    private Repository moduleRepo;
    private Repository programmeRepo;
    private Repository adminRepo;
    private Repository leaderRepo;
    private Repository studentRepo;
    private Repository roomRepo;
    private Repository subgroupRepo;
    private Repository studentGroupRepo;
    private Repository timeslotRepo;
    private Repository sessionRepo;
    private Repository timetableRepo;

    private static LinkedHashMap<String, Module> modules = new LinkedHashMap<>();
    private static LinkedHashMap<String, Programme> programmes = new LinkedHashMap<>();
    private static LinkedHashMap<String, Admin> admins = new LinkedHashMap<>();
    private static LinkedHashMap<String, Leader> leaders = new LinkedHashMap<>();
    private static LinkedHashMap<String, Student> students = new LinkedHashMap<>();
    private static LinkedHashMap<String, Room> rooms = new LinkedHashMap<>();
    private static LinkedHashMap<String, Subgroup> subgroups = new LinkedHashMap<>();
    private static LinkedHashMap<String, StudentGroup> studentGroups = new LinkedHashMap<>();
    private static LinkedHashMap<String, Timeslot> timeslots = new LinkedHashMap<>();
    private static LinkedHashMap<String, Session> sessions = new LinkedHashMap<>();
    private static LinkedHashMap<String, Timetable> timetables = new LinkedHashMap<>();

    public PersistenceManager(String dataDirectory) {
        this.dataDirectory = dataDirectory + "/";
        moduleRepo = new Repository(this.dataDirectory + "modules");
        programmeRepo = new Repository(this.dataDirectory + "programmes");
        adminRepo = new Repository(this.dataDirectory + "admins");
        leaderRepo = new Repository(this.dataDirectory + "leaders");
        studentRepo = new Repository(this.dataDirectory + "students");
        roomRepo = new Repository(this.dataDirectory + "rooms");
        subgroupRepo = new Repository(this.dataDirectory + "subgroups");
        studentGroupRepo = new Repository(this.dataDirectory + "groups");
        timeslotRepo = new Repository(this.dataDirectory + "timeslots");
        sessionRepo = new Repository(this.dataDirectory + "sessions");
        timetableRepo = new Repository(this.dataDirectory + "timetables");
    }

    public void load() {
        //Phase 1: Load Modules
        for (String line : moduleRepo.readAll()) {
            String[] tokens = line.split(",");
            modules.put(tokens[0], Module.deserialize(line));
        }

        //Phase 2: Load Programmes
        for (String line : programmeRepo.readAll()) {
            String[] tokens = line.split(",");
            programmes.put(tokens[0], Programme.deserialize(line));
        }

        //Phase 3: Load Admins
        for (String line : adminRepo.readAll()) {
            String[] tokens = line.split(",");
            admins.put(tokens[0], Admin.deserialize(line));
        }

        //Phase 4: Load Leaders
        for (String line : leaderRepo.readAll()) {
            String[] tokens = line.split(",");
            Leader leader = Leader.deserialize(line);
            //fill in led programme list
            if (tokens.length > 3)
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
                Leader leader = leaders.get(leaderUUID);
                if (leader != null)
                    programme.addLeader(leader);
            }
        }

        //Phase 5: Load Students
        for (String line : studentRepo.readAll()) {
            String[] tokens = line.split(",");
            Student student = Student.deserialize(line);

            student.setProgramme(programmes.get(tokens[3]));

            students.put(tokens[0], student);
        }

        //Phase 6: Load Rooms
        for (String line : roomRepo.readAll()) {
            String[] tokens = line.split(",");
            rooms.put(tokens[0], Room.deserialize(line));
        }

        //Phase 7: Load Subgroups
        for (String line : subgroupRepo.readAll()) {
            String[] tokens = line.split(",");
            subgroups.put(tokens[0], Subgroup.deserialize(line));
        }

        //Phase 7.1: Fill in Subgroup in Students
        for (String line : studentRepo.readAll())  {
            String[] tokens = line.split(",");
            Student student = students.get(tokens[0]);

            if(tokens.length < 6)
                continue;
            for (String subgroupUUID : tokens[5].split("\\|")) {
                Subgroup subgroup = subgroups.get(subgroupUUID);
                if (subgroup == null)
                    tokenNotFound(tokens[4]);
                student.addSubgroup(subgroup);
            }
        }

        //Phase 8: Load StudentGroup
        for (String line: studentGroupRepo.readAll()) {
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
                localLectureSubgroups.add(subgroups.get(subgroupUUID));
            }

            List<Subgroup> localLabSubgroups = new ArrayList<>();
            for(String subgroupUUID : tokens[5].split("\\|")) {
                Subgroup s = subgroups.get(subgroupUUID);
                localLabSubgroups.add(subgroups.get(subgroupUUID));
            }

            List<Subgroup> localTutorialSubgroups = new ArrayList<>();
            for(String subgroupUUID : tokens[6].split("\\|")) {
                Subgroup s = subgroups.get(subgroupUUID);
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

        for (String line : timetableRepo.readAll())  {
            String[] tokens = line.split(",");
            Timetable tb = new Timetable();

            String[] days = tokens[1].split("\\|\\|");
            for (int i = 0; i < days.length; i++) {
                String[] day = days[i].split("\\|");
                for (int j = 0; j < day.length;  j++) {
                    String uuid = day[j];
                    //i love ternary operator
                    Session s = sessions.get(uuid) == null ? null : sessions.get(uuid);
                    Day d = Day.values()[i];
                    Period p = Period.values()[j];

                    tb.setSession(d, p, s);
                }
            }

            timetables.put(tokens[0], tb);
        }
    }

    public void save() {
        //first, flush cache
        this.moduleRepo.clear();
        this.programmeRepo.clear();
        this.adminRepo.clear();
        this.leaderRepo.clear();
        this.studentRepo.clear();
        this.roomRepo.clear();
        this.subgroupRepo.clear();
        this.studentGroupRepo.clear();
        this.timeslotRepo.clear();
        this.sessionRepo.clear();
        this.timetableRepo.clear();

        //next, serialize everything
        for (AbstractPersistable obj : modules.values())
            moduleRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : programmes.values())
            programmeRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : admins.values())
            adminRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : leaders.values())
            leaderRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : students.values())
            studentRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : rooms.values())
            roomRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : subgroups.values())
            subgroupRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : studentGroups.values())
            studentGroupRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : timeslots.values())
            timeslotRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : sessions.values())
            sessionRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : timetables.values())
            timetableRepo.addLine(obj.serialize());

        //last, save everything!
        this.moduleRepo.save();
        this.programmeRepo.save();
        this.adminRepo.save();
        this.leaderRepo.save();
        this.studentRepo.save();
        this.roomRepo.save();
        this.subgroupRepo.save();
        this.studentGroupRepo.save();
        this.timeslotRepo.save();
        this.sessionRepo.save();
        this.timetableRepo.save();
    }

    public static List<Module> getModules() {
        return new ArrayList<>(modules.values());
    }

    public static void addModule(Module... modulesToAdd) {
        for (Module m : modulesToAdd) {
            modules.put(m.getUUID(), m);
        }
    }

    public static void removeModule(String... uuids) {
        for (String uuid : uuids) {
            modules.remove(uuid);
        }
    }


    public static List<Programme> getProgrammes() {
        return new ArrayList<>(programmes.values());
    }

    public static void addProgramme(Programme... programmesToAdd) {
        for (Programme p : programmesToAdd) {
            programmes.put(p.getUUID(), p);
        }
    }

    public static void removeProgramme(String... uuids) {
        for (String uuid : uuids) {
            programmes.remove(uuid);
        }
    }


    public static List<Admin> getAdmins() {
        return new ArrayList<>(admins.values());
    }

    public static void addAdmin(Admin... adminsToAdd) {
        for (Admin a : adminsToAdd) {
            admins.put(a.getUUID(), a);
        }
    }

    public static void removeAdmin(String... uuids) {
        for (String uuid : uuids) {
            admins.remove(uuid);
        }
    }


    public static List<Leader> getLeaders() {
        return new ArrayList<>(leaders.values());
    }

    public static void addLeader(Leader... leadersToAdd) {
        for (Leader l : leadersToAdd) {
            leaders.put(l.getUUID(), l);
        }
    }

    public static void removeLeader(String... uuids) {
        for (String uuid : uuids) {
            leaders.remove(uuid);
        }
    }

    public static List<Student> getStudents() {
        return new ArrayList<>(students.values());
    }

    public static void addStudent(Student... studentsToAdd) {
        for (Student s : studentsToAdd) {
            students.put(s.getUUID(), s);
        }
    }

    public static void removeStudent(String... uuids) {
        for (String uuid : uuids) {
            students.remove(uuid);
        }
    }

    public static List<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    public static void addRoom(Room... roomsToAdd) {
        for (Room r : roomsToAdd) {
            rooms.put(r.getUUID(), r);
        }
    }

    public static void removeRoom(String... uuids) {
        for (String uuid : uuids) {
            rooms.remove(uuid);
        }
    }

    public static List<Subgroup> getSubgroups() {
        return new ArrayList<>(subgroups.values());
    }

    public static void addSubgroup(Subgroup... subgroupsToAdd) {
        for (Subgroup sg : subgroupsToAdd) {
            subgroups.put(sg.getUUID(), sg);
        }
    }

    public static void removeSubgroup(String... uuids) {
        for (String uuid : uuids) {
            subgroups.remove(uuid);
        }
    }

    public static List<StudentGroup> getStudentGroups() {
        return new ArrayList<>(studentGroups.values());
    }

    public static void addStudentGroup(StudentGroup... groupsToAdd) {
        for (StudentGroup g : groupsToAdd) {
            studentGroups.put(g.getUUID(), g);
        }
    }

    public static void removeStudentGroup(String... uuids) {
        for (String uuid : uuids) {
            studentGroups.remove(uuid);
        }
    }

    public static List<Timeslot> getTimeslots() {
        return new ArrayList<>(timeslots.values());
    }

    public static void addTimeslot(Timeslot... timeslotsToAdd) {
        for (Timeslot t : timeslotsToAdd) {
            timeslots.put(t.getUUID(), t);
        }
    }

    public static void removeTimeslot(String... uuids) {
        for (String uuid : uuids) {
            timeslots.remove(uuid);
        }
    }

    public static List<Session> getSessions() {
        return new ArrayList<>(sessions.values());
    }

    public static void addSession(Session... sessionsToAdd) {
        for (Session s : sessionsToAdd) {
            sessions.put(s.getUUID(), s);
        }
    }

    public static void removeSession(String... uuids) {
        for (String uuid : uuids) {
            sessions.remove(uuid);
        }
    }

    public static List<Timetable> getTimetables() {
        return new ArrayList<>(timetables.values());
    }

    public static void addTimetable(Timetable... timetablesToAdd) {
        for (Timetable t : timetablesToAdd) {
            timetables.put(t.getUUID(), t);
        }
    }

    public static void removeTimetable(String... uuids) {
        for (String uuid : uuids) {
            timetables.remove(uuid);
        }
    }

    private void tokenNotFound(String token) {
        throw new IllegalArgumentException("Token {" + token + "} not found.");
    }
}
