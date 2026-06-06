/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 * Representa a consulta para um paciente
 */
public class Consulta {

    private String data;
    private String horario;

    private Medico medico;
    private Paciente paciente;

    // Consulta normal: duração de 1 hora, retorno: duração de 30 minutos).
    private String tipo;

    public Consulta(String data, String horario, Medico medico, Paciente paciente, String tipo) {
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.paciente = paciente;
        this.tipo = tipo;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDuracao() {
        if (this.tipo == null) return "Tipo não definido";
        if (this.tipo.equalsIgnoreCase("normal")) return "1 hora";
        else return "30 minutos";
    }
}
