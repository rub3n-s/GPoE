package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.model.ModelManager;

import java.io.File;
import java.io.IOException;

public class AppMenu extends MenuBar {
    ModelManager model;
    Menu mnFile;
    MenuItem miLoad, miSave, miExit;

    public AppMenu(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnFile = new Menu("Ficheiro");
        miLoad = new MenuItem("_Carregar");
        miLoad.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        miSave = new MenuItem("_Guardar");
        miExit = new MenuItem("_Terminar");
        mnFile.getItems().addAll(miLoad, miSave, miExit);

        this.getMenus().addAll(mnFile);
    }

    private void registerHandlers() {
        //TODO: init
        miLoad.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir ficheiro...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Guardar (.*dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("Todos", "*.*")
            );
            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());
            if (hFile != null) {
                try {
                    model.load(hFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        miSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar ficheiro...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Guardar (.*dat)", "*.dat"),
                    new FileChooser.ExtensionFilter("Todos", "*.*")
            );
            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());
            if (hFile != null) {
                try {
                    model.save(hFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        miExit.setOnAction(actionEvent -> Platform.exit());
    }

    private void update() {    }
}
