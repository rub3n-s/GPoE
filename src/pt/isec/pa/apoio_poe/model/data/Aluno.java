package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Aluno implements Cloneable, Serializable {
    private long numero;
    private String nome;
    private String email;
    private String siglaCurso;
    private String siglaRamo;
    private double classificacao;
    private boolean estagio;

    static final long serialVersionUID = 1L;

    public Aluno(long numero, String nome, String email, String siglaCurso,
                 String siglaRamo, double classificacao, boolean estagio) {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
        this.siglaCurso = siglaCurso;
        this.siglaRamo = siglaRamo;
        this.classificacao = classificacao;
        this.estagio = estagio;
    }

    /* getters */
    public long getNumero() { return numero; }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public String getSiglaCurso() { return siglaCurso; }

    public String getSiglaRamo() { return siglaRamo; }

    public double getClassificacao() { return classificacao; }

    /* setters */
    public boolean isEstagio() { return estagio; }

    public void setNome(String nome) { this.nome = nome; }

    public void setSiglaCurso(String siglaCurso) { this.siglaCurso = siglaCurso; }

    public void setSiglaRamo(String siglaRamo) { this.siglaRamo = siglaRamo; }

    public void setClassificacao(double classificacao) { this.classificacao = classificacao; }

    public void setEstagio(boolean estagio) { this.estagio = estagio; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(numero + ", " + nome + ", " + email + ", " + siglaCurso + ", "
                + siglaRamo + ", " + classificacao + ", " + estagio +"\n");
        return sb.toString();
    }
}
