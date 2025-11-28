package controller;
import java.util.Scanner;
import model.user.Leader;
import view.cli.LeaderCLI;

public class LeaderController {
    private Leader leader;
    private LeaderCLI view;
    private Scanner input = new Scanner(System.in);

    public void start() {
        boolean more = true;

        while (more) {
            view.displayPanel();
            String command = input.nextLine();

            if (command.equals("D")) {
                view.displayLeaderProgrammes(leader);
                view.print("Leader Modules Successfully Shown");
            }
            else if(command.equals("S")){
                view.displayTimetable(leader);
                view.print("Timetable successfully Shown");
            }
            else if (command == "Q") {
                more = false;
                view.print("Quitting...");
            }
        }
    }
}