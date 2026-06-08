package models;

/**
 * Representa uma consulta agendada na clínica.
 * Associa um paciente a um médico em uma data e horário específicos.
 */
public class Consulta {
    
    /** Data da consulta. */
    private String data;
    
    /** Horário da consulta. */
    private String horario;

    /** Médico responsável pela consulta. */
    private Medico medico;
    
    /** Paciente atendido na consulta. */
    private final Paciente paciente;

    /** Tipo da consulta: "Normal" (1 hora) ou "Retorno" (30 minutos). */
    private String tipo;
    
    /**
     * Cria uma nova consulta com todos os dados necessários.
     * * @param data Data da consulta.
     * @param horario Horário da consulta.   
     * @param medico Médico responsável.
     * @param paciente Paciente a ser atentido.
     * @param tipo Tipo de consulta: "Normal" ou "Retorno"
     */
    public Consulta(String data, String horario, Medico medico, Paciente paciente, String tipo) {
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.paciente = paciente;
        setTipo(tipo);
    }
    
    /**
     * Retorna a data da consulta.
     * @return data 
     */
    public String getData() {
        return this.data;
    }
    
    /**
     * Define a data da consulta.
     * @param data nova data.
     */
    public void setData(String data) {
        this.data = data;
    }
    
    /**
     * Retorna o horário da consulta.
     * @return horário da consulta.
     */
    public String getHorario() {
        return this.horario;
    }
    
    /**
     * Define o horário da consulta.
     * @param horario novo horário.
     */
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    /**
     * Retorna o médico responsável pela consulta.
     * @return médico da consulta.
     */
    public Medico getMedico() {
        return this.medico;
    }
    
    /**
     * Define o médico responsável pela consulta.
     * @param medico novo médico.
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
    /**
     * Retorna o paciente atendido na consulta.
     * @return paciente da consulta.
     */
    public Paciente getPaciente() {
        return this.paciente;
    }
    
    /**
     * Retorna o tipo da consulta.
     * @return "Normal" ou "Retorno".
     */
    public String getTipo() {
        return this.tipo;
    }
    
    /**
     * Define o tipo da consulta.
     * @param tipo "Normal" ou "Retorno".
     */
    public void setTipo(String tipo) {
        if (tipo == null || !(tipo.equalsIgnoreCase("Normal") || tipo.equalsIgnoreCase("Retorno"))) {
            throw new IllegalArgumentException("Tipo de consulta inválido. Deve ser 'Normal' ou 'Retorno'.");
        }
        this.tipo = tipo;
    }
    
    /**
     * Retorna a duração da consulta com base no tipo.
     * Consulta normal tem duração de 1 hora; retorno, 30 minutos.
     *
     * @return string com a duração da consulta.
     */
    public String getDuracao() {
        if (this.tipo == null) return "Tipo não definido";
        
        if (this.tipo.equalsIgnoreCase("Normal")) {
            return "1 hora";
        } else if (this.tipo.equalsIgnoreCase("Retorno")) {
            return "30 minutos";
        }
        
        return "";
    }
}