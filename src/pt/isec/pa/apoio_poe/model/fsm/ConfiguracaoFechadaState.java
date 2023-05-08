package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class ConfiguracaoFechadaState extends ManagementStateAdapter {
    public ConfiguracaoFechadaState(ManagementContext context, ManagementData data) {
        super(context,data);
    }

    @Override
    public ManagementState getState() {
        return ManagementState.CONFIGURACAO_FECHADA;
    }

    @Override
    public boolean avancar() {
        if (data.isFechada(ManagementState.CANDIDATURA)) {
            changeState(ManagementState.CANDIDATURA_FECHADA.createState(context, data));
            return true;
        }
        changeState(ManagementState.CANDIDATURA.createState(context,data));
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
