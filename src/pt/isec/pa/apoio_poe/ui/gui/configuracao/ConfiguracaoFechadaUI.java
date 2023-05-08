package pt.isec.pa.apoio_poe.ui.gui.configuracao;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

public class ConfiguracaoFechadaUI extends BorderPane {
    ModelManager model;
    Button [] menuInicialBtns;
    Singleton singleton;

    public ConfiguracaoFechadaUI(ModelManager model) {
        this.model = model;
        menuInicialBtns = new Button[5];
        singleton = Singleton.Singleton();
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Label label = new Label("CONFIGURACAO (FECHADA)");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);
        this.setStyle("-fx-background-color: #A0FF80;");

        VBox menuInicial = new VBox(10);
        for (int i = 0; i < menuInicialBtns.length; i++)  {
            switch(i) {
                case 0 -> menuInicialBtns[i] = new Button("Consultar Alunos");
                case 1 -> menuInicialBtns[i] = new Button("Consultar Docentes");
                case 2 -> menuInicialBtns[i] = new Button("Consultar Propostas");
                case 3 -> menuInicialBtns[i] = new Button("Exportar Fase 1");
                case 4 -> menuInicialBtns[i] = new Button("Avancar");
            }
            if (i != 4) {
                menuInicialBtns[i].setMinWidth(150);
                menuInicial.getChildren().add(menuInicialBtns[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);
        this.setCenter(menuInicial);

        VBox vBoxVoltarBtn = new VBox();
        vBoxVoltarBtn.setPadding(new Insets(50));
        this.setLeft(vBoxVoltarBtn);

        VBox vBoxAvancarBtn = new VBox(menuInicialBtns[4]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        menuInicialBtns[0].setOnAction(actionEvent ->  consultarAlunos());     // Consultar Alunos
        menuInicialBtns[1].setOnAction(actionEvent -> consultarDocentes());     // Consultar Docentes
        menuInicialBtns[2].setOnAction(actionEvent -> consultarPropostas());     // Consultar Propostas
        menuInicialBtns[3].setOnAction(actionEvent -> exportarFase1());       // Exportar Fase 1
        menuInicialBtns[4].setOnAction(actionEvent -> model.avancar());       // Avancar
    }

    private void update() {
        this.setVisible(model != null && model.getState() == ManagementState.CONFIGURACAO_FECHADA);
    }

    /*===================================== EXPORTAR FASE =====================================*/
    private void exportarFase1() {
        model.exportarFase1();
        showAlert("Exportar Fase 1");
    }

    /*===================================== ALUNO =====================================*/
    private void consultarAlunos() {
        model.consultarAlunos();
        showAlert("Lista de Alunos");
    }

    /*===================================== DOCENTE =====================================*/
    private void consultarDocentes() {
        model.consultarDocentes();
        showAlert("Lista de Docentes");
    }

    /*===================================== PROPOSTA =====================================*/
    private void consultarPropostas() {
        model.consultarPropostas();
        showAlert("Lista de Propostas");
    }

    private void showAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.getDialogPane().setMinWidth(500);
        alert.setContentText(singleton.message);
        alert.show();
    }
}
