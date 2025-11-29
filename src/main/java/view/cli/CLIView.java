package view.cli;

import controller.TimetableController;
import model.user.User;

import java.util.Scanner;

public abstract class CLIView {

    private final Scanner input = new Scanner(System.in);

    public abstract void print(String msg);
    public abstract void error(String msg);
    public abstract void displayPanel();

    public String prompt(String msg) {
        System.out.print(msg);
        return input.nextLine();
    }

    public abstract void setController(TimetableController controller);
    public abstract void setUser(User user);
}
