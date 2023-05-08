package pt.isec.pa.apoio_poe.ui.gui.proposta;

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

public class PropostaFechadaUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    /* Menu Inicial*/
    VBox menuInicial;
    Button [] menuInicialBtns;

    /* Consultar */
    VBox consultar;
    Button[] consultarBtns;

    /* Consultar Atribuicao Propostas*/
    VBox consultarAtribPropostas;
    CheckBox[] checkConsultarProp;
    Button[] consultarAtribBtns;

    public PropostaFechadaUI(ModelManager model) {
        this.model = model;
        singleton = Singleton.Singleton();

        /* Menu Inicial*/
        menuInicial = new VBox(10);
        menuInicialBtns = new Button[5];

        /* Consultar */
        consultar = new VBox(10);
        consultarBtns = new Button[5];

        /* Consultar Atribuicao Propostas*/
        consultarAtribPropostas = new VBox(10);
        checkConsultarProp = new CheckBox[4];
        consultarAtribBtns = new Button[2];

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #d080ff;");

        Label label = new Label("PROPOSTA (FECHADA)");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /* Menu Inicial */
        this.setCenter(menuInicial);
        for (int i = 0; i < menuInicialBtns.length; i++)  {
            switch(i) {
                case 0 -> menuInicialBtns[i] = new Button("Consultar Propostas");
                case 1 -> menuInicialBtns[i] = new Button("Consultar Atribuicao Propostas");
                case 2 -> menuInicialBtns[i] = new Button("Exportar Fase 3");
                case 3 -> menuInicialBtns[i] = new Button("Avancar");
                case 4 -> menuInicialBtns[i] = new Button("Voltar");
            }
            if (i != 3 && i != 4) {
                menuInicialBtns[i].setMinWidth(235);
                menuInicial.getChildren().add(menuInicialBtns[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);

        // Botao VOLTAR
        VBox vBoxVoltarBtn = new VBox(menuInicialBtns[4]);
        vBoxVoltarBtn.setAlignment(Pos.CENTER);
        vBoxVoltarBtn.setPadding(new Insets(20));
        this.setLeft(vBoxVoltarBtn);

        // Botao AVANCAR
        VBox vBoxAvancarBtn = new VBox(menuInicialBtns[3]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /* Consultar */
        consultar.getChildren().add(new Label("Proposta > Consultar"));
        VBox vBoxConsultar = new VBox(
                10,
                consultarBtns[0] = new Button("Com Autoproposta Associada"),
                consultarBtns[1] = new Button("Com Candidatura Registada"),
                consultarBtns[2] = new Button("Tem Proposta Atribuida"),
                consultarBtns[3] = new Button("Nao Tem Proposta Atribuida"),
                consultarBtns[4] = new Button("Voltar")
        );
        for (int i = 0; i < consultarBtns.length - 1; i++) consultarBtns[i].setMinWidth(200);
        vBoxConsultar.setAlignment(Pos.CENTER);
        consultar.setAlignment(Pos.CENTER);
        consultar.getChildren().add(vBoxConsultar);

        /* Consultar Com Filtros*/
        consultarAtribPropostas.getChildren().add(new Label("Proposta > Consultar com Filtros"));

        VBox vBoxCheckBoxConsultar = new VBox(10);
        for (int i = 0; i < checkConsultarProp.length; i++) {
            switch(i) {
                case 0 -> checkConsultarProp[i] = new CheckBox("Autopropostas de Alunos");
                case 1 -> checkConsultarProp[i] = new CheckBox("Propostas de Docentes");
                case 2 -> checkConsultarProp[i] = new CheckBox("Propostas Disponiveis");
                case 3 -> checkConsultarProp[i] = new CheckBox("Propostas Atribuidas");
            }
            vBoxCheckBoxConsultar.getChildren().add(checkConsultarProp[i]);
        }

        HBox hBoxCheckConsultarProp = new HBox(50, consultarAtribBtns[0] = new Button("Consultar"),consultarAtribBtns[1] = new Button("Voltar"));
        hBoxCheckConsultarProp.setAlignment(Pos.CENTER);

        vBoxCheckBoxConsultar.getChildren().add(hBoxCheckConsultarProp);
        vBoxCheckBoxConsultar.setAlignment(Pos.CENTER);

        consultarAtribPropostas.getChildren().add(vBoxCheckBoxConsultar);
        consultarAtribPropostas.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        menuInicialBtns[0].setOnAction(actionEvent -> this.setCenter(consultar));          // Consultar Propostas
        menuInicialBtns[1].setOnAction(actionEvent -> consultarAtribuicaoPropostas());        // Consultar Atribuicao Propostas
        menuInicialBtns[2].setOnAction(actionEvent -> exportarFase3());      // Exportar Fase 3
        menuInicialBtns[3].setOnAction(actionEvent -> model.avancar());     // Avancar
        menuInicialBtns[4].setOnAction(actionEvent -> model.voltar());     // Voltar

        /* Consultar */
        consultarBtns[0].setOnAction(actionEvent -> consultarComAutoProp());
        consultarBtns[1].setOnAction(actionEvent -> consultarAlunosRegistadas());
        consultarBtns[2].setOnAction(actionEvent -> consultarAlunosAtribuidas());
        consultarBtns[3].setOnAction(actionEvent -> consultarAlunosNaoAtribuidas());
        consultarBtns[4].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Consultar c/ Filtros*/
        consultarAtribBtns[0].setOnAction(actionEvent -> consultarAtribuicaoPropostas());
        consultarAtribBtns[1].setOnAction(actionEvent -> this.setCenter(menuInicial));
    }

    private void update() {
        this.setVisible(model != null && model.getState() == ManagementState.PROPOSTA_FECHADA);
    }

    private void consultarComAutoProp() {
        model.consultarComAutoProp();
        showAlert("Com Autoproposta Associada");
    }

    private void consultarAlunosRegistadas() {
        model.consultarAlunosRegistadas();
        showAlert("Com Candidatura Registada");
    }

    private void consultarAlunosAtribuidas() {
        model.consultarAlunosAtribuidas();
        showAlert("Tem Proposta Atribuida");
    }

    private void consultarAlunosNaoAtribuidas() {
        model.consultarAlunosNaoAtribuidas();
        showAlert("Nao Tem Proposta Atribuida");
    }

    private void consultarAtribuicaoPropostas() {
        ArrayList<Integer> opcoes = new ArrayList<>();
        for (int i = 0; i < checkConsultarProp.length; i++)
            if (checkConsultarProp[i].isSelected())
                opcoes.add(i+1);
        if (opcoes.isEmpty())
            Collections.addAll(opcoes,1,2,3,4);
        model.consultarPropostas(opcoes);
        showAlert("Consultar Atribuicao Propostas (c/ Filtros)");
        // limpa as checkbox
        for (CheckBox checkBox : checkConsultarProp) checkBox.setSelected(false);
        this.setCenter(menuInicial);
    }

    private void exportarFase3() {
        model.exportarFase2();
        showAlert("Exportar Fase 3");
    }

    private void showAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.setContentText(singleton.message);
        alert.show();
    }
}
