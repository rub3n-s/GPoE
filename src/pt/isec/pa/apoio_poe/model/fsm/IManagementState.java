package pt.isec.pa.apoio_poe.model.fsm;

import java.util.ArrayList;

public interface IManagementState {
    /* devolve o estado da maquina */
    ManagementState getState();

    /* fechar/avancar de fase */
    boolean fechar();
    boolean avancar();
    boolean voltar();

    /* undo/redo */
    boolean hasUndo();
    boolean undo();
    boolean hasRedo();
    boolean redo();
}
