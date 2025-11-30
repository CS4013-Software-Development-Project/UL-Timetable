package controller;

import java.util.List;
import model.module.Programme;
import model.user.Leader;
import view.cli.LeaderCLI;

/**
 * Controller for a {@link model.user.Leader}.
 * <p>
 * The controller allows a Leader to:
 * <ul>
 *   <li>display all programmes that the leader is assigned to ({@code D})</li>
 *   <li>display the current timetable ({@code S})</li>
 *   <li>quit the application ({@code Q})</li>
 * </ul>
 * All I/O is performed through the {@link view.cli.LeaderCLI} instance.
 * Uses the {@link persistence.PersistenceManager} singleton.
 * </p>
 *
 * @see model.user.Leader
 * @see view.cli.LeaderCLI
 *
 * @author Willow Johnson
 */
public class LeaderController extends Controller {
    /** The leader this controller is attached to. */
    private Leader leader;
    /** The view that this controller uses for all I/O. */
    LeaderCLI view;

    /**
     * Constructs a new instance of {@code LeaderController} using the supplied view.
     * @param view the command‑line view used to display information and read commands
     */
    public LeaderController(LeaderCLI view) {
        super(view);
        this.view = view;
    }

    /**
     * Starts the interactive command loop.
     */
    public void start() {
        boolean more = true;

        MAIN_LOOP: while (more) {
            String command = view.prompt("D)isplay Led Programmes S)how Timetable Q)uit");

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