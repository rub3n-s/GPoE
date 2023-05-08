package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;
import pt.isec.pa.apoio_poe.model.data.Singleton;

public class OrientadoresState extends ManagementStateAdapter {
    Singleton singleton;

    public OrientadoresState(ManagementContext context, ManagementData data) {
        super(context,data);
        singleton = Singleton.Singleton();
    }

    @Override
    public ManagementState getState() {
        return ManagementState.ORIENTADORES;
    }

    @Override
    public boolean avancar() {
        // se a CONSULTA ja estiver fechada avanca para a fase CONSULTA_FECHADA
        changeState(ManagementState.CONSULTA.createState(context,data));
        return true;
    }

    @Override
    public boolean voltar() {
        // se a PROPOSTA ja estiver fechada nao e possivel voltar
        if (data.isFechada(ManagementState.PROPOSTA)) {
            changeState(ManagementState.PROPOSTA_FECHADA.createState(context,data));
            return false;
        }
        changeState(ManagementState.PROPOSTA.createState(context,data));
        return true;
    }

    @Override
    public boolean fechar() {
        // guarda a informação que esta fechada no ManagementData
        data.fechaState(ManagementState.ORIENTADORES);

        // avancar de fase (CONSULTA)
        avancar();

        singleton.setMessage("\nFase 'ORIENTADORES' fechada\n");
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
