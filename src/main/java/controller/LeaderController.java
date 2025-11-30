package controller;
import java.util.List;
import model.module.Programme;
import model.user.Leader;
import view.cli.AdminCLI;
import view.cli.LeaderCLI;

public class LeaderController extends Controller {
    private Leader leader;
    LeaderCLI view;

    public LeaderController(LeaderCLI view) {
        super(view);
        this.view = view;
    }

    public void start() {
        boolean more = true;

        MAIN_LOOP: while (more) {
            String command = view.prompt("D)isplay Led Programmes S)how Timetable Q)uit");

            switch (command) {
                case "D"://Display Led Programmes
                    view.displayLeaderProgrammes();
                    List<Programme> ledProgrammes = leader.getLedProgrammes();
                    for (Programme ledProgramme : ledProgrammes) {
                        view.print(ledProgramme.getName());
                    }
                    break;
                case "S"://Show Timetable
                    view.displayTimetable();


                    view.print("Timetable successfully Shown");
                    break;
                case "Q"://Quit
                    more = false;
                    view.print("Quitting...");
                    break;
            }
        }
    }
}