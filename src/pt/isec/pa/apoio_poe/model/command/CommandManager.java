package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.data.Singleton;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager {
    private Deque<ICommand> history;
    private Deque<ICommand> redoCmds;
    Singleton singleton;

    public CommandManager() {
        singleton = Singleton.Singleton();
        history = new ArrayDeque<>();
        redoCmds = new ArrayDeque<>();
    }

    public boolean invokeCommand(ICommand cmd) {
        redoCmds.clear();
        if (cmd.execute()) {
            history.push(cmd);
            return true;
        }
        history.clear();
        return false;
    }

    // undo/redo
    public boolean undo() {
        if (history.isEmpty()) {
            singleton.setMessage("Nao existem operacoes para serem revertidas.");
            return false;
        }
        ICommand cmd = history.pop();
        cmd.undo();
        redoCmds.push(cmd);
        singleton.setMessage("Operacao de 'undo' efetuada com sucesso.");
        return true;
    }

    public boolean redo() {
        if (redoCmds.isEmpty()) {
            singleton.setMessage("Nao existem operacoes para serem refeitas.");
            return false;
        }
        ICommand cmd = redoCmds.pop();
        cmd.execute();
        history.push(cmd);
        singleton.setMessage("Operacao de 'redo' efetuada com sucesso.");
        return true;
    }

    public boolean hasUndo() {
        return history.size() > 0;
    }

    public boolean hasRedo() {
        return redoCmds.size()>0;
    }
}
