package entidades;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    
    public Medico() {}
    
    public Medico(String cpf, String nome, String sobrenome, String telefone, String endereco, String especialidade, String crm) {
        super(cpf, nome, sobrenome, telefone, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
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
}
