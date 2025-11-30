import controller.LoginController;
import controller.TimetableController;
import model.grouping.Subgroup;
import model.module.Module;
import model.module.Programme;
import model.user.Leader;
import model.user.Student;
import model.user.User;
import model.user.Admin;
import persistence.PersistenceManager;
import view.cli.MainCLI;

public class ULTimetable {

    private static final String dataDir = "persistence";

    public static void main(String[] args) {
        //on cold boot: load everything
        PersistenceManager pm = new PersistenceManager(dataDir);
        pm.load();

        //add root testing admin
        //TODO: Remove this once testing is complete.
        if (PersistenceManager.admins.values().stream().noneMatch(p -> p.getUsername().equals("root")))
            PersistenceManager.addAdmin(new Admin("root","root"));

        //create a view & controller for logging in
        MainCLI loginView = new  MainCLI();
        LoginController loginController = new LoginController(loginView);

        //enter main loop
        loginController.start();

        //gracefully save all data
        pm.save();
    }
}