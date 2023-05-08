package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Proposta implements Cloneable, Serializable {
    private String tipo;    // T1, T2 ou T3
    private String codigo;  // por ex.: P031
    private String titulo;

    static final long serialVersionUID = 1L;

    public Proposta(String tipo, String codigo, String titulo) {
        this.tipo = tipo;
        this.codigo = codigo;
        this.titulo = titulo;
    }

    /* getters */
    public String getTipo() { return tipo; }

    public String getCodigo() { return codigo; }

    public String getTitulo() { return titulo; }

    public String toString() { return null; }

    public String getRamoDestino() { return null; }

    public long getAlunoAtribuido() { return 0; }

    public String getEntAcolhimento() { return null; }

    /* projeto */
    public String getDocente() { return null; }

    /* setters */
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDocente(String docente) { }

    public void setRamoDestino(String areaDestino) { }

    public void setEntAcolhimento(String entAcolhimento) { }

    public void setAlunoAtribuido(long numero) { }

    @Override
    public Object clone() throws CloneNotSupportedException { return super.clone(); }
}
