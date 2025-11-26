package view.cli;

import model.user.Leader;
import model.user.Student;

public class StudentCLI extends MainCLI {

    @Override
    public void displayPanel() {
        System.out.println("D)isplay Programme G)et Student SubGroup S)how Timetable Q)uit");
    }

    public void displayProgramme(Student student){
        System.out.println("Student's Programme: "+student.getProgramme());
    }

    public void getGroups(Student student){
        System.out.println("Sub Group: " + student.getSubgroups());
    }

    public void displayTimetable(Student student){

    }

    @Override
    public void print(String msg){
        System.out.println("[Success] "+msg);
    }

    @Override
    public void error(String msg){
        System.out.println("[Error] "+msg);
    }
}