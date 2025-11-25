package view.cli;

public class AdminCLI extends MainCLI {
    @Override
    public void displayPanel() {
        System.out.println("A)ppoint Leader R)emove Leader C)hange Password Q)uit");
    }

    public void promptLeaderUsername() {
        System.out.print("Enter Leader username: ");
    }

    public void promptProgrammeName() {
        System.out.print("Enter Programme username: ");
    }
    public void changePassword(){
        System.out.println("Enter New Password: ");
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