package entidades;
import java.util.ArrayList;

public class Paciente extends Pessoa {
    private String tipoConvenio;
    private String dataNascimento;
    private Boolean fuma;
    private Boolean bebe;
    private String colesterol;
    private Boolean diabetes;
    private Boolean doencaCardiaca;
    private ArrayList<String> alergias;
    private ArrayList<String> cirurgias;
    
    public Paciente() {
        this.alergias = new ArrayList<>();
        this.cirurgias = new ArrayList<>();
    }
    
    public Paciente(String cpf, String nome, String sobrenome, String telefone, String endereco, String tipoConvenio, String dataNascimento) {
        super(cpf, nome, sobrenome, telefone, endereco);
        this.tipoConvenio = tipoConvenio;
        this.dataNascimento = dataNascimento;
        this.fuma = false;
        this.alergias = new ArrayList();
        this.fuma = false;
        this.bebe = false;
        this.colesterol = "";
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
    
    public String getDataNascimento() {
        return this.dataNascimento;
    }
    
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getColesterol() {
        return this.colesterol;
    }

    public void setColesterol(String colesterol) {
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
}
