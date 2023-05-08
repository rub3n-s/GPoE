package pt.isec.pa.apoio_poe.ui.gui.candidatura;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

import java.util.ArrayList;
import java.util.Collections;

public class CandidaturaFechadaUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    /* Menu Inicial */
    VBox menuInicial;
    Button[] btnsMenuInicial;

    /* Consultar Candidaturas */
    VBox consultarCandidaturas;
    Button btnConsultarVoltar;
    Button[] btnConsultarCand;

    /* Consultar Propostas */
    VBox consultarPropostas;
    CheckBox[] checkConsultarProp;
    Button[] btnConsultarProp;

    public CandidaturaFechadaUI(ModelManager model) {
        this.model = model;
        singleton = Singleton.Singleton();

        /*  Menu Inicial */
        menuInicial = new VBox(10);
        btnsMenuInicial = new Button[5];

        /* Consultar Candidaturas */
        btnConsultarCand = new Button[3];
        consultarCandidaturas = new VBox(10);
        consultarPropostas = new VBox(10);
        btnConsultarVoltar = new Button("Voltar");

        /* Consultar Propostas */
        btnConsultarProp = new Button[2];
        checkConsultarProp = new CheckBox[4];

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #ffee80;");

        Label label = new Label("CANDIDATURA (FECHADA)");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /*================== Menu Inicial ==================*/
        this.setCenter(menuInicial);
        for (int i = 0; i < btnsMenuInicial.length; i++)  {

            switch(i) {
                case 0 -> btnsMenuInicial[i] = new Button("Consultar Candidaturas");
                case 1 -> btnsMenuInicial[i] = new Button("Consultar Propostas (Filtros)");
                case 2 -> btnsMenuInicial[i] = new Button("Exportar Fase 2");
                case 3 -> btnsMenuInicial[i] = new Button("Avancar");
                case 4 -> btnsMenuInicial[i] = new Button("Voltar");
            }
            if (i != 3 && i != 4) {
                btnsMenuInicial[i].setMinWidth(170);
                menuInicial.getChildren().add(btnsMenuInicial[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);

        // Botao VOLTAR
        VBox vBoxVoltarBtn = new VBox(btnsMenuInicial[4]);
        vBoxVoltarBtn.setAlignment(Pos.CENTER);
        vBoxVoltarBtn.setPadding(new Insets(20));
        this.setLeft(vBoxVoltarBtn);

        // Botao AVANCAR
        VBox vBoxAvancarBtn = new VBox(btnsMenuInicial[3]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /*================== Consultar Candidaturas ==================*/
        consultarCandidaturas.getChildren().add(new Label("Candidatura > Consultar Candidaturas"));
        VBox vBoxConsultarCand = new VBox(10);
        for (int i = 0; i < btnConsultarCand.length; i++) {
            switch(i) {
                case 0 -> btnConsultarCand[i] = new Button("Candidaturas Com Autoproposta");
                case 1 -> btnConsultarCand[i] = new Button("Alunos com Candidatura");
                case 2 -> btnConsultarCand[i] = new Button("Alunos sem Candidatura");
            }
            btnConsultarCand[i].setMinWidth(200);
            vBoxConsultarCand.getChildren().add(btnConsultarCand[i]);
        }
        vBoxConsultarCand.getChildren().add(btnConsultarVoltar);
        vBoxConsultarCand.setAlignment(Pos.CENTER);
        consultarCandidaturas.setAlignment(Pos.CENTER);
        consultarCandidaturas.getChildren().add(vBoxConsultarCand);

        /*================== Consultar Propostas ==================*/
        consultarPropostas.getChildren().add(new Label("Candidatura > Consultar Propostas"));
        VBox vBoxConsultarProp = new VBox(10);

        for (int i = 0; i < checkConsultarProp.length; i++) {
            switch(i) {
                case 0 -> checkConsultarProp[i] = new CheckBox("Autopropostas de Alunos");
                case 1 -> checkConsultarProp[i] = new CheckBox("Propostas de Docentes");
                case 2 -> checkConsultarProp[i] = new CheckBox("Propostas Com Candidaturas");
                case 3 -> checkConsultarProp[i] = new CheckBox("Propostas Sem Candidaturas");
            }
            vBoxConsultarProp.getChildren().add(checkConsultarProp[i]);
        }

        HBox hBoxConsultarPropBtns = new HBox(50,btnConsultarProp[0] = new Button("Consultar"),btnConsultarProp[1] = new Button("Cancelar"));
        hBoxConsultarPropBtns.setAlignment(Pos.CENTER);

        vBoxConsultarProp.getChildren().add(hBoxConsultarPropBtns);
        vBoxConsultarProp.setAlignment(Pos.CENTER);

        consultarPropostas.setAlignment(Pos.CENTER);
        consultarPropostas.getChildren().add(vBoxConsultarProp);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        btnsMenuInicial[0].setOnAction(actionEvent -> this.setCenter(consultarCandidaturas));     // Consultar Candidaturas
        btnsMenuInicial[1].setOnAction(actionEvent -> this.setCenter(consultarPropostas));     // Consultar Propostas (FILTROS)
        btnsMenuInicial[2].setOnAction(actionEvent -> exportarFase2());     // Exportar Fase 2
        btnsMenuInicial[3].setOnAction(actionEvent -> model.avancar());     // Avancar
        btnsMenuInicial[4].setOnAction(actionEvent -> model.voltar());        // Voltar

        /*  Consultar Candidaturas */
        btnConsultarCand[0].setOnAction(actionEvent -> consultarComAutoProp());
        btnConsultarCand[1].setOnAction(actionEvent -> consultarComCandidatura());
        btnConsultarCand[2].setOnAction(actionEvent -> consultarSemCandidatura());
        btnConsultarVoltar.setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Consultar Propostas */
        btnConsultarProp[0].setOnAction(actionEvent -> consultarPropostas());
        btnConsultarProp[1].setOnAction(actionEvent -> this.setCenter(menuInicial));
    }

    private void update() {
        this.setVisible(model != null && model.getState() == ManagementState.CANDIDATURA_FECHADA);
    }

    public void consultarComAutoProp() {
        model.consultarComAutoProp();
        showAlert("Lista de Candidaturas Com Autoproposta");
        this.setCenter(menuInicial);
    }

    public void consultarComCandidatura() {
        model.consultarComCandidatura();
        showAlert("Lista de Propostas (com Candidatura)");
        this.setCenter(menuInicial);
    }

    public void consultarSemCandidatura() {
        model.consultarSemCandidatura();
        showAlert("Lista de Propostas (sem Candidatura)");
        this.setCenter(menuInicial);
    }

    public void consultarPropostas() {
        ArrayList<Integer> opcoes = new ArrayList<>();
        for (int i = 0; i < checkConsultarProp.length; i++)
            if (checkConsultarProp[i].isSelected())
                opcoes.add(i+1);
        if (opcoes.isEmpty())
            Collections.addAll(opcoes,1,2,3,4);
        for (int i = 0; i < checkConsultarProp.length; i++) // limpa as checkbox
            checkConsultarProp[i].setSelected(false);
        model.consultarPropostas(opcoes);
        showAlert("Lista de Propostas (c/ Filtros)");
        this.setCenter(menuInicial);
    }

    private void exportarFase2() {
        model.exportarFase2();
        showAlert("Exportar Fase 2");
    }

    private void showAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.setContentText(singleton.message);
        alert.show();
    }
}
