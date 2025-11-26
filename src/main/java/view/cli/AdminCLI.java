package view.cli;

public class AdminCLI extends MainCLI {
    @Override
    public void displayPanel() {
        System.out.println("A)ppoint Leader to Programme R)emove Leader From Programme C)hange Password U)ser Creation M)odule Creation Q)uit");
    }
    public void userDisplayPanel(){System.out.println("L)eader S)tudent A)dmin B)ack");}
    public void moduleDisplayPanel(){System.out.println("P)rogramme M)odule B)ack");}
    public void promptUsername() {
        System.out.print("Enter Username: ");
    }
    public void promptPassword() {
        System.out.print("Enter Password: ");
    }
    public void promptProgrammeName() {
        System.out.print("Enter Programme name: ");
    }
    public void changePassword(){
        System.out.println("Enter New Password: ");
    }
    public void promptModuleCode(){System.out.println("Enter Module Code: ");}
    public void promptModuleName(){System.out.println("Enter Module Name: ");}

    @Override
    public void print(String msg){
        System.out.println("[Success] "+msg);
    }

    @Override
    public void error(String msg){
        System.out.println("[Error] "+msg);
    }
}