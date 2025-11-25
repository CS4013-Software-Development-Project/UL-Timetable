package view.cli;

import controller.TimetableController;
import model.user.User;

public interface ICLIView {
    //This interface is to be confirmed, but all CLI views need an interface.
    void print(String msg);
    void error(String msg);
    String prompt(String msg);
    void displayPanel();

    void run();

    void setController(TimetableController controller);
    void setUser(User user);
}
