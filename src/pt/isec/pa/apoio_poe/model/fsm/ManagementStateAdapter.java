package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.command.ICommand;
import pt.isec.pa.apoio_poe.model.data.ManagementData;

import java.util.ArrayList;

abstract class ManagementStateAdapter implements IManagementState, ICommand {
    ManagementData data;
    ManagementContext context;

    protected ManagementStateAdapter(ManagementContext context, ManagementData data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(IManagementState newState) {
        context.changeState(newState);
    }

    @Override
    public ManagementState getState() {
        return null;
    }

    /* ir para fase seguinte */
    @Override
    public boolean avancar() {
        return false;
    }

    /* fechar fase */
    @Override
    public boolean fechar() {
        return false;
    }

    /* ir para fase anterior */
    @Override
    public boolean voltar() {
        return false;
    }

    /* undo / redo */
    @Override
    public boolean hasUndo() { return false; }

    @Override
    public boolean undo() { return false; }

    @Override
    public boolean hasRedo() { return false; }

    @Override
    public boolean redo() { return false; }
}
