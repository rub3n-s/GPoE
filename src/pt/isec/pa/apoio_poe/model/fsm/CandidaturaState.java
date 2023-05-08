package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.data.Singleton;

public class CandidaturaState extends ManagementStateAdapter {
    Singleton singleton;

    public CandidaturaState(ManagementContext context, ManagementData data) {
        super(context,data);
        singleton = Singleton.Singleton();
    }

    @Override
    public ManagementState getState() {
        return ManagementState.CANDIDATURA;
    }

    @Override
    public boolean avancar() {
        // se a PROPOSTA ja estiver fechada avanca para a fase PROPOSTA_FECHADA
        if (data.isFechada(ManagementState.PROPOSTA))
            changeState(ManagementState.PROPOSTA_FECHADA.createState(context,data));
        else
            changeState(ManagementState.PROPOSTA.createState(context,data));
        return true;
    }

    @Override
    public boolean voltar() {
        // se a CONFIGURACAO ja estiver fechada avanca para a fase CONFIGURACAO_FECHADA
        if (data.isFechada(ManagementState.CONFIGURACAO))
            changeState(ManagementState.CONFIGURACAO_FECHADA.createState(context,data));
        else
            changeState(ManagementState.CONFIGURACAO.createState(context,data));
        return true;
    }

    @Override
    public boolean fechar() {
        if (!data.isFechada(ManagementState.CONFIGURACAO)) {
            singleton.setMessage("\nEsta fase so pode ser fechada quando a 'CONFIGURACAO' estiver fechada.\n");
            return false;
        }

        // guarda a informação que esta fechada no ManagementData
        data.fechaState(ManagementState.CANDIDATURA);

        // avancar de fase (PROPOSTA)
        avancar();

        singleton.setMessage("\nFase 'CANDIDATURA' fechada\n");
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
