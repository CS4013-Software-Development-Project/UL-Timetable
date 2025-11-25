package persistence;

import model.module.Module;
import model.module.Programme;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;

import java.util.HashMap;

public class PersistenceManager {
    String dataDirectory;

    Repository moduleRepo;
    Repository programmeRepo;
    Repository adminRepo;
    Repository leaderRepo;
    Repository studentRepo;


    public PersistenceManager(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        moduleRepo = new Repository(dataDirectory + "modules");
        programmeRepo = new Repository(dataDirectory + "programmes");
        adminRepo = new Repository(dataDirectory + "admins");
        leaderRepo = new Repository(dataDirectory + "leaders");
        studentRepo = new Repository(dataDirectory + "students");
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
            students.put(tokens[0], Student.deserialize(line));
        }
    }
}
