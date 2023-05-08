package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Autoproposto extends Proposta implements Cloneable {
    private long alunoAtribuido;

    public Autoproposto(String tipo, String codigo, String titulo) {
        super(tipo,codigo,titulo);
    }

    public Autoproposto(String tipo, String codigo, String titulo, long alunoAtribuido) {
        super(tipo,codigo,titulo);
        this.alunoAtribuido = alunoAtribuido;
    }

    /* getters */
    @Override
    public long getAlunoAtribuido() { return alunoAtribuido; }

    /* setters */
    @Override
    public void setAlunoAtribuido(long alunoAtribuido) { this.alunoAtribuido = alunoAtribuido; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getTipo() + ", " + this.getCodigo() + ", " + this.getTitulo());
        if (alunoAtribuido > 0)
            sb.append(", " + alunoAtribuido);
        sb.append("\n");
        return sb.toString();
    }
}
