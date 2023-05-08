package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

public class ListPane extends ListView<HBox> {
    ModelManager model;

    /* Configuracao */
    HBox[] configuracao;
    Label lbNumAlunos, lbNumDocentes, lbNumPropostas, lbListaAlunos, lbListaDocentes, lbListaPropostas;

    /* Candidatura */
    HBox[] candidatura;
    Label lbNumAlunosCand, lbNumCandidaturas, lbAlunosComCandidatura, lbCandidaturas;

    /* Proposta */
    HBox[] proposta;
    Label lbNumPropostasAtribuidas, lbNumPropostasEstagio, lbNumPropostasProjeto, lbNumPropostasAuto, lbAtribuidasProp;

    /* Orientadores */
    HBox[] orientacoes;
    Label lbNumAlunosPropDocAssoc, lbNumAlunosPropSDocAssoc, lbNumTotalOrientacoes, lbAtribuidasOrient;

    HBox[] consulta;
    Label lbNumAlunosConsulta, lbNumDocentesConsulta, lbNumPropostasConsulta, lbNumCandidaturasConsulta, lbAlunosComCandidaturaConsulta,
            lbAlunosConsulta, lbDocentesConsulta, lbPropostaConsulta, lbCandidaturasConsulta, lbAtribuidasConsulta;

    public ListPane(ModelManager model) {
        this.model = model;

        /* Configuracao */
        configuracao = new HBox[6];
        lbNumAlunos = new Label();
        lbNumDocentes = new Label();
        lbNumPropostas = new Label();
        lbListaAlunos = new Label();
        lbListaDocentes = new Label();
        lbListaPropostas = new Label();

        /* Candidatura */
        candidatura = new HBox[4];
        lbNumAlunosCand = new Label();
        lbNumCandidaturas = new Label();
        lbAlunosComCandidatura = new Label();
        lbCandidaturas = new Label();

        /* Proposta */
        proposta = new HBox[5];
        lbNumPropostasAtribuidas = new Label();
        lbNumPropostasEstagio = new Label();
        lbNumPropostasProjeto = new Label();
        lbNumPropostasAuto = new Label();
        lbAtribuidasProp = new Label();

        /* Orientadores */
        orientacoes = new HBox[4];
        lbNumAlunosPropDocAssoc = new Label();
        lbNumAlunosPropSDocAssoc = new Label();
        lbNumTotalOrientacoes = new Label();
        lbAtribuidasOrient = new Label();

        /* Consulta*/
        consulta = new HBox[10];
        lbNumAlunosConsulta = new Label();
        lbNumDocentesConsulta = new Label();
        lbNumPropostasConsulta = new Label();
        lbNumCandidaturasConsulta = new Label();
        lbAlunosComCandidaturaConsulta = new Label();
        lbAlunosConsulta = new Label();
        lbDocentesConsulta = new Label();
        lbPropostaConsulta = new Label();
        lbCandidaturasConsulta = new Label();
        lbAtribuidasConsulta = new Label();

        createViews();
        registerHandlers();
        update();
    }

    public void createViews() {
        /* Configuracao */
        for (int i = 0; i < configuracao.length; i++) {
            switch(i) {
                case 0 -> configuracao[i] = new HBox(new Label("Numero de Alunos: "), lbNumAlunos);
                case 1 -> configuracao[i] = new HBox(new Label("Numero de Docentes: "), lbNumDocentes);
                case 2 -> configuracao[i] = new HBox(new Label("Numero de Propostas: "), lbNumPropostas);
                case 3 -> configuracao[i] = new HBox(new Label("Lista de Alunos: "), lbListaAlunos);
                case 4 -> configuracao[i] = new HBox(new Label("Lista de Docentes: "), lbListaDocentes);
                case 5 -> configuracao[i] = new HBox(new Label("Lista de Propostas: "), lbListaPropostas);
            }
            this.getItems().add(configuracao[i]);   // inicializar com os parametros da configuracao
        }

        /* Candidatura */
        for (int i = 0; i < candidatura.length; i++) {
            switch(i) {
                case 0 -> candidatura[i] = new HBox(new Label("Numero de Alunos: "), lbNumAlunosCand);
                case 1 -> candidatura[i] = new HBox(new Label("Numero de Candidaturas: "), lbNumCandidaturas);
                case 2 -> candidatura[i] = new HBox(new Label("Alunos com Candidatura: "), lbAlunosComCandidatura);
                case 3 -> candidatura[i] = new HBox(new Label("Candidaturas: \n"), lbCandidaturas);
            }
        }

        /* Proposta */
        for (int i = 0; i < proposta.length; i++) {
            switch(i) {
                case 0 -> proposta[i] = new HBox(new Label("Propostas Atribuidas: "), lbNumPropostasAtribuidas);
                case 1 -> proposta[i] = new HBox(new Label("Propostas de Estagio: "), lbNumPropostasEstagio);
                case 2 -> proposta[i] = new HBox(new Label("Propostas de Projeto: "), lbNumPropostasProjeto);
                case 3 -> proposta[i] = new HBox(new Label("Propostas de AutoPropostos: "), lbNumPropostasAuto);
                case 4 -> proposta[i] = new HBox(new Label("Propostas Atribuidas: \n"), lbAtribuidasProp);
            }
        }

        /* Orientadores */
        for (int i = 0; i < orientacoes.length; i++) {
            switch(i) {
                case 0 -> orientacoes[i] = new HBox(new Label("Alunos com Proposta e Docente Associado: "), lbNumAlunosPropDocAssoc);
                case 1 -> orientacoes[i] = new HBox(new Label("Alunos com Proposta e Sem Docente Associado: "), lbNumAlunosPropSDocAssoc);
                case 2 -> orientacoes[i] = new HBox(new Label("Numero de Orientacoes por Docente: "), lbNumTotalOrientacoes);
                case 3 -> orientacoes[i] = new HBox(new Label("Propostas Atribuidas: \n"), lbAtribuidasOrient);
            }
        }

        /* Consulta */
        for (int i = 0; i < consulta.length; i++) {
            switch(i) {
                case 0 -> consulta[i] = new HBox(new Label("Alunos: "), lbNumAlunosConsulta);
                case 1 -> consulta[i] = new HBox(new Label("Docentes: "), lbNumDocentesConsulta);
                case 2 -> consulta[i] = new HBox(new Label("Propostas: "), lbNumPropostasConsulta);
                case 3 -> consulta[i] = new HBox(new Label("Candidatuars: "), lbNumCandidaturasConsulta);
                case 4 -> consulta[i] = new HBox(new Label("Alunos com Candidatura: "), lbAlunosComCandidaturaConsulta);
                case 5 -> consulta[i] = new HBox(new Label("Alunos: "), lbAlunosConsulta);
                case 6 -> consulta[i] = new HBox(new Label("Docentes: "), lbDocentesConsulta);
                case 7 -> consulta[i] = new HBox(new Label("Propostas: "), lbPropostaConsulta);
                case 8 -> consulta[i] = new HBox(new Label("Candidaturas: \n"), lbCandidaturasConsulta);
                case 9 -> consulta[i] = new HBox(new Label("Propostas Atribuidas: \n"), lbAtribuidasConsulta);
            }
        }
    }

    public void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> { update(); });

        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> {
            // limpar a ListView para inserir as Labels consoante a fase que foi recebida no listener
            this.getItems().clear();

            if (model.getState().equals(ManagementState.CONFIGURACAO) || model.getState().equals(ManagementState.CONFIGURACAO_FECHADA))
                for (HBox hBox : configuracao) this.getItems().add(hBox);
            else if (model.getState().equals(ManagementState.CANDIDATURA) || model.getState().equals(ManagementState.CANDIDATURA_FECHADA))
                for (HBox hBox : candidatura) this.getItems().add(hBox);
            else if (model.getState().equals(ManagementState.PROPOSTA) || model.getState().equals(ManagementState.PROPOSTA_FECHADA))
                for (HBox hBox : proposta) this.getItems().add(hBox);
            else if (model.getState().equals(ManagementState.ORIENTADORES))
                for (HBox hBox : orientacoes) this.getItems().add(hBox);
            else if (model.getState().equals(ManagementState.CONSULTA))
                for (HBox hBox : consulta) this.getItems().add(hBox);

            update();
        });
    }

    public void update() {
        if (model.getState().equals(ManagementState.CONFIGURACAO) || model.getState().equals(ManagementState.CONFIGURACAO_FECHADA)) {
            lbNumAlunos.setText("" + model.getNumAlunos());
            lbNumDocentes.setText("" + model.getNumDocentes());
            lbNumPropostas.setText("" + model.getNumPropostas());
            lbListaAlunos.setText("" + model.getListaAlunos());
            lbListaDocentes.setText("" + model.getListaDocentes());
            lbListaPropostas.setText("" + model.getListaPropostas());
        }
        else if (model.getState().equals(ManagementState.CANDIDATURA) || model.getState().equals(ManagementState.CANDIDATURA_FECHADA)) {
            lbNumAlunosCand.setText("" + model.getNumAlunos());
            lbNumCandidaturas.setText("" + model.getNumCandidaturas());
            lbAlunosComCandidatura.setText("" + model.getNumAlunosCandidatura());
            lbCandidaturas.setText(model.getListaCandidaturas());
        }
        else if (model.getState().equals(ManagementState.PROPOSTA) || model.getState().equals(ManagementState.PROPOSTA_FECHADA)) {
            lbNumPropostasAtribuidas.setText("" + model.getNumAtribuidas());
            lbNumPropostasEstagio.setText("" + model.getNumEstagios());
            lbNumPropostasProjeto.setText("" + model.getNumProjetos());
            lbNumPropostasAuto.setText("" + model.getNumAutos());
            lbAtribuidasProp.setText(model.getListaPropostasAtribuidas());
        }
        else if (model.getState().equals(ManagementState.ORIENTADORES)) {
            lbNumAlunosPropDocAssoc.setText("" + model.getNumAlunosProDocAssoc());
            lbNumAlunosPropSDocAssoc.setText("" + model.getNumAlunosProSDocAssoc());
            lbNumTotalOrientacoes.setText("" + model.getNumTotalOrientacoes());
            lbAtribuidasOrient.setText(model.getListaPropostasAtribuidas());
        }
        else if (model.getState().equals(ManagementState.CONSULTA)) {
            lbNumAlunosConsulta.setText("" + model.getNumAlunos());
            lbNumDocentesConsulta.setText("" + model.getNumAlunos());
            lbNumPropostasConsulta.setText("" + model.getNumAlunos());
            lbNumCandidaturasConsulta.setText("" + model.getNumCandidaturas());
            lbAlunosComCandidaturaConsulta.setText("" + model.getNumAlunosCandidatura());
            lbAlunosConsulta.setText("" + model.getListaAlunos());
            lbDocentesConsulta.setText("" + model.getListaDocentes());
            lbPropostaConsulta.setText("" + model.getListaPropostas());
            lbCandidaturasConsulta.setText("" + model.getListaCandidaturas());
            lbAtribuidasConsulta.setText(model.getListaPropostasAtribuidas());
        }
    }
}
