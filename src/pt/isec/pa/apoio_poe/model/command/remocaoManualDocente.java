package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class remocaoManualDocente extends CommandAdapter {
    private String codigo;
    private String email;

    public remocaoManualDocente(ManagementData receiver, String codigo, String email) {
        super(receiver);
        this.codigo = codigo;
        this.email = email;
    }

    @Override
    public boolean execute() { return receiver.remocaoManualDocente(codigo,email); }

    @Override
    public boolean undo() {
        return receiver.atribuicaoManualDocente(codigo,email);
    }
}
