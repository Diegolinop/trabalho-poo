package models;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private String tipoConvenio;
    private String dataNascimento;
    private HistoricoMedico historicoMedico;
    private List<Prontuario> prontuarios;

    public Paciente(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String tipoConvenio, String dataNascimento) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.tipoConvenio = tipoConvenio;
        this.dataNascimento = dataNascimento;
        this.prontuarios = new ArrayList<>();
    }

    public String getTipoConvenio() {
        return this.tipoConvenio;
    }

    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public HistoricoMedico getHistoricoMedico() {
        return this.historicoMedico;
    }

    public void setHistoricoMedico(HistoricoMedico historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(this.prontuarios);
    }
    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
    public void removerProntuario(Prontuario prontuario) {
        this.prontuarios.remove(prontuario);
    }
}
