package controller;
import model.module.Programme;
import model.user.Admin;
import model.user.Leader;
import persistence.PersistenceManager;
import view.cli.AdminCLI;
import java.util.Scanner;

public class AdminController extends TimetableController{
    private String username,password;
    private Admin admin = new Admin(username, password);
    private AdminCLI view = new AdminCLI();
    private Scanner input = new Scanner(System.in);

    public void start(){
        boolean more = true;

        while(more){
            view.displayPanel();
            String command = input.nextLine();

            if(command.equals("A")){
                view.promptUsername();
                String username = input.nextLine();
                boolean check = false;
                Leader leader = new Leader("blank","blank");
                for(int i = 0; i<PersistenceManager.getLeaders().size(); i++){
                    if(PersistenceManager.getLeaders().get(i).getUsername().equals(username)){
                        leader = PersistenceManager.getLeaders().get(i);
                        check = true;
                    }
                }
                if(!check){
                    view.error("Leader not found");
                }
                else {
                    view.promptProgrammeName();
                    String programmeName = input.nextLine();
                    Programme programme = new Programme(programmeName);
                    admin.appointLeader(leader, programme);
                    view.print("Leader added to module");
                }
            }
            else if(command.equals("R")){
                view.promptUsername();
                String username = input.nextLine();
                boolean check = false;
                Leader leader = new Leader("blank","blank");
                for(int i = 0; i<PersistenceManager.getLeaders().size(); i++){
                    if(PersistenceManager.getLeaders().get(i).getUsername().equals(username)){
                        leader = PersistenceManager.getLeaders().get(i);
                        check = true;
                    }
                }
                if(!check) {
                    view.error("Leader not Found");
                }
                else {
                    view.promptProgrammeName();
                    String programmeName = input.nextLine();
                    Programme programme = new Programme(programmeName);
                    admin.removeLeader(leader, programme);
                    view.print("Leader removed from module");
                }
            }
            else if(command.equals("C")){
                view.changePassword();
                String username = input.nextLine();
                String password = input.nextLine();
                Leader leader = new Leader(username, "welcome2025");
                leader.resetPassword(password);
                view.print("Password Reset");
            }
            else if(command.equals("U")){
                boolean extra = true;
                while(extra) {
                    view.userDisplayPanel();
                    command = input.nextLine();
                    if (command.equals("L")) {
                        view.promptUsername();
                        String username = input.nextLine();
                        view.promptPassword();
                        String password = input.nextLine();
                        admin.addLeader(username, password);
                        view.print("Leader Added");
                    } else if (command.equals("S")) {
                        view.promptUsername();
                        String username = input.nextLine();
                        view.promptPassword();
                        String password = input.nextLine();
                        admin.addStudent(username, password);
                        view.print("Student Added");
                    } else if (command.equals("A")) {
                        view.promptUsername();
                        String username = input.nextLine();
                        view.promptPassword();
                        String password = input.nextLine();
                        admin.addAdmin(username, password);
                        view.print("Admin Added");
                    }
                    else if(command.equals("B")){
                        extra = false;
                        view.print("Backing out...");
                        command = "invalid";
                    }
                }
            }
            else if(command.equals("M")){
                boolean extra = true;
                while(extra){
                    view.moduleDisplayPanel();
                    command = input.nextLine();
                    if(command.equals("P")){
                        view.promptProgrammeName();
                        String name = input.nextLine();
                        admin.addProgramme(name);
                        view.print("Programme Added");
                    }
                    else if(command.equals("M")){
                        view.promptProgrammeName();
                        String name = input.nextLine();
                        view.promptModuleCode();
                        String code = input.nextLine();
                        view.promptModuleName();
                        String moduleName = input.nextLine();
                        boolean checker = false;

                        for(int i = 0; i<PersistenceManager.getProgrammes().size(); i++){
                            if(PersistenceManager.getProgrammes().get(i).getName().equals(name)){
                                admin.addModule(PersistenceManager.getProgrammes().get(i), code, moduleName);
                                view.print("Module Added");
                                checker = true;
                            }
                        }
                        if(!checker){
                            view.error("Programme not found");
                        }
                    }
                    else if(command.equals("B")){
                        extra = false;
                        view.print("Backing out...");
                        command = "Invalid";
                    }
                }
            }
            else if(command.equals("Q")){
                more = false;
                view.print("Quitting...");
            }
        }
    }

}