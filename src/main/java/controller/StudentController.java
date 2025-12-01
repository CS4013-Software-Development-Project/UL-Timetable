package controller;

import model.grouping.Subgroup;
import model.module.Programme;
import model.user.Student;
import persistence.PersistenceManager;
import view.cli.LeaderCLI;
import view.cli.StudentCLI;

/**
 *  Controller class for {@link Student} users.
 *  <p>
 *  The controller allows a {@link Student} to:
 *  <ul>
 *      <li>display the course (programme) they are enrolled in ({@code D})</li>
 *      <li>display the sub‑groups they belong to ({@code G})</li>
 *      <li>display the timetable for their programme ({@code S})</li>
 *      <li>quit the interface ({@code Q})</li>
 *  </ul>
 *  </p>
 */
public class StudentController extends Controller {
    Student student;
    StudentCLI view;

    /**
     * Creates an instance of {@link StudentController} with the provided {@link StudentCLI}
     * @param view the View to use for I/O ops.
     */
    public StudentController(StudentCLI view, Student student) {
        super(view, student);
        this.view = view;
        this.student = student;
    }

    /**
     * Starts the interactive command loop.
     */
    public void start() {
        boolean more = true;

        MAIN_LOOP: while (more) {
            String command = view.prompt("D)isplay Course G)et Student SubGroup S)how Timetable Q)uit\n");

            switch (command.toUpperCase()) {
                //Display course
                case "D":
                    view.displayProgramme();
                    student.getProgramme().getName();

                    view.print(student.getProgramme().getName());

                    break;
                    //Display the student group
                case "G":
                    view.getGroups();
                    System.out.println(student.getSessions());
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