package view.cli;
import model.module.Programme;
import model.user.Leader;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Willow
 */
public class LeaderCLI extends MainCLI {
    @Override
    public void displayPanel() {
        System.out.println("D)isplay Led Programmes S)how Timetable Q)uit");
    }

    public void displayLeaderProgrammes(){
       System.out.println("Leader's Led Courses: ");
    }

    public void displayTimetable(){
        System.out.println("Leader Timetable:");
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