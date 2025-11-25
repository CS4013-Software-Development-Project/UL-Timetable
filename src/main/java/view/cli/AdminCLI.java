package view.cli;

public class AdminCLI extends MainCLI {
    public void displayAdminPanel(){
        System.out.println("A)ppoint Leader R)emove Leader Q)uit");
    }

    public void promptLeaderUsername() {
        System.out.print("Enter Leader username: ");
    }

    public void promptProgrammeName() {
        System.out.print("Enter Programme username: ");
    }

    public void showSuccess(String message){
        System.out.println("[Success] " + message);
    }

    public void showError(String message){
        System.out.println("[Error] " + message);
    }
}