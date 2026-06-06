package models;

/**
 * Representa a secretária da clínica Responsável por gerenciar pacientes,
 * consultas e relatórios. Herda os dados pessoais de Pessoa.
 */
public class Secretaria extends Pessoa {

    /**
     * Matrícula de identificação da secretária na clínica.
     */
    private String matricula;

    /**
     *
     * @param cpf
     * @param nome
     * @param sobrenome
     * @param telefone
     * @param email
     * @param endereco
     * @param matricula
     */
    public Secretaria(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String matricula) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
