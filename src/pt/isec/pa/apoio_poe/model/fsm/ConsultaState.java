package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class ConsultaState extends ManagementStateAdapter {
    public ConsultaState(ManagementContext context, ManagementData data) { super(context,data); }

    @Override
    public ManagementState getState() {
        return ManagementState.CONSULTA;
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
