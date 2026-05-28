package entidades;

public class Secretaria extends Pessoa {
    private String matricula;
    
    public Secretaria() {}
    
    public Secretaria(String cpf, String nome, String sobrenome, String telefone, String endereco, String matricula) {
        super(cpf, nome, sobrenome, telefone, endereco);
        this.matricula = matricula;
    }
    
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
