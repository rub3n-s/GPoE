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

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class PropostaUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    /* Menu Inicial*/
    VBox menuInicial;
    Button [] menuInicialBtns;

    /* Menu Atribuicao Manual */
    VBox manualMenu;
    Button[] manualMenuBtns;

    /* Atribuicao Manual */
    VBox atribuicaoManual;
    TextField[] atribuicaoManualTF;
    Button[] atribuicaoManualBtns;
    Button addManualVoltarBtn;

    /* Remocao Manual */
    VBox remocaoManual;
    TextField[] remocaoManualTF;
    Button[] remocaoManualBtns;
    Button delManualVoltarBtn;

    /* Undo/Redo */
    Button undo, redo;

    /* Consultar */
    VBox consultar;
    Button[] consultarBtns;

    /* Consultar Atribuicao Propostas*/
    VBox consultarAtribPropostas;
    CheckBox[] checkConsultarProp;
    Button[] consultarAtribBtns;

    public PropostaUI(ModelManager model) {
        this.model = model;
        this.singleton = Singleton.Singleton();

        /* Menu Inicial*/
        menuInicial = new VBox(10);
        menuInicialBtns = new Button[9];

        /* VBox Atribuicao/Remocao*/
        manualMenu = new VBox(10);
        manualMenuBtns = new Button[5];

        /* Atribuicao Manual */
        atribuicaoManual = new VBox(10);
        atribuicaoManualTF = new TextField[2];
        atribuicaoManualBtns = new Button[2];
        addManualVoltarBtn = new Button();

        /* Remocao Manual */
        remocaoManual = new VBox(10);
        remocaoManualTF = new TextField[2];
        remocaoManualBtns = new Button[2];
        delManualVoltarBtn = new Button();

        /* Undo/Redo */
        undo = new Button();
        redo = new Button();

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

        Label label = new Label("PROPOSTA");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /* Menu Inicial */
        this.setCenter(menuInicial);
        for (int i = 0; i < menuInicialBtns.length; i++)  {
            switch(i) {
                case 0 -> menuInicialBtns[i] = new Button("Atribuicao Auto Alunos Associados");
                case 1 -> menuInicialBtns[i] = new Button("Atribuicao Auto Alunos Nao Associados");
                case 2 -> menuInicialBtns[i] = new Button("Atribuicao/Remocao Manual");
                case 3 -> menuInicialBtns[i] = new Button("Consultar Propostas");
                case 4 -> menuInicialBtns[i] = new Button("Consultar Atribuicao Propostas");
                case 5 -> menuInicialBtns[i] = new Button("Exportar Fase 3");
                case 6 -> menuInicialBtns[i] = new Button("Fechar");
                case 7 -> menuInicialBtns[i] = new Button("Avancar");
                case 8 -> menuInicialBtns[i] = new Button("Voltar");
            }
            if (i != 7 && i != 8) {
                menuInicialBtns[i].setMinWidth(235);
                menuInicial.getChildren().add(menuInicialBtns[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);

        // Botao VOLTAR
        VBox vBoxVoltarBtn = new VBox(menuInicialBtns[8]);
        vBoxVoltarBtn.setAlignment(Pos.CENTER);
        vBoxVoltarBtn.setPadding(new Insets(20));
        this.setLeft(vBoxVoltarBtn);

        // Botao AVANCAR
        VBox vBoxAvancarBtn = new VBox(menuInicialBtns[7]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /* Menu Atribuicao Manual */
        manualMenu.getChildren().addAll(new Label("Proposta > Atribuicao/Remocao Manual"),
                manualMenuBtns[0] = new Button("Atribuicao Manual"),
                manualMenuBtns[1] = new Button("Remocao Manual"),
                manualMenuBtns[2] = new Button("Undo"),
                manualMenuBtns[3] = new Button("Redo"),
                manualMenuBtns[4] = new Button("Voltar"));
        for (int i = 0; i < manualMenuBtns.length - 1; i++)
            manualMenuBtns[i].setMinWidth(150);
        manualMenu.setAlignment(Pos.CENTER);

        /* Atribuicao Manual */
        atribuicaoManual.getChildren().add(new Label("Proposta > Atribuicao Manual"));
        HBox[] hBoxAtribuicaoManual = new HBox[3];
        for (int i = 0; i < hBoxAtribuicaoManual.length; i++) {
            switch(i) {
                case 0 -> hBoxAtribuicaoManual[i] = new HBox(30,new Label("Codigo"),atribuicaoManualTF[0] = new TextField());
                case 1 -> hBoxAtribuicaoManual[i] = new HBox(30,new Label("Numero de aluno"),atribuicaoManualTF[1] = new TextField());
                case 2 -> hBoxAtribuicaoManual[i] = new HBox(30,atribuicaoManualBtns[0] = new Button("Ok"),atribuicaoManualBtns[1] = new Button("Cancelar"));
            }
            hBoxAtribuicaoManual[i].setAlignment(Pos.CENTER);
            atribuicaoManual.getChildren().add(hBoxAtribuicaoManual[i]);
        }
        atribuicaoManual.setAlignment(Pos.CENTER);

        /* Remocao Manual */
        remocaoManual.getChildren().add(new Label("Proposta > Remocao Manual"));
        HBox[] hBoxRemocaoManual = new HBox[3];
        for (int i = 0; i < hBoxRemocaoManual.length; i++) {
            switch(i) {
                case 0 -> hBoxRemocaoManual[i] = new HBox(30,new Label("Codigo"),remocaoManualTF[0] = new TextField());
                case 1 -> hBoxRemocaoManual[i] = new HBox(30,new Label("Numero de aluno"),remocaoManualTF[1] = new TextField());
                case 2 -> hBoxRemocaoManual[i] = new HBox(30,remocaoManualBtns[0] = new Button("Ok"),remocaoManualBtns[1] = new Button("Cancelar"));
            }
            hBoxRemocaoManual[i].setAlignment(Pos.CENTER);
            remocaoManual.getChildren().add(hBoxRemocaoManual[i]);
        }
        remocaoManual.setAlignment(Pos.CENTER);

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

        menuInicialBtns[0].setOnAction(actionEvent -> atribuicaoAutoAlunosA());     // Atribuicao Auto Alunos Associados
        menuInicialBtns[1].setOnAction(actionEvent -> atribuicaoAutoAlunosNA());     // Atribuicao Auto Alunos Nao Associados
        menuInicialBtns[2].setOnAction(actionEvent -> this.setCenter(manualMenu));          // Atribuicao/Remocao Manual
        menuInicialBtns[3].setOnAction(actionEvent -> this.setCenter(consultar));          // Consultar Propostas
        menuInicialBtns[4].setOnAction(actionEvent -> this.setCenter(consultarAtribPropostas));        // Consultar Atribuicao Propostas
        menuInicialBtns[5].setOnAction(actionEvent -> exportarFase3());      // Exportar Fase 3
        menuInicialBtns[6].setOnAction(actionEvent -> fechar());     // Fechar a fase
        menuInicialBtns[7].setOnAction(actionEvent -> { model.avancar(); this.setCenter(menuInicial); });     // Avancar
        menuInicialBtns[8].setOnAction(actionEvent -> model.voltar());     // Voltar

        /* Menu Atribuicao/Remocao */
        manualMenuBtns[0].setOnAction(actionEvent -> this.setCenter(atribuicaoManual));
        manualMenuBtns[1].setOnAction(actionEvent -> this.setCenter(remocaoManual));
        manualMenuBtns[2].setOnAction(actionEvent -> undo());
        manualMenuBtns[3].setOnAction(actionEvent -> redo());
        manualMenuBtns[4].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Atribuicao Manual */
        atribuicaoManualBtns[0].setOnAction(actionEvent -> atribuicaoManual());
        atribuicaoManualBtns[1].setOnAction(actionEvent -> cancelarAtribuicao());

        /* Remocao Manual */
        remocaoManualBtns[0].setOnAction(actionEvent -> remocaoManual());
        remocaoManualBtns[1].setOnAction(actionEvent -> cancelarRemocao());

        /* Consultar */
        consultarBtns[0].setOnAction(actionEvent -> consultarComAutoProp());
        consultarBtns[1].setOnAction(actionEvent -> consultarAlunosRegistadas());
        consultarBtns[2].setOnAction(actionEvent -> consultarAlunosAtribuidas());
        consultarBtns[3].setOnAction(actionEvent -> consultarAlunosNaoAtribuidas());
        consultarBtns[4].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Consultar c/ Filtros*/
        consultarAtribBtns[0].setOnAction(actionEvent -> consultarAtribuicaoPropostas());
        consultarAtribBtns[1].setOnAction(actionEvent -> cancelarConsultar());
    }

    private void update() { this.setVisible(model != null && model.getState() == ManagementState.PROPOSTA); }

    private void atribuicaoAutoAlunosA() {
        model.atribuicaoAutomaticaAlunoAssoc();
        showAlert("Atribuicao Auto Alunos Associados");
    }

    private void atribuicaoAutoAlunosNA() {
        // fica em ciclo ate se verificar que a funcao de atribuicao chegou ao final com sucesso
        do {
            model.atribuicaoAutomaticaAlunoNAssoc();

            if (!model.isTerminouAtribuicao()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("Atribuicao Auto Alunos Nao Associados");
                alert.setContentText(singleton.message);
                ButtonType aluno1 = new ButtonType("Aluno 1", ButtonBar.ButtonData.NO);
                ButtonType aluno2 = new ButtonType("Aluno 2", ButtonBar.ButtonData.YES);
                alert.getButtonTypes().setAll(aluno1, aluno2);
                alert.getDialogPane().setMinWidth(500);
                alert.showAndWait().ifPresent(type -> {
                    if (type == aluno1)
                        model.userEscolheAluno(0);
                    else if (type == aluno2)
                        model.userEscolheAluno(1);
                });
                showAlert("Atribuicao Auto Alunos Nao Associados");
            }
        } while(!model.isTerminouAtribuicao());

        // redefinir o valor da condicao de paragem
        model.setTerminouAtribuicao();
    }

    private void atribuicaoManual() {
        model.atribuicaoManualProposta(atribuicaoManualTF[0].getText(), Long.parseLong(atribuicaoManualTF[1].getText()));
        for (TextField textField : atribuicaoManualTF) textField.clear();
        showAlert("Atribuicao Manual");
        this.setCenter(manualMenu);
    }

    private void remocaoManual() {
        model.remocaoManualProposta(remocaoManualTF[0].getText(), Long.parseLong(remocaoManualTF[1].getText()));
        for (TextField textField : remocaoManualTF) textField.clear();
        showAlert("Remocao Manual");
        this.setCenter(manualMenu);
    }

    private void cancelarAtribuicao() {
        for (TextField textField : atribuicaoManualTF) textField.clear();
        this.setCenter(manualMenu);
    }

    private void cancelarRemocao() {
        for (TextField textField : remocaoManualTF) textField.clear();
        this.setCenter(manualMenu);
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

    private void cancelarConsultar() {
        for (CheckBox checkBox : checkConsultarProp) checkBox.setSelected(false);
        this.setCenter(menuInicial);
    }

    private void exportarFase3() {
        model.exportarFase3();
        showAlert("Exportar Fase 3");
    }

    private void fechar() {
        model.fechar();
        showAlert("Fechar a Fase");
    }

    private void undo() {
        model.undo();
        showAlert("Undo");
    }

    private void redo() {
        model.undo();
        showAlert("Redo");
    }

    private void showAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(headerText);
        alert.setContentText(singleton.message);
        alert.getDialogPane().setMinWidth(500);
        alert.show();
    }
}
