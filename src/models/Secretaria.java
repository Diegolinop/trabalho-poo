package models;

/**
 * Representa a secretária da clínica.
 * Responsável por gerenciar pacientes, consultas e relatórios.
 * Herda os dados pessoais de {@link Pessoa} e adiciona
 * a matrícula de identificação na clínica.
 */
public class Secretaria extends Pessoa {

    /** Matrícula de identificação da secretária na clínica. Formato: SEC-XXX. */
    private String matricula;

    /**
     * Cria uma nova secretária com todos os dados pessoais e a matrícula.
     *
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @param nome Primeiro nome.
     * @param sobrenome Sobrenome.
     * @param telefone Telefone de contato (pode ser null).
     * @param email E-mail de contato (pode ser null).
     * @param endereco Endereço residencial.
     * @param matricula Matrícula de identificação no formato SEC-XXX.
     */
    public Secretaria(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String matricula) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.matricula = matricula;
    }
    
    /**
     * Retorna a matrícula da secretária.
     * @return matrícula no formato SEC-XXX.
     */
    public String getMatricula() {
        return matricula;
    }
    
    /**
     * Define a matrícula da secretária.
     * @param matricula nova matrícula no formato SEC-XXX.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
