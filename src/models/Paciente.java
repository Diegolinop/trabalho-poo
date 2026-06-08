package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um paciente da clínica.
 * Herda os dados pessoais de {@link Pessoa} e adiciona
 * informações como tipo de convênio, data de nascimento,
 * histórico médico e prontuários associados.
 */
public class Paciente extends Pessoa {
    
    /** Tipo de convênio do paciente: "Particular" ou "Plano de Saúde". */
    private String tipoConvenio;
    
    /** Data de nascimento do paciente. */
    private String dataNascimento;
    
    /**
     * Histórico médico do paciente com informações de saúde adicionais.
     * Preenchido exclusivamente pelo médico.
     */
    private HistoricoMedico historicoMedico;
    
    /** Lista de prontuários associados a este paciente. */
    private final List<Prontuario> prontuarios;
    
    /**
     * Cria um novo paciente com todos os dados necessários.
     *
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @param nome Primeiro nome.
     * @param sobrenome Sobrenome.
     * @param telefone Telefone de contato (pode ser null).
     * @param email E-mail de contato (pode ser null).
     * @param endereco Endereço residencial.
     * @param tipoConvenio Tipo de convênio: "Particular" ou "Plano de Saúde".
     * @param dataNascimento Data de nascimento no formato dd/mm/aaaa.
     */
    public Paciente(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String tipoConvenio, String dataNascimento) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.tipoConvenio = tipoConvenio;
        this.dataNascimento = dataNascimento;
        this.prontuarios = new ArrayList<>();
    }

    /**
     * Retorna o tipo de convênio do paciente.
     * @return "Particular" ou "Plano de Saúde".
     */
    public String getTipoConvenio() {
        return this.tipoConvenio;
    }
    
    /**
     * Define o tipo de convênio do paciente.
     * @param tipoConvenio "Particular" ou "Plano de Saúde".
     */
    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }
    
    /**
     * Retorna a data de nascimento do paciente.
     * @return data.
     */
    public String getDataNascimento() {
        return this.dataNascimento;
    }
    
    /**
     * Define a data de nascimento do paciente.
     * @param dataNascimento nova data.
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    /**
     * Retorna o histórico médico do paciente.
     * Pode ser null caso ainda não tenha sido cadastrado pelo médico.
     * @return histórico médico ou null.
     */
    public HistoricoMedico getHistoricoMedico() {
        return this.historicoMedico;
    }
    
    /**
     * Define o histórico médico do paciente.
     * @param historicoMedico novo histórico médico, ou null para removê-lo.
     */
    public void setHistoricoMedico(HistoricoMedico historicoMedico) {
        this.historicoMedico = historicoMedico;
    }
    
    /**
     * Retorna uma cópia da lista de prontuários do paciente.
     * @return lista de prontuários associados.
     */
    public List<Prontuario> getProntuarios() {
        return new ArrayList<>(this.prontuarios);
    }
    
    /**
     * Adiciona um prontuário.
     * @param prontuario prontuário a ser adicionado.
     */
    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
    
    /**
     * Remove um prontuário da lista.
     * @param prontuario prontuário a ser removido.
     */
    public void removerProntuario(Prontuario prontuario) {
        this.prontuarios.remove(prontuario);
    }
}
