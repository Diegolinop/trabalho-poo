package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o prontuário médico de um paciente
 */
public class Prontuario {

    private Paciente paciente;
    private Medico medico;
    private String data;
    private List<String> sintomas;
    private String diagnostico;
    private String prescricao;

    /**
     * O Prontuário é criado com as variáveis imutáveis, que são o paciente, o
     * médico e a data
     *
     * @param paciente
     * @param medico
     * @param data
     */

    public Prontuario(Paciente paciente, Medico medico, String data) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.sintomas = new ArrayList<>();
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public String getData() {
        return this.data;
    }

    public void adicionarSintoma(String sintoma) {
        this.sintomas.add(sintoma);
    }

    public void removerSintoma(String sintoma) {
        if (this.sintomas.contains(sintoma)) {
            this.sintomas.remove(sintoma);
        }
    }

    public List<String> getSintomas() {
        return this.sintomas;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return this.prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }
}
