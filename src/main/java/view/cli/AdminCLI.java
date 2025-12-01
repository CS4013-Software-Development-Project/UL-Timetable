package view.cli;
/**
 * @author Willow
 */
public class AdminCLI extends MainCLI {
    @Override
    public void displayPanel() {
        System.out.println("A)ppoint Leader to Programme R)emove Leader From Programme C)hange Password U)ser Creation M)odule Creation Q)uit");
    }

    public void changePassword(){
        System.out.print("Enter New Password: ");
    }

    @Override
    public void print(String msg){
        System.out.println("[Success] "+msg);
    }

    public void display(String msg) { System.out.println(msg); }

    @Override
    public void error(String msg){
        System.out.println("[Error] "+msg);
    }
}