package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class OrientadoresFechadaState extends ManagementStateAdapter {
    public OrientadoresFechadaState(ManagementContext context, ManagementData data) {
        super(context,data);
    }

    @Override
    public ManagementState getState() {
        return ManagementState.ORIENTADORES_FECHADA;
    }

    @Override
    public boolean avancar() {
        changeState(ManagementState.CONSULTA.createState(context, data));
        return true;
    }

    @Override
    public boolean voltar() {
        if (data.isFechada(ManagementState.PROPOSTA)) {
            changeState(ManagementState.PROPOSTA_FECHADA.createState(context, data));
            return true;
        }
        changeState(ManagementState.PROPOSTA.createState(context,data));
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
