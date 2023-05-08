import javafx.application.Application;
import pt.isec.pa.apoio_poe.ui.gui.MainJFX;

public class Main {
    public static void main(String[] args) throws Exception {
        /*ManagementContext fsm = new ManagementContext();
        ManagementUI ui = new ManagementUI(fsm);
        ui.start(); */
        Application.launch(MainJFX.class,args);
    }
}
