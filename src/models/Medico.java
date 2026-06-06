package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um médico da clínica. Herda os dados pessoais de Pessoa.
 */
public class Medico extends Pessoa {

    /**
     * Registro do conselho de medicina do médico -> Idenficação.
     */
    private String crm;

    private String especialidade;
    private List<Prontuario> prontuarios;

    /**
     *
     * @param cpf
     * @param nome
     * @param sobrenome
     * @param telefone
     * @param email
     * @param endereco
     * @param especialidade
     * @param crm
     */
    public Medico(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String especialidade, String crm) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
        this.prontuarios = new ArrayList<>();
    }

    public String getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return this.crm;
    }

    public List<Prontuario> getProntuarios() {
        return this.prontuarios;
    }
}
