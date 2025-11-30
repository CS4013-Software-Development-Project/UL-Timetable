package persistence;

import model.grouping.Subgroup;
import model.module.Module;
import model.module.Programme;
import model.module.Session;
import model.room.Room;
import model.schedule.Timeslot;
import model.schedule.Timetable;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;

import java.util.LinkedHashMap;

/**
 * @author Kuba Rodak (24436755)
 */
public class PersistenceManager {
    String dataDirectory;

    private final Repository moduleRepo;
    private final Repository programmeRepo;
    private final Repository adminRepo;
    private final Repository leaderRepo;
    private final Repository studentRepo;
    private final Repository roomRepo;
    private final Repository subgroupRepo;
    private final Repository timeslotRepo;
    private final Repository sessionRepo;
    private final Repository timetableRepo;

    public static LinkedHashMap<String, Module> modules = new LinkedHashMap<>();
    public static LinkedHashMap<String, Programme> programmes = new LinkedHashMap<>();
    public static LinkedHashMap<String, Admin> admins = new LinkedHashMap<>();
    public static LinkedHashMap<String, Leader> leaders = new LinkedHashMap<>();
    public static LinkedHashMap<String, Student> students = new LinkedHashMap<>();
    public static LinkedHashMap<String, Room> rooms = new LinkedHashMap<>();
    public static LinkedHashMap<String, Subgroup> subgroups = new LinkedHashMap<>();
    public static LinkedHashMap<String, Timeslot> timeslots = new LinkedHashMap<>();
    public static LinkedHashMap<String, Session> sessions = new LinkedHashMap<>();
    public static LinkedHashMap<String, Timetable> timetables = new LinkedHashMap<>();

    public PersistenceManager(String dataDirectory) {
        this.dataDirectory = dataDirectory + "/";
        moduleRepo = new Repository(this.dataDirectory + "modules");
        programmeRepo = new Repository(this.dataDirectory + "programmes");
        adminRepo = new Repository(this.dataDirectory + "admins");
        leaderRepo = new Repository(this.dataDirectory + "leaders");
        studentRepo = new Repository(this.dataDirectory + "students");
        roomRepo = new Repository(this.dataDirectory + "rooms");
        subgroupRepo = new Repository(this.dataDirectory + "subgroups");
        timeslotRepo = new Repository(this.dataDirectory + "timeslots");
        sessionRepo = new Repository(this.dataDirectory + "sessions");
        timetableRepo = new Repository(this.dataDirectory + "timetables");
    }

    public void load() {
        //Phase 1: Load Modules
        for (String line : moduleRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                modules.put(tokens[0], Module.deserialize(tokens));
        }

        //Phase 2: Load Programmes
        for (String line : programmeRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                programmes.put(tokens[0], Programme.deserialize(tokens));
        }

        //Phase 3: Load Admins
        for (String line : adminRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                admins.put(tokens[0], Admin.deserialize(tokens));
        }

        //Phase 4: Load Leaders
        for (String line : leaderRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                leaders.put(tokens[0], Leader.deserialize(tokens));
        }

        //Phase 5: Load Students
        for (String line : studentRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                students.put(tokens[0], Student.deserialize(tokens));
        }

        //Phase 6: Load Rooms
        for (String line : roomRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                rooms.put(tokens[0], Room.deserialize(tokens));
        }

        //Phase 7: Load Subgroups
        for (String line : subgroupRepo.readAll()) {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                subgroups.put(tokens[0], Subgroup.deserialize(tokens));
        }

        //Phase 8: Timeslots
        for (String line : timeslotRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                timeslots.put(tokens[0], Timeslot.deserialize(tokens));
        }

        //Phase 9: Sessions
        for (String line : sessionRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                sessions.put(tokens[0], Session.deserialize(tokens));
        }

        //Phase 10: Timetable
        for (String line : timetableRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length >= 1)
                timetables.put(tokens[0], Timetable.deserialize(tokens));
        }

        //Phase 11: Resolve references in Modules
        for (String line : moduleRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Module x = modules.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 12: Resolve references in Programmes
        for (String line : programmeRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Programme x = programmes.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 13: Admin is self-contained teehee

        //Phase 14: Resolve references in Leader
        for (String line : leaderRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Leader x = leaders.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 15: Resolve references in Student
        for (String line : studentRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Student x = students.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 16: Room is self-contained

        //Phase 17: Resolve references in Subgroup
        for (String line : subgroupRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Subgroup x = subgroups.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 18: Resolve references in Timeslot
        for (String line : timeslotRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Timeslot x = timeslots.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 19: Resolve references in Session
        for (String line : sessionRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Session x = sessions.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //Phase 20: Resolve references in Timetable
        for (String line : timetableRepo.readAll())  {
            String[] tokens = line.split(",");
            if (tokens.length == 0)
                break;
            Timetable x = timetables.get(tokens[0]);
            if (x != null)
                x.resolveReferences(tokens);
        }

        //we need to fix the counter also, so after loading everything
        //new IDs will only ever add to the count - never overwrite
        int maxID = 0;

        for (AbstractPersistable x : modules.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : programmes.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : admins.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : leaders.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : students.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : subgroups.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : timeslots.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : sessions.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));
        for (AbstractPersistable x : timetables.values())
            maxID = Math.max(maxID, Integer.parseInt(x.getUUID()));

        ID.setCounter(maxID);
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
        this.timeslotRepo.clear();
        this.sessionRepo.clear();
        this.timetableRepo.clear();

        //resolve all dependencies
        //noinspection DuplicatedCode
        for (AbstractPersistable obj : modules.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : programmes.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : admins.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : leaders.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : students.values())
            obj.resolveDependencies();

        //noinspection DuplicatedCode
        for (AbstractPersistable obj : rooms.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : subgroups.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : timeslots.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : sessions.values())
            obj.resolveDependencies();

        for (AbstractPersistable obj : timetables.values())
            obj.resolveDependencies();

        //next, serialize everything
        //noinspection DuplicatedCode
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

        //noinspection DuplicatedCode
        for (AbstractPersistable obj : rooms.values())
            roomRepo.addLine(obj.serialize());

        for (AbstractPersistable obj : subgroups.values())
            subgroupRepo.addLine(obj.serialize());

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
        this.timeslotRepo.save();
        this.sessionRepo.save();
        this.timetableRepo.save();
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
