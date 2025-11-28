package controller;

import model.schedule.Timetable;
import model.user.Admin;
import model.user.Leader;
import model.user.Student;
import persistence.PersistenceManager;
import view.cli.MainCLI;

import java.util.Scanner;

public class TimetableController {
    private Timetable timetable = new Timetable();
    private MainCLI view = new MainCLI();
    private Scanner input = new Scanner(System.in);

    public void start(){
        boolean more = true;

        while(more){
            view.displayPanel();
            String command = input.nextLine();

            if(command.equals("L")){
                boolean extra = true;

                while(extra){
                    view.userLoginPanel();
                    command = input.nextLine();

                    if(command.equals("A")){
                        boolean userCheck = false;
                        boolean passwordCheck = false;
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();


                        Admin adminLogin = new Admin(username, password);

                        for(int i = 0; i<PersistenceManager.getAdmins().size(); i++){
                            if(PersistenceManager.getAdmins().get(i).getUsername().equals(username)){
                                userCheck = true;
                            }
                        }

                        if(userCheck == false){
                            view.error("Account with username '"+username+"' not found." );
                        }
                        else{
                            passwordCheck = adminLogin.authenticate(password);
                        }

                        if(passwordCheck == false){
                            view.error("Password '"+password+"' is incorrect." );
                        }
                        else{
                            extra = false;
                            more = false;
                            view.print("Welcome "+username);
                            new AdminController().start();
                        }
                    }
                    else if(command.equals("L")){
                        boolean userCheck = false;
                        boolean passwordCheck = false;
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();

                        Leader leaderLogin = new Leader(username, password);

                        for(int i = 0; i<PersistenceManager.getLeaders().size(); i++){
                            if(PersistenceManager.getLeaders().get(i).getUsername().equals(username)){
                                userCheck = true;
                            }
                        }

                        if(userCheck == false){
                            view.error("Account with username '"+username+"' not found." );
                        }
                        else{
                            passwordCheck = leaderLogin.authenticate(password);
                        }

                        if(passwordCheck == false){
                            view.error("Password '"+password+"' is incorrect." );
                        }
                        else{
                            extra = false;
                            more = false;
                            view.print("Welcome "+username);
                            new LeaderController().start();
                        }
                    }
                    else if(command.equals("S")){
                        boolean userCheck = false;
                        boolean passwordCheck = false;
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();

                        Student studentLogin = new Student(username, password);

                        for(int i = 0; i<PersistenceManager.getStudents().size(); i++){
                            if(PersistenceManager.getStudents().get(i).getUsername().equals(username)){
                                userCheck = true;
                            }
                        }

                        if(userCheck == false){
                            view.error("Account with username '"+username+"' not found." );
                        }
                        else{
                            passwordCheck = studentLogin.authenticate(password);
                        }

                        if(passwordCheck == false){
                            view.error("Password '"+password+"' is incorrect." );
                        }
                        else{
                            extra = false;
                            more = false;
                            view.print("Welcome "+username);
                            new StudentController().start();
                        }
                    }
                    else if(command.equals("B")){
                        extra = false;
                        view.print("Backing Out...");
                        command = "0";
                    }
                }
            }
            else if(command.equals("C")){
                view.promptProgrammeName();
                String programmeName = input.nextLine();

                for(int i = 0; i<PersistenceManager.getProgrammes().size(); i++){
                    if(PersistenceManager.getProgrammes().get(i).getName().equals(programmeName)){
                        view.promptYear();
                        int year = input.nextInt();
                        System.out.println("Course Timetable of Appropraite Year Prints Here");
                    }
                }
            }
            else if(command.equals("M")){
                view.promptModuleCode();
                String moduleCode = input.nextLine();

                for(int i = 0; i<PersistenceManager.getModules().size(); i++){
                    if(PersistenceManager.getModules().get(i).getModuleCode().equals(moduleCode)){
                        System.out.println("Module Timetable Here");
                    }
                }
            }
            else if(command.equals("R")){
                view.promptRoomName();
                String roomNumber = input.nextLine();

                for(int i = 0; i<PersistenceManager.getRooms().size(); i++){
                    if(PersistenceManager.getRooms().get(i).getroomNumber().equals(roomNumber)){
                        System.out.println("Room Timetable Here");
                    }
                }
            }
            else if(command.equals("Q")){
                more = false;
                view.print("Quiting...");
            }
        }
    }

}