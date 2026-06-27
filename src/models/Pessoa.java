package models;

import javax.persistence.*;

/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Serve como base para {@link Medico}, {@link Paciente} e {@link Secretaria},
 * centralizando os dados pessoais comuns a todos os perfis.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa {

    /**
     * CPF da pessoa no formato XXXXXXXXXXX
     */
    @Id
    @Column(nullable = false, unique = true)
    private String cpf;

    /**
     * Primeiro nome da pessoa.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Sobrenome da pessoa.
     */
    @Column(nullable = false)
    private String sobrenome;

    /**
     * Telefone de contato. Pode ser null.
     */
    private String telefone;

    /**
     * E-mail de contato. Pode ser null.
     */
    private String email;

    /**
     * Endereço residencial da pessoa.
     */
    private String endereco;

    /**
     * Construtor padrão vazio exigido pela especificação do JPA.
     */
    public Pessoa() {
    }

    /**
     * Cria uma nova pessoa validando o CPF antes de armazenar os dados.
     *
     * @param cpf       CPF no formato XXX.XXX.XXX-XX.
     * @param nome      Primeiro nome.
     * @param sobrenome Sobrenome.
     * @param telefone  Telefone de contato (pode ser null).
     * @param email     E-mail de contato (pode ser null).
     * @param endereco  Endereço residencial.
     * @throws IllegalArgumentException se o CPF não estiver no formato correto.
     */
    public Pessoa(String cpf, String nome, String sobrenome, String telefone, String email, String endereco) {
        validarCpf(cpf);
        this.cpf = cpf;

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    /**
     * Retorna o CPF da pessoa.
     *
     * @return CPF no formato XXX.XXX.XXX-XX.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Valida o formato do CPF informado.
     *
     * @param cpf CPF a ser validado.
     * @throws IllegalArgumentException se o formato for inválido.
     */
    private static void validarCpf(String cpf) {
        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("\nCPF inválido. Use o formato XXX.XXX.XXX-XX");
        }
    }

    /**
     * Retorna o primeiro nome da pessoa.
     *
     * @return nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o primeiro nome da pessoa.
     *
     * @param nome novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o sobrenome da pessoa.
     *
     * @return sobrenome.
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Define o sobrenome da pessoa.
     *
     * @param sobrenome novo sobrenome.
     */
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    /**
     * Retorna o telefone de contato.
     *
     * @return telefone ou null se não cadastrado.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone de contato.
     *
     * @param telefone novo telefone.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o e-mail de contato.
     *
     * @return e-mail ou null se não cadastrado.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Define o e-mail de contato.
     *
     * @param email novo e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o endereço residencial da pessoa.
     *
     * @return endereço.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço residencial da pessoa.
     *
     * @param endereco novo endereço.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o nome completo da pessoa, composto por nome e sobrenome.
     *
     * @return nome + sobrenome.
     */
    public String getNomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }
}