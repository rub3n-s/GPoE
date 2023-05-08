package pt.isec.pa.apoio_poe.ui.gui.candidatura;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

import java.util.ArrayList;
import java.util.Collections;

public class CandidaturaUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    /* Menu Inicial */
    VBox menuInicial;
    Button[] btnsMenuInicial;

    /* Menu Inserir Candidatura */
    VBox menuInserirCandidatura;
    Button[] menuInserirCandidaturaBtns;

    /* Inserir Candidaturas */
    VBox inserirCandidatura;
    TextField[] inserirCandidaturaTF;
    Button[] inserirCandBtns;

    /* Adicionar/Remover Codigo */
    VBox adicionarCodigo, removerCodigo;
    TextField[] adicionarCodigoTF, removerCodigoTF;
    Button[] adicionarCodigoBtns, removerCodigoBtns;

    /* Consultar Candidaturas */
    VBox consultarCandidaturas;
    Button btnConsultarVoltar;
    Button[] btnConsultarCand;

    /* Consultar Propostas */
    VBox consultarPropostas;
    CheckBox[] checkConsultarProp;
    Button[] btnConsultarProp;

    /* ELiminar */
    VBox eliminarCandidatura;
    TextField eliminarCandidaturaTF;
    Button[] eliminarCandidaturaBtns;

    public CandidaturaUI(ModelManager model) {
        this.model = model;
        singleton = Singleton.Singleton();

        /*  Menu Inicial */
        menuInicial = new VBox(10);
        btnsMenuInicial = new Button[10];

        /* Menu Inserir Candidatura */
        menuInserirCandidatura = new VBox(10);
        menuInserirCandidaturaBtns = new Button[3];

        /*  Inserir Candidatura */
        inserirCandidatura = new VBox(10);
        inserirCandidaturaTF = new TextField[2];
        inserirCandBtns = new Button[2];

        /* Consultar Candidaturas */
        btnConsultarCand = new Button[3];
        consultarCandidaturas = new VBox(10);
        consultarPropostas = new VBox(10);
        btnConsultarVoltar = new Button("Voltar");

        /* Consultar Propostas */
        btnConsultarProp = new Button[2];
        checkConsultarProp = new CheckBox[4];

        /* Adicionar codigo */
        adicionarCodigo = new VBox(10);
        adicionarCodigoTF = new TextField[2];
        adicionarCodigoBtns = new Button[2];

        /* Remover codigo */
        removerCodigo = new VBox(10);
        removerCodigoTF = new TextField[2];
        removerCodigoBtns = new Button[2];

        /* Eliminar candidatura */
        eliminarCandidatura = new VBox(10);
        eliminarCandidaturaBtns = new Button[2];
        eliminarCandidaturaTF = new TextField();
        eliminarCandidaturaTF.setMinWidth(200);

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #ffee80;");

        Label label = new Label("CANDIDATURA");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        /*================== Menu Inicial ==================*/
        this.setCenter(menuInicial);
        for (int i = 0; i < btnsMenuInicial.length; i++)  {
            switch(i) {
                case 0 -> btnsMenuInicial[i] = new Button("Inserir Candidatura");
                case 1 -> btnsMenuInicial[i] = new Button("Consultar Candidaturas");
                case 2 -> btnsMenuInicial[i] = new Button("Consultar Propostas (Filtros)");
                case 3 -> btnsMenuInicial[i] = new Button("Adicionar Codigo");
                case 4 -> btnsMenuInicial[i] = new Button("Remover Codigo");
                case 5 -> btnsMenuInicial[i] = new Button("Eliminar Candidatura");
                case 6 -> btnsMenuInicial[i] = new Button("Exportar Fase 2");
                case 7 -> btnsMenuInicial[i] = new Button("Fechar");
                case 8 -> btnsMenuInicial[i] = new Button("Avancar");
                case 9 -> btnsMenuInicial[i] = new Button("Voltar");
            }
            if (i != 8 && i != 9) {
                btnsMenuInicial[i].setMinWidth(170);
                menuInicial.getChildren().add(btnsMenuInicial[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);

        // Botao VOLTAR
        VBox vBoxVoltarBtn = new VBox(btnsMenuInicial[9]);
        vBoxVoltarBtn.setAlignment(Pos.CENTER);
        vBoxVoltarBtn.setPadding(new Insets(20));
        this.setLeft(vBoxVoltarBtn);

        // Botao AVANCAR
        VBox vBoxAvancarBtn = new VBox(btnsMenuInicial[8]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /*================== Menu Inserir Candidatura ==================*/
        menuInserirCandidatura.getChildren().add(new Label("Candidatura > Menu Inserir Candidatura"));
        for (int i = 0; i < menuInserirCandidaturaBtns.length; i++) {
            switch(i) {
                case 0 -> menuInserirCandidaturaBtns[i] = new Button("Inserir Manualmente");
                case 1 -> menuInserirCandidaturaBtns[i] = new Button("Ler CSV");
                case 2 -> menuInserirCandidaturaBtns[i] = new Button("Voltar");
            }
            if (i < menuInserirCandidaturaBtns.length - 1)
                menuInserirCandidaturaBtns[i].setMinWidth(150);
            menuInserirCandidatura.getChildren().add(menuInserirCandidaturaBtns[i]);
        }
        menuInserirCandidatura.setAlignment(Pos.CENTER);

        /*================== Inserir Candidatura ==================*/
        inserirCandidatura.getChildren().add(new Label("Candidatura > Inserir Candidatura"));

        HBox hBoxInserirNumero = new HBox(30,new Label("Numero de Aluno"),inserirCandidaturaTF[0] = new TextField());
        HBox hBoxInserirCodigos = new HBox(30,new Label("Codigos (split ',')"),inserirCandidaturaTF[1] = new TextField());
        for (TextField textField : inserirCandidaturaTF) textField.setMinWidth(200);
        hBoxInserirNumero.setAlignment(Pos.CENTER);
        hBoxInserirCodigos.setAlignment(Pos.CENTER);

        HBox hBoxInserirCandBtns = new HBox(50,inserirCandBtns[0] = new Button("Ok"),inserirCandBtns[1] = new Button("Cancelar"));
        hBoxInserirCandBtns.setAlignment(Pos.CENTER);

        inserirCandidatura.setAlignment(Pos.CENTER);
        inserirCandidatura.getChildren().addAll(hBoxInserirNumero,hBoxInserirCodigos,hBoxInserirCandBtns);

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

        /*================== Adicionar Codigo ==================*/
        adicionarCodigo.getChildren().add(new Label("Candidatura > Adicionar Codigo"));

        HBox[] hBoxAdicionarCodigo = new HBox[2];
        hBoxAdicionarCodigo[0] = new HBox(30,new Label("Inserir Numero"),adicionarCodigoTF[0] = new TextField());
        hBoxAdicionarCodigo[0].setAlignment(Pos.CENTER);
        hBoxAdicionarCodigo[1] = new HBox(30,new Label("Codigo da Proposta"),adicionarCodigoTF[1] = new TextField());
        hBoxAdicionarCodigo[1].setAlignment(Pos.CENTER);

        HBox hBoxAdicionarCodigoBtns = new HBox(50,adicionarCodigoBtns[0] = new Button("Ok"),
                adicionarCodigoBtns[1] = new Button("Cancelar"));
        hBoxAdicionarCodigoBtns.setAlignment(Pos.CENTER);

        VBox vBoxAdicionarCodigo = new VBox(10,hBoxAdicionarCodigo[0],hBoxAdicionarCodigo[1],hBoxAdicionarCodigoBtns);
        vBoxAdicionarCodigo.setAlignment(Pos.CENTER);

        adicionarCodigo.setAlignment(Pos.CENTER);
        adicionarCodigo.getChildren().add(vBoxAdicionarCodigo);

        /*================== Remover Codigo ==================*/
        removerCodigo.getChildren().add(new Label("Candidatura > Remover Codigo"));

        HBox[] hBoxRemoverCodigo = new HBox[2];
        hBoxRemoverCodigo[0] = new HBox(30,new Label("Inserir Numero"),removerCodigoTF[0] = new TextField());
        hBoxRemoverCodigo[0].setAlignment(Pos.CENTER);
        hBoxRemoverCodigo[1] = new HBox(30,new Label("Codigo da Proposta"),removerCodigoTF[1] = new TextField());
        hBoxRemoverCodigo[1].setAlignment(Pos.CENTER);

        HBox hBoxRemoverCodigoBtns = new HBox(50,removerCodigoBtns[0] = new Button("Ok"),removerCodigoBtns[1] = new Button("Cancelar"));
        hBoxRemoverCodigoBtns.setAlignment(Pos.CENTER);

        VBox vBoxRemoverCodigo = new VBox(10,hBoxRemoverCodigo[0],hBoxRemoverCodigo[1],hBoxRemoverCodigoBtns);

        removerCodigo.setAlignment(Pos.CENTER);
        removerCodigo.getChildren().add(vBoxRemoverCodigo);

        /*================== Eliminar Candidatura ==================*/
        eliminarCandidatura.getChildren().add(new Label("Candidatura > Eliminar Candidatura"));

        HBox hBoxEliminarCandidatura = new HBox(30,new Label("Inserir Numero"),eliminarCandidaturaTF);
        hBoxEliminarCandidatura.setAlignment(Pos.CENTER);

        HBox hBoxEliminarCandidaturaBtns = new HBox(50,eliminarCandidaturaBtns[0] = new Button("Ok"),
                eliminarCandidaturaBtns[1] = new Button("Cancelar"));
        hBoxEliminarCandidaturaBtns.setAlignment(Pos.CENTER);

        VBox vBoxEliminarCandidatura = new VBox(10,hBoxEliminarCandidatura,hBoxEliminarCandidaturaBtns);
        vBoxEliminarCandidatura.setAlignment(Pos.CENTER);

        eliminarCandidatura.setAlignment(Pos.CENTER);
        eliminarCandidatura.getChildren().add(vBoxEliminarCandidatura);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        btnsMenuInicial[0].setOnAction(actionEvent -> this.setCenter(menuInserirCandidatura));    // Inserir Candidatura
        btnsMenuInicial[1].setOnAction(actionEvent -> this.setCenter(consultarCandidaturas));     // Consultar Candidaturas
        btnsMenuInicial[2].setOnAction(actionEvent -> this.setCenter(consultarPropostas));     // Consultar Propostas (FILTROS)
        btnsMenuInicial[3].setOnAction(actionEvent -> this.setCenter(adicionarCodigo));     // Modificar Candidatura
        btnsMenuInicial[4].setOnAction(actionEvent -> this.setCenter(removerCodigo));     // Modificar Candidatura
        btnsMenuInicial[5].setOnAction(actionEvent -> this.setCenter(eliminarCandidatura));     // Eliminar Candidatura
        btnsMenuInicial[6].setOnAction(actionEvent -> exportarFase2());     // Exportar Fase 2
        btnsMenuInicial[7].setOnAction(actionEvent -> fechar());     // Fechar a fase
        btnsMenuInicial[8].setOnAction(actionEvent -> model.avancar());     // Avancar
        btnsMenuInicial[9].setOnAction(actionEvent -> model.voltar());        // Voltar

        /* Menu Inserir Candidatura*/
        menuInserirCandidaturaBtns[0].setOnAction(actionEvent -> this.setCenter(inserirCandidatura));
        menuInserirCandidaturaBtns[1].setOnAction(actionEvent -> lerCSVCandidaturas());
        menuInserirCandidaturaBtns[2].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Inserir Candidatura*/
        inserirCandBtns[0].setOnAction(actionEvent -> inserirCandidatura());
        inserirCandBtns[1].setOnAction(actionEvent -> cancelarCandidatura());

        /*  Consultar Candidaturas */
        btnConsultarCand[0].setOnAction(actionEvent -> consultarComAutoProp());
        btnConsultarCand[1].setOnAction(actionEvent -> consultarComCandidatura());
        btnConsultarCand[2].setOnAction(actionEvent -> consultarSemCandidatura());
        btnConsultarVoltar.setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Consultar Propostas */
        btnConsultarProp[0].setOnAction(actionEvent -> consultarPropostas());
        btnConsultarProp[1].setOnAction(actionEvent -> this.setCenter(menuInicial));

        /* Adicionar/Remover Codigo*/
        adicionarCodigoBtns[0].setOnAction(actionEvent -> adicionarCodigo());
        adicionarCodigoBtns[1].setOnAction(actionEvent -> cancelarAdicionar());

        removerCodigoBtns[0].setOnAction(actionEvent -> removerCodigo());
        removerCodigoBtns[1].setOnAction(actionEvent -> cancelarRemover());

        /* Eliminar Candidatura*/
        eliminarCandidaturaBtns[0].setOnAction(actionEvent -> eliminarCandidatura());
        eliminarCandidaturaBtns[1].setOnAction(actionEvent -> cancelarEliminar());
    }

    private void update() {
        this.setVisible(model != null && model.getState() == ManagementState.CANDIDATURA);
    }

    private void lerCSVCandidaturas() {
        model.lerCSVCandidaturas();
        showAlert("Ler CSV");
        this.setCenter(menuInicial);
    }
    private void inserirCandidatura() {
        ArrayList<String> codigos;
        codigos = model.getArrayCodigos(inserirCandidaturaTF[1].getText());
        model.inserirCandidatura(Long.parseLong(inserirCandidaturaTF[0].getText()),codigos);
        for (TextField textField : inserirCandidaturaTF) textField.clear();
        showAlert("Inserir Candidatura");
        this.setCenter(menuInicial);
    }

    private void cancelarCandidatura() {
        for (TextField textField : inserirCandidaturaTF) textField.clear();
        this.setCenter(menuInicial);
    }

    private void adicionarCodigo() {
        model.adicionarCodigo(Long.parseLong(adicionarCodigoTF[0].getText()),adicionarCodigoTF[1].getText());
        for (TextField textField : adicionarCodigoTF) textField.clear();
        showAlert("Adicionar Codigo");
        this.setCenter(menuInicial);
    }

    private void cancelarAdicionar() {
        for (TextField textField : adicionarCodigoTF) textField.clear();
        this.setCenter(menuInicial);
    }

    private void removerCodigo() {
        model.removerCodigo(Long.parseLong(removerCodigoTF[0].getText()),removerCodigoTF[1].getText());
        for (TextField textField : removerCodigoTF) textField.clear();
        showAlert("Remover Codigo");
        this.setCenter(menuInicial);
    }

    private void cancelarRemover() {
        for (TextField textField : removerCodigoTF) textField.clear();
        this.setCenter(menuInicial);
    }

    private void eliminarCandidatura() {
        model.eliminarCandidatura(Long.parseLong(eliminarCandidaturaTF.getText()));
        eliminarCandidaturaTF.clear();
        showAlert("Eliminar Candidatura");
        this.setCenter(menuInicial);
    }

    private void cancelarEliminar() {
        eliminarCandidaturaTF.clear();
        this.setCenter(menuInicial);
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
        model.consultarPropostas(opcoes);
        showAlert("Lista de Propostas (c/ Filtros)");
        for (int i = 0; i < checkConsultarProp.length; i++)     // limpa as checkbox
            checkConsultarProp[i].setSelected(false);
        this.setCenter(menuInicial);
    }

    private void exportarFase2() {
        model.exportarFase2();
        showAlert("Exportar Fase 2");
    }

    private void fechar() {
        model.fechar();
        showAlert("Fechar a Fase");
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
