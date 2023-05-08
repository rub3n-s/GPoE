package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Candidatura implements Cloneable, Serializable {
    private ArrayList<String> codigos;
    private long alunoAtribuido;

    static final long serialVersionUID = 1L;

    public Candidatura(long alunoAtribuido, ArrayList<String> codigos) {
        this.codigos = codigos;
        this.alunoAtribuido = alunoAtribuido;
    }

    /* getters */
    public ArrayList<String> getCodigos() { return codigos; }

    public long getAlunoAtribuido() { return alunoAtribuido; }

    /* setters */
    public void setCodigo(String codigo) { this.codigos.add(codigo); }

    public void setAlunoAtribuido(long numero) { this.alunoAtribuido = numero; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(alunoAtribuido);
        for (var c : codigos)
            sb.append(", " + c);
        sb.append("\n");
        return sb.toString();
    }
}
