package controller;
import java.util.List;
import java.util.Scanner;

import model.module.Programme;
import model.user.Leader;
import view.cli.LeaderCLI;

public class LeaderController {
    private Leader leader = new Leader("username", "password");
    private LeaderCLI view =  new LeaderCLI();
    private Scanner input = new Scanner(System.in);

    public void start() {
        boolean more = true;

        while (more) {
            view.displayPanel();
            String command = input.nextLine();

            if (command.equals("D")) {
                view.displayLeaderProgrammes();
                List<Programme> ledProgrammes = leader.getLedProgrammes();
                for (Programme ledProgramme : ledProgrammes) {
                    System.out.println(ledProgramme);
                }
                view.print("Leader Modules Successfully Shown");
            }
            else if(command.equals("S")){
                view.displayTimetable();



                view.print("Timetable successfully Shown");
            }
            else if (command == "Q") {
                more = false;
                view.print("Quitting...");
            }
        }
    }
}