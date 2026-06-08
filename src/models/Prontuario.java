package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa o prontuário médico de um paciente.
 * Registra os sintomas relatados, o diagnóstico e a prescrição
 * de tratamento definidos pelo médico durante o atendimento.
 * 
 * O paciente, o médico, a data e o ID são definidos na criação
 * e não podem ser alterados posteriormente.
 */
public class Prontuario {
    
    /** Paciente atendido neste prontuário. */
    private final Paciente paciente;
    
    /** Médico responsável pelo atendimento. */
    private final Medico medico;
    
    /** Data do atendimento. */
    private final String data;
    
    /** Lista de sintomas relatados pelo paciente durante a consulta. */
    private final List<String> sintomas;

    /** Diagnóstico definido pelo médico. */
    private String diagnostico;

    /** Prescrição de tratamento definida pelo médico. */
    private String prescricao;

    /** Identificador único do prontuário dentro da lista do médico. */
    private final int id;

    /**
     * Cria um novo prontuário com os dados imutáveis do atendimento.
     * Sintomas, diagnóstico e prescrição devem ser adicionados separadamente.
     *
     * @param paciente Paciente atendido.
     * @param medico Médico responsável pelo atendimento.
     * @param data Data do atendimento.
     * @param id Identificador único do prontuário para este médico.
     */
    public Prontuario(Paciente paciente, Medico medico, String data, int id) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.id = id;
        this.sintomas = new ArrayList<>();
    }
    
    /**
     * Retorna o paciente atendido neste prontuário.
     * @return paciente do prontuário.
     */
    public Paciente getPaciente() {
        return this.paciente;
    }
    
    /**
     * Retorna o médico responsável pelo atendimento.
     * @return médico do prontuário.
     */
    public Medico getMedico() {
        return this.medico;
    }
    
    /**
     * Retorna a data do atendimento.
     * @return data no formato dd/mm/aaaa.
     */
    public String getData() {
        return this.data;
    }
    
    /**
     * Retorna o identificador único do prontuário.
     * @return ID do prontuário.
     */
    public int getId(){
        return this.id;
    }
    
     /**
     * Adiciona um sintoma à lista do prontuário.
     * @param sintoma sintoma.
     */
    public void adicionarSintoma(String sintoma) {
        this.sintomas.add(sintoma);
    }
    
    /**
     * Remove um sintoma da lista do prontuário.
     * Exibe mensagem caso o sintoma não seja encontrado.
     * @param sintoma sintoma a ser removido.
     */
    public void removerSintoma(String sintoma) {
        if (this.sintomas.contains(sintoma)) {
            this.sintomas.remove(sintoma);
        }
        else {
            System.out.println("Sintoma não encontrado no prontuário");
        }
    }
    
    /**
     * Retorna uma cópia da lista de sintomas do prontuário.
     * @return lista de sintomas relatados.
     */
    public List<String> getSintomas() {
        return new ArrayList<>(this.sintomas);
    }
    
    /**
     * Retorna o diagnóstico definido pelo médico.
     * @return diagnóstico do prontuário.
     */
    public String getDiagnostico() {
        return this.diagnostico;
    }
    
    /**
     * Define o diagnóstico do prontuário.
     * @param diagnostico diagnóstico definido pelo médico.
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    /**
     * Retorna a prescrição de tratamento definida pelo médico.
     * @return prescrição do prontuário.
     */
    public String getPrescricao() {
        return this.prescricao;
    }

    /**
     * Define a prescrição de tratamento do prontuário.
     * @param prescricao prescrição definida pelo médico.
     */
    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }
}
