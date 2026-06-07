package models;

/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Base para Medico, Paciente e Secretaria.
 */
public abstract class Pessoa {
    private String cpf;
    private String nome;
    private String sobrenome;

    private String telefone;
    private String email;

    private String endereco;
    
    /**
     *
     * @param cpf
     * @param nome
     * @param sobrenome
     * @param telefone
     * @param endereco
     */
    public Pessoa(String cpf, String nome, String sobrenome, String telefone, String email, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o nome completo da pessoa.
     *
     * @return nome + sobrenome
     */
    public String getNomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }
}