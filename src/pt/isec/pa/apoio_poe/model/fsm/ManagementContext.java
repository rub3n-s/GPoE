package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.ModelManager;
import pt.isec.pa.apoio_poe.model.command.*;
import pt.isec.pa.apoio_poe.model.data.Docente;
import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.data.Singleton;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagementContext {
    IManagementState state;
    ManagementData data;
    CommandManager cm;
    ModelManager model;
    Singleton singleton;

    public ManagementContext() {
        data = new ManagementData();
        state = ManagementState.CONFIGURACAO.createState(this,data);
        cm = new CommandManager();
        singleton = Singleton.Singleton();
    }

    void changeState(IManagementState newState) {
        state = newState;
    }

    /* devolve o estado atual da app */
    public ManagementState getState() {
        if (state == null)
            return null;
        return state.getState();
    }

    public  void setData(ManagementData data) { this.data = data; }

    public  ManagementData getData() { return this.data; }

    /* ir para fase seguinte */
    public boolean avancar() { return state.avancar(); }

    /* fechar fase */
    public boolean fechar() { return state.fechar(); }

    /* ir para fase anterior */
    public boolean voltar() { return state.voltar(); }

    /* ler ficheiros csv de varias classes */
    public boolean lerCSVAluno(String nome_fich) { return data.lerAluno(nome_fich); }
    public boolean lerCSVDocente(String nome_fich) { return data.lerDocente(nome_fich); }
    public boolean lerCSVProposta(String nome_fich) { return data.lerProposta(nome_fich); }
    public boolean lerCSVCandidatura(String nome_fich) { return data.lerCandidatura(nome_fich); }

    /* guarda o estado da app na fase 'CONFIGURACAO' */
    public boolean exportarFase1() { return data.exportarFase1(); }

    /* guarda o estado da app na fase 'CANDIDATURA' */
    public boolean exportarFase2() { return data.exportarFase2(); }

    /* guarda o estado da app na fase 'PROPOSTA' */
    public boolean exportarFase3() { return data.exportarFase3(); }

    /* guarda o estado da app na fase 'ORIENTADORES' e 'CONSULTA' */
    public boolean exportarFase4e5() { return data.exportarFase4e5(); }

    /*============================== ALUNOS > MODIFICAR/VERIFICAR/ELIMINAR ==============================*/
    /* verifica e insere aluno */
    public boolean verificaAluno(long numero, String nome, String email, String curso, String ramo, Double classif, Boolean estagio) {
        return data.verificaAluno(numero,nome,email,curso,ramo,classif,estagio);
    }

    public boolean verificaNumero(long numero) {return data.verificaNumero(numero); }

    public boolean alunoSetNome(long numero, String nome) { return data.alunoSetNome(numero, nome); }

    public boolean alunoSetCurso(long numero, String curso) { return data.alunoSetCurso(numero, curso); }

    public boolean alunoSetRamo(long numero, String ramo) { return data.alunoSetRamo(numero, ramo); }

    public boolean alunoSetClassif(long numero, Double classif) { return data.alunoSetClassif(numero, classif); }

    public boolean alunoSetEstagio(long numero, Boolean estagio) { return data.alunoSetEstagio(numero,estagio); }

    public boolean eliminaAluno(long numero) { return data.eliminaAluno(numero); }

    /*==============================  DOCENTES ==============================*/
    /* verifica e insere docente */
    public boolean verificaDocente(String nome, String email) { return data.verificaDocente(nome,email); }

    /* modificacao de parametros dos docentes */
    public boolean docenteSetNome(String email, String nome) { return data.docenteSetNome(email,nome); }

    /* eliminar docente*/
    public boolean eliminaDocente(String email) { return data.eliminaDocente(email); }

    /* verifiac se o email do docente existe */
    public boolean verificaEmail(String email) { return data.verificaEmail(email); }

    /*============================== PROPOSTAS ==============================*/
    /* verifica e insere propostas */
    public boolean verificaEstagio(String codigo, String titulo, String ramo, String entidade, long numero) {
        return data.verificaEstagio(codigo, titulo, ramo, entidade, numero);
    }

    public boolean verificaProjeto(String codigo, String titulo, String ramo, String emailDocente, long numero) {
        return data.verificaProjeto(codigo, titulo, ramo, emailDocente, numero);
    }

    public boolean verificaAutoProp(String codigo, String titulo, long numero) {
        return data.verificaAutoProp(codigo, titulo, numero);
    }

    /* modificacao de parametros das propostas */
    public boolean propostaSetTitulo(String codigo, String titulo) { return data.propostaSetTitulo(codigo,titulo); }

    public boolean propostaSetArea(String codigo, String area) { return data.propostaSetRamo(codigo,area); }

    public boolean propostaSetEnt(String codigo, String entidade) { return data.propostaSetEnt(codigo,entidade); }

    public boolean propostaSetAluno(String codigo, Long numero) { return data.propostaSetAluno(codigo,numero); }

    public boolean propostaSetDocente(String codigo, String email) { return data.propostaSetDocente(codigo,email); }
    /* eliminar docente*/
    public boolean eliminaProposta(String codigo) { return data.eliminaProposta(codigo); }

    /*============================== CANDIDATURAS ==============================*/
    /* verifica e insere candidatura */
    public boolean verificaCandidatura(long numero, ArrayList<String> codigos) { return data.verificaCandidatura(numero,codigos); }

    /* modificacao de parametros das candidaturas */
    public boolean adicionarCodigo(Long numero, String codigo) { return data.adicionarCodigo(numero,codigo); }

    public boolean removerCodigo(Long numero, String codigo) { return data.removerCodigo(numero,codigo); }

    /* eliminar candidatura */
    public boolean eliminaCandidatura(Long numero) { return data.eliminaCandidatura(numero); }

    /* devolve um array com codigos */
    public ArrayList<String> getArrayCodigos(String codigos) { return data.getArrayCodigos(codigos); }

    /*============================== FASE PROPOSTAS ==============================*/
    public boolean atribuicaoAutomaticaDocente() { return data.atribuicaoAutomaticaDocente(); }

    /*============================== FASE ORIENTADORES ==============================*/
    /* consulta propostas associadas ao docente */
    public boolean consultarDocenteProp(String email) { return data.consultarDocenteProp(email); }

    /* altera o docente associado a proposta */
    public boolean alterarDocenteProp(String email, String codigo) { return data.alterarDocenteProp(email,codigo); }

    /*============================== COMANDOS ==============================*/
    /* atribuicaoManual / RemocaoManual (Alunos/Docentes) */
    public boolean atribuicaoManual(String codigo, long numAluno) {
        if (!data.verificaEstadoAtribuicoes())
            return false;
        return cm.invokeCommand(new atribuicaoManualAluno(data,codigo,numAluno));
    }

    public boolean remocaoManual(String codigo, long numAluno) { return cm.invokeCommand(new remocaoManualAluno(data,codigo,numAluno)); }

    public boolean atribuicaoManualDocente(String codigo, String email) { return cm.invokeCommand(new atribuicaoManualDocente(data,codigo,email)); }

    public boolean remocaoManualDocente(String codigo, String email) { return cm.invokeCommand(new remocaoManualDocente(data,codigo,email)); }

    /* undo / redo */
    public boolean hasUndo() { return cm.hasUndo(); }

    public boolean undo() { return cm.undo(); }

    public boolean hasRedo() { return cm.hasRedo(); }

    public boolean redo() { return cm.redo(); }

    /*============================== CONSULTAR DADOS ==============================*/
    public boolean consultarAlunos() { return data.consultarAlunos(); }

    public boolean consultarDocentes() { return data.consultarDocentes(); }

    public boolean consultarPropostas() { return data.consultarPropostas(); }

    /* 'CANDIDATURAS' > LISTAGEM DE ALUNOS */
    public boolean consultarComAutoProp() { return data.consultarComAutoProp(); }

    public boolean consultarComCandidatura() { return data.consultarComCandidatura(); }

    public boolean consultarSemCandidatura() { return data.consultarSemCandidatura(); }

    /* 'CANDIDATURAS' > LISTAGEM COM FILTROS */
    public boolean consultarFiltrosCandidatura(ArrayList<Integer> arr) { return data.consultarFiltrosCandidatura(arr); }

    /* 'PROPOSTAS' > LISTAGEM DE ALUNOS */
    public boolean consultarAlunosRegistadas() { return data.consultarAlunosRegistadas(); }

    public boolean consultarAlunosAtribuidas() { return data.consultarAlunosAtribuidas(); }

    public boolean consultarAlunosNaoAtribuidas() { return data.consultarAlunosNaoAtribuidas(); }

    /* 'PROPOSTAS' > LISTAGEM DE PROPOSTAS (C/ FILTROS) PROJETO/ESTAGIO */
    public boolean consultarFiltrosPropostas(ArrayList<Integer> arr) { return data.consultarFiltrosPropostas(arr); }

    public boolean atribuicaoAutomaticaAlunoAssoc() { return data.atribuicaoAutomaticaAlunoAssoc(); }

    public boolean atribuicaoAutomaticaAlunoNAssoc() { return data.atribuicaoAutomaticaAlunoNAssoc(); }

    /* 'ORIENTADORES' > LISTAGEM DE ATRIBUICAO DE DOCENTES */
    public boolean consultarAlunosPropDocente() { return data.consultarAlunosPropDocente(); }

    public boolean consultarAlunosPropSemDocente() { return data.consultarAlunosPropSemDocente(); }

    public boolean consultarOrientacoes() { return data.consultarOrientacoes(); }

    /* 'CONSULTA' > LISTAGEM DE ATRIBUICAO DE DOCENTES */
    public boolean consultarSemPropComCandidatura() { return data.consultarSemPropComCandidatura(); }

    public boolean consultarPropComCandidatura() { return data.consultarPropComCandidatura(); }

    public boolean consultarPropostasNaoAtribuidas() { return data.consultarPropostasNaoAtribuidas(); }

    public boolean consultarPropostasAtribuidas() { return data.consultarPropostasAtribuidas(); }

    /*============================== LISTVIEW ==============================*/
    /* Configuracao */
    public int getNumAlunos() { return data.getAlunos().size(); }

    public int getNumDocentes() { return data.getDocentes().size(); }

    public int getNumPropostas() { return data.getPropostas().size(); }

    /* Candidatura */
    public int getNumCandidaturas() { return data.getCandidaturas().size(); }

    public int getNumAlunosCandidatura() { return data.getNumAlunosComCandidatura(); }

    public String getListaCandidaturas() { return data.getListaCandidaturas(); }

    /* Proposta */
    public int getNumAtribuidas() { return data.getPropostasAtribuidas().size(); }

    public int getNumEstagios() { return data.getNumEstagios(); }

    public int getNumProjetos() { return data.getNumProjetos(); }

    public int getNumAutos() { return data.getNumAutos(); }

    public String getListaPropostasAtribuidas() { return data.getListaPropostasAtribuidas(); }

    public boolean userEscolheAluno(int index) { return data.userEscolheAluno(index); }

    public boolean isTerminouAtribuicao() { return data.isTerminouAtribuicao(); }

    public void setTerminouAtribuicao() { data.setTerminouAtribuicao(); }

    /* Orientadores */
    public int getNumAlunosProDocAssoc() { return data.getNumAlunosProDocAssoc(); }

    public int getNumAlunosProSDocAssoc() { return data.getNumAlunosProSDocAssoc(); }

    public int getNumTotalOrientacoes() { return data.getNumTotalOrientacoes(); }

    /* Consulta */
    public int getEstagiosDA() { return data.getEstagiosDA(); }

    public int getEstagiosSI() { return data.getEstagiosSI(); }

    public int getEstagiosRAS() { return data.getEstagiosRAS(); }

    public int getNumNAtribuidas() { return data.getNumNAtribuidas(); }

    public HashMap<Docente,Integer> getDocentesTop5() { return data.getDocentesTop5(); }

    public HashMap<String,Integer> getEmpresasTop5() { return data.getEmpresasTop5(); }

    public String getListaAlunos() { return data.getListaAlunos(); }

    public String getListaDocentes() { return data.getListaDocentes(); }

    public String getListaPropostas() { return data.getListaPropostas(); }
}
