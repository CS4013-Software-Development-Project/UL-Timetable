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
            view.displayAdminPanel();
            String command = input.nextLine();

            if(command == "A"){
                view.promptLeaderUsername();
                String username = input.nextLine();
                Leader leader = new Leader(username, "");
                view.promptProgrammeName();
                String programmeName = input.nextLine();
                Programme programme = new Programme(programmeName);
                admin.appointLeader(leader, programme);
                view.showSuccess("Leader added to module");
            }
            else if(command == "R"){
                view.promptLeaderUsername();
                String username = input.nextLine();
                Leader leader = new Leader(username, "");
                view.promptProgrammeName();
                String programmeName = input.nextLine();
                Programme programme = new Programme(programmeName);
                admin.removeLeader(leader, programme);
                view.showSuccess("Leader removed from module");
            }
            else if(command == "Q"){
                more = false;
            }
        }
    }

}