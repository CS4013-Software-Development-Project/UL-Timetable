package controller;

import model.module.Module;
import model.module.Programme;
import model.room.Room;
import model.schedule.Timetable;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import persistence.PersistenceManager;
import view.cli.MainCLI;

/**
 * @author Willow
 */

public class TimetableController {
    private Timetable timetable = new Timetable();
    private MainCLI view = new MainCLI();

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