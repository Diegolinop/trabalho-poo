package entidades;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

public class Paciente extends Pessoa {
    private String tipoConvenio;
    private LocalDate dataNascimento;
    
    // Acesso somente se for médico
    private Boolean fuma;
    private Boolean bebe;
    private double colesterol;
    private Boolean diabetes;
    private Boolean doencaCardiaca;
    private ArrayList<String> cirurgias;
    private ArrayList<String> alergias;
    
    public Paciente() {
        this.alergias = new ArrayList<>();
        this.cirurgias = new ArrayList<>();
    }
    
    public Paciente(String cpf, String nome, String sobrenome, String telefone, String endereco, String tipoConvenio, LocalDate dataNascimento) {
        super(cpf, nome, sobrenome, telefone, endereco);
        this.tipoConvenio = tipoConvenio;
        this.dataNascimento = dataNascimento;
        this.fuma = false;
        this.bebe = false;
        this.colesterol = 0;
        this.diabetes = false;
        this.doencaCardiaca = false;
        this.alergias = new ArrayList<>();
        this.cirurgias = new ArrayList<>();
    }
    
    public String getTipoConvenio(){
        return this.tipoConvenio;
    }
    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }
    
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }
    
    public Period calcularIdade() {
        Period idade = Period.between(LocalDate.now(), getDataNascimento());
        System.out.println("Idade do Paciente " + getNome() + ":");
        System.out.println(idade.getYears() + "anos e " + idade.getMonths());
        return idade;
    }
    
    public Boolean getFuma() {
        return this.fuma;
    }
    public void setFuma(Boolean fuma) {
        this.fuma = fuma;
    }

    public Boolean getBebe() {
        return this.bebe;
    }
    public void setBebe(Boolean bebe) {
        this.bebe = bebe;
    }

    public double getColesterol() {
        return this.colesterol;
    }
    public void setColesterol(double colesterol) {
        this.colesterol = colesterol;
    }

    public Boolean getDiabetes() {
        return this.diabetes;
    }
    public void setDiabetes(Boolean diabetes) {
        this.diabetes = diabetes;
    }

    public Boolean getDoencaCardiaca() {
        return this.doencaCardiaca;
    }
    public void setDoencaCardiaca(Boolean doencaCardiaca) {
        this.doencaCardiaca = doencaCardiaca;
    }

    public ArrayList<String> getAlergias() {
        return this.alergias;
    }
    public void adicionarAlergia(String alergia){
        this.alergias.add(alergia);
    }
    public void removerAlergia(String alergia){
        if (this.alergias.contains(alergia)) {
            this.alergias.remove(alergia);
        }
    }

    public ArrayList<String> getCirurgias() {
        return this.cirurgias;
    }

    public void adicionarCirurgia(String cirurgia) {
        this.cirurgias.add(cirurgia);
    }

    public void removerCirurgia(String cirurgia) {
        if (this.cirurgias.contains(cirurgia)) {
            this.cirurgias.remove(cirurgia);
        }
    }
    
    @Override
    public String toString() {
        return "Paciente{nome=" + getNome() + ", cpf=" + getCpf() + "}";
    }
}
