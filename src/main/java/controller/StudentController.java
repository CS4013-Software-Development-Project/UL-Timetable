package controller;

import model.user.Student;
import view.cli.LeaderCLI;
import view.cli.StudentCLI;


public class StudentController extends Controller {
    private Student student;
    StudentCLI view;

    public StudentController(StudentCLI view) {
        super(view);
        this.view = view;
    }

    public void start() {
        boolean more = true;

        MAIN_LOOP:  while (more) {
            String command = view.prompt("D)isplay Course G)et Student SubGroup S)how Timetable Q)uit");
            command = command.toUpperCase();
            switch (command) {
                case "D":
                    view.displayProgramme();
                    System.out.println(student.getProgramme());
                    view.print("Student Course Successfully Shown");
                    break;
                case "G":
                    view.getGroups();
                    System.out.println(student.getSubgroups());
                    view.print("SubGroups successfully Shown");
                    break;
                case "S":
                    view.displayTimetablePrompt();


                    view.print("Timetable successfully Shown");
                    break;
                case "Q":
                    more = false;
                    break;
            }
        }
    }
}