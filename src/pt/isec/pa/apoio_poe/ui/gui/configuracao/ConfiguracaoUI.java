package pt.isec.pa.apoio_poe.ui.gui.configuracao;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.data.Singleton;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

public class ConfiguracaoUI extends BorderPane {
    ModelManager model;
    Singleton singleton;

    /* Menu Inicial */
    Button [] btnMenuInicial;
    VBox menuInicial;

    /* Alunos */
    VBox gestaoAlunos, inserirAluno, modificarAluno, eliminarAluno;
    Button [] btnGestaoAlunos, btnInserirAluno, btnModificarAluno, btnEliminarAluno;

    /* Alunos > Inserir - Modificar - Eliminar */
    TextField[] addAlunoTF, modAlunoTF;
    TextField delAlunoTF;
    ComboBox comboBoxAluno;

    /* Docentes */
    VBox gestaoDocentes, inserirDocente, modificarDocente, eliminarDocente;
    Button [] btnGestaoDocentes, btnInserirDocente, btnModificarDocente, btnEliminarDocente;

    /* Docentes > Inserir - Modificar - Eliminar */
    TextField[] addDocenteTF, modificarDocenteTF;
    TextField eliminarDocenteTF;

    /* Propostas */
    VBox gestaoPropostas, inserirProposta, modificarProposta, eliminarProposta;
    Button [] btnGestaoPropostas, btnInserirProposta, btnModificarProposta, btnEliminarProposta;

    Button [] btnInserirEstagio, btnInserirProjeto, btnInserirAuto;

    VBox inserirEstagio, inserirAuto, inserirProjeto;

    ComboBox comboBoxProposta;
    Button btnVoltarProposta;

    TextField [] estagioTF, projetoTF, autoPropTF;

    /* Propostas > Modificar */
    VBox modificarEstagio, modificarAuto, modificarProjeto;
    ComboBox comboBoxModProposta;
    Button btnVoltarModProposta;
    Button [] btnModificarEstagio, btnModificarProjeto, btnModificarAuto;

    HBox [] hBoxModificarEstagio, hBoxModificarProjeto, hBoxModificarAuto;
    TextField [] modEstagioTF, modProjetoTF, modAutoTF;

    ComboBox comboBoxModEstagio, comboBoxModProjeto, comboBoxModAuto;

    /* Propostas > Eliminar */
    TextField delCodigoProposta;

    public ConfiguracaoUI(ModelManager model) {
        this.model = model;
        singleton = Singleton.Singleton();

        menuInicial = new VBox(10);
        btnMenuInicial = new Button[6];

        //======== Alunos
        gestaoAlunos = new VBox(10);
        inserirAluno = new VBox(10);
        modificarAluno = new VBox(10);
        eliminarAluno = new VBox(10);

        btnGestaoAlunos = new Button[6];
        btnInserirAluno = new Button[2];
        btnModificarAluno = new Button[2];
        btnEliminarAluno = new Button[2];

        //======== Alunos > Inserir
        addAlunoTF = new TextField[7];

        //======== Alunos > Modificar
        comboBoxAluno = new ComboBox();
        comboBoxAluno.getItems().addAll("Nome","Curso","Ramo","Classificacao","Estagio");

        modAlunoTF = new TextField[2];

        //======== Alunos > Eliminar
        delAlunoTF = new TextField();

        //======== Docentes
        gestaoDocentes = new VBox(10);
        inserirDocente = new VBox(10);
        modificarDocente = new VBox(10);
        eliminarDocente = new VBox(10);

        btnGestaoDocentes = new Button[6];
        btnInserirDocente = new Button[2];
        btnModificarDocente = new Button[2];
        btnEliminarDocente = new Button[2];

        //======== Docentes > Inserir
        addDocenteTF = new TextField[2];

        //======== Docentes > Modificar
        modificarDocenteTF = new TextField[2];

        //======== Docentes > Eliminar
        eliminarDocenteTF = new TextField();
        eliminarDocenteTF.setMaxWidth(200);

        //======== Propostas
        gestaoPropostas = new VBox(10);
        inserirProposta = new VBox(10);
        modificarProposta = new VBox(10);
        eliminarProposta = new VBox(10);

        btnGestaoPropostas = new Button[6];
        btnInserirProposta = new Button[2];
        btnModificarProposta = new Button[2];
        btnEliminarProposta = new Button[2];

        btnVoltarProposta = new Button("Voltar");

        inserirEstagio = new VBox();
        inserirAuto = new VBox();
        inserirProjeto = new VBox();

        btnInserirEstagio = new Button[2];
        btnInserirProjeto = new Button[2];
        btnInserirAuto = new Button[2];

        //======== Propostas
        comboBoxProposta = new ComboBox();
        comboBoxProposta.getItems().addAll("Estagio","Projeto","Autoproposto");

        estagioTF = new TextField[5];
        projetoTF = new TextField[5];
        autoPropTF = new TextField[3];

        //======== Propostas > Modificar
        modificarEstagio = new VBox();
        modificarAuto = new VBox();
        modificarProjeto = new VBox();

        comboBoxModProposta = new ComboBox();
        comboBoxModProposta.getItems().addAll("Estagio","Projeto","Autoproposto");

        comboBoxModEstagio = new ComboBox();
        comboBoxModEstagio.getItems().addAll("Titulo","Area Destino","Entidade Acolhimento","Aluno");

        comboBoxModProjeto = new ComboBox();
        comboBoxModProjeto.getItems().addAll("Titulo","Area Destino","Entidade Acolhimento","Aluno");

        comboBoxModAuto = new ComboBox();
        comboBoxModAuto.getItems().addAll("Titulo","Aluno");

        modEstagioTF = new TextField[3];
        modProjetoTF = new TextField[3];
        modAutoTF = new TextField[3];

        hBoxModificarEstagio = new HBox[3];
        hBoxModificarProjeto = new HBox[3];
        hBoxModificarAuto = new HBox[3];

        btnVoltarModProposta = new Button("Voltar");
        btnModificarEstagio = new Button[2];
        btnModificarProjeto = new Button[2];
        btnModificarAuto = new Button[2];

        //======== Propostas > Eliminar
        delCodigoProposta = new TextField();
        delCodigoProposta.setMaxWidth(200);

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A0FF80;");

        /*================================ MENU INICIAL ================================*/
        Label label = new Label("CONFIGURACAO");
        label.setStyle("-fx-font: 24 arial;");
        label.setPadding(new Insets(50,50,-50,50));
        HBox title = new HBox(label);
        title.setAlignment(Pos.CENTER);
        this.setTop(title);

        for (int i = 0; i < btnMenuInicial.length; i++)  {
            switch(i) {
                case 0 -> btnMenuInicial[i] = new Button("Gestao de Alunos");
                case 1 -> btnMenuInicial[i] = new Button("Gestao de Docentes");
                case 2 -> btnMenuInicial[i] = new Button("Gestao de Propostas");
                case 3 -> btnMenuInicial[i] = new Button("Exportar Fase 1");
                case 4 -> btnMenuInicial[i] = new Button("Fechar");
                case 5 -> btnMenuInicial[i] = new Button("Avancar");
            }
            if (i != 5) {
                btnMenuInicial[i].setMinWidth(150);
                menuInicial.getChildren().add(btnMenuInicial[i]);
            }
        }
        menuInicial.setAlignment(Pos.CENTER);
        this.setCenter(menuInicial);

        VBox vBoxVoltarBtn = new VBox();
        vBoxVoltarBtn.setPadding(new Insets(50));
        this.setLeft(vBoxVoltarBtn);

        VBox vBoxAvancarBtn = new VBox(btnMenuInicial[5]);
        vBoxAvancarBtn.setAlignment(Pos.CENTER);
        vBoxAvancarBtn.setPadding(new Insets(20));
        this.setRight(vBoxAvancarBtn);

        /*================================ GESTAO DE ALUNOS ================================*/
        for (int i = 0; i < btnGestaoAlunos.length; i++)  {
            switch(i) {
                case 0 -> btnGestaoAlunos[i] = new Button("Inserir Aluno (Manual)");
                case 1 -> btnGestaoAlunos[i] = new Button("Ler CSV");
                case 2 -> btnGestaoAlunos[i] = new Button("Consultar Alunos");
                case 3 -> btnGestaoAlunos[i] = new Button("Modificar Aluno");
                case 4 -> btnGestaoAlunos[i] = new Button("Eliminar Aluno");
                case 5 -> btnGestaoAlunos[i] = new Button("Voltar");
            }
            btnGestaoAlunos[i].setMinWidth(150);
            gestaoAlunos.getChildren().add(btnGestaoAlunos[i]);
        }
        gestaoAlunos.setAlignment(Pos.CENTER);

        //========================== GESTAO DE ALUNOS > INSERIR ALUNO
        HBox [] hBoxInserirAluno = new HBox[8];
        for (int i = 0; i < hBoxInserirAluno.length; i++) {
            if (i < hBoxInserirAluno.length - 1) {
                addAlunoTF[i] = new TextField();
                addAlunoTF[i].setMinWidth(200);
            }
            switch(i) {
                case 0 -> hBoxInserirAluno[i] = new HBox(30, new Label("Numero"), addAlunoTF[i]);
                case 1 -> hBoxInserirAluno[i] = new HBox(30, new Label("Nome"), addAlunoTF[i]);
                case 2 -> hBoxInserirAluno[i] = new HBox(30, new Label("Email"), addAlunoTF[i]);
                case 3 -> hBoxInserirAluno[i] = new HBox(30, new Label("Curso"), addAlunoTF[i]);
                case 4 -> hBoxInserirAluno[i] = new HBox(30, new Label("Ramo"), addAlunoTF[i]);
                case 5 -> hBoxInserirAluno[i] = new HBox(30, new Label("Classificacao (0.0 - 1.0)"), addAlunoTF[i]);
                case 6 -> hBoxInserirAluno[i] = new HBox(30, new Label("Estagio (true/false)"), addAlunoTF[i]);
                case 7 -> {
                    hBoxInserirAluno[i] = new HBox(50);
                    for (int j = 0; j < 2; j++)  {
                        hBoxInserirAluno[i].getChildren().add(btnInserirAluno[j] = new Button());
                        switch(j) {
                            case 0 -> btnInserirAluno[j].setText("Ok");
                            case 1 -> btnInserirAluno[j].setText("Cancelar");
                        }
                        btnInserirAluno[j].setMinWidth(50);
                    }
                }
            }
            hBoxInserirAluno[i].setAlignment(Pos.CENTER_RIGHT);
            hBoxInserirAluno[i].setPadding(new Insets(0,50,0, 0));
            inserirAluno.setAlignment(Pos.CENTER);
            inserirAluno.getChildren().add(hBoxInserirAluno[i]);
        }

        //========================== GESTAO DE ALUNOS > MODIFICAR ALUNO
        modificarAluno.getChildren().add(new Label("Configuracao > Gestao de Alunos > Modificar Aluno"));
        modificarAluno.getChildren().add(comboBoxAluno);

        HBox[] hBoxModificarAluno = new HBox[3];
        for (int i = 0; i < hBoxModificarAluno.length; i++) {
            if (i < 2) {
                modAlunoTF[i] = new TextField();
                modAlunoTF[i].setMinWidth(200);
            }
            switch(i) {
                case 0 -> hBoxModificarAluno[i] = new HBox(30,new Label("Numero de aluno"),modAlunoTF[i]);
                case 1 -> hBoxModificarAluno[i] = new HBox(30,new Label("Novo valor"),modAlunoTF[i]);
                case 2 -> {
                    hBoxModificarAluno[i] = new HBox(30);
                    for (int j = 0; j < 2; j++)  {
                        hBoxModificarAluno[i].getChildren().add(btnModificarAluno[j] = new Button());
                        switch(j) {
                            case 0 -> btnModificarAluno[j].setText("Ok");
                            case 1 -> btnModificarAluno[j].setText("Cancelar");
                        }
                        btnModificarAluno[j].setMinWidth(50);
                    }
                }
            }
            if (i != 2) {
                hBoxModificarAluno[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxModificarAluno[i].setPadding(new Insets(0, 50, 0, 0));
            } else {
                hBoxModificarAluno[i].setAlignment(Pos.CENTER);
            }
            modificarAluno.getChildren().add(hBoxModificarAluno[i]);
        }
        modificarAluno.setAlignment(Pos.CENTER);

        //========================== GESTAO DE ALUNOS > ELIMINAR ALUNO
        eliminarAluno.getChildren().add(new Label("Configuracao > Gestao de Alunos > Eliminar Aluno"));

        delAlunoTF.setMinWidth(200);

        HBox hBoxEliminarAluno = new HBox(30,new Label("Numero de aluno"),delAlunoTF);
        hBoxEliminarAluno.setAlignment(Pos.CENTER_RIGHT);
        hBoxEliminarAluno.setPadding(new Insets(0,50,0, 0));
        eliminarAluno.getChildren().add(hBoxEliminarAluno);

        HBox hBoxBtnEliminarAluno = new HBox(30);
        for (int i = 0; i < 2; i++)  {
            hBoxBtnEliminarAluno.getChildren().add(btnEliminarAluno[i] = new Button());
            switch(i) {
                case 0 -> btnEliminarAluno[i].setText("Ok");
                case 1 -> btnEliminarAluno[i].setText("Cancelar");
            }
            btnEliminarAluno[i].setMinWidth(50);
        }

        hBoxBtnEliminarAluno.setAlignment(Pos.CENTER);
        eliminarAluno.getChildren().add(hBoxBtnEliminarAluno);
        eliminarAluno.setAlignment(Pos.CENTER);

        /*================================ GESTAO DE DOCENTES ================================*/
        gestaoDocentes.getChildren().add(new Label("Configuracao > Gestao de Docentes"));
        for (int i = 0; i < btnGestaoDocentes.length; i++)  {
            switch(i) {
                case 0 -> btnGestaoDocentes[i] = new Button("Inserir Docente (Manual)");
                case 1 -> btnGestaoDocentes[i] = new Button("Ler CSV");
                case 2 -> btnGestaoDocentes[i] = new Button("Consultar Docentes");
                case 3 -> btnGestaoDocentes[i] = new Button("Modificar Docente");
                case 4 -> btnGestaoDocentes[i] = new Button("Eliminar Docente");
                case 5 -> btnGestaoDocentes[i] = new Button("Voltar");
            }
            btnGestaoDocentes[i].setMinWidth(150);
            gestaoDocentes.getChildren().add(btnGestaoDocentes[i]);
        }
        gestaoDocentes.setAlignment(Pos.CENTER);

        //================ GESTAO DE DOCENTES > INSERIR DOCENTE
        inserirDocente.getChildren().addAll(new Label("Configuracao > Gestao de Docentes > Inserir Docente"));
        HBox [] hBoxInserirDocente = new HBox[3];
        for (int i = 0; i < hBoxInserirDocente.length; i++) {
            if (i < 2) {
                addDocenteTF[i] = new TextField();
                addDocenteTF[i].setMinWidth(200);
            }
            switch (i) {
                case 0 -> hBoxInserirDocente[i] = new HBox(30, new Label("Email"), addDocenteTF[0]);
                case 1 -> hBoxInserirDocente[i] = new HBox(30, new Label("Nome"), addDocenteTF[1]);
                case 2 -> {
                    hBoxInserirDocente[i] = new HBox(50);
                    for (int j = 0; j < 2; j++) {
                        hBoxInserirDocente[i].getChildren().add(btnInserirDocente[j] = new Button());
                        switch (j) {
                            case 0 -> btnInserirDocente[j].setText("Ok");
                            case 1 -> btnInserirDocente[j].setText("Cancelar");
                        }
                        btnInserirDocente[j].setMinWidth(50);
                    }
                }
            }
            if (i != 2) {
                hBoxInserirDocente[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxInserirDocente[i].setPadding(new Insets(0, 50, 0, 0));
            }
            inserirDocente.getChildren().add(hBoxInserirDocente[i]);
        }
        hBoxInserirDocente[2].setAlignment(Pos.CENTER);
        inserirDocente.setAlignment(Pos.CENTER);

        //================ GESTAO DE DOCENTES > MODIFICAR DOCENTES
        modificarDocente.getChildren().add(new Label("Configuracao > Gestao de Docentes > Modificar Docente"));
        HBox []hBoxModDocente = new HBox[2];
        for (int i = 0; i < modificarDocenteTF.length; i++) {
            modificarDocenteTF[i] = new TextField();
            modificarDocenteTF[i].setMinWidth(200);
            switch (i) {
                case 0 -> hBoxModDocente[i] = new HBox(30,new Label("Email do docente"),modificarDocenteTF[i]);
                case 1 -> hBoxModDocente[i] = new HBox(30,new Label("Novo Nome"),modificarDocenteTF[i]);
            }
            hBoxModDocente[i].setAlignment(Pos.CENTER_RIGHT);
            hBoxModDocente[i].setPadding(new Insets(0,50,0, 0));
            modificarDocente.getChildren().add(hBoxModDocente[i]);
        }

        HBox hBoxBtnModDocente = new HBox(30);
        for (int i = 0; i < 2; i++)  {
            hBoxBtnModDocente.getChildren().add(btnModificarDocente[i] = new Button());
            switch(i) {
                case 0 -> btnModificarDocente[i].setText("Ok");
                case 1 -> btnModificarDocente[i].setText("Cancelar");
            }
            btnModificarDocente[i].setMinWidth(50);
        }

        hBoxBtnModDocente.setAlignment(Pos.CENTER);
        modificarDocente.getChildren().add(hBoxBtnModDocente);
        modificarDocente.setAlignment(Pos.CENTER);

        //================ GESTAO DE DOCENTES > ELIMINAR DOCENTE
        eliminarDocente.getChildren().add(new Label("Configuracao > Gestao de Docentes > Eliminar Docente"));

        eliminarDocenteTF.setMinWidth(200);

        HBox hBoxEliminarDocente = new HBox(30,new Label("Email do docente"),eliminarDocenteTF);
        hBoxEliminarDocente.setAlignment(Pos.CENTER);
        eliminarDocente.getChildren().add(hBoxEliminarDocente);

        HBox hBoxBtnsEliminarDocente = new HBox(30);
        for (int i = 0; i < 2; i++)  {
            hBoxBtnsEliminarDocente.getChildren().add(btnEliminarDocente[i] = new Button());
            switch(i) {
                case 0 -> btnEliminarDocente[i].setText("Ok");
                case 1 -> btnEliminarDocente[i].setText("Cancelar");
            }
            btnEliminarDocente[i].setMinWidth(50);
        }

        hBoxBtnsEliminarDocente.setAlignment(Pos.CENTER);
        eliminarDocente.getChildren().add(hBoxBtnsEliminarDocente);
        eliminarDocente.setAlignment(Pos.CENTER);

        /*================================ GESTAO DE PROPOSTAS ================================*/
        gestaoPropostas.getChildren().add(new Label("Configuracao > Gestao de Propostas"));

        for (int i = 0; i < btnGestaoPropostas.length; i++)  {
            switch(i) {
                case 0 -> btnGestaoPropostas[i] = new Button("Inserir Proposta (Manual)");
                case 1 -> btnGestaoPropostas[i] = new Button("Ler CSV");
                case 2 -> btnGestaoPropostas[i] = new Button("Consultar Propostas");
                case 3 -> btnGestaoPropostas[i] = new Button("Modificar Proposta");
                case 4 -> btnGestaoPropostas[i] = new Button("Eliminar Proposta");
                case 5 -> btnGestaoPropostas[i] = new Button("Voltar");
            }
            btnGestaoPropostas[i].setMinWidth(150);
            gestaoPropostas.getChildren().add(btnGestaoPropostas[i]);
        }
        gestaoPropostas.setAlignment(Pos.CENTER);

        inserirProposta.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Inserir Proposta"),comboBoxProposta, btnVoltarProposta);
        inserirProposta.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > INSERIR ESTAGIO
        inserirEstagio.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Inserir Estagio"));

        HBox [] hBoxInserirEstagio = new HBox[6];
        for (int i = 0; i < hBoxInserirEstagio.length; i++) {
            switch(i) {
                case 0 -> hBoxInserirEstagio[i] = new HBox(30,new Label("Codigo"),estagioTF[i] = new TextField());
                case 1 -> hBoxInserirEstagio[i] = new HBox(30,new Label("Titulo"),estagioTF[i] = new TextField());
                case 2 -> hBoxInserirEstagio[i] = new HBox(30,new Label("Area de destino"),estagioTF[i] = new TextField());
                case 3 -> hBoxInserirEstagio[i] = new HBox(30,new Label("Entidade de acolhimento"),estagioTF[i] = new TextField());
                case 4 -> hBoxInserirEstagio[i] = new HBox(30,new Label("Numero de aluno"),estagioTF[i] = new TextField());
                case 5 -> {
                    hBoxInserirEstagio[i] = new HBox(50);
                    for (int j = 0; j < 2; j++)  {
                        hBoxInserirEstagio[i].getChildren().add(btnInserirEstagio[j] = new Button());
                        switch(j) {
                            case 0 -> btnInserirEstagio[j].setText("Ok");
                            case 1 -> btnInserirEstagio[j].setText("Cancelar");
                        }
                        btnInserirEstagio[j].setMinWidth(50);
                    }
                }
            }
            if (i < 5)
                estagioTF[i].setMaxWidth(200);
            if (i != 5) {
                hBoxInserirEstagio[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxInserirEstagio[i].setPadding(new Insets(5, 50, 0, 0));
            }
            inserirEstagio.getChildren().add(hBoxInserirEstagio[i]);
        }
        hBoxInserirEstagio[5].setAlignment(Pos.CENTER);
        hBoxInserirEstagio[5].setPadding(new Insets(5, 0, 0, 0));
        inserirEstagio.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > INSERIR PROJETO
        inserirProjeto.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Inserir Projeto"));

        HBox [] hBoxInserirProjeto = new HBox[6];
        for (int i = 0; i < hBoxInserirProjeto.length; i++) {
            switch(i) {
                case 0 -> hBoxInserirProjeto[i] = new HBox(30,new Label("Codigo"),projetoTF[i] = new TextField());
                case 1 -> hBoxInserirProjeto[i] = new HBox(30,new Label("Titulo"),projetoTF[i] = new TextField());
                case 2 -> hBoxInserirProjeto[i] = new HBox(30,new Label("Area de destino"),projetoTF[i] = new TextField());
                case 3 -> hBoxInserirProjeto[i] = new HBox(30,new Label("Email do docente"),projetoTF[i] = new TextField());
                case 4 -> hBoxInserirProjeto[i] = new HBox(30,new Label("Numero de aluno"),projetoTF[i] = new TextField());
                case 5 -> {
                    hBoxInserirProjeto[i] = new HBox(50);
                    for (int j = 0; j < 2; j++)  {
                        hBoxInserirProjeto[i].getChildren().add(btnInserirProjeto[j] = new Button());
                        switch(j) {
                            case 0 -> btnInserirProjeto[j].setText("Ok");
                            case 1 -> btnInserirProjeto[j].setText("Cancelar");
                        }
                        btnInserirProjeto[j].setMinWidth(50);
                    }
                }
            }
            if (i < 5)
                projetoTF[i].setMaxWidth(200);
            if (i != 5) {
                hBoxInserirProjeto[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxInserirProjeto[i].setPadding(new Insets(5, 50, 0, 0));
            }
            inserirProjeto.getChildren().add(hBoxInserirProjeto[i]);
        }
        hBoxInserirProjeto[5].setAlignment(Pos.CENTER);
        hBoxInserirProjeto[5].setPadding(new Insets(5, 0, 0, 0));
        inserirProjeto.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > INSERIR AUTOPROPOSTO
        inserirAuto.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Inserir AutoProposto"));

        HBox [] hBoxInserirAuto = new HBox[4];
        for (int i = 0; i < hBoxInserirAuto.length; i++) {
            switch(i) {
                case 0 -> hBoxInserirAuto[i] = new HBox(30,new Label("Codigo"),autoPropTF[i] = new TextField());
                case 1 -> hBoxInserirAuto[i] = new HBox(30,new Label("Titulo"),autoPropTF[i] = new TextField());
                case 2 -> hBoxInserirAuto[i] = new HBox(30,new Label("Aluno atribuido"),autoPropTF[i] = new TextField());
                case 3 -> {
                    hBoxInserirAuto[i] = new HBox(50);
                    for (int j = 0; j < 2; j++)  {
                        hBoxInserirAuto[i].getChildren().add(btnInserirAuto[j] = new Button());
                        switch(j) {
                            case 0 -> btnInserirAuto[j].setText("Ok");
                            case 1 -> btnInserirAuto[j].setText("Cancelar");
                        }
                        btnInserirAuto[j].setMinWidth(50);
                    }
                }
            }
            if (i < 3)
                autoPropTF[i].setMaxWidth(200);
            if (i != 3) {
                hBoxInserirAuto[i].setAlignment(Pos.CENTER_RIGHT);
                hBoxInserirAuto[i].setPadding(new Insets(5, 50, 0, 0));
            }
            inserirAuto.getChildren().add(hBoxInserirAuto[i]);
        }
        hBoxInserirAuto[3].setAlignment(Pos.CENTER);
        hBoxInserirAuto[3].setPadding(new Insets(5, 0, 0, 0));
        inserirAuto.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > MODIFICAR PROPOSTA
        modificarProposta.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Modificar Proposta"),comboBoxModProposta,btnVoltarModProposta);
        modificarProposta.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > MODIFICAR ESTAGIO
        modificarEstagio.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Modificar Estagio"),comboBoxModEstagio);

        for (int i = 0; i < hBoxModificarEstagio.length; i++) {
            if (i < 2) {
                modEstagioTF[i] = new TextField();
                modEstagioTF[i].setMinWidth(200);
            }
            switch (i) {
                case 0 -> hBoxModificarEstagio[i] = new HBox(30, new Label("Codigo"), modEstagioTF[i]);
                case 1 -> hBoxModificarEstagio[i] = new HBox(30, new Label("Novo valor"), modEstagioTF[i]);
                case 2 -> {
                    hBoxModificarEstagio[i] = new HBox(50);
                    for (int j = 0; j < 2; j++) {
                        hBoxModificarEstagio[i].getChildren().add(btnModificarEstagio[j] = new Button());
                        switch (j) {
                            case 0 -> btnModificarEstagio[j].setText("Ok");
                            case 1 -> btnModificarEstagio[j].setText("Cancelar");
                        }
                        btnModificarEstagio[j].setMinWidth(30);
                    }
                }
            }
            hBoxModificarEstagio[i].setAlignment(Pos.CENTER_RIGHT);
            //hBoxModificarEstagio[i].setPadding(new Insets(10,170,0, 0));
            modificarEstagio.getChildren().add(hBoxModificarEstagio[i]);
        }
        modificarEstagio.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > MODIFICAR PROJETO
        modificarProjeto.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Modificar Projeto"),comboBoxModProjeto);

        for (int i = 0; i < hBoxModificarProjeto.length; i++) {
            if (i < 2) {
                modProjetoTF[i] = new TextField();
                modProjetoTF[i].setMinWidth(200);
            }
            switch (i) {
                case 0 -> hBoxModificarProjeto[i] = new HBox(30, new Label("Codigo"), modProjetoTF[i]);
                case 1 -> hBoxModificarProjeto[i] = new HBox(30, new Label("Novo valor"), modProjetoTF[i]);
                case 2 -> {
                    hBoxModificarProjeto[i] = new HBox(50);
                    for (int j = 0; j < 2; j++) {
                        hBoxModificarProjeto[i].getChildren().add(btnModificarProjeto[j] = new Button());
                        switch (j) {
                            case 0 -> btnModificarProjeto[j].setText("Ok");
                            case 1 -> btnModificarProjeto[j].setText("Cancelar");
                        }
                        btnModificarProjeto[j].setMinWidth(50);
                    }
                }
            }
            hBoxModificarProjeto[i].setAlignment(Pos.CENTER_RIGHT);
            //hBoxModificarProjeto[i].setPadding(new Insets(10,170,0, 0));
            modificarProjeto.getChildren().add(hBoxModificarProjeto[i]);
        }
        modificarProjeto.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > MODIFICAR AUTOPROPOSTO
        modificarAuto.getChildren().addAll(new Label("Configuracao > Gestao de Propostas > Modificar Projeto"),comboBoxModAuto);

        for (int i = 0; i < hBoxModificarAuto.length; i++) {
            if (i < 2) {
                modAutoTF[i] = new TextField();
                modAutoTF[i].setMinWidth(200);
            }
            switch (i) {
                case 0 -> hBoxModificarAuto[i] = new HBox(30, new Label("Codigo"), modAutoTF[i]);
                case 1 -> hBoxModificarAuto[i] = new HBox(30, new Label("Novo valor"), modAutoTF[i]);
                case 2 -> {
                    hBoxModificarAuto[i] = new HBox(50);
                    for (int j = 0; j < 2; j++) {
                        hBoxModificarAuto[i].getChildren().add(btnModificarAuto[j] = new Button());
                        switch (j) {
                            case 0 -> btnModificarAuto[j].setText("Ok");
                            case 1 -> btnModificarAuto[j].setText("Cancelar");
                        }
                        btnModificarAuto[j].setMinWidth(50);
                    }
                }
            }
            hBoxModificarAuto[i].setAlignment(Pos.CENTER_RIGHT);
            //hBoxModificarAuto[i].setPadding(new Insets(10,170,0, 0));
            modificarAuto.getChildren().add(hBoxModificarAuto[i]);
        }
        modificarAuto.setAlignment(Pos.CENTER);

        //================ GESTAO DE PROPOSTAS > ELIMINAR PROPOSTA
        eliminarProposta.getChildren().add(new Label("Configuracao > Gestao de Propostas > Eliminar Proposta"));

        HBox hBoxEliminarProposta = new HBox(30,new Label("Codigo da proposta"),delCodigoProposta);
        hBoxEliminarProposta.setAlignment(Pos.CENTER);
        eliminarProposta.getChildren().add(hBoxEliminarProposta);

        HBox delBtnProposta = new HBox(30);
        for (int i = 0; i < 2; i++)  {
            delBtnProposta.getChildren().add(btnEliminarProposta[i] = new Button());
            switch(i) {
                case 0 -> btnEliminarProposta[i].setText("Ok");
                case 1 -> btnEliminarProposta[i].setText("Cancelar");
            }
            btnEliminarProposta[i].setMinWidth(50);
        }

        delBtnProposta.setAlignment(Pos.CENTER);
        eliminarProposta.getChildren().add(delBtnProposta);
        eliminarProposta.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        /*================================ Eventos Botoes > MENU INCIAL ================================*/
        btnMenuInicial[0].setOnAction(actionEvent -> this.setCenter(gestaoAlunos));     // Gestao de Alunos
        btnMenuInicial[1].setOnAction(actionEvent -> this.setCenter(gestaoDocentes));    // Gestao de Propostas
        btnMenuInicial[2].setOnAction(actionEvent -> this.setCenter(gestaoPropostas));     // Gestao de Docentes
        btnMenuInicial[3].setOnAction(actionEvent -> exportarFase1());     // Exportar Fase 1
        btnMenuInicial[4].setOnAction(actionEvent -> fechar());     // Fechar a fase
        btnMenuInicial[5].setOnAction(actionEvent -> { model.avancar(); this.setCenter(menuInicial); });      // Avancar

        /*================================ Eventos Botoes > GESTAO ALUNOS ================================*/
        btnGestaoAlunos[0].setOnAction(actionEvent -> this.setCenter(inserirAluno));     // Inserir aluno (Manual)
        btnGestaoAlunos[1].setOnAction(actionEvent -> lerCSVAluno());  // Ler CSV alunos
        btnGestaoAlunos[2].setOnAction(actionEvent -> consultarAlunos()); // Consultar Alunos
        btnGestaoAlunos[3].setOnAction(actionEvent -> this.setCenter(modificarAluno));  // Modificar Aluno
        btnGestaoAlunos[4].setOnAction(actionEvent -> this.setCenter(eliminarAluno));   // Eliminar Aluno
        btnGestaoAlunos[5].setOnAction(actionEvent -> this.setCenter(menuInicial));     // Voltar

        /* [OK/CANCEL] INSERIR ALUNO */
        btnInserirAluno[0].setOnAction(actionEvent -> inserirAluno());     // Ok
        btnInserirAluno[1].setOnAction(actionEvent -> { this.setCenter(gestaoAlunos); });    // Cancelar

        /* [OK/CANCEL] MODIFICAR ALUNO */
        btnModificarAluno[0].setOnAction(actionEvent -> modificarAluno());     // Ok
        btnModificarAluno[1].setOnAction(actionEvent -> this.setCenter(gestaoAlunos));  // Cancelar

        /* [OK/CANCEL] ELIMINAR ALUNO */
        btnEliminarAluno[0].setOnAction(actionEvent -> eliminarAluno());     // Ok
        btnEliminarAluno[1].setOnAction(actionEvent -> this.setCenter(gestaoAlunos));   // Cancelar

        /*================================ Eventos Botoes > GESTAO DOCENTES ================================*/
        btnGestaoDocentes[0].setOnAction(actionEvent -> this.setCenter(inserirDocente));    // Inserir Docente
        btnGestaoDocentes[1].setOnAction(actionEvent -> lerCSVDocente());       // Ler CSV Docentes
        btnGestaoDocentes[2].setOnAction(actionEvent -> consultarDocentes());     // Consultar Docentes
        btnGestaoDocentes[3].setOnAction(actionEvent -> this.setCenter(modificarDocente));  // Modificar Docente
        btnGestaoDocentes[4].setOnAction(actionEvent -> this.setCenter(eliminarDocente));   // Eliminar Docente
        btnGestaoDocentes[5].setOnAction(actionEvent -> this.setCenter(menuInicial));   // Voltar

        /* [OK/CANCEL] INSERIR DOCENTE */
        btnInserirDocente[0].setOnAction(actionEvent -> inserirDocente());      // Ok
        btnInserirDocente[1].setOnAction(actionEvent -> this.setCenter(gestaoDocentes));   // Cancelar

        /* [OK/CANCEL] MODIFICAR DOCENTE */
        btnModificarDocente[0].setOnAction(actionEvent -> modificarDocente());    // OK
        btnModificarDocente[1].setOnAction(actionEvent ->  this.setCenter(gestaoDocentes));   // Cancelar

        /* [OK/CANCEL] ELIMINAR DOCENTE */
        btnEliminarDocente[0].setOnAction(actionEvent -> eliminarDocente());      // Ok
        btnEliminarDocente[1].setOnAction(actionEvent ->  this.setCenter(gestaoDocentes));   // Cancelar

        //================ Eventos Botoes > GESTAO PROPOSTAS
        btnGestaoPropostas[0].setOnAction(actionEvent -> this.setCenter(inserirProposta));  // Inserir Proposta
        btnGestaoPropostas[1].setOnAction(actionEvent -> lerCSVProposta());      // Ler CSV Propostas
        btnGestaoPropostas[2].setOnAction(actionEvent -> consultarPropostas());     // Consultar Propostas
        btnGestaoPropostas[3].setOnAction(actionEvent -> this.setCenter(modificarProposta));     // Modificar Propostas
        btnGestaoPropostas[4].setOnAction(actionEvent -> this.setCenter(eliminarProposta));     // Eliminar Proposta
        btnGestaoPropostas[5].setOnAction(actionEvent -> this.setCenter(menuInicial)); // Voltar

        //================ Eventos Botoes > GESTAO PROPOSTAS > INSERIR
        comboBoxProposta.setOnAction(actionEvent -> {
            switch(comboBoxProposta.getSelectionModel().getSelectedIndex()) {
                case 0 -> this.setCenter(inserirEstagio);
                case 1 -> this.setCenter(inserirProjeto);
                case 2 -> this.setCenter(inserirAuto);
            }
            comboBoxProposta.getSelectionModel().clearSelection();
            comboBoxProposta.setValue(null);
        });

        btnVoltarProposta.setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        /* [OK/CANCEL] INSERIR > ESTAGIO */
        btnInserirEstagio[0].setOnAction(actionEvent -> inserirEstagio());
        btnInserirEstagio[1].setOnAction(actionEvent -> this.setCenter(inserirProposta));

        /* [OK/CANCEL] INSERIR > PROJETO */
        btnInserirProjeto[0].setOnAction(actionEvent -> inserirProjeto());
        btnInserirProjeto[1].setOnAction(actionEvent -> this.setCenter(inserirProposta));

        /* [OK/CANCEL] INSERIR > AUTOPROPOSTO */
        btnInserirAuto[0].setOnAction(actionEvent -> inserirAutoProp());
        btnInserirAuto[1].setOnAction(actionEvent -> this.setCenter(inserirProposta));

        //================ Eventos Botoes > GESTAO PROPOSTAS > MODIFICAR
        btnVoltarModProposta.setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        comboBoxModProposta.setOnAction(actionEvent -> {
            switch(comboBoxModProposta.getSelectionModel().getSelectedIndex()) {
                case 0 -> this.setCenter(modificarEstagio);
                case 1 -> this.setCenter(modificarProjeto);
                case 2 -> this.setCenter(modificarAuto);
            }
            comboBoxModProposta.getSelectionModel().clearSelection();
            comboBoxModProposta.setValue(null);
        });

        btnVoltarModProposta.setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        /* [OK/CANCEL] MODIFICAR > ESTAGIO */
        btnModificarEstagio[0].setOnAction(actionEvent -> modificarEstagio());
        btnModificarEstagio[1].setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        /* [OK/CANCEL] MODIFICAR > PROJETO */
        btnModificarProjeto[0].setOnAction(actionEvent -> modificarProjeto());
        btnModificarProjeto[1].setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        /* [OK/CANCEL] MODIFICAR > AUTOPROPOSTO */
        btnModificarAuto[0].setOnAction(actionEvent -> modificarAutoProp());
        btnModificarAuto[1].setOnAction(actionEvent -> this.setCenter(gestaoPropostas));

        /* [OK/CANCEL] ELIMINAR PROPOSTAS */
        btnEliminarProposta[0].setOnAction(actionEvent -> eliminarProposta());      // Ok
        btnEliminarProposta[1].setOnAction(actionEvent -> this.setCenter(gestaoPropostas));   // Cancelar
    }

    private void update() {
        this.setVisible(model != null && model.getState() == ManagementState.CONFIGURACAO);
    }

    /*===================================== EXPORTAR FASE =====================================*/
    private void exportarFase1() {
        model.exportarFase1();
        showAlert("Exportar Fase 1");
    }

    /*===================================== ALUNO =====================================*/
    private void lerCSVAluno() {
        model.lerCSVAlunos();
        showAlert("Ler CSV Alunos");
    }

    private void consultarAlunos() {
        model.consultarAlunos();
        showAlert("Lista de Alunos");
    }

    private void inserirAluno() {
        model.verificaAluno(Long.parseLong(addAlunoTF[0].getText()),addAlunoTF[1].getText(),addAlunoTF[2].getText(),
                addAlunoTF[3].getText(),addAlunoTF[4].getText(),Double.parseDouble(addAlunoTF[5].getText()),
                Boolean.parseBoolean(addAlunoTF[6].getText()));
        showAlert("Inserir aluno");
        for (int i = 0; i < addAlunoTF.length; i++)
            addAlunoTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void modificarAluno() {
        switch(comboBoxAluno.getSelectionModel().getSelectedItem().toString()) {
            case "Nome" -> model.setNomeAluno(Long.parseLong(modAlunoTF[0].getText()),modAlunoTF[1].getText());  // nome
            case "Curso" -> model.setCursoAluno(Long.parseLong(modAlunoTF[0].getText()),modAlunoTF[1].getText());  // curso
            case "Ramo" -> model.setRamoAluno(Long.parseLong(modAlunoTF[0].getText()),modAlunoTF[1].getText());  // ramo
            case "Classificacao" -> model.setClassifAluno(Long.parseLong(modAlunoTF[0].getText()),Double.parseDouble(modAlunoTF[1].getText()));   // classif
            case "Estagio" -> model.setEstagioAluno(Long.parseLong(modAlunoTF[0].getText()),Boolean.parseBoolean(modAlunoTF[1].getText())); // estagio
        }
        showAlert("Modificar '" + comboBoxAluno.getSelectionModel().getSelectedItem().toString() + "' Aluno");
        comboBoxAluno.setValue(null);
        comboBoxAluno.getSelectionModel().clearSelection();
        for (int i = 0; i < modAlunoTF.length; i++)
            modAlunoTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void eliminarAluno() {
        model.eliminaAluno(Long.parseLong(delAlunoTF.getText()));
        showAlert("Eliminar Aluno");
        delAlunoTF.clear();
        this.setCenter(menuInicial);
    }

    /*===================================== DOCENTE =====================================*/
    private void lerCSVDocente() {
        model.lerCSVDocentes();
        showAlert("Ler CSV Docentes");
    }

    private void consultarDocentes() {
        model.consultarDocentes();
        showAlert("Lista de Docentes");
    }

    private void inserirDocente() {
        model.verificaDocente(addDocenteTF[0].getText(),addDocenteTF[1].getText());
        showAlert("Inserir Docente");
        for (int i = 0; i < addDocenteTF.length; i++)
            addDocenteTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void modificarDocente() {
        model.setNomeDocente(modificarDocenteTF[0].getText(),modificarDocenteTF[1].getText());
        showAlert("Modificar 'Nome' Docente");
        for (int i = 0; i < modificarDocenteTF.length; i++)
            modificarDocenteTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void eliminarDocente() {
        model.eliminaDocente(eliminarDocenteTF.getText());
        showAlert("Eliminar Docente");
        eliminarDocenteTF.clear();
        this.setCenter(menuInicial);
    }

    /*===================================== PROPOSTA =====================================*/
    private void lerCSVProposta() {
        model.lerCSVPropostas();
        showAlert("Ler CSV Propostas");
    }

    private void consultarPropostas() {
        model.consultarPropostas();
        showAlert("Lista de Propostas");
    }

    private void inserirEstagio() {
        model.verificarEstagio(estagioTF[0].getText(),estagioTF[1].getText(),
                estagioTF[2].getText(),estagioTF[3].getText(), Long.parseLong(estagioTF[4].getText()));
        showAlert("Inserir Estagio");
        for (int i = 0; i < estagioTF.length; i++)
            estagioTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void inserirProjeto() {
        model.verificarProjeto(projetoTF[0].getText(),projetoTF[1].getText(),
                projetoTF[2].getText(),projetoTF[3].getText(), Long.parseLong(projetoTF[4].getText()));
        showAlert("Inserir Projeto");
        for (int i = 0; i < projetoTF.length; i++)
            projetoTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void inserirAutoProp() {
        model.verificarAutoProp(autoPropTF[0].getText(),autoPropTF[1].getText(),
                Long.parseLong(autoPropTF[2].getText()));
        showAlert("Inserir AutoProposto");
        for (int i = 0; i < autoPropTF.length; i++)
            autoPropTF[i].clear();
        this.setCenter(menuInicial);
    }

    private void modificarEstagio() {
        switch (comboBoxModEstagio.getSelectionModel().getSelectedIndex()) {
            case 0 -> model.propostaSetTitulo(modEstagioTF[0].getText(), modEstagioTF[1].getText());  // titulo
            case 1 -> model.propostaSetEnt(modEstagioTF[0].getText(), modEstagioTF[1].getText());     // entidade
            case 2 -> model.propostaSetArea(modEstagioTF[0].getText(), modEstagioTF[1].getText());    // area destino
            case 3 -> model.propostaSetAluno(modEstagioTF[0].getText(), Long.parseLong(modEstagioTF[1].getText())); // aluno
        }
        showAlert("Modificar '" + comboBoxModEstagio.getSelectionModel().getSelectedItem().toString() + "' Estagio");
        comboBoxModEstagio.setValue(null);
        comboBoxModEstagio.getSelectionModel().clearSelection();
        this.setCenter(menuInicial);
    }

    private void modificarProjeto() {
        switch (comboBoxModProjeto.getSelectionModel().getSelectedIndex()) {
            case 0 -> model.propostaSetTitulo(modProjetoTF[0].getText(), modProjetoTF[1].getText());  // titulo
            case 1 -> model.propostaSetEnt(modProjetoTF[0].getText(), modProjetoTF[1].getText());     // entidade
            case 2 -> model.propostaSetDocente(modProjetoTF[0].getText(), modProjetoTF[1].getText());    // docente
            case 3 -> model.propostaSetAluno(modProjetoTF[0].getText(), Long.parseLong(modProjetoTF[1].getText()));   // aluno
        }
        showAlert("Modificar '" + comboBoxModProjeto.getSelectionModel().getSelectedItem().toString() + "' Projeto");
        comboBoxModProjeto.setValue(null);
        comboBoxModProjeto.getSelectionModel().clearSelection();
        this.setCenter(menuInicial);
    }

    private void modificarAutoProp() {
        switch (comboBoxModAuto.getSelectionModel().getSelectedIndex()) {
            case 0 -> model.propostaSetTitulo(modAutoTF[0].getText(), modAutoTF[1].getText());  // titulo
            case 1 -> model.propostaSetAluno(modAutoTF[0].getText(), Long.parseLong(modAutoTF[1].getText())); // aluno
        }
        showAlert("Modificar '" + comboBoxModAuto.getSelectionModel().getSelectedItem().toString() + "' Autoproposto");
        comboBoxModAuto.setValue(null);
        comboBoxModAuto.getSelectionModel().clearSelection();
        this.setCenter(menuInicial);
    }

    private void eliminarProposta() {
        model.eliminaProposta(delCodigoProposta.getText());
        showAlert("Eliminar Proposta");
        delCodigoProposta.clear();
        this.setCenter(menuInicial);
    }

    private void fechar() {
        model.fechar();
        showAlert("Fechar a Fase");
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
