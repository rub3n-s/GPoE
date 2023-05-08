package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.fsm.ManagementState;
import pt.isec.pa.apoio_poe.ui.utils.PAInput;

import java.io.*;
import java.util.*;

public class ManagementData implements Serializable {
    private ArrayList<Aluno> alunos;
    private ArrayList<Docente> docentes;
    private ArrayList<Proposta> propostas;  // estagio, projeto, autoproposto
    private ArrayList<Candidatura> candidaturas;

    static final long serialVersionUID = 1L;

    ArrayList<Proposta> propostasAtribuidas;

    Singleton singleton;

    // Fecho das fases
    private boolean configuracaoFechada;
    private boolean candidaturaFechada;
    private boolean propostaFechada;
    private boolean orientadoresFechada;
    private boolean consultaFechada;

    HashMap<Proposta,HashMap<Aluno,Aluno>> escolherAlunosProposta;
    HashMap<Aluno,Aluno> escolherAlunos;
    boolean terminouAtribuicao;

    public ManagementData() {
        initManagement();
    }

    private void initManagement() { // metodo privado para inicializacao das listas
        alunos = new ArrayList<>();
        docentes = new ArrayList<>();
        propostas = new ArrayList<>();
        candidaturas = new ArrayList<>();

        propostasAtribuidas = new ArrayList<>();

        configuracaoFechada = false;
        candidaturaFechada = false;
        propostaFechada = false;
        orientadoresFechada = false;
        consultaFechada = false;

        singleton = Singleton.Singleton();

        terminouAtribuicao = false;
        escolherAlunosProposta = new HashMap<>();
        escolherAlunos = new HashMap<>();
    }

    public List<Aluno> getAlunos() {
        List<Aluno> lst = new ArrayList<>();
        for(Aluno aluno : alunos) {
            try {
                lst.add((Aluno)aluno.clone());
            } catch (CloneNotSupportedException e) {
            }
        }
        return lst;
    }

    public List<Docente> getDocentes() {
        List<Docente> lst = new ArrayList<>();
        for(Docente docente : docentes) {
            try {
                lst.add((Docente) docente.clone());
            } catch (CloneNotSupportedException e) {
            }
        }
        return lst;
    }

    public List<Proposta> getPropostas() {
        List<Proposta> lst = new ArrayList<>();
        for(Proposta proposta : propostas) {
            try {
                lst.add((Proposta)proposta.clone());
            } catch (CloneNotSupportedException e) {
            }
        }
        return lst;
    }

    public List<Candidatura> getCandidaturas() {
        List<Candidatura> lst = new ArrayList<>();
        for(Candidatura candidatura : candidaturas) {
            try {
                lst.add((Candidatura) candidatura.clone());
            } catch (CloneNotSupportedException e) {
            }
        }
        return lst;
    }

    public List<Proposta> getPropostasAtribuidas() {
        List<Proposta> lst = new ArrayList<>();
        for(Proposta proposta : propostasAtribuidas) {
            try {
                lst.add((Proposta) proposta.clone());
            } catch (CloneNotSupportedException e) {
            }
        }
        return lst;
    }

    /* getters (listas)*/
    //public ArrayList<Aluno> getAlunos() {  return alunos; }  // devolve a lista de alunos
    //public ArrayList<Docente> getDocentes() { return docentes; }    // devolve a lista de docentes
    //public ArrayList<Proposta> getPropostas() { return propostas; }    // devolve a lista de estagios
    //public ArrayList<Candidatura> getCandidaturas() { return candidaturas; }    // devolve a lista de candidaturas
    //public ArrayList<Proposta> getPropostasAtribuidas() { return propostasAtribuidas; }    // devolve a lista de propostas atribuidas

    /* setters (listas) para operacao 'load' */
    public void setAlunos(ArrayList<Aluno> alunos) {  this.alunos = alunos; }  // carrega a lista de alunos
    public void setDocentes(ArrayList<Docente> docentes) { this.docentes = docentes; }    // carrega a lista de docentes
    public void setPropostas(ArrayList<Proposta> propostas) { this.propostas = propostas; }    // carrega a lista de estagios
    public void setCandidaturas(ArrayList<Candidatura> candidaturas) { this.candidaturas = candidaturas; }    // carrega a lista de candidaturas
    public void setPropostasAtribuidas(ArrayList<Proposta> propostasAtribuidas) { this.propostasAtribuidas = propostasAtribuidas; }    // carrega a lista de candidaturas

    public boolean isConfiguracaoFechada() { return configuracaoFechada; }
    public boolean isCandidaturaFechada() { return candidaturaFechada; }
    public boolean isPropostaFechada() { return propostaFechada; }
    public boolean isOrientadoresFechada() { return orientadoresFechada; }
    public boolean isConsultaFechada() { return consultaFechada; }

    public void setConfiguracaoFechada(boolean configuracaoFechada) { this.configuracaoFechada = configuracaoFechada; }
    public void setCandidaturaFechada(boolean candidaturaFechada) { this.candidaturaFechada = candidaturaFechada; }
    public void setPropostaFechada(boolean propostaFechada) { this.propostaFechada = propostaFechada; }
    public void setOrientadoresFechada(boolean orientadoresFechada) { this.orientadoresFechada = orientadoresFechada; }
    public void setConsultaFechada(boolean consultaFechada) { this.consultaFechada = consultaFechada; }

    /* getter e setter para o estado da fase */
    public boolean isFechada(ManagementState state) {
        switch(state) {
            case CONFIGURACAO -> { return configuracaoFechada; }
            case CANDIDATURA -> { return candidaturaFechada; }
            case PROPOSTA -> { return propostaFechada; }
            case ORIENTADORES -> { return orientadoresFechada; }
        }
        return false;
    }

    public void fechaState(ManagementState state) {
        switch(state) {
            case CONFIGURACAO -> configuracaoFechada = true;
            case CANDIDATURA -> candidaturaFechada = true;
            case PROPOSTA -> propostaFechada = true;
            case ORIENTADORES -> orientadoresFechada = true;
        }
    }

    public boolean isTerminouAtribuicao() { return terminouAtribuicao; }

    public void setTerminouAtribuicao() { terminouAtribuicao = false; }

    /*============================== LEITURA/ESCRITA > CSV ==============================*/
    public boolean lerAluno(String nome_fich)  {
        /* verifica se o nome introduzido do ficheiro existe */
        File file = new File("./csv/" + nome_fich + ".csv");
        if (!file.exists()) {
            singleton.setMessage("\nO ficheiro não existe!");
            return false;
        }

        String line = "";
        String splitBy = ",";
        StringBuilder sb = new StringBuilder();
        try {
            /* abertura do ficheiro dentro do try catch porque pode gerar uma excepcao */
            BufferedReader br = new BufferedReader(new FileReader("./csv/" + nome_fich + ".csv"));

            while ((line = br.readLine()) != null) {
                String[] aluno = line.split(splitBy);    // usa virgula como separador

                /* verificar se a linha lida tem o numero de colunas necessarias para criar um objeto Aluno */
                if (aluno.length == 7) {
                    aluno[6] = aluno[6].toUpperCase();
                    if (aluno[6].equalsIgnoreCase("TRUE") || aluno[6].equalsIgnoreCase("FALSE")) {
                        verificaAluno(Long.parseLong(aluno[0]), aluno[1], aluno[2],
                                aluno[3], aluno[4], Double.parseDouble(aluno[5]), Boolean.parseBoolean(aluno[6])); // true -> insere o aluno
                    }
                }
            }
            singleton.setMessage("Ficheiro '" + nome_fich + ".csv' carregado com sucesso.");

            /* fechar o BufferedReader */
            br.close();

            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean lerDocente(String nome_fich) {
        /* verifica se o nome introduzido do ficheiro existe */
        File file = new File("./csv/" + nome_fich + ".csv");
        if (!file.exists()) {
            singleton.setMessage("\nO ficheiro não existe!");
            return false;
        }

        String line = "";
        String splitBy = ",";
        try {
            /* abertura do ficheiro dentro do try catch porque pode gerar uma excepcao */
            BufferedReader br = new BufferedReader(new FileReader("./csv/" + nome_fich + ".csv"));

            while ((line = br.readLine()) != null) {
                String[] docente = line.split(splitBy);    // usa virgula como separador

                /* verificar se a linha lida tem o numero de colunas necessarias para criar um objeto Docente */
                if (docente.length == 2)
                    verificaDocente(docente[0], docente[1]); // true -> insere o aluno
            }
            singleton.setMessage("Ficheiro '" + nome_fich + ".csv' carregado com sucesso.");

            /* fechar o BufferedReader */
            br.close();

            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean lerProposta(String nome_fich) {
        /* verifica se o nome introduzido do ficheiro existe */
        File file = new File("./csv/" + nome_fich + ".csv");
        if (!file.exists()) {
            singleton.setMessage("\nO ficheiro não existe!");
            return false;
        }

        String line = "";
        String splitBy = ",";
        long numTmp;    // numero temporario, usado para saber quando é lido o numero de aluno
        String strTmp;   // numero temporario, usado para saber quando é lido o numero de aluno
        try {
            /* abertura do ficheiro dentro do try catch porque pode gerar uma excepcao */
            BufferedReader br = new BufferedReader(new FileReader("./csv/" + nome_fich + ".csv"));
            while ((line = br.readLine()) != null) {
                numTmp = 0;  strTmp = null; // redefine os valores

                /* divide a linha usando a virgula como separador */
                String[] proposta = line.split(splitBy);
                switch (proposta[0]) {
                    case "T1":  // estagio
                        if (proposta.length < 5) return false;    // parametros insuficientes
                        if (proposta.length == 6)
                            numTmp = Long.parseLong(proposta[5]);
                        verificaEstagio(proposta[1], proposta[2], proposta[3], proposta[4], numTmp); // true -> insere estagio
                        break;
                    case "T2":  // projeto
                        if (proposta.length < 5) return false;    // parametros insuficientes
                        if (proposta.length == 6)    // numero aluno
                            numTmp = Long.parseLong(proposta[5]);
                        verificaProjeto(proposta[1], proposta[2], proposta[3], proposta[4], numTmp); // true -> insere projeto
                        break;
                    case "T3":  // autoproposto
                        if (proposta.length < 3) return false;    // parametros insuficientes
                        if (proposta.length == 4)
                            numTmp = Long.parseLong(proposta[3]);
                        verificaAutoProp(proposta[1], proposta[2], numTmp); // true -> insere autoproposto
                        break;
                }
            }
            singleton.setMessage("Ficheiro '" + nome_fich + ".csv' carregado com sucesso.");

            /* fechar o BufferedReader */
            br.close();

            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean lerCandidatura(String nome_fich) {
        /* verifica se o nome introduzido do ficheiro existe */
        File file = new File("./csv/" + nome_fich + ".csv");
        if (!file.exists()) {
            singleton.setMessage("\nO ficheiro não existe!");
            return false;
        }

        String line = "";
        String splitBy = ",";
        ArrayList<String> codigos;

        if (alunos.isEmpty()) { // nao existem alunos para poder candidatar
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        }
        else if (propostas.isEmpty()) { // nao existem propostas para se poder candidatar
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }

        try {
            /* abertura do ficheiro dentro do try catch porque pode gerar uma excepcao */
            BufferedReader br = new BufferedReader(new FileReader("./csv/" + nome_fich + ".csv"));

            while ((line = br.readLine()) != null) {
                String[] candidatura = line.split(splitBy);    // usa virgula como separador

                /* verificar se a linha lida tem o numero de colunas necessarias para criar um objeto Aluno */
                if (candidatura.length >= 2) {
                    codigos = new ArrayList<>();
                    for (int i = 1; i < candidatura.length; i++)
                        codigos.add(candidatura[i]);
                    /* converter os valores lidos como string */
                    verificaCandidatura(Long.parseLong(candidatura[0]),codigos);    // true -> insere a candidatura
                }
            }
            singleton.setMessage("Ficheiro '" + nome_fich + ".csv' carregado com sucesso.");

            /* fechar o BufferedReader */
            br.close();

            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exportarFase1() {
        File file = new File("./csv/fase1.csv");
        if (file.exists()) {
            file.delete();
            file = new File("./csv/fase1.csv");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write("Alunos:\n");
            if (!alunos.isEmpty())
                for (var a : alunos)
                    bw.write(a.toString());
            else
                bw.write("Lista de alunos vazia.\n");

            bw.write("\nDocentes:\n");
            if (!docentes.isEmpty())
                for (var d : docentes)
                    bw.write(d.toString());
            else
                bw.write("Lista de docentes vazia.\n");

            bw.write("\nPropostas:\n");
            if (!propostas.isEmpty())
                for (var p : propostas)
                    bw.write(p.toString());
            else
                bw.write("Lista de propostas vazia.\n");

            singleton.setMessage("Escreveu para o ficheiro 'fase1.csv' com sucesso.");
            /* fechar o BufferedWriter */
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean exportarFase2() {
        File file = new File("./csv/fase2.csv");
        if (file.exists()) {
            file.delete();  /* se o ficheiro já existir -> elimina e cria um novo ficheiro */
            file = new File("./csv/fase2.csv");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write("Candidaturas:\n");
            if (!candidaturas.isEmpty())
                for (var c : candidaturas)
                    bw.write(c.toString());
            else
                bw.write("Lista de candidaturas vazia.\n");

            singleton.setMessage("Escreveu para o ficheiro 'fase2.csv' com sucesso.");
            /* fechar o BufferedWriter */
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean exportarFase3() {
        File file = new File("./csv/fase3.csv");
        if (file.exists()) {    /* se o ficheiro já existir -> elimina e cria um novo ficheiro */
            file.delete();
            file = new File("./csv/fase3.csv");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            boolean candidatura = false;
            boolean atribuida = false;

            if (alunos.isEmpty()) {
                bw.write("Lista de alunos vazia.\n");
                return true;
            }

            for (var a : alunos) {
                bw.write("Aluno: " + a.toString());
                for (var c : candidaturas) {
                    if (c.getAlunoAtribuido() == a.getNumero()) {
                        bw.write("Opcoes de Candidatura: " + c.toString());
                        candidatura = true;
                        break;
                    }
                }
                if (!candidatura)
                    bw.write("Opcoes de Candidatura: Sem candidatura.\n");
                candidatura = false;

                for (var p : propostasAtribuidas) {
                    if (p.getAlunoAtribuido() == a.getNumero()) {
                        bw.write("Proposta Atribuida: " + p.toString());
                        atribuida = true;
                        break;
                    }
                }
                if (!atribuida)
                    bw.write("Proposta Atribuida: Sem proposta atribuida.\n");
                atribuida = false;
                bw.write("\n");
            }
            singleton.setMessage("Escreveu para o ficheiro 'fase3.csv' com sucesso.");
            /* fechar o BufferedWriter */
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean exportarFase4e5() {
        File file = new File("./csv/fase4e5.csv");
        if (file.exists()) {    /* se o ficheiro já existir -> elimina e cria um novo ficheiro */
            file.delete();
            file = new File("./csv/fase4e5.csv");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            if (alunos.isEmpty()) // nao existem alunos para poder candidatar
                return false;
            else if (docentes.isEmpty())    // nao existem docentes para se poder candidatar
                return false;
            else if (propostas.isEmpty()) // nao existem propostas para se poder candidatar
                return false;

            boolean candidatura = false;
            boolean atribuida = false;
            int index = 0;

            for (var a : alunos) {
                bw.write("Aluno: " + a.toString());

                bw.write("Opcoes de candidatura: ");
                for (var c : candidaturas) {
                    if (c.getAlunoAtribuido() == a.getNumero()) {
                        candidatura = true;
                        bw.write("\n");
                        for (int i = 0; i < c.getCodigos().size(); i++)
                            bw.write("\t" + (i+1) +  " - " + c.getCodigos().get(i) + "\n");
                    }
                }
                if (!candidatura)
                    bw.write("Sem candidatura registada.\n");

                bw.write("Proposta atribuida: ");
                for (var p : propostasAtribuidas) {
                    if (p.getAlunoAtribuido() == a.getNumero()) {
                        atribuida = true;
                        // se tiver candidatura mostra qual a opcao
                        if (candidatura) {
                            for (var c : candidaturas) {
                                if (c.getAlunoAtribuido() == a.getNumero()) {
                                    for (int i = 0; i < c.getCodigos().size(); i++)
                                        if (c.getCodigos().get(i).equals(p.getCodigo())) {
                                            bw.write("Opcao(" + (i + 1) + ") > ");
                                            break;
                                        }
                                }
                            }
                        }
                        bw.write(p.toString());

                        if (p.getTipo().equalsIgnoreCase("T2")) {
                            bw.write("Docente: ");
                            if (p.getDocente() != null)
                                bw.write(p.getDocente() + "\n");
                            else
                                bw.write("Sem docente atribuido.\n");
                        }
                    }
                }
                if (!atribuida)
                    bw.write("Sem proposta atribuida.\n");
                candidatura = false;
                atribuida = false;
                bw.write("\n");
            }
            singleton.setMessage("Escreveu para o ficheiro 'fase4e5.csv' com sucesso.");
            /* fechar o BufferedWriter */
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /*==================== VERIFICACAO PARAMETROS > ALUNO/DOCENTE/PROPOSTA/CANDIDATURA ======================*/
    public boolean verificaAluno(long numero, String nome, String email, String curso, String ramo, Double classif, Boolean estagio) {
        // alunos -> numero e email (valores unicos)
        Aluno aluno = new Aluno(numero,nome,email,curso,ramo,classif,estagio);

        if (aluno.getNumero() < 0 || aluno.getNome() == null || aluno.getEmail() == null)
            return false;
        if (!alunos.isEmpty()) {
            for (var a : alunos) {
                if (a.getEmail().equals(aluno.getEmail()) || a.getNumero() == aluno.getNumero()) {
                    singleton.setMessage("Ja existe um aluno com o numero '" + aluno.getNumero() + "' ou email '" +
                            aluno.getEmail() + "'.");
                    return false;
                }
            }
        }
        // previne a introdução de ramos e cursos incorretos
        if ((!aluno.getSiglaRamo().equalsIgnoreCase("RAS") && !aluno.getSiglaRamo().equalsIgnoreCase("SI")
                && !aluno.getSiglaRamo().equalsIgnoreCase("DA"))
            || (!aluno.getSiglaCurso().equalsIgnoreCase("LEI") && !aluno.getSiglaCurso().equalsIgnoreCase("LEI-PL"))
            || (aluno.getClassificacao() < 0.0 || aluno.getClassificacao() > 1.0)) {
            singleton.setMessage("Ramo (DA,SI,RAS) ou Curso (LEI ou LEI-PL) inserido incorretamente.");
            return false;
        }

        inserir(aluno); //  se os dados de aluno forem validados é inserido no sistema
        singleton.setMessage("Aluno com o numero '" + aluno.getNumero() + "' inserido com sucesso.");
        return true;
    }

    public boolean verificaDocente(String nome, String email) {  // docentes -> email (valor unico)
        Docente docente = new Docente(nome,email);

        if (docente.getNome() == null || docente.getEmail() == null)
            return false;
        if (!docentes.isEmpty()) {
            for (var a : docentes) {
                if (a.getEmail().equals(docente.getEmail())) {
                    singleton.setMessage("Ja existe um docente com o email '" + docente.getEmail() + "'.");
                    return false;
                }
            }
        }
        inserir(docente);   //  se os dados do docente forem validados é inserido no sistema
        singleton.setMessage("Docente com o email '" + docente.getEmail() + "' inserido com sucesso.");
        return true;
    }

    public boolean verificaEstagio(String codigo, String titulo, String ramo, String entidade, long numero) {
        if (codigo == null) {
            singleton.setMessage("Codigo nao pode ser 'null'");
            return false;
        } else if (titulo == null) {
            singleton.setMessage("Titulo nao pode ser 'null'");
            return false;
        } else if (ramo == null) {
            singleton.setMessage("Ramo nao pode ser 'null'");
            return false;
        } else if (entidade == null) {
            singleton.setMessage("Entidade nao pode ser 'null'");
            return false;
        }

        Estagio proposta = new Estagio("T1",codigo, titulo, ramo, entidade, numero);

        // verifica se o numero de aluno foi inserido corretamente
        if (proposta.getAlunoAtribuido() > 0) {
            boolean aluno = false;
            for (var a : alunos)
                if (a.getNumero() == proposta.getAlunoAtribuido())
                    aluno = true;
            if (!aluno) {
                singleton.setMessage("Nao existe nenhum aluno com o numero '" + proposta.getAlunoAtribuido() + "'.");
                return false;
            }
        }

        // verifica se a proposta ja existe ou ja foi atribuida
        if (!propostas.isEmpty()) {
            for (var p : propostas) {
                if (p.getCodigo().equals(proposta.getCodigo()) || p.getAlunoAtribuido() == proposta.getAlunoAtribuido() && p.getAlunoAtribuido() != 0) {
                    singleton.setMessage("Ja existe uma proposta com o codigo'" + proposta.getCodigo() + "'.");
                    return false;
                }
            }
        }

        /* verifica quando há mais do que uma ramo separado por "|" */
        String[] ramos = proposta.getRamoDestino().split("\\|+");
        if (ramos.length <= 0 || ramos.length > 3)
            return false;

        // verifica se os ramos foram inseridos corretamente
        for (var r : ramos)
            if (!r.equalsIgnoreCase("DA") && !r.equalsIgnoreCase("RAS") && !r.equalsIgnoreCase("SI"))
                return false;

        inserir(proposta);  /*  se os dados da proposta forem validados é inserido no sistema   */
        singleton.setMessage("Proposta com o codigo '" + proposta.getCodigo() + "' inserida com sucesso.");
        return true;
    }

    public boolean verificaProjeto(String codigo, String titulo, String ramo, String emailDocente, long numero) {
        if (codigo == null) {
            singleton.setMessage("Codigo nao pode ser 'null'");
            return false;
        } else if (titulo == null) {
            singleton.setMessage("Titulo nao pode ser 'null'");
            return false;
        } else if (ramo == null) {
            singleton.setMessage("Titulo nao pode ser 'null'");
            return false;
        }

        Projeto proposta = new Projeto("T2",codigo, titulo, ramo, emailDocente, numero);

        // verifica se o numero de aluno foi inserido corretamente
        if (proposta.getAlunoAtribuido() > 0) {
            boolean aluno = false;
            for (var a : alunos)
                if (a.getNumero() == proposta.getAlunoAtribuido())
                    aluno = true;
            if (!aluno) {
                singleton.setMessage("Nao existe nenhum aluno com o numero '" + proposta.getAlunoAtribuido() + "'.");
                return false;
            }
        }

        // verifica se a proposta ja existe ou ja foi atribuida
        if (!propostas.isEmpty()) {
            for (var p : propostas) {
                if (p.getCodigo().equals(proposta.getCodigo()) || p.getAlunoAtribuido() == proposta.getAlunoAtribuido() && p.getAlunoAtribuido() != 0) {
                    singleton.setMessage("Ja existe uma proposta com o codigo'" + proposta.getCodigo() + "'.");
                    return false;
                }
            }
        }

        // verifica se o email de docente foi inserido corretamente
        if (proposta.getDocente() != null) {
            boolean docente = false;
            for (var d : docentes)
                if (proposta.getDocente().equals(d.getEmail()))
                    docente = true;
            if (!docente) {
                singleton.setMessage("Nao existe nenhum docente com o email '" + proposta.getDocente() + "'.");
                return false;
            }
        }

        /* verifica quando há mais do que uma ramo separado por "|" */
        String[] ramos = proposta.getRamoDestino().split("\\|+");
        if (ramos.length <= 0 || ramos.length > 3)
            return false;

        // verifica se os ramos foram inseridos corretamente
        for (var r : ramos)
            if (!r.equalsIgnoreCase("DA") && !r.equalsIgnoreCase("RAS") && !r.equalsIgnoreCase("SI"))
                return false;

        inserir(proposta);  /*  se os dados da proposta forem validados é inserido no sistema   */
        singleton.setMessage("Proposta com o codigo '" + proposta.getCodigo() + "' inserida com sucesso.");
        return true;
    }

    public boolean verificaAutoProp(String codigo, String titulo, long numero) {
        if (codigo == null) {
            singleton.setMessage("Codigo nao pode ser 'null'");
            return false;
        } else if (titulo == null) {
            singleton.setMessage("Titulo nao pode ser 'null'");
            return false;
        }

        Autoproposto proposta = new Autoproposto("T3",codigo,titulo,numero);

        // verifica se o numero de aluno foi inserido corretamente
        boolean aluno = false;
        for (var a : alunos)
            if (a.getNumero() == proposta.getAlunoAtribuido())
                aluno = true;
        if (!aluno) {
            singleton.setMessage("Nao existe nenhum aluno com o numero '" + proposta.getAlunoAtribuido() + "'.");
            return false;
        }

        if (!propostas.isEmpty()) {
            for (var p : propostas) {
                if (p.getCodigo().equals(proposta.getCodigo()) || p.getAlunoAtribuido() == proposta.getAlunoAtribuido() && p.getAlunoAtribuido() != 0) {
                    singleton.setMessage("Ja existe uma proposta com o codigo'" + proposta.getCodigo() + "'.");
                    return false;
                }
            }
        }

        inserir(proposta);  /*  se os dados da proposta forem validados é inserido no sistema   */
        singleton.setMessage("Proposta com o codigo '" + proposta.getCodigo() + "' inserida com sucesso.");
        return true;
    }

    public boolean verificaCandidatura(long numero,ArrayList<String> codigos) {
        Candidatura candidatura = new Candidatura(numero,codigos);

        if (!verificaNumero(candidatura.getAlunoAtribuido())) // verifica se a lista de alunos esta vazia e se o numero existe
            return false;
        if (candidatura.getCodigos() == null) {
            singleton.setMessage("Lista de codigos nao pode estar vazia.");
            return false;
        }

        // o aluno ja tem candidatura registada
        for (var c : candidaturas)
            if (candidatura.getAlunoAtribuido() == c.getAlunoAtribuido())
                return false;

        // o aluno ja tem proposta atribuida
        for (var p : propostas) {
            if (p.getAlunoAtribuido() == candidatura.getAlunoAtribuido()) {
                singleton.setMessage("O aluno '" + candidatura.getAlunoAtribuido() + "' ja tem uma proposta atribuida.");
                return false;
            }
        }

        // verifica se a proposta existe e ja tem um aluno atribuido
        boolean existeProposta = false;
        boolean alunoAtribuido = false;
        for (var c : candidatura.getCodigos()) {
            for (var p : propostas) {
                if (c.equals(p.getCodigo()))
                    existeProposta = true;
                if (c.equals(p.getCodigo()) && p.getAlunoAtribuido() != 0) {
                    alunoAtribuido = true;
                    break;
                }
            }
            if (!existeProposta) {
                singleton.setMessage("Nao existe nenhuma proposta com o codigo '" + c + "'.");
                return false;
            }
            else if (alunoAtribuido) {
                singleton.setMessage("A proposta com o codigo '" + c + "' ja foi atribuida.");
                return false;
            }
            existeProposta = false;
            alunoAtribuido = false;
        }

        inserir(candidatura);   /*  se os dados da proposta forem validados é inserido no sistema   */
        singleton.setMessage("Candidatura do aluno '" + candidatura.getAlunoAtribuido() + "' inserida com sucesso.");
        return true;
    }

    /* metodo privado para insercao de alunos, docentes e propostas */
    private void inserir(Object obj) {
        switch(obj.getClass().getSimpleName()) {
            case "Aluno" -> alunos.add((Aluno) obj);
            case "Docente" -> docentes.add((Docente) obj);
            case "Estagio" -> propostas.add((Estagio) obj);
            case "Projeto" -> propostas.add((Projeto) obj);
            case "Autoproposto" -> propostas.add((Autoproposto) obj);
            case "Candidatura" -> candidaturas.add((Candidatura) obj);
        }
    }

    /*============================== CONSULTA > LISTAS ==============================*/
    public boolean consultarAlunos() {
        StringBuilder sb = new StringBuilder();
        if (alunos.isEmpty()) {
            sb.append("Vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        }
        for (var a : alunos)
           sb.append(a.toString());
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarDocentes() {
        StringBuilder sb = new StringBuilder();
        if (docentes.isEmpty()) {
            sb.append("Vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        }
        for (var d : docentes)
            sb.append(d.toString());
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarPropostas() {
        StringBuilder sb = new StringBuilder();
        if (propostas.isEmpty()) {
            sb.append("Vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        }
        for (var p : propostas)
            sb.append(p.toString());
        singleton.setMessage(sb.toString());
        return true;
    }

    //===== 'CANDIDATURAS' > LISTAGEM DE ALUNOS
    public boolean consultarComAutoProp() {
        boolean flg = false;
        StringBuilder sb = new StringBuilder();
        if (alunos.isEmpty()) {
            sb.append("Lista de alunos vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        } else if (candidaturas.isEmpty()) {
            sb.append("Lista de candidaturas vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        } else if (propostas.isEmpty()) {
            sb.append("Lista de propostas vazia.\n");
            singleton.setMessage(sb.toString());
            return false;
        }
        ArrayList<Long> numerosAluno = new ArrayList<>();
        for (var p : propostas) {
            for (var c : candidaturas) {
                for (var cod : c.getCodigos()) {
                    if (p.getCodigo().equals(cod) && p.getTipo().equalsIgnoreCase("T3")
                            && !numerosAluno.contains(c.getAlunoAtribuido()))
                        numerosAluno.add(c.getAlunoAtribuido());
                }
            }
            if (p.getTipo().equalsIgnoreCase("T3") && p.getAlunoAtribuido() > 0 &&
                    !numerosAluno.contains(p.getAlunoAtribuido()))
                numerosAluno.add(p.getAlunoAtribuido());
        }
        for (var a : alunos) {
            if (numerosAluno.contains(a.getNumero())) {
                sb.append(a.toString());
                flg = true;
            }
        }
        if (!flg) sb.append("Nao ha alunos com autoproposta.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarComCandidatura() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        } else if (candidaturas.isEmpty()) {
            singleton.setMessage("Lista de candidaturas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        for (var a : alunos) {
            for (var c : candidaturas)
                if (a.getNumero() == c.getAlunoAtribuido()) {
                    sb.append(a.toString());
                    flg = true;
                }
        }
        if (!flg) sb.append("Nao ha alunos com candidatura.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarSemCandidatura() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        } else if (candidaturas.isEmpty()) {
            singleton.setMessage("Lista de candidaturas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean temCandidatura = false;
        boolean flg = false;
        for (var a : alunos) {
            for (var c : candidaturas)
                if (a.getNumero() == c.getAlunoAtribuido())
                    temCandidatura = true;
            if (!temCandidatura) {
                sb.append(a.toString());
                flg = true;
            }
            temCandidatura = false;
        }
        if (!flg) sb.append("Nao ha alunos sem candidatura.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarAlunosAuto() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        } else if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean atribuidas = false;
        for (var a : alunos) {
            for (var p : propostas)
                if (a.getNumero() == p.getAlunoAtribuido() && p.getTipo().equalsIgnoreCase("T3")) {
                    sb.append(a.toString());
                    atribuidas = true;
                }
        }
        if (!atribuidas) sb.append("Nao ha autopropostas associadas.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    //========= 'PROPOSTAS' > LISTAGEM DE ALUNOS
    public boolean consultarAlunosRegistadas() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        } else if (candidaturas.isEmpty()) {
            singleton.setMessage("Lista de candidaturas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        for (var a : alunos) {
            for (var c : candidaturas)
                if (a.getNumero() == c.getAlunoAtribuido()) {
                    sb.append(a.toString());
                    flg = true;
                }
        }
        if (!flg) sb.append("Nao ha alunos com candidatura registada.");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarAlunosAtribuidas() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        } else if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        for (var a : alunos) {
            for (var p : propostasAtribuidas)
                if (a.getNumero() == p.getAlunoAtribuido()) {
                    sb.append(a.toString());
                    flg = true;
                }
        }
        if (!flg) sb.append("Nao ha alunos com propostas atribuidas.");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarAlunosNaoAtribuidas() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        // se estiver vazia, nao ha propostas atribuidas > lista todos os alunos
        if (propostasAtribuidas.isEmpty()) {
            for (var a : alunos)
                sb.append(a.toString());
        } else {
            boolean atribuida = false;
            for (var a : alunos) {
                for (var p : propostasAtribuidas)
                    if (a.getNumero() == p.getAlunoAtribuido())
                        atribuida = true;
                if (!atribuida)
                    sb.append(a.toString());
                atribuida = false;
            }
        }
        singleton.setMessage(sb.toString());
        return true;
    }

    //========= 'ORIENTADORES' > LISTAGEM DE ATRIBUICAO DE DOCENTES
    public boolean consultarAlunosPropDocente() {
        if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (var p : propostasAtribuidas)
            if (p.getTipo().equalsIgnoreCase("T2") && p.getAlunoAtribuido() > 0
                    && p.getDocente() != null)
                sb.append(p.toString());
        if (sb.isEmpty())
            singleton.setMessage("Nao ha propostas com aluno e docente associado.");
        else
            singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarAlunosPropSemDocente() {
        if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (var p : propostasAtribuidas)
            if (p.getTipo().equalsIgnoreCase("T2") && p.getAlunoAtribuido() > 0
                    && p.getDocente() == null)
                sb.append(p.toString());
        if (sb.isEmpty())
            singleton.setMessage("Nao ha propostas com aluno e sem docente associado.");
        else
            singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarOrientacoes() {
        boolean flg = false;
        if (docentes.isEmpty()) {
            singleton.setMessage("Lista de docentes vazia.\n");
            return false;
        }
        else if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        Map<String,Integer> orientacoes = new HashMap<>();
        int numOrientacoes = 0, orientadores = 0, min = 9999, max = 0;
        for (var p : propostasAtribuidas) {
            if (p.getTipo().equalsIgnoreCase("T2") && p.getDocente() != null) {
                if (!orientacoes.containsKey(p.getDocente())) {
                    orientacoes.put(p.getDocente(), 1);
                    orientadores++;
                    numOrientacoes++;
                } else {
                    for (var o : orientacoes.entrySet())
                        if (o.getKey().equals(p.getDocente()))
                            o.setValue(o.getValue()+1);
                    numOrientacoes++;
                }
                flg = true;
            }
        }
        // nao ha propostas com docentes atribuidos
        if (!flg) {
            singleton.setMessage("Nao ha propostas com docentes atribuidos.\n");
            return false;
        }
        //  devolve o MENOR numero de orientacoes de um docente
        for (var o : orientacoes.entrySet())
            if (o.getValue() < min)
                min = o.getValue();
        //  devolve o MAIOR numero de orientacoes de um docente
        for (var o : orientacoes.entrySet())
            if (o.getValue() > max)
                max = o.getValue();
        //  mostra o numero MIN, MAX, MEDIA e por docente (de orientacoes)
        for (var o : orientacoes.entrySet())
            sb.append("Docente: " + o.getKey() + ", com " + o.getValue() + " orientacoes.\n");

        sb.append("\nMaximo orientacoes: " + max);
        sb.append("\nMinimo orientacoes: " + min);
        sb.append("\nMedia: " + numOrientacoes/orientadores + "\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    //========= 'CONSULTA' > LISTAGEM DE ATRIBUICAO DE DOCENTES
    public boolean consultarSemPropComCandidatura() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        }
        else if (candidaturas.isEmpty()) {
            singleton.setMessage("Lista de candidaturas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        boolean encontrou = false;
        for (var a : alunos) {
            for (var p : propostasAtribuidas)
                if (a.getNumero() == p.getAlunoAtribuido())
                    encontrou = true;
            // se nao tiver proposta atribuida, resta verificar se tem candidatura registada
            if (!encontrou) {
                for (var c : candidaturas)
                    if (c.getAlunoAtribuido() == a.getNumero()) {
                        sb.append(a.toString() + "\n");
                        flg = true;
                    }
            }
            encontrou = false;
        }
        if (!flg) sb.append("Sem resultados.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarPropComCandidatura() {
        if (docentes.isEmpty()) {
            singleton.setMessage("Lista de docentes vazia.\n");
            return false;
        }
        else if (candidaturas.isEmpty()) {
            singleton.setMessage("Lista de candidaturas vazia.\n");
            return false;
        }
        else if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean encontrou = false;
        boolean flg = false;
        for (var a : alunos) {
            for (var p : propostasAtribuidas)
                if (a.getNumero() == p.getAlunoAtribuido())
                    encontrou = true;
            // se nao tiver proposta atribuida, resta verificar se tem candidatura registada
            if (!encontrou) {
                for (var c : candidaturas)
                    if (c.getAlunoAtribuido() == a.getNumero()) {
                        sb.append(a.toString() + "\n");
                        flg = true;
                    }
            }
            encontrou = false;
        }
        if (!flg) sb.append("Sem resultados.\n");
        singleton.setMessage(sb.toString());
        return false;
    }

    public boolean consultarPropostasNaoAtribuidas() {
        if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean flg = false;
        boolean indisponivel = false;
        for (var p : propostas) {
            for (var pA : propostasAtribuidas)
                if (p.getCodigo().equals(pA.getCodigo()))
                    indisponivel = true;
            if (!indisponivel) {
                sb.append(p.toString());
                flg = true;
            }
            indisponivel = false;
        }
        if (!flg) sb.append("Sem resultados.\n");
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean consultarPropostasAtribuidas() {
        if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }
        else if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("Lista de propostas atribuidas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (var pA : propostasAtribuidas)
            sb.append(pA.toString());
        singleton.setMessage(sb.toString());
        return true;
    }

    //========= 'CANDIDATURAS' > LISTAGEM COM FILTROS
    public boolean consultarFiltrosCandidatura(ArrayList<Integer> arr) {
        if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }

        if (arr.isEmpty())
            Collections.addAll(arr,1,2,3,4);

        StringBuilder sb = new StringBuilder();
        ArrayList<Proposta> listagem = new ArrayList<>();

        for (var a : arr) {
            switch (a) {
                case 1:   // lista de autopropostas de alunos
                    for (var p : propostas)
                        if (p.getTipo().equalsIgnoreCase("T3") && p.getAlunoAtribuido() > 0)
                            if (!listagem.contains(p))
                                listagem.add(p);
                    break;
                case 2:   // lista de propostas de docentes
                    for (var p : propostas)
                        if (p.getDocente() != null && p.getTipo().equalsIgnoreCase("T2"))
                            if (!listagem.contains(p))
                                listagem.add(p);
                    break;
                case 3:   // lista de propostas com candidaturas
                    if (candidaturas.isEmpty()) {
                        sb.append("Lista de candidaturas vazia.\n");
                        singleton.setMessage(sb.toString());
                        return false;
                    }
                    ArrayList<String> tmp = new ArrayList<>();
                    for (var c : candidaturas)
                        for (var cod : c.getCodigos())
                            if (!tmp.contains(cod))
                                tmp.add(cod);
                    for (var p : propostas)
                        if (tmp.contains(p.getCodigo()) && !listagem.contains(p))
                            listagem.add(p);
                    break;
                case 4:   // lista de propostas sem candidaturas
                    if (candidaturas.isEmpty()) {
                        sb.append("Lista de candidaturas vazia.\n");
                        singleton.setMessage(sb.toString());
                        return false;
                    }
                    boolean semCandidatura = false;
                    for (var p : propostas) {
                        for (var c : candidaturas)
                            for (int i = 0; i < c.getCodigos().size(); i++)
                                if (p.getCodigo().equals(c.getCodigos().get(i)))
                                    semCandidatura = true;
                        if (!semCandidatura && !listagem.contains(p))    // se nao encontrou, mostra no ecra
                            listagem.add(p);
                        semCandidatura = false;
                    }
                    break;
            }
        }

        if (listagem.isEmpty()) {
            singleton.setMessage("Sem resultados.\n");
            return false;
        }
        // lista as propostas sem repeticao
        for (var l : listagem)
            sb.append(l.toString());
        singleton.setMessage(sb.toString());
        return false;
    }

    //========= 'PROPOSTAS' > LISTAGEM DE PROPOSTAS (C/ FILTROS) PROJETO/ESTAGIO
    public boolean consultarFiltrosPropostas(ArrayList<Integer> arr) {
        if (arr.isEmpty()) {
            singleton.setMessage("Array de opcoes vazio.\n");
            return false;
        }
        else if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }

        StringBuilder sb = new StringBuilder();
        ArrayList<Proposta> listagem = new ArrayList<>();

        //========= 'PROPOSTAS' > LISTAGEM DE PROPOSTAS (C/ FILTROS) PROJETO/ESTAGIO
        for (var a : arr) {
            switch(a) {
                case 1:     // lista propostas com autoproposta associada
                    for (var p : propostas)
                        if (p.getTipo().equalsIgnoreCase("T3"))
                            if (!listagem.contains(p))
                                listagem.add(p);
                    break;
                case 2:     // lista propostas feitas por docentes
                    for (var p : propostas)
                        if (p.getTipo().equalsIgnoreCase("T2") && p.getDocente() != null)
                            if (!listagem.contains(p))
                                listagem.add(p);
                    break;
                case 3:     // lista as proposta que ainda nao foram atribuidas
                    boolean indisponivel = false;
                    for (var p : propostas) {
                        for (var pA : propostasAtribuidas)
                            if (p.getCodigo().equals(pA.getCodigo()))
                                indisponivel = true;
                        if (!indisponivel && !listagem.contains(p))
                            listagem.add(p);
                        indisponivel = false;
                    }
                    break;
                case 4:     // lista as propostas atribuidas
                    boolean repetida = false;
                    for (var pA : propostasAtribuidas) {
                        for (var l : listagem)
                            if (l.getCodigo() == pA.getCodigo())
                                repetida = true;
                        if (!repetida)
                            listagem.add(pA);
                        repetida = false;
                    }
                    break;
            }
        }
        if (listagem.isEmpty()) {
            singleton.setMessage("Sem resultados.\n");
            return false;
        }
        // lista as propostas sem repeticao
        for (var l : listagem)
            sb.append(l.toString());
        singleton.setMessage(sb.toString());
        return true;
    }

    // usada para fechar o estado de proposta (todos os alunos com candidaturas devem ter propostas atribuidas)
    public boolean consultarAtribuicoes() {
        if (alunos.isEmpty()) return false;
        ArrayList<Long> tmp = new ArrayList<>();
        for (var c : candidaturas)
            tmp.add(c.getAlunoAtribuido());
        for (var p : propostasAtribuidas)
            if (!tmp.contains(p.getAlunoAtribuido()))   // candidatura sem proposta atribuida
                return false;
        return true;
    }

    //============================= ATRIBUICAO PROPOSTAS ALUNOS =============================
    public boolean atribuicaoAutomaticaAlunoNAssoc() {
        boolean alunoAtribuido = false, mesmaPreferencia = false, atribuida = false, prioridadeMaiorI = true, candidaturaConcluida = false;
        StringBuilder sb = new StringBuilder();

        // percorrer as candidaturas
        for (var c1 : candidaturas) {
            // verificar se este aluno ja consta da lista de propostas atribudias
            for (var pA1 : propostasAtribuidas)
                if (c1.getAlunoAtribuido() == pA1.getAlunoAtribuido())
                    alunoAtribuido = true;

            // se nao tiver sido atribuido
            if (!alunoAtribuido) {
                // percorrer as preferencias dele e comparar com outros alunos
                for (int i = 0; i < c1.getCodigos().size(); i++) {
                    // verificar se a proposta com este codigo ja foi atribuida ou se na iteracao anterior o aluno
                    for (var pA2 : propostasAtribuidas) {                   // foi atribuido
                        if (pA2.getAlunoAtribuido() == c1.getAlunoAtribuido())
                            alunoAtribuido = true;
                        if (pA2.getCodigo().equals(c1.getCodigos().get(i)))
                            atribuida = true;
                    }
                    // aluno foi atribuido na iteracao anterior, quebra o ciclo dos codigos e passa para outra candidatura
                    if (alunoAtribuido)
                        break;

                    // se nao tiver sido atribuida
                    if (!atribuida) {
                        // comparar com outras candidaturas
                        for (var c2 : candidaturas) {
                            for (var pA3 : propostasAtribuidas) {
                                if (pA3.getAlunoAtribuido() == c2.getAlunoAtribuido()) {
                                    candidaturaConcluida = true;
                                    break;
                                }
                            }

                            if (c1.getAlunoAtribuido() != c2.getAlunoAtribuido() && !candidaturaConcluida) {
                                // procurar na candidatura o mesmo codigo e verificar se a prioridade e igual
                                for (int j = 0; j < c2.getCodigos().size(); j++) {
                                    if (c2.getCodigos().get(j).equals(c1.getCodigos().get(i)))
                                        if (i == j)         // assinalar que ambos tem a mesma preferencia
                                            mesmaPreferencia = true;
                                        else if (j < i)     // ha um aluno que tem uma prioridade maior
                                            prioridadeMaiorI = false;
                                }

                                Proposta p = null;
                                for (var proposta : propostas) {
                                    if (proposta.getCodigo().equals(c1.getCodigos().get(i))) {
                                        p = proposta;
                                        break;
                                    }
                                }

                                if (mesmaPreferencia) {
                                    // comparar os dois alunos e ver qual tem melhor classificacao
                                    Aluno aluno1 = null, aluno2 = null;
                                    for (var a : alunos) {
                                        if (a.getNumero() == c1.getAlunoAtribuido())
                                            aluno1 = a;
                                        else if (a.getNumero() == c2.getAlunoAtribuido())
                                            aluno2 = a;
                                    }

                                    /*-------------------------- ESTAGIO --------------------------*/
                                    // se ambos estiverem propostos a estagio e o aluno1 tiver melhor classificacao ele fica com a proposta
                                    if (aluno1.isEstagio() && aluno2.isEstagio() && aluno1.getClassificacao() > aluno2.getClassificacao()) {
                                        Proposta estagio = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                p.getTitulo(), p.getEntAcolhimento(), aluno1.getNumero());
                                        propostasAtribuidas.add(estagio);
                                        sb.append("Aluno '" + aluno1.getNumero() + " atribuido a proposta de estagio '" + p.getCodigo() + "'.");
                                    }
                                    // caso contrario o aluno2 fica com a proposta
                                    else if (aluno1.isEstagio() && aluno2.isEstagio() && aluno1.getClassificacao() < aluno2.getClassificacao()) {
                                        Proposta estagio = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                p.getTitulo(), p.getEntAcolhimento(), aluno2.getNumero());
                                        propostasAtribuidas.add(estagio);
                                        sb.append("Aluno '" + aluno2.getNumero() + " atribuido a proposta de estagio'" + p.getCodigo() + "'.");
                                    } else if (aluno1.isEstagio() && aluno2.isEstagio() && aluno1.getClassificacao() == aluno2.getClassificacao()) {
                                        sb.append("\n\nHouve um empate para a proposta: \n");
                                        sb.append("\t" + p.toString());
                                        sb.append("\nQual aluno escolhe? \n\n");
                                        sb.append("1) " + aluno1.toString());
                                        sb.append("2) " + aluno2.toString());
                                        singleton.setMessage(sb.toString());

                                        // atribuir os alunos ao hashmap para ficarem guardados
                                        escolherAlunos.put(aluno1, aluno2);
                                        escolherAlunosProposta.put(p, escolherAlunos);

                                        return false;
                                    }

                                    /*-------------------------- PROJETO --------------------------*/
                                    // se ambos estiverem propostos a projeto e o aluno1 tiver melhor classificacao ele fica com a proposta
                                    if (!aluno1.isEstagio() && !aluno2.isEstagio() && aluno1.getClassificacao() > aluno2.getClassificacao()) {
                                        Proposta projeto = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                p.getTitulo(), p.getDocente(), aluno1.getNumero());
                                        propostasAtribuidas.add(projeto);
                                        sb.append("Aluno '" + aluno1.getNumero() + " atribuido a proposta de projeto '" + p.getCodigo() + "'.");
                                    }
                                    // caso contrario o aluno2 fica com a proposta
                                    else if (!aluno1.isEstagio() && !aluno2.isEstagio() && aluno1.getClassificacao() < aluno2.getClassificacao()) {
                                        Proposta projeto = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                p.getTitulo(), p.getDocente(), c2.getAlunoAtribuido());
                                        propostasAtribuidas.add(projeto);
                                        sb.append("Aluno '" + aluno2.getNumero() + " atribuido a proposta de projeto'" + p.getCodigo() + "'.");
                                    } else if (!aluno1.isEstagio() && !aluno2.isEstagio() && aluno1.getClassificacao() == aluno2.getClassificacao()) {
                                        sb.append("\n\nHouve um empate para a proposta: \n");
                                        sb.append("\t" + p.toString());
                                        sb.append("\nQual aluno escolhe? \n\n");
                                        sb.append("1) " + aluno1.toString());
                                        sb.append("2) " + aluno2.toString());
                                        singleton.setMessage(sb.toString());

                                        // atribuir os alunos ao hashmap para ficarem guardados
                                        escolherAlunos.put(aluno1, aluno2);
                                        escolherAlunosProposta.put(p, escolherAlunos);

                                        return false;
                                    }
                                }

                                // se nao encontrou nenhuma candidatura com a mesma preferencia 'i' fica com a proposta
                                if (prioridadeMaiorI) {
                                    switch(p.getTipo()) {
                                        case "T1" -> {
                                            Proposta estagio = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                    p.getTitulo(), p.getEntAcolhimento(), c1.getAlunoAtribuido());
                                            propostasAtribuidas.add(estagio);
                                            sb.append("Aluno '" + c1.getAlunoAtribuido() + " atribuido a proposta de estagio'" + p.getCodigo() + "'.");
                                        }
                                        case "T2" -> {
                                            Proposta projeto = new Projeto(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                                                    p.getTitulo(), p.getDocente(), c1.getAlunoAtribuido());
                                            propostasAtribuidas.add(projeto);
                                            sb.append("Aluno '" + c1.getAlunoAtribuido() + " atribuido a proposta de projeto'" + p.getCodigo() + "'.");
                                        }
                                    }
                                    // quebra o ciclo porque esta candidatura foi atribuida
                                    // procura outras candidaturas para serem atribudias
                                    break;
                                }
                                prioridadeMaiorI = true;
                            }
                            candidaturaConcluida = false;
                        }
                    }
                    atribuida = false;
                }
            }
            alunoAtribuido = false;
        }

        // variavel que permite saber quando o ciclo das candidatura foi percorrido por completo
        terminouAtribuicao = true;

        return true;
    }

    public boolean userEscolheAluno(int index) {
        long numeroAluno = 0;
        for (var a : escolherAlunos.entrySet()) {
            // adiciona o primeiro elemento do hashmap
            if (index == 0)
                numeroAluno = a.getKey().getNumero();
                // adiciona o segundo elemento do hashmap
            else
                numeroAluno = a.getValue().getNumero();
        }

        for (var p : escolherAlunosProposta.keySet()) {
            switch(p.getTipo()) {
                case "T1":
                    Proposta estagio = new Estagio(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                            p.getTitulo(), p.getEntAcolhimento(), numeroAluno);
                    propostasAtribuidas.add(estagio);
                    break;
                case "T2":
                    Proposta projeto = new Projeto(p.getTipo(), p.getCodigo(), p.getRamoDestino(),
                            p.getTitulo(), p.getDocente(), numeroAluno);
                    propostasAtribuidas.add(projeto);
                    break;
            }
            singleton.setMessage("Escolheu o aluno '" + numeroAluno + "' e foi atribuido com sucesso " +
                    "a proposta '" + p.getCodigo() + "'.");
        }

        // limpa o hashmap para ter sempre apenas 1 escolha a ser feita
        escolherAlunos.clear();
        escolherAlunosProposta.clear();

        return true;
    }

    public boolean atribuicaoAutomaticaAlunoAssoc() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        }
        else if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }

        StringBuilder sb = new StringBuilder();
        boolean existemAtribuicoes = false, disponivel = true;
        // atribuicao automatica das autopropostas
        for (var p : propostas) {
            for (var pA : propostasAtribuidas)
                if (p.getCodigo().equals(pA.getCodigo()))  // se ja proposta ainda tiver sido atribuida
                    disponivel = false;
            if (disponivel) {
                if (p.getTipo().equalsIgnoreCase("T3") && p.getAlunoAtribuido() > 0) {
                    Proposta tmp = new Autoproposto(p.getTipo(), p.getCodigo(), p.getTitulo());
                    tmp.setAlunoAtribuido(p.getAlunoAtribuido());
                    propostasAtribuidas.add(tmp);
                    sb.append("Proposta '" + p.getCodigo() + "' atribuida ao aluno '" + p.getAlunoAtribuido() + "'.\n");
                    existemAtribuicoes = true;
                }
            }
            disponivel = true;
        }

        // para atribuir docentes verifica primeiro se a lista esta vazia
        if (docentes.isEmpty()) {
            sb.append("\nLista de docentes vazia, nao foi possivel atribuir.\n");
            singleton.setMessage(sb.toString());
            return false;
        }

        disponivel = true;
        // atribuicao automatica dos propostas de docentes com aluno atribuido
        for (var p : propostas) {
            for (var pA : propostasAtribuidas)
                if (p.getCodigo().equals(pA.getCodigo()))  // se ja proposta ainda tiver sido atribuida
                    disponivel = false;
            if (disponivel) {
                if (p.getTipo().equalsIgnoreCase("T2") && p.getDocente() != null && p.getAlunoAtribuido() > 0) {
                    propostasAtribuidas.add(p);
                    sb.append("Proposta '" + p.getCodigo() + "' com o docente '" + p.getDocente() +
                            "' atribuida ao aluno '" + p.getAlunoAtribuido() + "'.\n");
                    existemAtribuicoes = true;
                }
            }
            disponivel = true;
        }

        if (!existemAtribuicoes)
            singleton.setMessage("Nao existem atribuicoes a serem feitas.");
        else
            singleton.setMessage(sb.toString());

        return true;
    }

    public boolean atribuicaoManualAluno(String codigo, Long numero) {
        if (!verificaNumero(numero)) return false;
        else if (!verificaCodigo(codigo)) return false;
        else if (!verificaAlunoCandidatura(numero)) return false;
        // verifica se a fase de candidaturas esta fechada
        else if (candidaturaFechada) {
            singleton.setMessage("\nJa nao e possivel fazer atribuicoes, a fase de candidatura fechou\n");
            return false;
        }

        // se a lista de propostas atribuidas ja tiver esta proposta
        for (var p : propostasAtribuidas) {
            if (p.getCodigo().equals(codigo)) {
                singleton.setMessage("\nProposta '" + codigo + "' ja foi atribuida atribuida.");
                return false;
            }
            else if (p.getAlunoAtribuido() == numero) {
                singleton.setMessage("\nAluno '" + numero + "' ja tem uma proposta atribuida.");
                return false;
            }
        }

        // se ainda nao foi atribuida, verifica se a proposta esta disponivel (nao esta associada)
        for (var p : propostas) {
            if (p.getCodigo().equals(codigo) && p.getAlunoAtribuido() == 0) {
                switch(p.getTipo()) {
                    case "T1":
                        Proposta estagio = new Estagio(p.getTipo(),p.getCodigo(),p.getRamoDestino(),
                                p.getTitulo(),p.getEntAcolhimento(),numero);
                        propostasAtribuidas.add(estagio);
                        break;
                    case "T2":
                        Proposta projeto = new Projeto(p.getTipo(),p.getCodigo(),p.getRamoDestino(),
                                p.getTitulo(),p.getDocente(),numero);
                        propostasAtribuidas.add(projeto);
                        break;
                    case "T3":
                        Proposta auto = new Autoproposto(p.getTipo(),p.getCodigo(),p.getTitulo());
                        auto.setAlunoAtribuido(numero);
                        propostasAtribuidas.add(auto);
                        break;
                }
                singleton.setMessage("\nA proposta '" + codigo + "' foi atribuida ao aluno '" + numero + "'.");
                return true;
            }
        }
        singleton.setMessage("\nErro a atribuir a proposta '" + codigo + "' ao aluno '" + numero + "'.");
        return false;
    }

    public boolean remocaoManualAluno(String codigo, Long numero) {
        if (!verificaNumero(numero)) return false;
        else if (!verificaAlunoCandidatura(numero)) return false;
        else if (!verificaCodigo(codigo)) return false;

        // verifica se a fase de candidaturas esta fechada
        if (candidaturaFechada) {
            singleton.setMessage("\nJa nao e possivel fazer atribuicoes, a fase de candidatura fechou\n");
            return false;
        }

        for (var p : propostasAtribuidas) {
            if (p.getCodigo().equals(codigo) && p.getAlunoAtribuido() == numero) {
                propostasAtribuidas.remove(p);
                singleton.setMessage("\nA proposta '" + codigo + "' foi removida ao aluno '" + numero + "'.");
                return true;
            }
        }
        singleton.setMessage("\nErro a remover a proposta '" + codigo + "' do aluno '" + numero + "'.");
        return false;
    }

    public boolean verificaEstadoAtribuicoes() {
        // enquanto a fase de candidatura estiver aberta, as atribuicoes automaticas sao a unica
        // maneira de realizar alguma atribuicao
        if (!isCandidaturaFechada())  {
            singleton.setMessage("Enquanto a fase de candidaturas estiver aberta, apenas sao aceites atribuicoes automaticas.");
            return false;
        }
        return true;
    }

    //============================= ATRIBUICAO DOCENTES =============================
    public boolean atribuicaoAutomaticaDocente() {
        if (alunos.isEmpty()) {
            singleton.setMessage("Lista de alunos vazia.\n");
            return false;
        }
        else if (propostas.isEmpty()) {
            singleton.setMessage("Lista de propostas vazia.\n");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        boolean atribuida = false;
        int atribuicoes = 0;
        for (var p : propostas) { // lista de propostas de projeto sem docente
            if (p.getTipo().equalsIgnoreCase("T2") && p.getDocente() != null) {
                for (var pA : propostasAtribuidas) {  // verifica se a proposta ja esta atribuida a um docente
                    if (pA.getCodigo().equals(p.getCodigo()) && pA.getDocente() != null && pA.getDocente().equals(p.getDocente()))
                        atribuida = true;
                    else if (pA.getCodigo().equals(p.getCodigo()) && pA.getDocente() == null) {
                        atribuida = true;
                        atribuicoes++;
                        pA.setDocente(p.getDocente());
                        sb.append("Proposta '" + p.getCodigo() + "' atribuida ao docente '" + p.getDocente() + "'.");
                    }
                }
                if (!atribuida) {
                    propostasAtribuidas.add(p);
                    atribuicoes++;
                    sb.append("Proposta '" + p.getCodigo() + "' atribuida ao docente '" + p.getDocente() + "'.");
                }
                atribuida = false;
            }
        }
        if (atribuicoes == 0) {
            singleton.setMessage("Nao ha atribuicoes a serem feitas.");
            return false;
        }
        singleton.setMessage(sb.toString());
        return true;
    }

    public boolean atribuicaoManualDocente(String codigo, String email) {
        if (!verificaEmail(email)) return false;
        else if (!verificaCodigo(codigo)) return false;

        // verifica se a proposta ja foi atribuida a um docente
        for (var pA : propostasAtribuidas) {
            if (pA.getCodigo().equals(codigo) && pA.getTipo().equalsIgnoreCase("T2") && pA.getDocente() != null) {
                singleton.setMessage("\nProposta '" + codigo + "' ja esta atribuida ao docente '" + pA.getDocente() + "'.");
                return false;
            }
            else if (pA.getCodigo().equals(codigo) && pA.getTipo().equalsIgnoreCase("T2") && pA.getDocente() == null) {
                pA.setDocente(email);
                singleton.setMessage("\nProposta '" + codigo + "' atribuida ao docente '" + email + "'.");
                return false;
            }
        }

        // lista de propostas de projeto sem docente
        for (var p : propostas) {
            if (p.getCodigo().equals(codigo) && !p.getTipo().equalsIgnoreCase("T2")) {
                singleton.setMessage("\nProposta '" + codigo + "' nao e do tipo projeto.");
                return false;
            }
            else if (p.getCodigo().equals(codigo) && p.getTipo().equalsIgnoreCase("T2")) {
                Proposta projeto = new Projeto(p.getTipo(),p.getCodigo(),p.getRamoDestino(),p.getTitulo(),email,p.getAlunoAtribuido());
                propostasAtribuidas.add(p);
                singleton.setMessage("\nProposta '" + codigo + "' atribuida ao docente '" + email + "'.");
                return true;
            }
        }
        singleton.setMessage("Erro na atribuicao manual.");
        return false;
    }

    public boolean remocaoManualDocente(String codigo, String email) {
        if (!verificaEmail(email)) return false;
        else if (!verificaCodigo(codigo)) return false;

        for (var p : propostasAtribuidas) {
            // se existir um aluno atribuido a proposta, apenas remove o docente
            if (p.getCodigo().equals(codigo) && p.getDocente().equals(email) && p.getAlunoAtribuido() > 0) {
                p.setDocente(null);
                singleton.setMessage("\nDocente '" + email + "' associado a proposta '" + codigo + "' removido com sucesso.");
                return true;
            }
            // se nao existir nenhum aluno remove a proposta da lista
            else if (p.getCodigo().equals(codigo) && p.getDocente().equals(email) && p.getAlunoAtribuido() == 0) {
                propostasAtribuidas.remove(p);
                singleton.setMessage("\nDocente '" + email + "' associado a proposta '" + codigo + "' removido com sucesso.");
                return true;
            }
        }
        singleton.setMessage("Erro na remocao manual.");
        return false;
    }

    public boolean consultarDocenteProp(String email) {
        if (!verificaEmail(email)) return false;
        else if (propostasAtribuidas.isEmpty()) {
            singleton.setMessage("\nLista de propostas atribuidas vazia.\n");
            return false;
        }

        StringBuilder sb = new StringBuilder("Proposta associada ao docente '" + email + "': \n");
        for (var p : propostasAtribuidas) {
            if (p.getDocente().equals(email)) {
                sb.append(p.toString());
            }
        }
        if (sb.isEmpty())
            singleton.setMessage("\nDocente '" + email + "' nao tem nenhuma proposta associada.");
        else
            singleton.setMessage(sb.toString());
        return false;
    }

    public boolean alterarDocenteProp(String email, String codigo) {
        if (!verificaEmail(email)) return false;
        else if (!verificaCodigo(codigo)) return false;
        for (var p : propostasAtribuidas) {
            if (p.getCodigo().equals(codigo)) {
                p.setDocente(email);
                singleton.setMessage("\nDocente '" + email + "' associado a proposta '" + codigo + "'.");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar o docente da proposta '" + codigo + "'.");
        return false;
    }

    /*======================== MODIFICACAO > ALUNOS ========================*/
    public boolean alunoSetNome(long numero, String nome) {
        if (!verificaNumero(numero)) return false;
        //if (!verificaString(nome)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                a.setNome(nome);
                singleton.setMessage("Nome do aluno '" + numero + "' modificado com sucesso.\n");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar o nome do aluno '" + numero + "'.");
        return false;
    }

    public boolean alunoSetCurso(long numero, String curso) {
        if (!verificaNumero(numero)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                a.setSiglaCurso(curso);
                singleton.setMessage("Curso do aluno '" + numero + "' modificado com sucesso.\n");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar o curso do aluno '" + numero + "'.");
        return false;
    }

    public boolean alunoSetRamo(long numero, String ramo) {
        if (!verificaNumero(numero)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                a.setSiglaRamo(ramo);
                singleton.setMessage("Ramo do aluno '" + numero + "' modificado com sucesso.\n");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar o ramo do aluno '" + numero + "'.");
        return false;
    }

    public boolean alunoSetClassif(long numero, Double classif) {
        if (!verificaNumero(numero)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                a.setClassificacao(classif);
                singleton.setMessage("Classificacao do aluno '" + numero + "' modificada com sucesso.\n");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar a classificacao do aluno '" + numero + "'.");
        return false;
    }

    public boolean alunoSetEstagio(long numero, Boolean estagio) {
        if (!verificaNumero(numero)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                a.setEstagio(estagio);
                singleton.setMessage("Estagio do aluno '" + numero + "' modificado com sucesso.\n");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel alterar o estagio do aluno '" + numero + "'.");
        return false;
    }

    public boolean eliminaAluno(long numero) {
        if (!verificaNumero(numero)) return false;
        for (var a : alunos) {
            if (a.getNumero() == numero) {
                alunos.remove(a);
                singleton.setMessage("Aluno '" + numero + "' eliminado com sucesso.");
                break;
            }
        }
        if (!propostas.isEmpty()) {
            for (var p : propostas) // deixa de existir o aluno atribuido
                if (p.getAlunoAtribuido() == numero)
                    p.setAlunoAtribuido(0);
        }
        if (!candidaturas.isEmpty()) {
            for (var c : candidaturas)  // deixa de existir o aluno atribuido
                if (c.getAlunoAtribuido() == numero)
                    candidaturas.remove(c);
        }

        return true;
    }

    /*======================== MODIFICACAO > DOCENTES ========================*/
    public boolean docenteSetNome(String email, String nome) {
        if (!verificaEmail(email)) return false;
        if (!verificaString(nome)) return false;
        for (var d : docentes) {
            if (d.getEmail().equals(email)) {
                d.setNome(nome);
                break;
            }
        }
        singleton.setMessage("Nome do docente '" + email + "' modificado com sucesso.\n");
        return true;
    }

    public boolean eliminaDocente(String email) {
        if (!verificaEmail(email)) return false;
        for (var d : docentes) {
            if (d.getEmail().equals(email)) {
                docentes.remove(d);
                singleton.setMessage("Docente '" + email + "' eliminado com sucesso.");
                break;
            }
        }
        if (!propostas.isEmpty()) {
            for (var p : propostas) // deixa de existir o docente responsavel
                if (p.getDocente().equals(email))
                    p.setDocente(null);
        }
        return true;
    }

    /*======================== MODIFICACAO > PROPOSTAS ========================*/
    public boolean propostaSetTitulo(String codigo, String titulo) {
        if (!verificaCodigo(codigo)) return false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo))
                    p.setTitulo(titulo);
        singleton.setMessage("Titulo da proposta '" + codigo + "' modificado com sucesso.");
        return true;
    }

    public boolean propostaSetRamo(String codigo, String ramo) {
        if (!verificaCodigo(codigo)) return false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo))
                p.setRamoDestino(ramo);
        singleton.setMessage("Ramo da proposta '" + codigo + "' modificado com sucesso.");
        return true;
    }

    public boolean propostaSetEnt(String codigo, String entidade) {
        if (!verificaCodigo(codigo)) return false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo))
                p.setEntAcolhimento(entidade);
        singleton.setMessage("Entidade da proposta '" + codigo + "' modificado com sucesso.");
        return true;
    }

    public boolean propostaSetAluno(String codigo, Long numero) {
        if (!verificaCodigo(codigo)) return false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo))
                p.setAlunoAtribuido(numero);
        singleton.setMessage("Aluno da proposta '" + codigo + "' modificado com sucesso.");
        return true;
    }

    public boolean propostaSetDocente(String codigo, String email) {
        if (!verificaCodigo(codigo)) return false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo))
                p.setDocente(email);

        singleton.setMessage("Docente atribuido a proposta'" + codigo + "' com sucesso.");
        return true;
    }

    public boolean eliminaProposta(String codigo) {
        if (!verificaCodigo(codigo)) return false;
        for (var c : propostas) {
            if (c.getCodigo().equals(codigo)) {
                propostas.remove(c);
                singleton.setMessage("Proposta '" + codigo + "' eliminada com sucesso.");
            }
        }
        if (!candidaturas.isEmpty()) {
            for (var c : candidaturas)  // deixa de existir essa proposta
                if (c.getCodigos().contains(codigo))
                    candidaturas.remove(c);
        }
        return true;
    }

    /*======================== MODIFICACAO > CANDIDATURAS ========================*/
    public boolean adicionarCodigo(Long numero, String codigo) {
        if (!verificaAlunoCandidatura(numero)) return false;
        boolean alunoAtribuido = false;
        for (var p : propostas)
            if (p.getCodigo().equals(codigo) && p.getAlunoAtribuido() > 0)
                alunoAtribuido = true;
        if (!alunoAtribuido) {
            for (var c : candidaturas) {
                if (c.getAlunoAtribuido() == numero) {
                    c.setCodigo(codigo);
                    singleton.setMessage("Codigo '" + codigo + "' adicionado a candidatura '" + numero + "' com sucesso.");
                    return true;
                }
            }
        }
        singleton.setMessage("Nao foi possivel adicionar o codigo '" + codigo + "' a candidatura do aluno '" + numero + "'.");
        return false;
    }

    public boolean removerCodigo(Long numero, String codigo) {
        if (!verificaAlunoCandidatura(numero)) return false;
        for (var c : candidaturas) {
            // se so tiver um codigo e for igual ao inserido, remove a candidatura
            if (c.getAlunoAtribuido() == numero && c.getCodigos().size() == 1 && c.getCodigos().contains(codigo)) {
                candidaturas.remove(c);
                break;
            } else if (c.getAlunoAtribuido() == numero && c.getCodigos().size() > 1) {
                for (var cod : c.getCodigos()) {
                    if (cod.equals(codigo)) {
                        c.getCodigos().remove(cod);
                        singleton.setMessage("Codigo '" + codigo + "' removido da candidatura '" + numero + "' com sucesso.");
                        return true;
                    }
                }
            }
        }
        singleton.setMessage("Nao foi possivel remover o codigo '" + codigo + "' da candidatura do aluno '" + numero + "'.");
        return false;
    }

    public boolean eliminaCandidatura(Long numero) {
        if (!verificaNumero(numero)) return false;
        if (!verificaAlunoCandidatura(numero)) return false;
        for (var c : candidaturas) {
            if (c.getAlunoAtribuido() == numero) {
                candidaturas.remove(c);
                singleton.setMessage("Candidatura do aluno '" + numero + "' eliminado com sucesso.");
                return true;
            }
        }
        singleton.setMessage("Nao foi possivel eliminar a candidatura do aluno '" + numero + "'.");
        return false;
    }

    public ArrayList<String> getArrayCodigos(String codigos) {
        ArrayList<String> arr = new ArrayList<>();
        /* verifica quando há mais do que uma ramo separado por "," */
        String[] codigosArr = codigos.split(",");
        for (int i = 0; i < codigosArr.length; i++)
            arr.add(codigosArr[i]);
        return arr;
    }

    /*======================== Verificacao dos Parametros de Valor Unico ========================*/
    public boolean verificaNumero(long numero) {   // numero alunos
        if (numero <= 0) {
            singleton.setMessage("Numero de ser maior que zero.\n");
            return false;
        }
        if (!alunos.isEmpty()) {
            for (var a : alunos)
                if (a.getNumero() == numero)
                    return true;
            singleton.setMessage("Numero de aluno nao existe.\n");
            return false;
        }
        singleton.setMessage("Lista de alunos vazia.\n");
        return false;
    }

    public boolean verificaEmail(String email) {   // email docentes
        if (email == null) {
            singleton.setMessage("Email nao pode ser null.\n");
            return false;
        }
        if (!docentes.isEmpty()) {
            for (var d : docentes)
                if (d.getEmail().equals(email))
                    return true;
            singleton.setMessage("Email de docente nao existe.\n");
            return false;
        }
        singleton.setMessage("Lista de docentes vazia.\n");
        return false;
    }

    private boolean verificaCodigo(String codigo) { // codigo propostas
        if (codigo == null) {
            singleton.setMessage("Codigo nao pode ser null.\n");
            return false;
        }
        if (!propostas.isEmpty()) {
            for (var p : propostas)
                if (p.getCodigo().equals(codigo))
                    return true;
            singleton.setMessage("Codigo nao existe.\n");
            return false;
        }
        singleton.setMessage("Lista de propostas vazia.\n");
        return true;
    }

    private boolean verificaAlunoCandidatura(Long numero) {
        if (numero <= 0) {
            singleton.setMessage("Numero de ser maior que zero.\n");
            return false;
        }
        if (!candidaturas.isEmpty()) {
            for (var c : candidaturas)
                if (c.getAlunoAtribuido() == numero)
                    return true;
            singleton.setMessage("Candidatura nao existe.\n");
            return false;
        }
        singleton.setMessage("Lista de candidaturas vazia.\n");
        return false;
    }

    public boolean verificaString(String string) {   // email docentes
        if (string == null) {
            singleton.setMessage("String nao pode ser null.\n");
            return false;
        }
        return true;
    }

    /*============================  Metodos para apresentar numeros na ListView ============================*/
    /* Candidatura*/
    public int getNumAlunosComCandidatura() {
        int contador = 0;
        if (candidaturas.isEmpty() || alunos.isEmpty())
            return contador;
        for (var c : candidaturas)
            for (var a : alunos)
                if (a.getNumero() == c.getAlunoAtribuido())
                    contador++;
        return contador;
    }

    public String getListaCandidaturas() {
        StringBuilder sb = new StringBuilder();
        for (var c : candidaturas)
            sb.append(c.toString());
        return sb.toString();
    }

    /* Proposta */
    public int getNumEstagios() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T1"))
                contador++;
        return contador;
    }

    public int getNumProjetos() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T2"))
                contador++;
        return contador;
    }

    public int getNumAutos() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T3"))
                contador++;
        return contador;
    }

    public String getListaPropostasAtribuidas() {
        StringBuilder sb = new StringBuilder();
        for (var p : propostasAtribuidas)
            sb.append(p.toString());
        return sb.toString();
    }

    /* Orientacoes */
    public int getNumAlunosProDocAssoc() {
        int contador = 0;
        if (propostas.isEmpty() || propostasAtribuidas.isEmpty())
            return contador;
        for (var p : propostasAtribuidas)
            if (p.getAlunoAtribuido() != 0 && p.getDocente() != null)
                contador++;
        return contador;
    }

    public int getNumAlunosProSDocAssoc() {
        int contador = 0;
        if (propostas.isEmpty() || propostasAtribuidas.isEmpty())
            return contador;
        for (var p : propostasAtribuidas)
            if (p.getAlunoAtribuido() != 0 && p.getDocente() == null)
                contador++;
        return contador;
    }

    public int getNumTotalOrientacoes() {
        int contador = 0;
        if (propostas.isEmpty() || docentes.isEmpty() || propostasAtribuidas.isEmpty())
            return contador;
        for (var d : docentes)
            for (var p : propostasAtribuidas)
                if (d.getEmail().equalsIgnoreCase(p.getDocente()))
                    contador++;
        return contador;
    }

    /* Consulta */
    public int getEstagiosDA() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T1") && p.getRamoDestino().equalsIgnoreCase("DA"))
                contador++;
        return contador;
    }

    public int getEstagiosSI() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T1") && p.getRamoDestino().equalsIgnoreCase("SI"))
                contador++;
        return contador;
    }

    public int getEstagiosRAS() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        for (var p : propostas)
            if (p.getTipo().equalsIgnoreCase("T1") && p.getRamoDestino().equalsIgnoreCase("RAS"))
                contador++;
        return contador;
    }

    public int getNumNAtribuidas() {
        int contador = 0;
        if (propostas.isEmpty())
            return contador;
        boolean atribuida = false;
        for (var p : propostas) {
            for (var pA : propostasAtribuidas)
                if (pA.getCodigo().equals(p.getCodigo()))
                    atribuida = true;
            if (!atribuida)
                contador++;
            atribuida = false;
        }
        return contador;
    }

    public HashMap<Docente,Integer> getDocentesTop5() {
        if (docentes.isEmpty() || propostasAtribuidas.isEmpty())
            return null;
        HashMap<Docente,Integer> aux = new HashMap<>();
        int contador = 0;
        boolean temAtribuicoes = false;
        // procura os docentes que tenham propostas atribuidas e insere no hashmap com o contador
        for (var d : docentes) {
            contador = 0;
            for (var p : propostasAtribuidas) {
                if (d.getEmail().equals(p.getDocente())) {
                    contador++;
                    temAtribuicoes = true;
                }
            }
            if (temAtribuicoes)
                aux.put(d,contador);
            temAtribuicoes = false;
        }
        // se for menor ou igual a 5 nao e preciso excluir nenhum
        if (aux.size() <= 5)
            return aux;

        /*Set<Map.Entry<Docente, Integer>> set = aux.entrySet();

        List<Map.Entry<Docente, Integer>> list = new ArrayList<Map.Entry<Docente, Integer>>(set);

        Collections.sort(list, new Comparator<Map.Entry<Docente, Integer>>() {
            @Override
            public int compare(Map.Entry<Docente, Integer> o1,
                               Map.Entry<Docente, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }
        });
        list.subList(0, 5); */

        // obtem os 5 maiores valores para as orientacoes
        PriorityQueue<Integer> p = new PriorityQueue<Integer>(5);
        for (var i : aux.entrySet()){
            p.add(i.getValue());
            if (p.size() > 5)   // remove o primeiro elemento da fila ate ficar com apenas 5
                p.poll();
        }

        // remove todos os docentes que nao pertecam ao top5 obtido em cima
        for (var i : aux.entrySet()) {
            if (!p.contains(i.getValue()))
                aux.remove(i);
        }

        return aux;
    }

    public HashMap<String,Integer> getEmpresasTop5() {
        if (propostasAtribuidas.isEmpty())
            return null;
        HashMap<String,Integer> aux = new HashMap<>();
        int contador = 0;
        boolean existe = false;

        // procurar as empresas das propostas de estagios atribuidas
        for (var p : propostasAtribuidas) {
            for (var p2 : propostasAtribuidas) {
                if (p.getTipo().equalsIgnoreCase("T1") && p.getEntAcolhimento().equals(p2.getEntAcolhimento())
                        && !aux.containsKey(p.getEntAcolhimento())) {
                    contador++;
                    existe = true;
                }
            }
            if (existe)
                aux.put(p.getEntAcolhimento(),contador);
            existe = false;
            contador = 0;
        }
        // se for menor ou igual a 5 nao e preciso excluir nenhum
        if (aux.size() <= 5)
            return aux;

        // obtem os 5 maiores valores para as orientacoes
        PriorityQueue<Integer> p = new PriorityQueue<Integer>(5);
        for (var i : aux.entrySet()){
            p.add(i.getValue());
            if (p.size() > 5)   // remove o primeiro elemento da fila ate ficar com apenas 5
                p.poll();
        }

        // remove todos os docentes que nao pertecam ao top5 obtido em cima
        for (var i : aux.entrySet()) {
            if (!p.contains(i.getValue()))
                aux.remove(i);
        }

        return aux;
    }

    public String getListaAlunos() {
        StringBuilder sb = new StringBuilder();
        for (var a : alunos)
            sb.append(a.toString());
        return sb.toString();
    }

    public String getListaDocentes() {
        StringBuilder sb = new StringBuilder();
        for (var d : docentes)
            sb.append(d.toString());
        return sb.toString();
    }

    public String getListaPropostas() {
        StringBuilder sb = new StringBuilder();
        for (var p : propostas)
            sb.append(p.toString());
        return sb.toString();
    }
}