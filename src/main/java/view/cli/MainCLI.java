package view.cli;

import controller.TimetableController;
import model.user.User;

public class MainCLI extends CLIView {
    @Override
    public void displayPanel() {
        System.out.println("L)ogin C)ourse Timetable M)odule Timetable R)oom Timetable Q)uit");
    }

    public void userLoginPanel() {
        System.out.println("A)dmin L)eader S)tudent B)ack");
    }

    public void promptUsername(){
        System.out.print("Username: ");
    }

    public void promptPassword(){
        System.out.print("Password: ");
    }

    public void promptProgrammeName() {
        System.out.print("Enter Programme name: ");
    }
    public void promptYear(){
        System.out.println("Year: ");
    }
    public void promptModuleCode(){System.out.print("Enter Module Code: ");}

    public void promptRoomName(){System.out.print("Enter Room Name: ");}

    @Override
    public void print(String msg) {
        System.out.println("[SUCCESS] "+ msg);
    }

    @Override
    public void error(String msg) {
        System.out.println("[ERROR] "+ msg);
    }

    @Override
    public void setController(TimetableController controller) {

    }

    @Override
    public void setUser(User user) {

    }
}