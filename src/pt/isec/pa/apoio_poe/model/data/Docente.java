package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Docente implements Cloneable, Serializable {
    private String nome;
    private String email;

    static final long serialVersionUID = 1L;

    public Docente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    /* getters */
    public String getNome() { return nome; }

    public String getEmail() { return email; }

    /* setters */
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public Object clone() throws CloneNotSupportedException { return super.clone(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome + ", " + email + "\n");
        return sb.toString();
    }
}
