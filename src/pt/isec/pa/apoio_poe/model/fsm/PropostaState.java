package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.data.Singleton;

import java.util.ArrayList;

public class PropostaState extends ManagementStateAdapter {
    Singleton singleton;

    public PropostaState(ManagementContext context, ManagementData data) {
        super(context,data);
        singleton = Singleton.Singleton();
    }

    @Override
    public ManagementState getState() {
        return ManagementState.PROPOSTA;
    }

    @Override
    public boolean avancar() {
        // se a ORIENTADORES ja estiver fechada avanca para a fase ORIENTADORES_FECHADA
        if (data.isFechada(ManagementState.ORIENTADORES)) {
            changeState(ManagementState.ORIENTADORES_FECHADA.createState(context, data));
            return true;
        }
        changeState(ManagementState.ORIENTADORES.createState(context,data));
        return true;
    }

    @Override
    public boolean voltar() {
        // se a CANDIDATURA ja estiver fechada avanca para a fase CANDIDATURA_FECHADA
        if (data.isFechada(ManagementState.CANDIDATURA)) {
            changeState(ManagementState.CANDIDATURA_FECHADA.createState(context, data));
            return true;
        }
        changeState(ManagementState.CANDIDATURA.createState(context,data));
        return true;
    }

    @Override
    public boolean fechar() {
        // verifica se todas as candidaturas tem um projeto atribuido
        if (!data.consultarAtribuicoes()) {
            singleton.setMessage("Para fechar a fase todas as candidaturas devem ter um projeto atribuido");
            return false;
        }

        // guarda a informação que esta fechada no ManagementData
        data.fechaState(ManagementState.PROPOSTA);

        // avancar de fase (ORIENTADORES)
        avancar();

        singleton.setMessage("\nFase de Proposta fechada\n");
        return true;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean undo() {
        return false;
    }
}
