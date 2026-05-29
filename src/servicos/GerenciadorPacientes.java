package servicos;

import entidades.Paciente;
import java.util.Map;
import java.util.HashMap;

public abstract class GerenciadorPacientes {
    Map<String, Paciente> pacientes = new HashMap<>();
    
    public GerenciadorPacientes() {}
    
    public void adicionarCliente(Paciente paciente) {
        pacientes.put(paciente.getCpf(), paciente);
    }
    
    public void removerPaciente(String cpf) {
        pacientes.put(cpf, null);
    }
    
    public void lerDadosPaciente(String cpf) {
        Paciente paciente = pacientes.get(cpf);
        if (paciente == null){
            System.out.println("Paciente com cpf " + cpf + " não foi encontrado!");
        } else{
            System.out.println("CPF: " + paciente.getCpf());
            System.out.println("Nome Completo: " + paciente.getNome() + paciente.getSobrenome());
            System.out.println("Endereço: " + paciente.getEndereco());
            System.out.println("Telefone: " + paciente.getTelefone());
            System.out.println("Data de Nascimento: " + paciente.getDataNascimento());
            System.out.println("TipoDeConvenio" + paciente.getTipoConvenio());
        }
    }
}
