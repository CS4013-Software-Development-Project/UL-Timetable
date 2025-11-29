package controller;

import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import persistence.PersistenceManager;
import view.cli.AdminCLI;
import view.cli.MainCLI;

public class LoginController extends Controller{

    public LoginController(MainCLI view){
        super(view);
    }

    @Override
    public void start() {
        User testUser = getUserFromUsername();
        if (testUser == null)
            return;

        Controller controller = getControllerFromPassword(testUser);
        if (controller == null)
            return;

        controller.start();
    }

    private User getUserFromUsername() {
        return getUserFromUsername(0);
    }
    private User getUserFromUsername(int attempts) {
        if (attempts > 1) {
            view.print("Too many attempts.");
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
            view.print("Invalid username.");
            testUser = getUserFromUsername(attempts + 1);
        }

        return testUser;
    }

    private Controller getControllerFromPassword(User user) {
        return getControllerFromPassword(user, 0);
    }
    private Controller getControllerFromPassword(User user, int attempts) {
        if (attempts > 1) {
            view.print("Too many attempts.");
            return null;
        }

        String password = view.prompt("Password: ");
        boolean loginSuccess = user.authenticate(password);

        if (loginSuccess) {
            view.print("Login successful.");

            Controller controller = null;

            if (user instanceof Admin) {
                controller =  new AdminController(new AdminCLI());
            }
            else if (user instanceof Leader) {
                controller =  new LeaderController();
            }
            else if (user instanceof Student) {
                controller =  new StudentController();
            }

            return controller;
        }
        return getControllerFromPassword(user, attempts + 1);
    }
}
