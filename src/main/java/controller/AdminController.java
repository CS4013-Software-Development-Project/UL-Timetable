package controller;
import model.module.Programme;
import model.user.Admin;
import model.user.Leader;
import view.cli.AdminCLI;
import java.util.Scanner;

public class AdminController extends TimetableController{
    private Admin admin;
    private AdminCLI view;
    private Scanner input;

    public void start(){
        boolean more = true;

        while(more){
            view.displayPanel();
            String command = input.nextLine();

            if(command == "A"){
                view.promptLeaderUsername();
                String username = input.nextLine();
                Leader leader = new Leader(username, "welcome2025");
                view.promptProgrammeName();
                String programmeName = input.nextLine();
                Programme programme = new Programme(programmeName);
                admin.appointLeader(leader, programme);
                view.print("Leader added to module");
            }
            else if(command == "R"){
                view.promptLeaderUsername();
                String username = input.nextLine();
                //update when serialisation is done with leader list.
                Leader leader = new Leader(username, "welcome2025");
                view.promptProgrammeName();
                String programmeName = input.nextLine();
                Programme programme = new Programme(programmeName);
                admin.removeLeader(leader, programme);
                view.print("Leader removed from module");
            }
            else if(command == "C"){
                view.changePassword();
                String username = input.nextLine();
                String password = input.nextLine();
                Leader leader = new Leader(username, "welcome2025");
                leader.resetPassword(password);
            }
            else if(command == "Q"){
                more = false;
            }
        }
    }

}