package controller;

import model.user.Student;
import view.cli.StudentCLI;

import java.util.Scanner;

public class StudentController {
    private Student student;
    private StudentCLI view;
    private Scanner input = new Scanner(System.in);

    public void start() {
        boolean more = true;

        while (more) {
            view.displayPanel();
            String command = input.nextLine();

            if (command.equals("D")) {
                view.displayProgramme(student);
                view.print("Leader Modules Successfully Shown");
            }
            else if(command.equals("G")){
                view.getGroups(student);
                view.print("SubGroups successfully Shown");
            }
            else if(command.equals("s")){
                view.displayTimetable(student);
                view.print("Timetable successfully Shown");
            }
            else if (command.equals("q")) {
                more = false;
            }
        }
    }
}