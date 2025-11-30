package controller;
import model.module.Programme;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import persistence.PersistenceManager;
import view.cli.AdminCLI;

/**
 * Controller class for {@link model.user.Admin} users.
 * <p>
 * The controller allows an Admin to:
 * <ul>
 *   <li>appoint or remove leaders from programmes</li>
 *   <li>change the password of any user (leader, student or admin)</li>
 *   <li>create new users (leaders, students or admins)</li>
 *   <li>create new programmes and modules</li>
 *   <li>quit the interface</li>
 * </ul>
 * Uses the {@link persistence.PersistenceManager} singleton.
 * </p>
 *
 * @see model.user.Admin
 * @see model.module.Programme
 * @see view.cli.AdminCLI
 * @see persistence.PersistenceManager
 *
 * @author Willow Johnson
 */
public class AdminController extends Controller {
    /** The {@link Admin} this controller is attached to. */
    private Admin admin;
    /** The {@link AdminCLI} this controller is attached to. */
    AdminCLI view;

    /**
     * Creates a new {@code AdminController} with the supplied view.
     * @param view the view that will interact with the user
     */
    public AdminController(AdminCLI view) {
        super(view);
        this.view = view;
    }

    /**
     * Starts the interactive command loop.
     */
    public void start() {
        boolean more = true;

        MAIN_LOOP:
        while (more) {
            String command = view.prompt("A)ppoint Leader to Programme R)emove Leader From Programme C)hange Password U)ser Creation M)odule Creation Q)uit\n");

            switch (command.toUpperCase()) {
                //appoint leader
                case "A": {
                    String username = view.prompt("Enter Leader's username: ");

                    Leader testLeader = PersistenceManager.leaders.values().stream()
                            .filter(l -> l.getUsername().equals(username))
                            .findFirst().orElse(null);

                    if (testLeader == null) {
                        view.print("Leader does not exist for username " + username);
                        continue MAIN_LOOP;
                    }

                    String programmeName = view.prompt("Enter Programme name: ");
                    Programme testProgramme = PersistenceManager.programmes.values().stream().filter(
                            p -> p.getName().equals(programmeName)
                    ).findFirst().orElse(null);

                    if (testProgramme == null) {
                        view.error("Programme " + programmeName + " does not exist, so leader cannot be added, Please re-enter or create this programme.");
                        continue MAIN_LOOP;
                    }
                    admin.appointLeader(testLeader, testProgramme);
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
                        view.error("Leader does not exist for username " + username);
                        continue MAIN_LOOP;
                    }

                    String programmeName = view.prompt("Enter Programme name: ");
                    Programme testProgramme = PersistenceManager.programmes.values().stream().filter(
                            p -> p.getName().equals(programmeName)
                    ).findFirst().orElse(null);

                    if (testProgramme == null) {
                        view.error("Programme " + programmeName + " does not exist, so leader cannot be removed, Please re-enter programme name.");
                        continue MAIN_LOOP;
                    }
                    admin.removeLeader(testLeader, testProgramme);
                    view.print("Leader removed from module");
                    break;
                }
                //change password
                case "C": {
                    view.changePassword();
                    String username = view.prompt("Username: ");
                    String password = view.prompt("New Password: ");

                    User user = PersistenceManager.leaders.values().stream()
                            .filter(l -> l.getUsername().equals(username))
                            .findFirst().orElse(null);
                    if (user == null) {
                        user = PersistenceManager.students.values().stream()
                                .filter(l -> l.getUsername().equals(username))
                                .findFirst().orElse(null);
                    }
                    if (user == null) {
                        user = PersistenceManager.admins.values().stream()
                                .filter(l -> l.getUsername().equals(username))
                                .findFirst().orElse(null);
                    }
                    if (user == null) {
                        view.error("User does not exist for username " + username);
                        continue MAIN_LOOP;
                    }

                    user.resetPassword(password);
                    view.print("Password Reset");
                    break;
                }
                //user creation mode
                case "U": {
                    while (true) {
                        command = view.prompt("L)eader S)tudent A)dmin B)ack\n");
                        switch (command) {
                            case "L": {
                                String username = view.prompt("Username: ");
                                String password = view.prompt("Password: ");
                                admin.addLeader(username, password);
                                view.print("Leader Added");
                                break;
                            }
                            case "S": {
                                String username = view.prompt("Username: ");
                                String password = view.prompt("Password: ");
                                admin.addStudent(username, password);
                                view.print("Student Added");
                                break;
                            }
                            case "A": {
                                String username = view.prompt("Username: ");
                                String password = view.prompt("Password: ");
                                admin.addAdmin(username, password);
                                view.print("Admin Added");
                                break;
                            }
                            case "B":
                                view.print("Backing out...");
                                continue MAIN_LOOP;
                        }
                    }
                }
                //module creation mode
                case "M": {
                    while (true) {
                        command = view.prompt("P)rogramme M)odule B)ack\n");
                        switch (command) {
                            case "P": {
                                String name = view.prompt("Enter Programme name: ");
                                admin.addProgramme(name);
                                view.print("Programme Added");
                                break;
                            }
                            case "M": {
                                String name = view.prompt("Enter Programme name: ");
                                String moduleName = view.prompt("Enter Module name: ");
                                String code = view.prompt("Enter Module Code: ");

                                Programme testProgramme = PersistenceManager.programmes.values().stream().filter(
                                        p -> p.getName().equals(name)
                                ).findFirst().orElse(null);

                                if (testProgramme == null) {
                                    view.error("Programme not found");
                                    continue MAIN_LOOP;
                                }
                                admin.addModule(testProgramme,code,moduleName);
                                break;
                            }
                            case "B":
                                view.print("Backing out...");
                                continue MAIN_LOOP;
                        }
                    }
                }
                //quit
                case "Q":
                    more = false;
                    view.print("Quitting...");
                    break;
            }
        }
    }

    /**
     * Helper method for getting a User from the persistence store. Automatically searches all User repositories;
     * Admins, Leaders, and Students.
     * @return the User located inside the persistence store.
     */
    private User getUser() {
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