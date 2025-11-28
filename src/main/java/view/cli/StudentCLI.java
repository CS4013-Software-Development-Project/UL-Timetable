package view.cli;

import model.user.Leader;
import model.user.Student;

public class StudentCLI extends MainCLI {

    @Override
    public void displayPanel() {
        System.out.println("D)isplay Course G)et Student SubGroup S)how Timetable Q)uit");
    }

    public void displayProgramme(){
        System.out.println("Student's Course: ");
    }

    public void getGroups(){
        System.out.println("Sub Group: ");
    }

    public void displayTimetablePrompt(){
        System.out.println("Student Timetable: ");
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