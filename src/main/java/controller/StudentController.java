package controller;

import model.user.Student;
import view.cli.StudentCLI;

import java.util.Scanner;

public class StudentController {
    private Student student = new Student("username", "password");
    private StudentCLI view = new StudentCLI();
    private Scanner input = new Scanner(System.in);

    public void start() {
        boolean more = true;

        while (more) {
            view.displayPanel();
            String command = input.nextLine();

            if (command.equals("D")) {
                view.displayProgramme();
                System.out.println(student.getProgramme());
                view.print("Student Course Successfully Shown");
            }
            else if(command.equals("G")){
                view.getGroups();
                System.out.println(student.getSubgroups());
                view.print("SubGroups successfully Shown");
            }
            else if(command.equals("s")){
                view.displayTimetablePrompt();



                view.print("Timetable successfully Shown");
            }
            else if (command.equals("q")) {
                more = false;
            }
        }
    }
}