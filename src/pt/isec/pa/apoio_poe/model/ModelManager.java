package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.data.Docente;
import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.fsm.ManagementContext;
import pt.isec.pa.apoio_poe.model.fsm.ManagementState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelManager {
    public static final String PROP_STATE = "state";
    public static final String PROP_DATA  = "data";

    ManagementContext context;
    PropertyChangeSupport pcs;
    ManagementData data;

    public ModelManager() {
        this.context = new ManagementContext();
        this.data = context.getData();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    public ManagementState getState() {
        return context.getState();
    }

    public void avancar() {
        context.avancar();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public void voltar() {
        context.voltar();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public void fechar() {
        context.fechar();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public void undo() { context.undo(); }

    public void redo() { context.redo(); }

    /*================================================ CONFIGURACAO ================================================*/
    public void exportarFase1() { context.exportarFase1(); }

    //======================== ALUNOS
    public void consultarAlunos() { context.consultarAlunos(); }

    public void lerCSVAlunos() {
        context.lerCSVAluno("alunos");
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void verificaAluno(long numero, String nome, String email, String curso, String ramo, Double classif, Boolean estagio) {
        context.verificaAluno(numero,nome,email,curso,ramo,classif,estagio);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void setNomeAluno(long numero, String nome) { context.alunoSetNome(numero,nome); }

    public void setRamoAluno(long numero, String ramo) { context.alunoSetRamo(numero,ramo); }

    public void setCursoAluno(long numero, String curso) { context.alunoSetCurso(numero,curso); }

    public void setClassifAluno(long numero, Double classif) { context.alunoSetClassif(numero,classif); }

    public void setEstagioAluno(long numero, Boolean estagio) { context.alunoSetEstagio(numero,estagio); }

    public void eliminaAluno(long numero) {
        context.eliminaAluno(numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    //======================== DOCENTES
    public void lerCSVDocentes() {
        context.lerCSVDocente("docentes");
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void verificaDocente(String nome, String email) {
        context.verificaDocente(nome,email);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void consultarDocentes() { context.consultarDocentes(); }

    public void setNomeDocente(String email, String nome) { context.docenteSetNome(email,nome); }

    public void eliminaDocente(String email) {
        context.eliminaDocente(email);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    //======================== PROPOSTAS
    public void lerCSVPropostas() {
        context.lerCSVProposta("propostas");
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void verificarEstagio(String codigo, String titulo, String ramo, String entidade, long numero) {
        context.verificaEstagio(codigo,titulo,ramo,entidade,numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void verificarProjeto(String codigo, String titulo, String ramo, String docente, long numero) {
        context.verificaProjeto(codigo,titulo,ramo,docente,numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void verificarAutoProp(String codigo, String titulo, long numero) {
        context.verificaAutoProp(codigo,titulo,numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void consultarPropostas() { context.consultarPropostas(); }

    public void propostaSetTitulo(String codigo, String titulo) { context.propostaSetTitulo(codigo,titulo); }

    public void propostaSetArea(String codigo, String area) { context.propostaSetArea(codigo,area); }

    public void propostaSetEnt(String codigo, String entidade) { context.propostaSetEnt(codigo,entidade); }

    public void propostaSetAluno(String codigo, long numero) { context.propostaSetAluno(codigo,numero); }

    public void propostaSetDocente(String codigo, String email) { context.propostaSetDocente(codigo,email); }

    public void eliminaProposta(String codigo) {
        context.eliminaProposta(codigo);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    /*================================================ CANDIDATURA ================================================*/
    public void lerCSVCandidaturas() {
        context.lerCSVCandidatura("candidaturas");
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void inserirCandidatura(long numero, ArrayList<String> codigos) {
        context.verificaCandidatura(numero,codigos);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void adicionarCodigo(long numero,String codigo) {
        context.adicionarCodigo(numero,codigo);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void removerCodigo(long numero,String codigo) {
        context.removerCodigo(numero,codigo);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void consultarComAutoProp() { context.consultarComAutoProp(); }

    public void consultarComCandidatura() { context.consultarComCandidatura(); }

    public void consultarSemCandidatura() { context.consultarSemCandidatura(); }

    public void consultarPropostas(ArrayList<Integer> arr) { context.consultarFiltrosPropostas(arr); }

    public void eliminarCandidatura(long numero) {
        context.eliminaCandidatura(numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public ArrayList<String> getArrayCodigos(String codigos) { return context.getArrayCodigos(codigos); }

    public void exportarFase2() { context.exportarFase2(); }

    /*================================================ PROPOSTA ================================================*/
    public void atribuicaoAutomaticaAlunoAssoc() {
        context.atribuicaoAutomaticaAlunoAssoc();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void atribuicaoAutomaticaAlunoNAssoc() {
        context.atribuicaoAutomaticaAlunoNAssoc();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void atribuicaoManualProposta(String codigo, long numero) {
        context.atribuicaoManual(codigo,numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void remocaoManualProposta(String codigo, long numero) {
        context.remocaoManual(codigo,numero);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void  consultarAlunosRegistadas() { context.consultarAlunosRegistadas(); }

    public void  consultarAlunosAtribuidas() { context.consultarAlunosAtribuidas(); }

    public void  consultarAlunosNaoAtribuidas() { context.consultarAlunosNaoAtribuidas(); }

    public void exportarFase3() { context.exportarFase3(); }

    /*================================================ ORIENTADORES ================================================*/
    public void atribuicaoAutomaticaDocentes() {
        context.atribuicaoAutomaticaDocente();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void atribuicaoManualDocente(String codigo, String email) {
        context.atribuicaoManualDocente(codigo,email);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void remocaoManualDocente(String codigo, String email) {
        context.remocaoManualDocente(codigo,email);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void consultarDocente(String codigo) { context.consultarDocenteProp(codigo); }

    public void consultarAlunosPropDocente() { context.consultarAlunosPropDocente(); }

    public void consultarAlunosPropSemDocente() { context.consultarAlunosPropSemDocente(); }

    public void consultarOrientacoes() { context.consultarOrientacoes(); }

    public void exportarFase4e5() { context.exportarFase4e5(); }

    /*================================================ CONSULTA ================================================*/
    public void consultarSemPropComCandidatura() { context.consultarSemPropComCandidatura(); }

    public void consultarPropostasNaoAtribuidas() { context.consultarPropostasNaoAtribuidas(); }

    public void consultarPropostasAtribuidas() { context.consultarPropostasAtribuidas(); }

    /*================================================ TOOLBAR ================================================*/
    public boolean load(File hFile) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hFile))) {
            context.setData((ManagementData) ois.readObject());
            pcs.firePropertyChange(PROP_STATE,null,null);
            pcs.firePropertyChange(PROP_DATA,null,null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public boolean save(File hFile) throws IOException {
        try(ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(hFile))) {
            oss.writeObject(context.getData());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*================================================ LISTVIEW ================================================*/
    /* Configuracao */
    public int getNumAlunos() { return context.getNumAlunos(); }

    public int getNumDocentes() { return context.getNumDocentes(); }

    public int getNumPropostas() { return context.getNumPropostas(); }

    /* Candidatura */
    public int getNumCandidaturas() { return context.getNumCandidaturas(); }

    public int getNumAlunosCandidatura() { return context.getNumAlunosCandidatura(); }

    public String getListaCandidaturas() { return context.getListaCandidaturas(); }

    /* Proposta */
    public int getNumAtribuidas() { return context.getNumAtribuidas(); }

    public int getNumEstagios() { return context.getNumEstagios(); }

    public int getNumProjetos() { return context.getNumProjetos(); }

    public int getNumAutos() { return context.getNumAutos(); }

    public boolean isTerminouAtribuicao() { return context.isTerminouAtribuicao(); }

    public void setTerminouAtribuicao() { context.setTerminouAtribuicao(); }

    public void userEscolheAluno(int index) {
        context.userEscolheAluno(index);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    /* Orientador */
    public int getNumAlunosProDocAssoc() { return context.getNumAlunosProDocAssoc(); }

    public int getNumAlunosProSDocAssoc() { return context.getNumAlunosProSDocAssoc(); }

    public int getNumTotalOrientacoes() { return context.getNumTotalOrientacoes(); }

    public String getListaPropostasAtribuidas() { return context.getListaPropostasAtribuidas(); }

    /* Consulta */
    public int getEstagiosDA() { return context.getEstagiosDA(); }

    public int getEstagiosSI() { return context.getEstagiosSI(); }

    public int getEstagiosRAS() { return context.getEstagiosRAS(); }

    public int getNumNAtribuidas() { return context.getNumNAtribuidas(); }

    public HashMap<Docente,Integer> getDocentesTop5() { return context.getDocentesTop5(); }

    public HashMap<String,Integer> getEmpresasTop5() { return context.getEmpresasTop5(); }

    public String getListaAlunos() { return context.getListaAlunos(); }

    public String getListaDocentes() { return context.getListaDocentes(); }

    public String getListaPropostas() { return context.getListaPropostas(); }
}
