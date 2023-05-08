package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.fsm.ManagementContext;
import pt.isec.pa.apoio_poe.ui.utils.PAInput;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ManagementUI {
    ManagementContext fsm;
    Singleton singleton;

    public ManagementUI(ManagementContext fsm) {
        this.fsm = fsm;
        this.singleton = Singleton.Singleton();
    }

    private boolean finish = false;
    public void start() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("|  Gestao de Projeto e Estagios do DEIS do ISEC  |");
        System.out.println("|                                                |");
        System.out.println("|  Trabalho realizado por:                       |");
        System.out.println("|     Ruben Santos - 2019116244                  |");
        System.out.println("└────────────────────────────────────────────────┘");
        while(!finish) {
            switch (fsm.getState()) {
                case CONFIGURACAO -> configuracaoUI();
                case CANDIDATURA -> candidaturaUI();
                case PROPOSTA -> propostaUI();
                case ORIENTADORES -> orientadoresUI();
                case CONSULTA -> consultaUI();
                case CONFIGURACAO_FECHADA -> configuracaoFechadaUI();
                case CANDIDATURA_FECHADA -> candidaturaFechadaUI();
                case PROPOSTA_FECHADA -> propostaFechadaUI();
            }
        }
        System.out.println("\nA terminar a aplicacao...\n");
    }

    //==================== FASES > CONFIGURACAO/CANDIDATURA/PROPOSTA/ORIENTADORES/CONSULTA ====================
    private void configuracaoUI() {
        switch (PAInput.chooseOption("======== Configuracao ========", "Gestao de Alunos",
                "Gestao de Docentes", "Gestao de Propostas","Exportar Informacao > .csv","Fechar a fase","Avancar > CANDIDATURA","Terminar Programa")) {
            case 1 -> gestaoAlunos();
            case 2 -> gestaoDocentes();
            case 3 -> gestaoPropostas();
            case 4 -> { fsm.exportarFase1(); System.out.println(singleton.message); }
            case 5 -> fsm.fechar();
            case 6 -> fsm.avancar();
            case 7 -> finish = true;
        }
    }

    private void candidaturaUI() {
        switch(PAInput.chooseOption("======== Candidatura ========", "Inserir","Consultar Candidaturas","Consultar Propostas","Modificar",
                "Eliminar","Exportar Informacao > .csv","Fechar a fase","Avancar > PROPOSTA","Voltar > CONFIGURACAO")) {
            case 1 -> inserirCandidatura();
            case 2 -> consultarCandidatura();
            case 3 -> consultarPropostaFiltros();
            case 4 -> modificarCandidatura();
            case 5 -> eliminarCandidatura();
            case 6 -> { fsm.exportarFase2(); System.out.println(singleton.message); }
            case 7 -> fsm.fechar();
            case 8 -> fsm.avancar();
            case 9 -> fsm.voltar();
        }
    }

    private void propostaUI() {
        switch(PAInput.chooseOption("======== Proposta ========", "Atribuicao Automatica - Autopropostas ou Propostas de Docentes",
                "Atribuicao Automatica - Alunos Sem Atribuicoes","Atribuicao/Remocao Manual","Consultar Listas de Alunos",
                "Consultar Lista de Propostas (Filtros)","Exportar Informacao > .csv","Fechar a fase","Avancar > ORIENTADORES","Voltar > CANDIDATURA")) {
            case 1 -> { fsm.atribuicaoAutomaticaAlunoAssoc(); System.out.println(singleton.message);}
            case 2 -> { fsm.atribuicaoAutomaticaAlunoNAssoc(); System.out.println(singleton.message);}
            case 3 -> propostaAtribuicaoManual();
            case 4 -> consultarProposta();
            case 5 -> consultarAtribuicaoProposta();
            case 6 -> { fsm.exportarFase3(); System.out.println(singleton.message); }
            case 7 -> fsm.fechar();
            case 8 -> fsm.avancar();
            case 9 -> fsm.voltar();
        }
    }

    private void orientadoresUI() {
        switch(PAInput.chooseOption("======== Orientadores ========", "Atribuicao Automatica","Atribuicao/Remocao Manual",
                "Consultar/Alterar Docente","Exportar Informacao > .csv","Fechar a fase","Voltar > PROPOSTA")) {
            case 1 -> { fsm.atribuicaoAutomaticaDocente(); System.out.println(singleton.message); }
            case 2 -> orientadoresAtribuicaoManual();
            case 3 -> consultarDocente();
            case 4 -> { fsm.exportarFase4e5(); System.out.println(singleton.message); }
            case 5 -> fsm.fechar();
            case 6 -> fsm.voltar();
        }
    }

    private void consultaUI() {
        switch(PAInput.chooseOption("======== Consulta ========", "Alunos Com Propostas Atribuidas",
                "Sem Propostas mas Com Candidatura","Propostas Disponiveis","Propostas Atribuidas","Orientacoes",
                "Exportar Informacao > .csv","Terminar Programa")) {
            case 1 -> { fsm.consultarAlunosAtribuidas(); System.out.println(singleton.message);}
            case 2 -> { fsm.consultarSemPropComCandidatura(); System.out.println(singleton.message);}
            case 3 -> { fsm.consultarPropostasNaoAtribuidas(); System.out.println(singleton.message);}
            case 4 -> { fsm.consultarPropostasAtribuidas(); System.out.println(singleton.message);}
            case 5 -> { fsm.consultarOrientacoes(); System.out.println(singleton.message);}
            case 6 -> fsm.exportarFase4e5();
            case 7 -> finish = true;
        }
    }

    //==================== FASES FECHADAS ====================
    private void configuracaoFechadaUI() {
        switch (PAInput.chooseOption("======== Configuracao (Fechada) ========", "Consultar Alunos",
                "Consultar Docentes", "Consultar Propostas","Avancar > CANDIDATURA")) {
            case 1 -> { fsm.consultarAlunos(); System.out.println(singleton.message);}
            case 2 -> { fsm.consultarDocentes(); System.out.println(singleton.message);}
            case 3 -> { fsm.consultarPropostas(); System.out.println(singleton.message);}
            case 4 -> fsm.avancar();
        }
    }

    private void candidaturaFechadaUI() {
        switch (PAInput.chooseOption("==== Candidatura (Fechada) ====", "Consultar Candidaturas","Consultar Propostas"
                ,"Avancar > PROPOSTA","Voltar > CONFIGURACAO")) {
            case 1 -> consultarCandidatura();
            case 2 -> consultarPropostaFiltros();
            case 3 -> fsm.avancar();
            case 4 -> fsm.voltar();
        }
    }

    private void propostaFechadaUI() {
        switch (PAInput.chooseOption("======== Proposta (Fechada) ========", "Consultar Listas de Alunos",
                "Consultar Lista de Propostas (Filtros)","Avancar > ORIENTADORES","Voltar > CANDIDATURA")) {
            case 1 -> consultarProposta();
            case 2 -> consultarPropostaFiltros();
            case 3 -> fsm.avancar();
            case 4 -> fsm.voltar();
        }
    }

    //==================== CONFIGURACAO > GESTAO DE ALUNOS ====================
    private void gestaoAlunos() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Alunos ========", "Inserir",
                "Consultar","Modificar","Eliminar","Sair")) {
            case 1 -> inserirAluno();   // inserir
            case 2 -> { fsm.consultarAlunos(); System.out.println(singleton.message); }   // consultar
            case 3 -> modificarAluno();   // modificar
            case 4 -> eliminarAluno();   // eliminar
            case 5 -> { return; }   // return
        }
    }

    private void inserirAluno() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Alunos > Inserir ========",
                "Inserir manualmente", "Ler .csv","Sair")) {
            case 1 -> inserirAlunoManualmente();   // inserir manualmente
            case 2 -> { fsm.lerCSVAluno("alunos"); System.out.println(singleton.message); } // ler ficheiro .csv
            case 3 -> { return; }
        }
    }

    private void inserirAlunoManualmente() {
        int numAlunos = PAInput.readInt("\nQuantos alunos pretende criar? ");
        for (int i = 0 ; i < numAlunos; i++) {
            int numero = PAInput.readInt("\nNumero: ");
            String nome = PAInput.readString("Nome: ",false);
            String email = PAInput.readString("Email: ",true);
            String siglaCurso = getCurso();
            String siglaRamo = getRamo();
            double classif = getClassif();
            boolean estagio = getEstagio();
            System.out.println();


            // parametros de identificacao -> email e numero alunos (devem ser unicos)
            // verifica se o objeto a ser introduzido é unico
            fsm.verificaAluno(numero,nome,email,siglaCurso,siglaRamo,classif,estagio);
            System.out.println(singleton.message);
        }
    }

    private void modificarAluno() {
        int numAluno = PAInput.readInt("Qual o numero de aluno? ");
        switch(PAInput.chooseOption("Parametros a alterar: ","Nome","Sigla Curso","Sigla Ramo"
                ,"Classificacao","Estagio", "Sair")) {
            case 1 -> alunoSetNome(numAluno);
            case 2 -> alunoSetCurso(numAluno);
            case 3 -> alunoSetRamo(numAluno);
            case 4 -> alunoSetClassif(numAluno);
            case 5 -> alunoSetEstagio(numAluno);
            case 6 -> { return; }
        }
    }

    private void eliminarAluno() {
        long numero = PAInput.readInt("Qual o numero de aluno? ");
        fsm.eliminaAluno(numero);
        System.out.println(singleton.message);
    }

    private void alunoSetNome(int numero) {
        String nome = PAInput.readString("Novo nome: ",false);
        fsm.alunoSetNome(numero,nome);
        System.out.println(singleton.message);
    }

    private void alunoSetCurso(int numero) {
        String curso = getCurso();
        fsm.alunoSetCurso(numero,curso);
        System.out.println(singleton.message);
    }

    private void alunoSetRamo(int numero) {
        String ramo = getRamo();
        fsm.alunoSetRamo(numero,ramo);
        System.out.println(singleton.message);
    }

    private void alunoSetClassif(int numero) {
        double classif = getClassif();
        fsm.alunoSetClassif(numero,classif);
        System.out.println(singleton.message);
    }

    private void alunoSetEstagio(int numero) {
        boolean estagio = getEstagio();
        fsm.alunoSetEstagio(numero,estagio);
        System.out.println(singleton.message);
    }

    //==================== CONFIGURACAO > GESTAO DE DOCENTES ====================
    private void gestaoDocentes() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Docentes ========", "Inserir",
                "Consultar","Modificar","Eliminar","Sair")) {
            case 1 -> inserirDocente();   // inserir
            case 2 -> { fsm.consultarDocentes(); System.out.println(singleton.message); }
            case 3 -> modificarDocente();   // modificar
            case 4 -> eliminarDocente();   // eliminar
            case 5 -> { return; }   // return
            default -> System.out.println("\nOpcao invalida!\n");
        }
    }

    private void inserirDocente() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Docentes > Inserir ========",
                "Inserir manualmente", "Ler .csv", "Sair")) {
            case 1 -> inserirDocenteManualmente();   // inserir manualmente
            case 2 -> { fsm.lerCSVDocente("docentes"); System.out.println(singleton.message);} // ler ficheiro .csv
            case 3 -> { return; }
            default -> System.out.println("\nOpcao invalida!\n");
        }
    }

    private void inserirDocenteManualmente() {
        int numDocentes = PAInput.readInt("Quantos docentes pretende criar? ");
        for (int i = 0 ; i < numDocentes; i++) {
            String nome = PAInput.readString("\nNome: ",false);
            String email = PAInput.readString("Email: ",true);
            System.out.println();

            // parametros de identificacao -> email (deve ser unico)
            fsm.verificaDocente(nome,email);
            System.out.println(singleton.message);
        }
    }

    private void modificarDocente() {
        String email = PAInput.readString("Qual o email do docente? ",true);
        String nome = PAInput.readString("Qual o novo nome para o docente? ",false);
        fsm.docenteSetNome(email,nome);
        System.out.println(singleton.message);
    }

    private void eliminarDocente() {
        String email = PAInput.readString("Qual o email do docente? ",true);
        fsm.eliminaDocente(email);
        System.out.println(singleton.message);
    }

    //==================== CONFIGURACAO > GESTAO DE PROPOSTAS ====================
    private void gestaoPropostas() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Propostas ========", "Inserir",
                "Consultar","Modificar","Eliminar","Sair")) {
            case 1 -> inserirProposta();   // inserir
            case 2 -> { fsm.consultarPropostas(); System.out.println(singleton.message);}   // consultar
            case 3 -> modificarProposta();   // modificar
            case 4 -> eliminarProposta();   // eliminar
            case 5 -> { return; }   // return
            default -> finish = true;
        }
    }

    private void inserirProposta() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Propostas > Inserir ========",
                "Inserir manualmente", "Ler .csv", "Sair")) {
            case 1 -> tipoDeProposta();   // inserir manualmente
            //case 2 -> fsm.lerCSV(PAInput.readString("\nNome do ficheiro: ",true),"Proposta"); // ler ficheiro .csv
            case 2 -> { fsm.lerCSVProposta("propostas"); System.out.println(singleton.message); } // ler ficheiro .csv
            case 3 -> { return; }
            default -> finish = true;
        }
    }

    private void tipoDeProposta() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Propostas > Inserir > Tipo de Proposta  ========",
                "Estagio","Projeto","Autoproposto","Sair")) {
            case 1 -> inserirEstagio();
            case 2 -> inserirProjeto();
            case 3 -> inserirAuto();
            case 4 -> { return; }
            default -> finish = true;
        }
    }

    private void inserirEstagio() {
        int numPropostas = PAInput.readInt("Quantos estagios pretende criar? ");
        for (int i = 0 ; i < numPropostas; i++) {
            String codigo = PAInput.readString("\nCodigo: ",true);
            String titulo = PAInput.readString("Titulo: ",false);
            String areaDestino = PAInput.readString("Area de destino: ",false);
            String entAcolhimento = PAInput.readString("Entidade de acolhimento: ",false);
            long numAluno = PAInput.readInt("Aluno atribuido (numero): ");

            fsm.verificaEstagio(codigo, titulo, areaDestino, entAcolhimento, numAluno);
            System.out.println(singleton.message);
        }
    }

    private void inserirProjeto() {
        int numPropostas = PAInput.readInt("Quantos projetos pretende criar? ");
        for (int i = 0 ; i < numPropostas; i++) {
            String codigo = PAInput.readString("\nCodigo: ",true);
            String titulo = PAInput.readString("Titulo: ",false);
            String ramoDestino = getRamo();
            String emailDocente = PAInput.readString("Docente (email): ",true);
            long numAluno = PAInput.readInt("Aluno atribuido (numero): ");

            if (!fsm.verificaEmail(emailDocente)) {
                System.out.println(singleton.message);
                return;
            }

            // parametros de identificacao -> codigo (deve ser unico)
            fsm.verificaProjeto(codigo,titulo,ramoDestino,emailDocente,numAluno);
            System.out.println(singleton.message);
        }
    }

    private void inserirAuto() {
        int numPropostas = PAInput.readInt("Quantos estagios/projetos auto-propostos pretende criar? ");
        for (int i = 0 ; i < numPropostas; i++) {
            String codigo = PAInput.readString("\nCodigo: ",true);
            String titulo = PAInput.readString("Titulo: ",false);
            long numAluno = PAInput.readInt("Aluno atribuido (numero): ");

            // parametros de identificacao -> codigo (deve ser unico)
            fsm.verificaAutoProp(codigo,titulo,numAluno);
            System.out.println(singleton.message);
        }
    }

    private void modificarProposta() {
        switch(PAInput.chooseOption("======== Configuracao > Gestao de Propostas > Modificar > Tipo de Proposta  ========",
                "Estagio","Projeto","Autoproposto","Sair")) {
            case 1 -> modificarEstagio();
            case 2 -> modificarProjeto();
            case 3 -> modificarAuto();
            case 4 -> { return; }
            default -> finish = true;
        }
    }

    private void modificarEstagio() {
        String codigo = PAInput.readString("Qual o codigo do estagio? ",true);
        switch(PAInput.chooseOption("Parametros a alterar: ","Titulo","Area de Destino",
                "Entidade de acolhimento","Aluno Atribuido","Sair")) {
            case 1 -> propostaSetTitulo(codigo);
            case 2 -> propostaSetArea(codigo);
            case 3 -> propostaSetEnt(codigo);
            case 4 -> propostaSetAluno(codigo);
            case 5 -> { return; }
            default -> finish = true;
        }
    }

    private void modificarProjeto() {
        String codigo = PAInput.readString("Qual o codigo do projeto? ",true);
        switch(PAInput.chooseOption("Parametros a alterar: ","Titulo","Area de Destino",
                "Entidade de acolhimento","Aluno Atribuido","Sair")) {
            case 1 -> propostaSetTitulo(codigo);
            case 2 -> propostaSetArea(codigo);
            case 3 -> propostaSetEnt(codigo);
            case 4 -> propostaSetAluno(codigo);
            case 5 -> { return; }
            default -> finish = true;
        }
    }

    private void modificarAuto() {
        String codigo = PAInput.readString("Qual o codigo do projeto autoproposto? ",true);
        switch(PAInput.chooseOption("Parametros a alterar: ","Titulo",
                "Aluno Atribuido","Sair")) {
            case 1 -> propostaSetTitulo(codigo);
            case 2 -> propostaSetAluno(codigo);
            case 3 -> { return; }
            default -> finish = true;
        }
    }

    private void propostaSetTitulo(String codigo) {
        String titulo = PAInput.readString("Qual o novo titulo? ",false);
        fsm.propostaSetTitulo(codigo,titulo);
        System.out.println(singleton.message);
    }

    private void propostaSetArea(String codigo) {
        String area = PAInput.readString("Qual a nova area de destino? ",false);
        fsm.propostaSetArea(codigo,area);
        System.out.println(singleton.message);
    }

    private void propostaSetEnt(String codigo) {
        String entidade = PAInput.readString("Qual a nova entidade de acolhimento? ",false);
        fsm.propostaSetEnt(codigo,entidade);
        System.out.println(singleton.message);
    }

    private void propostaSetAluno(String codigo) {
        long numero = PAInput.readInt("Qual o novo numero? ");
        fsm.propostaSetAluno(codigo,numero);
        System.out.println(singleton.message);
    }

    private void eliminarProposta() {
        String codigo = PAInput.readString("Qual o codigo da proposta? ",true);
        fsm.eliminaProposta(codigo);
        System.out.println(singleton.message);
    }

    //==================== CANDIDATURA > INSERIR/CONSULTAR/MODIFICAR/ELIMINAR ====================
    private void inserirCandidatura() {
        switch(PAInput.chooseOption("======== Candidatura > Inserir ========",
                "Inserir manualmente", "Ler .csv","Sair")) {
            case 1 -> inserirCandidaturaManual();   // inserir manualmente
            case 2 -> fsm.lerCSVCandidatura("candidaturas"); // ler ficheiro .csv
            case 3 -> { return; }
            default -> finish = true;
        }
    }

    private void inserirCandidaturaManual() {
        int numCandidaturas = PAInput.readInt("\nQuantas candidaturas pretende criar? ");
        for (int i = 0 ; i < numCandidaturas; i++) {
            long numero = PAInput.readInt("Numero aluno: ");
            String codigo;
            ArrayList<String> codigos = new ArrayList<>();
            do {
                codigo = PAInput.readString("Codigo ('fim' para sair): ",true);
                if (!codigo.equalsIgnoreCase("fim"))
                    codigos.add(codigo);
            } while(!codigo.equalsIgnoreCase("fim"));

            fsm.verificaCandidatura(numero,codigos);
            System.out.println(singleton.message);
        }
    }

    private void consultarCandidatura() {
        switch(PAInput.chooseOption("======== Candidatura > Consultar Lista de Alunos ========", "Com Autoproposta","Com Candidatura Registada","Sem Candidatura Registada","Voltar")) {
            case 1 -> { fsm.consultarComAutoProp(); System.out.println(singleton.message); }
            case 2 -> { fsm.consultarComCandidatura(); System.out.println(singleton.message); }
            case 3 -> { fsm.consultarSemCandidatura(); System.out.println(singleton.message); }
            default -> { return; }
        }
    }

    private void consultarPropostaFiltros() {
        System.out.println("\n======== Candidatura > Consultar Lista de Propostas (Filtros) ========");
        Map<Integer,String> opcoes = new HashMap<>();
        opcoes.put(1,"1 - Autopropostas de Alunos");
        opcoes.put(2,"2 - Propostas de Docentes");
        opcoes.put(3,"3 - Propostas Com Candidaturas");
        opcoes.put(4,"4 - Propostas Sem Candidaturas");
        opcoes.put(5,"5 - Consultar");
        opcoes.put(6,"6 - Sair");

        Map<Integer,String> filtros = new HashMap<>();
        int op = 0;
        do {
            System.out.println("\nEscolher os filtros(0 ou mais): ");
            for (var o : opcoes.values())
                System.out.println(o);
            op = PAInput.readInt("Opcao: ");
            switch(op) {
                case 1:
                    opcoes.remove(1);
                    filtros.put(1,"Autopropostas de Alunos\n");
                    break;
                case 2:
                    opcoes.remove(2);
                    filtros.put(2,"Propostas de Docentes\n");
                    break;
                case 3:
                    opcoes.remove(3);
                    filtros.put(3,"Propostas com Candidaturas\n");
                    break;
                case 4:
                    opcoes.remove(4);
                    filtros.put(4,"Propostas Sem Candidaturas\n");
                    break;
                case 5:
                    System.out.println("\nFiltros usados: \n");
                    for (var f : filtros.values())
                        System.out.println(f);
                    // cria um array com as opcoes selecionadas
                    ArrayList<Integer> arr = new ArrayList<>();
                    if (!filtros.isEmpty())
                        for (var f : filtros.keySet())
                            arr.add(f);

                    // passa o array com as opcoes por parametro para ser avaliado
                    fsm.consultarFiltrosCandidatura(arr);
                    System.out.println(singleton.message);
                    return;
            }
        } while (op != 6);
    }

    private void modificarCandidatura() {
        Long numero = Long.valueOf(PAInput.readInt("Qual o numero de aluno? "));
        switch(PAInput.chooseOption("======== Candidatura > Modificar ========",
                "Adiciona Codigo","Remove Codigo","Sair")) {
            case 1 -> candidaturaAdicionaCodigo(numero);
            case 2 -> candidaturaRemoveCodigo(numero);
            case 3 -> { return; }
            default -> finish = true;
        }
    }

    private void candidaturaAdicionaCodigo(Long numero) {
        String codigo = PAInput.readString("\nCodigo da Proposta: ",true);
        fsm.adicionarCodigo(numero,codigo);
        System.out.println(singleton.message);
    }

    private void candidaturaRemoveCodigo(Long numero) {
        String codigo = PAInput.readString("\nCodigo da Proposta: ",true);
        fsm.removerCodigo(numero,codigo);
        System.out.println(singleton.message);
    }

    private void eliminarCandidatura() {
        Long numero = Long.valueOf(PAInput.readInt("Qual o numero de aluno? "));
        fsm.eliminaCandidatura(numero);
        System.out.println(singleton.message);
    }

    //==================== PROPOSTA > INSERIR/CONSULTAR/MODIFICAR/ELIMINAR ====================
    private void propostaAtribuicaoManual() {
        switch(PAInput.chooseOption("======== Proposta > Atribuicao/Remocao Manual ========", "Atribuicao Manual", "Remocao Manual"
                ,"Undo","Redo")) {
            case 1 -> { fsm.atribuicaoManual(
                            PAInput.readString("\nCodigo: ", true),
                            PAInput.readInt("Numero Aluno: "));
                        System.out.println(singleton.message); }
            case 2 -> { fsm.remocaoManual(
                            PAInput.readString("\nCodigo: ", true),
                            PAInput.readInt("Numero Aluno: "));
                        System.out.println(singleton.message); }
            case 3 -> fsm.undo();
            case 4 -> fsm.redo();
            case 5 -> { return; }
        }
    }

    private void consultarProposta() {
        switch(PAInput.chooseOption("======== Proposta > Consultar Lista de Alunos ========",
                "Com Autoproposta Associada","Com Candidatura Registada","Tem Proposta Atribuida",
                "Nao Tem Proposta Atribuida","Voltar")) {
            case 1 -> { fsm.consultarComAutoProp(); System.out.println(singleton.message); }
            case 2 -> { fsm.consultarAlunosRegistadas(); System.out.println(singleton.message); }
            case 3 -> { fsm.consultarAlunosAtribuidas(); System.out.println(singleton.message); }
            case 4 -> { fsm.consultarAlunosNaoAtribuidas(); System.out.println(singleton.message); }
            default -> { return; }
        }
    }

    private void consultarAtribuicaoProposta() {
        System.out.println("\n======== Proposta > Consultar Lista de Propostas (Filtros) ========");
        Map<Integer,String> opcoes = new HashMap<>();
        opcoes.put(1,"1 - Autopropostas de Alunos");
        opcoes.put(2,"2 - Propostas de Docentes");
        opcoes.put(3,"3 - Propostas Disponiveis");
        opcoes.put(4,"4 - Propostas Atribuidas");
        opcoes.put(5,"5 - Consultar");
        opcoes.put(6,"6 - Sair");

        Map<Integer,String> filtros = new HashMap<>();
        int op = 0;
        do {
            System.out.println("\nEscolher os filtros(0 ou mais): ");
            for (var o : opcoes.values())
                System.out.println(o);
            op = PAInput.readInt("Opcao: ");
            switch(op) {
                case 1:
                    opcoes.remove(1);
                    filtros.put(1,"Autopropostas de Alunos\n");
                    break;
                case 2:
                    opcoes.remove(2);
                    filtros.put(2,"Propostas de Docentes\n");
                    break;
                case 3:
                    opcoes.remove(3);
                    filtros.put(3,"Propostas Disponiveis\n");
                    break;
                case 4:
                    opcoes.remove(4);
                    filtros.put(4,"Propostas Atribuidas\n");
                    break;
                case 5:
                    System.out.println("\nFiltros usados: \n");
                    for (var f : filtros.values())
                        System.out.println(f);
                    // cria um array com as opcoes selecionadas
                    ArrayList<Integer> arr = new ArrayList<>();
                    if (filtros.isEmpty()) {
                        Integer []ops = new Integer[] {1,2,3,4};
                        Collections.addAll(arr,ops);
                    } else
                        for (var f : filtros.keySet())
                            arr.add(f);
                    // passa o array com as opcoes por parametro para ser avaliado
                    fsm.consultarFiltrosPropostas(arr);
                    System.out.println(singleton.message);
                    return;
            }
        } while (op != 6);
    }

    //==================== ORIENTADORES > INSERIR/CONSULTAR/MODIFICAR/ELIMINAR ====================
    private void consultarDocente() {
        switch(PAInput.chooseOption("======== Orientadores > Consultar Docente ========",
                "Consultar Propostas com Docente Associado","Alterar Docente da Proposta","Voltar")) {
            case 1 -> consultarDocenteProp();
            case 2 -> alterarDocenteProp();
            case 3 -> { return; }
        }
    }

    private void consultarDocenteProp() {
        String email = PAInput.readString("Email do docente a consultar: ", true);
        fsm.consultarDocenteProp(email);
        System.out.println(singleton.message);
    }

    private void alterarDocenteProp() {
        String email = PAInput.readString("Novo email do docente: ", true);
        String codigo = PAInput.readString("Codigo da proposta: ", true);
        fsm.alterarDocenteProp(email,codigo);
        System.out.println(singleton.message);
    }

    private void consultarPropostasDocente() {
        switch(PAInput.chooseOption("======== Orientadores ========", "Alunos com Proposta e Docente Associado",
                "Alunos com Proposta e Sem Docente Associado","Numero de Orientacoes por Docente","Voltar")) {
            case 1 -> { fsm.consultarAlunosPropDocente(); System.out.println(singleton.message); }
            case 2 -> { fsm.consultarAlunosPropSemDocente(); System.out.println(singleton.message); }
            case 3 -> { fsm.consultarOrientacoes(); System.out.println(singleton.message); }
            case 4 -> { return; }
        }
    }

    private void orientadoresAtribuicaoManual() {
        switch(PAInput.chooseOption("======== Orientadores > Atribuicao/Remocao Manual ========", "Atribuicao Manual", "Remocao Manual"
                ,"Undo","Redo","Voltar")) {
            case 1 -> { fsm.atribuicaoManualDocente(
                            PAInput.readString("\nCodigo: ", true),
                            PAInput.readString("Email Docente: ",true));
                        System.out.println(singleton.message); }
            case 2 -> { fsm.remocaoManualDocente(
                            PAInput.readString("\nCodigo: ", true),
                            PAInput.readString("Email Docente: ", true));
                        System.out.println(singleton.message); }
            case 3 -> fsm.undo();
            case 4 -> fsm.redo();
            case 5 -> { return; }
        }
    }

    //========== Metodos get para comparar os inputs do utilizador
    private String getCurso() {
        String siglaCurso;
        do {
            siglaCurso = PAInput.readString("Sigla curso (LEI ou LEI-PL): ",true);
        } while(!siglaCurso.equalsIgnoreCase("LEI") && !siglaCurso.equalsIgnoreCase("LEI-PL"));
        return siglaCurso.toUpperCase();
    }

    private String getRamo() {
        String siglaRamo;
        do {
            siglaRamo = PAInput.readString("Sigla ramo (DA, RAS ou SI): ",true);
        } while(!siglaRamo.equalsIgnoreCase("DA") && !siglaRamo.equalsIgnoreCase("RAS") && !siglaRamo.equalsIgnoreCase("SI"));
        return siglaRamo.toUpperCase();
    }

    private double getClassif() {
        double classificacao = 0.0;
        do {
            classificacao = PAInput.readNumber("Classificacao (0,0 a 1,0): ");
        } while(classificacao < 0.0 || classificacao > 1.0);
        return  classificacao;
    }

    private boolean getEstagio() {
        String op;
        boolean estagio = false;
        do {
            op = PAInput.readString("Estagio (true/false): ",true);
            switch(op.toUpperCase()) {
                case "TRUE" -> estagio = true;
            }
        } while(!op.equalsIgnoreCase("TRUE") && !op.equalsIgnoreCase("FALSE"));
        return  estagio;
    }

    public Integer escolherAluno(String menssagem) {
        System.out.println(menssagem);
        int op = 0;
        do
            op = PAInput.readInt("Qual escolhe? ");
        while (op != 1 && op != 2);
        return op;
    }
}