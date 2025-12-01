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

public class LoginController extends Controller{

    public LoginController(MainCLI view){
        super(view);
    }

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

    private User getUserFromUsername() {
        return getUserFromUsername(0);
    }
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

    private Controller getControllerFromPassword(User user) {
        return getControllerFromPassword(user, 0);
    }
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
                controller =  new AdminController(new AdminCLI());
            }
            else if (user instanceof Leader) {
                controller =  new LeaderController(new LeaderCLI(), (Leader) user);
            }
            else if (user instanceof Student) {
                controller =  new StudentController(new StudentCLI(), (Student) user);
            }

            return controller;
        }
        view.error("Incorrect password.");
        return getControllerFromPassword(user, attempts + 1);
    }
}
