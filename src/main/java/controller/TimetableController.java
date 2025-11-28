package controller;

import model.schedule.Timetable;
import model.user.Admin;
import view.cli.MainCLI;

import java.util.Scanner;

public class TimetableController {
    private Timetable timetable;
    private MainCLI view;
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
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();


                        Admin adminLogin = new Admin(username, password);
                    }
                    else if(command.equals("L")){
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();
                    }
                    else if(command.equals("S")){
                        view.promptUsername();
                        String username = input.nextLine();
                        System.out.println();
                        view.promptPassword();
                        String password = input.nextLine();
                        System.out.println();
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