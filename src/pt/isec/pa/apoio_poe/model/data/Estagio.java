package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Estagio extends Proposta implements Cloneable {
    private String ramoDestino;
    private String entAcolhimento;
    private long alunoAtribuido;

    public Estagio(String tipo, String codigo, String ramoDestino, String titulo, String entAcolhimento, long alunoAtribuido) {
        super(tipo,codigo,titulo);
        this.ramoDestino = ramoDestino;
        this.entAcolhimento = entAcolhimento;
        if (alunoAtribuido > 0)
            this.alunoAtribuido = alunoAtribuido;
        else
            this.alunoAtribuido = 0;
    }

    /* getters */
    @Override
    public String getRamoDestino() { return ramoDestino; }

    @Override
    public String getEntAcolhimento() {
        return entAcolhimento;
    }

    @Override
    public long getAlunoAtribuido() {
        return alunoAtribuido;
    }

    /* setters */
    @Override
    public void setRamoDestino(String ramoDestino) { this.ramoDestino = ramoDestino; }

    @Override
    public void setEntAcolhimento(String entAcolhimento) { this.entAcolhimento = entAcolhimento; }

    @Override
    public void setAlunoAtribuido(long alunoAtribuido) { this.alunoAtribuido = alunoAtribuido; }

    @Override
    public Object clone() throws CloneNotSupportedException { return super.clone(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getTipo() + ", " + this.getCodigo() + ", " + ramoDestino + ", " + this.getTitulo() + ", " +
                entAcolhimento);
        if (alunoAtribuido > 0)
            sb.append(", " + alunoAtribuido);
        sb.append("\n");
        return sb.toString();
    }
}
