package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um médico da clínica.
 * Herda os dados pessoais de {@link Pessoa} e adiciona
 * informações profissionais como CRM e especialidade,
 * além de gerenciar seus prontuários.
 */
public class Medico extends Pessoa {

    /**
     * Registro do conselho de medicina do médico - identificador único do médico.
     */
    private final String crm;
    
    /** Especialidade médica do profissional. */
    private String especialidade;
    
    /** Lista de prontuários registrados por este médico. */
    private final List<Prontuario> prontuarios;

    /**
     * Cria um novo médico com todos os dados pessoais e profissionais.
     *
     * @param cpf CPF do médico no formato XXX.XXX.XXX-XX.
     * @param nome Primeiro nome.
     * @param sobrenome Sobrenome.
     * @param telefone Telefone de contato.
     * @param email E-mail de contato.
     * @param endereco Endereço residencial.
     * @param especialidade Especialidade médica.
     * @param crm Número do CRM.
     */
    public Medico(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String especialidade, String crm) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
        this.prontuarios = new ArrayList<>();
    }
    
    /**
     * Retorna a especialidade médica do profissional.
     * @return especialidade do médico.
     */
    public String getEspecialidade() {
        return this.especialidade;
    }
    
    /**
     * Define a especialidade médica do profissional.
     * @param especialidade nova especialidade.
     */
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    /**
     * Retorna o CRM do médico.
     * @return número do CRM.
     */
    public String getCrm() {
        return this.crm;
    }
    
    /**
     * Retorna uma cópia da lista de prontuários registrados pelo médico.
     * @return lista de prontuários.
     */
    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(this.prontuarios);
    }
    
    /**
     * Adiciona um prontuário à lista do médico.
     * @param prontuario prontuário a ser adicionado.
     */
    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    } 
    
    /**
     * Remove um prontuário da lista do médico.
     * @param prontuario prontuário a ser removido.
     */
    public void removerProntuario(Prontuario prontuario) {
        this.prontuarios.remove(prontuario);
    }
}
