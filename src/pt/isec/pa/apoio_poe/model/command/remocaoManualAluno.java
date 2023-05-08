package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.data.ManagementData;

public class remocaoManualAluno extends CommandAdapter {
    private String codigo;
    private long numAluno;

    public remocaoManualAluno(ManagementData receiver, String codigo, long numAluno) {
        super(receiver);
        this.codigo = codigo;
        this.numAluno = numAluno;
    }

    @Override
    public boolean execute() {
        return receiver.remocaoManualAluno(codigo,numAluno);
    }

    @Override
    public boolean undo() {
        return receiver.atribuicaoManualAluno(codigo,numAluno);
    }
}
