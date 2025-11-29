package controller;
import model.module.Programme;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import persistence.PersistenceManager;
import view.cli.AdminCLI;
import java.util.Scanner;

public class AdminController extends Controller {
    //the Admin this controller is attached to
    private Admin admin;
    //the view this is attached to
    AdminCLI view;

    public AdminController(AdminCLI view) {
        super(view);
        this.view = view;
    }

    public void start() {
        boolean more = true;

        MAIN_LOOP: while(more){
            String command = view.prompt("A)ppoint Leader to Programme R)emove Leader From Programme C)hange Password U)ser Creation M)odule Creation Q)uit");

            switch (command) {
                //appoint leader
                case "A": {
                    String username = view.prompt("Enter new username: ");
                    String password = view.prompt("Enter new password: ");

                    for (Leader leader : PersistenceManager.leaders.values()) {
                        if (leader.getUsername().equals(username)) {
                            view.print("Leader already exists for username " + username);
                            continue MAIN_LOOP;
                        }
                    }

                    Leader newLeader = new Leader(username, password);

                    String programmeName = view.prompt("Enter Programme name: ");
                    Programme testProgramme = PersistenceManager.programmes.values().stream().filter(
                            p -> p.getName().equals(programmeName)
                    ).findFirst().orElse(null);

                    if (testProgramme == null) {
                        view.print("Programme " +  programmeName + " does not exist, so it has been created.");
                        testProgramme = new Programme(programmeName);
                        PersistenceManager.programmes.put(testProgramme.getName(), testProgramme);
                    }
                    admin.appointLeader(newLeader, testProgramme);
                    view.print("Leader added to programme");

                    break;
                }
                //remove a leader
                case "R": {
                    String username = view.prompt("Enter username: ");

                    Leader testLeader = PersistenceManager.leaders.values().stream()
                            .filter(l -> l.getUsername().equals(username))
                            .findFirst().orElse(null);

                    if (testLeader == null) {
                        view.print("Leader does not exist for username " + username);
                        continue MAIN_LOOP;
                    }

                    boolean check = false;
                    Leader leader = new Leader("blank", "blank");
                    for (int i = 0; i < PersistenceManager.getLeaders().size(); i++) {
                        if (PersistenceManager.getLeaders().get(i).getUsername().equals(username)) {
                            leader = PersistenceManager.getLeaders().get(i);
                            check = true;
                        }
                    }
                    if (!check) {
                        view.error("Leader not Found");
                    } else {
                        view.promptProgrammeName();
                        String programmeName = input.nextLine();
                        Programme programme = new Programme(programmeName);
                        admin.removeLeader(leader, programme);
                        view.print("Leader removed from module");
                    }
                    break;
                }
                //change password
                case "C": {
                    view.changePassword();
                    String username = input.nextLine();
                    String password = input.nextLine();
                    Leader leader = new Leader(username, "welcome2025");
                    leader.resetPassword(password);
                    view.print("Password Reset");
                    break;
                }
                //user creation mode
                case "U": {
                    boolean extra = true;
                    while (extra) {
                        view.userDisplayPanel();
                        command = input.nextLine();
                        if (command.equals("L")) {
                            view.promptUsername();
                            String username = input.nextLine();
                            view.promptPassword();
                            String password = input.nextLine();
                            admin.addLeader(username, password);
                            view.print("Leader Added");
                        } else if (command.equals("S")) {
                            view.promptUsername();
                            String username = input.nextLine();
                            view.promptPassword();
                            String password = input.nextLine();
                            admin.addStudent(username, password);
                            view.print("Student Added");
                        } else if (command.equals("A")) {
                            view.promptUsername();
                            String username = input.nextLine();
                            view.promptPassword();
                            String password = input.nextLine();
                            admin.addAdmin(username, password);
                            view.print("Admin Added");
                        } else if (command.equals("B")) {
                            extra = false;
                            view.print("Backing out...");
                            command = "invalid";
                        }
                    }
                    break;
                }
                //module creation mode
                case "M": {
                    boolean extra = true;
                    while (extra) {
                        view.moduleDisplayPanel();
                        command = input.nextLine();
                        if (command.equals("P")) {
                            view.promptProgrammeName();
                            String name = input.nextLine();
                            admin.addProgramme(name);
                            view.print("Programme Added");
                        } else if (command.equals("M")) {
                            view.promptProgrammeName();
                            String name = input.nextLine();
                            view.promptModuleCode();
                            String code = input.nextLine();
                            view.promptModuleName();
                            String moduleName = input.nextLine();
                            boolean checker = false;

                            for (int i = 0; i < PersistenceManager.getProgrammes().size(); i++) {
                                if (PersistenceManager.getProgrammes().get(i).getName().equals(name)) {
                                    admin.addModule(PersistenceManager.getProgrammes().get(i), code, moduleName);
                                    view.print("Module Added");
                                    checker = true;
                                }
                            }
                            if (!checker) {
                                view.error("Programme not found");
                            }
                        } else if (command.equals("B")) {
                            extra = false;
                            view.print("Backing out...");
                            command = "Invalid";
                        }
                    }
                    break;
                }
                //quit
                case "Q":
                    more = false;
                    view.print("Quitting...");
                    break;
            }
        }
    }

    public User getUser() {
        String username = view.prompt("Username: ");
        User testUser = null;

        for (Admin user : PersistenceManager.admins.values()) {
            if (user.getUsername().equals(username)) {
                testUser = user;
                break;
            }
        }
        if (testUser == null)
            for (Leader user : PersistenceManager.leaders.values()) {
                if (user.getUsername().equals(username)) {
                    testUser = user;
                    break;
                }
            }
        if (testUser == null)
            for (Student user : PersistenceManager.students.values()) {
                if (user.getUsername().equals(username)) {
                    testUser = user;
                    break;
                }
            }
        if (testUser == null) {
            view.print("Invalid username.");
            return null;
        }
        return testUser;
    }

}