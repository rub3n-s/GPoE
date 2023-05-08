package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.data.Singleton;

public class ConfiguracaoState extends ManagementStateAdapter {
    Singleton singleton;
    public ConfiguracaoState(ManagementContext context, ManagementData data) {
        super(context,data);
        singleton = Singleton.Singleton();
    }

    @Override
    public ManagementState getState() {
        return ManagementState.CONFIGURACAO;
    }

    @Override
    public boolean avancar() {
        // se a CANDIDATURA ja estiver fechada avanca para a fase CANDIDATURA_FECHADA
        if (data.isFechada(ManagementState.CANDIDATURA))
            changeState(ManagementState.CANDIDATURA_FECHADA.createState(context,data));
        else
            changeState(ManagementState.CANDIDATURA.createState(context,data));
        return true;
    }

    @Override
    public boolean fechar() {
        int alunosDA = 0, alunosRAS = 0, alunosSI = 0;  // contadores para os alunos
        for (var a : data.getAlunos()) {
            switch(a.getSiglaRamo()) {
                case "DA" -> alunosDA++;
                case "RAS" -> alunosRAS++;
                case "SI" -> alunosSI++;
            }
        }

        int propDA = 0, propRAS = 0, propSI = 0;    // contadores para as propostas
        String []propostas;
        for (var p : data.getPropostas()) {
            if (!p.getTipo().equalsIgnoreCase("T3")) {
                propostas = p.getRamoDestino().split("\\|+");
                for (var r : propostas) {
                    switch (r) {
                        case "DA" -> propDA++;
                        case "RAS" -> propRAS++;
                        case "SI" -> propSI++;
                    }
                }
            }
        }

        // se as propostas por cada ramo forem >= ao numero de alunos do mesmo ramo, pode fechar a fase
        if (propDA >= alunosDA && propRAS >= alunosRAS && propSI >= alunosSI) {
            // guarda a informação que esta fechada no ManagementData
            data.fechaState(ManagementState.CONFIGURACAO);

            // avancar de fase (CANDIDATURA)
            avancar();

            singleton.setMessage("\nFase 'CONFIGURACAO' fechada\n");
            return true;
        } else {
            singleton.setMessage("\nNumero de propostas por cada ramo inferior ao numero de alunos\n");
        }

        return false;
    }

    @Override
    public boolean execute() { return false; }

    @Override
    public boolean undo() { return false; }
}
