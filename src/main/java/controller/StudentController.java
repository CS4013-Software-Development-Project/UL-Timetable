package controller;

import model.user.Student;
import view.cli.StudentCLI;

import java.util.Scanner;

public class StudentController {
    private Student student;
    private StudentCLI view;
    private Scanner input;

    public void start() {
        boolean more = true;

        while (more) {
            view.displayPanel();
            String command = input.nextLine();

            if (command == "D") {
                view.displayProgramme(student);
                view.print("Leader Modules Successfully Shown");
            }
            else if(command == "G"){
                view.getGroups(student);
                view.print("Groups successfully Shown");
            }
            else if(command == "S"){
                view.displayTimetable(student);
                view.print("Timetable successfully Shown");
            }
            else if (command == "Q") {
                more = false;
            }
        }
    }
}