package pt.isec.pa.apoio_poe.ui.gui.orientadores;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

public class OrientadoresUI extends BorderPane {
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

    /* Consultar/Alterar Docente da Proposta */
    VBox consultAltDocente;
    Button[] consultAltDocenteBtns;

    /* Consultar Docente da Proposta */
    VBox consultarDocente;
    TextField consultarDocenteTF;
    Button[] consultarDocenteBtns;

    /* Consultar Docente da Proposta */
    VBox alterarDocente;
    TextField[] alterarDocenteTF;
    Button[] alterarDocenteBtns;

    /* Consultar Propostas Docentes*/
    VBox consultarPropostasDocentes;
    Button[] consultarPropDocentesBtns;

    public OrientadoresUI(ModelManager model) {
        this.model = model;
        this.singleton = Singleton.Singleton();

        /* Menu Inicial*/
        menuInicial = new VBox(10);
        menuInicialBtns = new Button[7];

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

        /* Consultar/Alterar Docente da Proposta */
        consultAltDocente = new VBox(10);
        consultAltDocenteBtns = new Button[3];

        /* Consultar Docente da Proposta */
        consultarDocente = new VBox(10);
        consultarDocenteTF = new TextField();
        consultarDocenteBtns = new Button[2];

        /* Consultar Docente da Proposta */
        alterarDocente = new VBox(10);
        alterarDocenteTF = new TextField[2];
        alterarDocenteBtns  = new Button[2];;

        /* Consultar Propostas Docentes*/
        consultarPropostasDocentes = new VBox(10);
        consultarPropDocentesBtns = new Button[4];

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #ff80c4;");

        Label label = new Label("ORIENTADORES");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /* Menu Inicial */
        this.setCenter(menuInicial);
        for (int i = 0; i < menuInicialBtns.length; i++)  {
            switch(i) {
                case 0 -> menuInicialBtns[i] = new Button("Atribuicao Automatica");
                case 1 -> menuInicialBtns[i] = new Button("Atribuicao/Remocao Manual");
                case 2 -> menuInicialBtns[i] = new Button("Consultar/Alterar Docente");
                case 3 -> menuInicialBtns[i] = new Button("Consultar Atribuicoes Docente");
                case 4 -> menuInicialBtns[i] = new Button("Exportar Fase 4");
                case 5 -> menuInicialBtns[i] = new Button("Fechar");
                case 6 -> menuInicialBtns[i] = new Button("Voltar");
            }
            if (i != 5 && i != 6) {
                menuInicialBtns[i].setMinWidth(235);
                menuInicial.getChildren().add(menuInicialBtns[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);

        // Botao VOLTAR
        VBox vBoxVoltarBtn = new VBox(menuInicialBtns[6]);
        vBoxVoltarBtn.setAlignment(Pos.CENTER);
        vBoxVoltarBtn.setPadding(new Insets(20));
        this.setLeft(vBoxVoltarBtn);

        // Botao AVANCAR
        VBox vBoxAvancarBtn = new VBox(menuInicialBtns[5]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /* Menu Atribuicao Manual */
        manualMenu.getChildren().addAll(new Label("Orientadores > Atribuicao/Remocao Manual"),
                manualMenuBtns[0] = new Button("Atribuicao Manual"),
                manualMenuBtns[1] = new Button("Remocao Manual"),
                manualMenuBtns[2] = new Button("Undo"),
                manualMenuBtns[3] = new Button("Redo"),
                manualMenuBtns[4] = new Button("Voltar"));
        for (int i = 0; i < manualMenuBtns.length - 1; i++)
            manualMenuBtns[i].setMinWidth(150);
        manualMenu.setAlignment(Pos.CENTER);

        /* Atribuicao Manual */
        atribuicaoManual.getChildren().add(new Label("Orientadores > Atribuicao Manual"));
        HBox[] hBoxAtribuicaoManual = new HBox[3];
        for (int i = 0; i < hBoxAtribuicaoManual.length; i++) {
            switch(i) {
                case 0 -> hBoxAtribuicaoManual[i] = new HBox(30,new Label("Codigo"),atribuicaoManualTF[0] = new TextField());
                case 1 -> hBoxAtribuicaoManual[i] = new HBox(30,new Label("Email do docente"),atribuicaoManualTF[1] = new TextField());
                case 2 -> hBoxAtribuicaoManual[i] = new HBox(30,atribuicaoManualBtns[0] = new Button("Ok"),atribuicaoManualBtns[1] = new Button("Cancelar"));
            }
            hBoxAtribuicaoManual[i].setAlignment(Pos.CENTER);
        }
        atribuicaoManual.getChildren().addAll(hBoxAtribuicaoManual[0],hBoxAtribuicaoManual[1],hBoxAtribuicaoManual[2]);
        atribuicaoManual.setAlignment(Pos.CENTER);

        /* Remocao Manual */
        remocaoManual.getChildren().add(new Label("Orientadores > Remocao Manual"));
        HBox[] hBoxRemocaoManual = new HBox[3];
        for (int i = 0; i < hBoxRemocaoManual.length; i++) {
            switch(i) {
                case 0 -> hBoxRemocaoManual[i] = new HBox(30,new Label("Codigo"),remocaoManualTF[0] = new TextField());
                case 1 -> hBoxRemocaoManual[i] = new HBox(30,new Label("Email do docente"),remocaoManualTF[1] = new TextField());
                case 2 -> hBoxRemocaoManual[i] = new HBox(30,remocaoManualBtns[0] = new Button("Ok"),remocaoManualBtns[1] = new Button("Cancelar"));
            }
            hBoxRemocaoManual[i].setAlignment(Pos.CENTER);
        }
        remocaoManual.getChildren().addAll(hBoxRemocaoManual[0],hBoxRemocaoManual[1],hBoxRemocaoManual[2]);
        remocaoManual.setAlignment(Pos.CENTER);

        /* Consultar/Alterar Docente da Proposta */
        consultAltDocente.getChildren().add(new Label("Orientadores > Consultar/Alterar Docente"));
        VBox vBoxConsultAltDocente = new VBox(10);
        for (int i = 0; i < consultAltDocenteBtns.length; i++) {
            switch (i) {
                case 0 -> consultAltDocenteBtns[0] = new Button("Consultar Docente");
                case 1 -> consultAltDocenteBtns[1] = new Button("Alterar Docente");
                case 2 -> consultAltDocenteBtns[2] = new Button("Voltar");
            }
            if (i < consultAltDocenteBtns.length - 1)
                consultAltDocenteBtns[i].setMinWidth(100);
            vBoxConsultAltDocente.getChildren().add(consultAltDocenteBtns[i]);
        }
        vBoxConsultAltDocente.setAlignment(Pos.CENTER);
        consultAltDocente.getChildren().add(vBoxConsultAltDocente);

        /* Consultar Docente da Proposta */
        consultarDocente.getChildren().add(new Label("Orientadores > Consultar Docente"));
        HBox[] hBoxConsultarDocente = new HBox[2];
        for (int i = 0; i < hBoxConsultarDocente.length; i++) {
            switch(i) {
                case 0 -> hBoxConsultarDocente[0] = new HBox(30,new Label("Email"),consultarDocenteTF = new TextField());
                case 1 -> hBoxConsultarDocente[1] = new HBox(50,consultarDocenteBtns[0] = new Button("Ok"),consultarDocenteBtns[1] = new Button("Cancelar"));
            }
            hBoxConsultarDocente[i].setAlignment(Pos.CENTER);
            consultarDocente.getChildren().add(hBoxConsultarDocente[i]);
        }
        consultarDocente.setAlignment(Pos.CENTER);

        /* Alterar Docente da Proposta */
        alterarDocente.getChildren().add(new Label("Orientadores > Alterar Docente"));
        HBox[] hBoxAlterarDocente = new HBox[3];
        for (int i = 0; i < hBoxAlterarDocente.length; i++) {
            switch(i) {
                case 0 -> hBoxAlterarDocente[i] = new HBox(30,new Label("Email"),alterarDocenteTF[0] = new TextField());
                case 1 -> hBoxAlterarDocente[i] = new HBox(30,new Label("Codigo"),alterarDocenteTF[0] = new TextField());
                case 2 -> hBoxAlterarDocente[i] = new HBox(50,alterarDocenteBtns[0] = new Button("Ok"),alterarDocenteBtns[1] = new Button("Cancelar"));
            }
            hBoxAlterarDocente[i].setAlignment(Pos.CENTER);
            alterarDocente.getChildren().add(hBoxAlterarDocente[i]);
        }
        alterarDocente.setAlignment(Pos.CENTER);

        /* Consultar Propostas Docentes*/
        consultarPropostasDocentes.getChildren().add(new Label("Orientadores > Consultar Propostas Docentes"));
        VBox vBoxConsultarPropDocente = new VBox(10);
        for (int i = 0; i < consultarPropDocentesBtns.length; i++) {
            switch(i) {
                case 0 -> consultarPropDocentesBtns[i] = new Button("Propostas com Aluno e Docente Associado");
                case 1 -> consultarPropDocentesBtns[i] = new Button("Proposta Com Aluno Sem Docente Associado");
                case 2 -> consultarPropDocentesBtns[i] = new Button("Numero de Orientacoes por Docente");
                case 3 -> consultarPropDocentesBtns[i] = new Button("Voltar");
            }
            if (i < consultarPropDocentesBtns.length - 1)
                consultarPropDocentesBtns[i].setMinWidth(280);
            vBoxConsultarPropDocente.getChildren().add(consultarPropDocentesBtns[i]);
        }
        vBoxConsultarPropDocente.setAlignment(Pos.CENTER);
        consultarPropostasDocentes.getChildren().add(vBoxConsultarPropDocente);
        consultarPropostasDocentes.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        menuInicialBtns[0].setOnAction(actionEvent -> atribuicaoAutoDocentes());     // Atribuicao Automatica Docentes
        menuInicialBtns[1].setOnAction(actionEvent -> this.setCenter(manualMenu));          // Atribuicao/Remocao Manual
        menuInicialBtns[2].setOnAction(actionEvent -> this.setCenter(consultarDocente));          // Consultar Docentes
        menuInicialBtns[3].setOnAction(actionEvent -> this.setCenter(consultarPropostasDocentes));        // Consultar Atribuicao Docentes
        menuInicialBtns[4].setOnAction(actionEvent -> exportarFase4());      // Exportar Fase 4
        menuInicialBtns[5].setOnAction(actionEvent -> model.fechar());     // fechar
        menuInicialBtns[6].setOnAction(actionEvent -> model.voltar());     // Voltar

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

        /* Consultar Propostas Docentes*/
        consultarPropDocentesBtns[0].setOnAction(actionEvent -> consultarAlunosPropDocente());
        consultarPropDocentesBtns[1].setOnAction(actionEvent -> consultarAlunosPropSemDocente());
        consultarPropDocentesBtns[2].setOnAction(actionEvent -> consultarOrientacoes());
        consultarPropDocentesBtns[3].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Consultar Docente */
        consultarDocenteBtns[0].setOnAction(actionEvent -> consultarDocente());
        consultarDocenteBtns[1].setOnAction(actionEvent -> cancelarConsulta());
    }

    private void update() { this.setVisible(model != null && model.getState() == ManagementState.ORIENTADORES); }

    private void exportarFase4() {
        model.exportarFase4e5();
        showAlert("Exportar Fase 4");
    }

    private void atribuicaoAutoDocentes() {
        model.atribuicaoAutomaticaDocentes();
        showAlert("Atribuicao Automatica Docentes");
    }

    private void atribuicaoManual() {
        model.atribuicaoManualDocente(atribuicaoManualTF[0].getText(), atribuicaoManualTF[1].getText());
        for (TextField textField : atribuicaoManualTF) textField.clear();
        showAlert("Atribuicao Manual");
        this.setCenter(manualMenu);
    }

    private void remocaoManual() {
        model.remocaoManualDocente(remocaoManualTF[0].getText(), remocaoManualTF[1].getText());
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

    private void consultarAlunosPropDocente() {
        model.consultarAlunosPropDocente();
        showAlert("Alunos com Proposta e Docente");
    }

    private void consultarAlunosPropSemDocente() {
        model.consultarAlunosPropSemDocente();
        showAlert("Alunos com Proposta Sem Docente");
    }

    private void consultarOrientacoes() {
        model.consultarOrientacoes();
        showAlert("Consultar Orientacoes");
    }

    private void fechar() {
        model.fechar();
        showAlert("Fechar a Fase");
    }

    private void consultarDocente() {
        model.consultarDocente(consultarDocenteTF.getText());
        showAlert("Consultar Docente");
        consultarDocenteTF.clear();
    }

    private void cancelarConsulta() {
        this.setCenter(menuInicial);
        consultarDocenteTF.clear();
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
        alert.getDialogPane().setMinWidth(500);
        alert.setContentText(singleton.message);
        alert.show();
    }
}
