package controller;

import java.util.List;
import model.module.Programme;
import model.user.Leader;
import view.cli.LeaderCLI;

/**
 * @author Willow
 */

public class LeaderController extends Controller {
    private Leader leader;
    LeaderCLI view;

    public LeaderController(LeaderCLI view, Leader leader) {
        super(view);
        this.view = view;
        this.leader = leader;
    }

    public void start() {
        boolean more = true;

        MAIN_LOOP: while (more) {
            String command = view.prompt("D)isplay Led Programmes S)how Timetable Q)uit\n");

            switch (command.toUpperCase()) {
                //Display Led Programmes
                case "D":
                    view.displayLeaderProgrammes();
                    List<Programme> ledProgrammes = leader.getLedProgrammes();
                    for (Programme ledProgramme : ledProgrammes) {
                        view.print(ledProgramme.getName());
                    }
                    break;
                //Show Timetable
                case "S":
                    view.displayTimetable();

                    view.print("Timetable successfully Shown");
                    break;
                //Quit
                case "Q":
                    more = false;
                    view.print("Quitting...");
                    break;
            }
        }
    }
}