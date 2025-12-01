package controller;

import model.module.Programme;
import model.user.Student;
import persistence.PersistenceManager;
import view.cli.LeaderCLI;
import view.cli.StudentCLI;

/**
 * @author Willow
 */

public class StudentController extends Controller {
    private Student student;// = new Student("username","password");
    StudentCLI view;

    public StudentController(StudentCLI view, Student student) {
        super(view);
        this.view = view;
        this.student = student;
    }

    public void start() {
        boolean more = true;

        MAIN_LOOP: while (more) {
            String command = view.prompt("D)isplay Course G)et Student SubGroup S)how Timetable Q)uit\n");

            switch (command.toUpperCase()) {
                //Display course
                case "D":
                    view.displayProgramme();
                    student.getProgramme().getName();

                    System.out.println(student.getProgramme().getName());
                    view.print("Student Course Successfully Shown");
                    break;
                    //Display the student group
                case "G":
                    view.getGroups();
                    System.out.println(student.getSubgroups());
                    view.print("SubGroups successfully Shown");
                    break;
                    //Show timetable
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