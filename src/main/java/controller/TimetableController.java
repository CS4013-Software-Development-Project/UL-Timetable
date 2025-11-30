package controller;

import model.module.Module;
import model.module.Programme;
import model.room.Room;
import model.schedule.Timetable;
import persistence.PersistenceManager;
import view.cli.MainCLI;

/**
 * Simple menu controller that allows a user to view various timetables
 * (programme, module or room) after logging in.
 *
 * @author Willow Johnson
 */
public class TimetableController {
    /**
     * Timetable data for this controller.
     */
    private Timetable timetable = new Timetable();
    /**
     * The view used by this controller.
     */
    private MainCLI view = new MainCLI();

    /**
     * Starts the interactive command loop.
     */
    public void start(){
        boolean more = true;

        while(more){
            String command = view.prompt("L)ogin C)ourse Timetable M)odule Timetable R)oom Timetable Q)uit\n");
            command = command.toUpperCase();

            switch (command) {
                case "L":
                    LoginController login = new LoginController(view);
                    login.start();
                    break;
                case "C":
                    String ProgrammeName = view.prompt("Enter Programme name: ");

                    for(Programme programme : PersistenceManager.programmes.values()){
                        if(programme.getName().equals(ProgrammeName)){
                            int year = Integer.valueOf(view.prompt("Course Year: "));

                            //Course Timetable of Appropriate Year Prints Here

                            break;
                        }
                    }
                    break;
                case "M":
                    String moduleCode = view.prompt("Enter Module Code: ");

                    for(Module module : PersistenceManager.modules.values()){
                        if(module.getModuleCode().equals(moduleCode)){

                            //Module Timetable Here

                            break;
                        }
                    }
                    break;
                case "R":
                    String roomNumber = view.prompt("Enter Room Number: ");

                    for(Room room : PersistenceManager.rooms.values()){
                        if(room.getroomNumber().equals(roomNumber)){

                            //Room Timetable Here

                            break;
                        }
                    }
                    break;
                case "Q":
                    more = false;
                    view.print("Quiting...");
                    break;
            }
        }
    }

}