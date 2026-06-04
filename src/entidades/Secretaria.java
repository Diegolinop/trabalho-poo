package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a secretária da clínica
 * Responsável por gerenciar pacientes, consultas e relatórios.
 * Herda os dados pessoais de Pessoa.
 */
public class Secretaria extends Pessoa {
    /** Matrícula de identificação da secretária na clínica. */
    private String matricula;
    
    /**
     * 
     * @param cpf
     * @param nome
     * @param sobrenome
     * @param telefone
     * @param email
     * @param endereco
     * @param matricula 
     */
    public Secretaria(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String matricula) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.matricula = matricula;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    /**
     * Cadastra um novo paciente na lista da clínica
     * @param pacientesClinica
     * @param novoPaciente 
     */
    public void cadastrarPaciente(List<Paciente> pacientesClinica, Paciente novoPaciente) {
        pacientesClinica.add(novoPaciente);
    }
    
    /**
     * Remove um paciente da lista da clínica buscando pelo CPF.
     * @param pacientesClinica
     * @param cpf
     */
    public void removerPaciente(List<Paciente> pacientesClinica, String cpf) {
        for (Paciente paciente : pacientesClinica) {
            if(paciente.getCpf().equals(cpf)) {
                pacientesClinica.remove(paciente);
                System.out.println("Paciente " + paciente.getNomeCompleto() + " portador do cpf " + paciente.getCpf() + " removido com sucesso");
                return;
            }
        }
        System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
    }
    
    /**
     * Busca um paciente pelo CPF.
     * @param pacientesClinica
     * @param cpf
     * @return 
     */
    public Paciente buscarPacientePorCpf(List<Paciente> pacientesClinica, String cpf) {
        for (Paciente paciente : pacientesClinica) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
        return null;
    }
    
    /**
     * Atualiza os dados de contato e convênio de um paciente existente.
     * @param paciente
     * @param telefone
     * @param email
     * @param endereco
     * @param convenio 
     */
    public void atualizarPaciente(Paciente paciente, String telefone, String email, String endereco, String convenio) {
        paciente.setTelefone(telefone);
        paciente.setEmail(email);
        paciente.setEndereco(endereco);
        paciente.setTipoConvenio(convenio);
        System.out.println("Dados do paciente atualizados com sucesso");
    }
    
    /**
     * Agenda uma nova consulta adicionando-a na lista geral da clínica
     * @param consultasClinica
     * @param novaConsulta 
     */
    public void agendarConsulta(List<Consulta> consultasClinica, Consulta novaConsulta) {
        consultasClinica.add(novaConsulta);
        System.out.println("Consulta de " + novaConsulta.getPaciente().getNomeCompleto() + 
                " com Dr(a). " + novaConsulta.getMedico().getNomeCompleto() + " agendada com sucesso!");
    }
    
    /**
     * Cancela uma consulta, tirando ela da lista da clínica
     * @param consultasClinica
     * @param consulta 
     */
    public void cancelarConsulta(List<Consulta> consultasClinica, Consulta consulta) {
        if (consultasClinica.contains(consulta)) {
            consultasClinica.remove(consulta);
            System.out.println("Consulta cancelada com sucesso.");
        } else {
            System.out.println("Não foi possível localizar a consulta para cancelamento.");
        }
    }
}
