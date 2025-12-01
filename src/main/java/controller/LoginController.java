package controller;

import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import persistence.PersistenceManager;
import view.cli.AdminCLI;
import view.cli.LeaderCLI;
import view.cli.MainCLI;
import view.cli.StudentCLI;

/**
 * Main controller handling User login.
 * <p>
 * This is the point of entry for the program. It prompts for a username,
 * verifies that it exists, then prompts for a password.  Depending on the
 * authenticated user’s type (admin, leader or student), it constructs the
 * right controller and then hands off to its start() method.
 * </p>
 *
 * @see controller.AdminController
 * @see controller.LeaderController
 * @see controller.StudentController
 * @see view.cli.MainCLI
 *
 * @author Kuba Rodak (24436755)
 */
public class LoginController extends Controller{

    /**
     * Creates an instance of {@link LoginController} with the provided view.
     * @param view the view that this controller will use for I/O ops.
     */
    public LoginController(MainCLI view){
        super(view, new Admin("", ""));
    }

    /**
     * Starts the login process. Will first ask & validate username.
     * Only once the provided username exists, will it ask for password.
     * Has a max of 2 attempts per each stage; failing will hand control
     * back to the main method.
     */
    @Override
    public void start() {
        User testUser = getUserFromUsername();
        if (testUser == null)
        {
            view.print("Username not found");
            return;
        }

        Controller controller = getControllerFromPassword(testUser);
        if (controller == null)
        {
            view.print("Login failed.");
            return;
        }

        view.print(
                "Login successful. Welcome " +
                        testUser.getUsername() + ", " + testUser.getClass().getCanonicalName());


        controller.start();
    }

    /**
     * Returns a {@link User} instance by asking the user to provide a username.
     * It checks that against the persistence store and only returns the User if
     * it finds one. Otherwise, returns null.
     * @return the User with a matching username. Null if none are found.
     */
    private User getUserFromUsername() {
        return getUserFromUsername(0);
    }
    /**
     * Returns a {@link User} instance by asking the user to provide a username.
     * It checks that against the persistence store and only returns the User if
     * it finds one. Otherwise, returns null.
     * @param attempts amount of allowed login attempts before fail.
     * @return the User with a matching username. Null if none are found.
     */
    private User getUserFromUsername(int attempts) {
        if (attempts > 1) {
            return null;
        }

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
            view.error("Invalid username.");
            testUser = getUserFromUsername(attempts + 1);
        }

        return testUser;
    }

    /**
     * Returns a {@link Controller} instance by asking the user to provide a password.
     * This is then authenticated with the provided User instance.
     * @param user the {@link User} to check password against.
     * @return The appropriate {@link Controller} for this type of {@link User}.
     */
    private Controller getControllerFromPassword(User user) {
        return getControllerFromPassword(user, 0);
    }
    /**
     * Returns a {@link Controller} instance by asking the user to provide a password.
     * This is then authenticated with the provided User instance.
     * @param user the {@link User} to check password against.
     * @param attempts amount of allowed login attempts before fail.
     * @return The appropriate {@link Controller} for this type of {@link User}.
     */
    private Controller getControllerFromPassword(User user, int attempts) {
        if (attempts > 1) {
            view.error("Too many attempts.");
            return null;
        }

        String password = view.prompt("Password: ");
        boolean loginSuccess = user.authenticate(password);

        if (loginSuccess) {
            Controller controller = null;

            if (user instanceof Admin) {
                controller =  new AdminController(new AdminCLI(), (Admin)user);
            }
            else if (user instanceof Leader) {
                controller =  new LeaderController(new LeaderCLI(), (Leader)user);
            }
            else if (user instanceof Student) {
                controller =  new StudentController(new StudentCLI(), (Student)user);
            }

            return controller;
        }
        view.error("Incorrect password.");
        return getControllerFromPassword(user, attempts + 1);
    }
}
