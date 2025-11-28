package view.cli;

import controller.TimetableController;
import model.user.User;

public class MainCLI implements ICLIView {
    @Override
    public void displayPanel() {
        System.out.println("L)ogin P)rogramme Timetable M)odule Timetable Q)uit");
    }

    public void userLoginPanel() {
        System.out.println("A)dmin L)eader S)tudent");
    }

    public void promptUsername(){
        System.out.print("Username: ");
    }

    public void promptPassword(){
        System.out.print("Password: ");
    }

    @Override
    public void print(String msg) {

    }

    @Override
    public void error(String msg) {

    }
    
    @Override
    public void run() {

    }

    @Override
    public void setController(TimetableController controller) {

    }

    @Override
    public void setUser(User user) {

    }
}