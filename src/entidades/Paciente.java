package entidades;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private String tipoConvenio;
    private String dataNascimento;
    private DadosAdicionais dadosAdicionais;
    private List<Prontuario> prontuarios;
    
    public Paciente(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String tipoConvenio, String dataNascimento) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.tipoConvenio = tipoConvenio;
        this.dataNascimento = dataNascimento;
        this.prontuarios = new ArrayList<>();
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
    
    public DadosAdicionais getDadosAdicionais() {
        return this.dadosAdicionais;
    }
    public void setDadosAdicionais(DadosAdicionais dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }
    
    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
    public void removerProntuario(Prontuario prontuario) {
        if (this.prontuarios.contains(prontuario)) {
            this.prontuarios.remove(prontuario);
        }
    }
    public List<Prontuario> getProntuarios() {
        return this.prontuarios;
    }
}
