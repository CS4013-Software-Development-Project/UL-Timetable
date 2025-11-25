package view.cli;
import model.module.Programme;
import model.user.Leader;
import java.util.ArrayList;
import java.util.List;

public class LeaderCLI extends MainCLI {
    @Override
    public void displayPanel() {
        System.out.println("D)isplay Led Programmes S)how Timetable Q)uit");
    }

    public void displayLeaderProgrammes(Leader leader){
        List<Programme> ledProgrammes = leader.getLedProgrammes();
        for(int i = 0; i<ledProgrammes.size(); i++){
            System.out.println(ledProgrammes.get(i));
        }
    }

    public void displayTimetable(Leader leader){

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