package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.ModelManager;

public class MainJFX extends Application {
    ModelManager model;

    public MainJFX() {
        model = new ModelManager();
    }

    @Override
    public void start(Stage stage) throws Exception {
        configureStage(stage, 600, 500);
        configureListPane(new Stage(), stage.getX() + stage.getWidth(), stage.getY());
    }

    private void configureStage(Stage stage, double x, double y) {
        RootPane root = new RootPane(model);
        Scene scene = new Scene(root,x,y);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Gestao de Projeto e Estagios do DEIS do ISEC");
        stage.show();
    }

    private void configureListPane(Stage stage, double x, double y) {
        ListPane list = new ListPane(model);
        Scene scene = new Scene(list,450,400);
        stage.setScene(scene);
        stage.setTitle("Lista@Apoio_Poe");
        if (x >= 0 && y >= 0) {
            stage.setX(x);
            stage.setY(y);
        }
        stage.show();
    }
}
