package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class atribuicaoManualAluno extends CommandAdapter {
    private String codigo;
    private long numAluno;

    public atribuicaoManualAluno(ManagementData receiver, String codigo, long numAluno) {
        super(receiver);
        this.codigo = codigo;
        this.numAluno = numAluno;
    }

    @Override
    public boolean execute() { return receiver.atribuicaoManualAluno(codigo,numAluno); }

    @Override
    public boolean undo() {
        return receiver.remocaoManualAluno(codigo,numAluno);
    }
}
