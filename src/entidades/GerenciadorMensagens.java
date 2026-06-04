package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por simular o envio de mensagens (e-mail e SMS)
 * para pacientes com consultas no dia seguinte.
 */
public class GerenciadorMensagens {
    
    /**
     * Envia mensagens para todos os pacientes com consulta no dia seguinte.
     * Filtra por quem tem e-mail e/ou telefone cadastrado.
     * @param consultas lista geral de consultas da clínica
     * @param diaSeguinte data do dia seguinte no formato dd/mm/aaaa
     */
    public void enviarMensagens(List<Consulta> consultas, String diaSeguinte) {
        List<Consulta> consultasDoDia = filtrarConsultasPorData(consultas, diaSeguinte);

        if (consultasDoDia.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o dia " + diaSeguinte + ".");
            return;
        }

        System.out.println("\n--- ENVIANDO LEMBRETES PARA " + diaSeguinte + " ---");
        System.out.println("Total de consultas: " + consultasDoDia.size());
        System.out.println("--------------------------------------");

        for (Consulta consulta : consultasDoDia) {
            Paciente paciente = consulta.getPaciente();
            boolean temEmail = paciente.getEmail() != null;
            boolean temTelefone = paciente.getTelefone() != null;

            System.out.println("\nPaciente: " + paciente.getNomeCompleto());
            System.out.println("Consulta: " + consulta.getData() + " às " + consulta.getHorario() +
                    " | Dr(a). " + consulta.getMedico().getNomeCompleto() +
                    " | Duração: " + consulta.getDuracao());

            if (temEmail) {
                enviarEmail(paciente, consulta);
            }
            if (temTelefone) {
                enviarSMS(paciente, consulta);
            }
            if (!temEmail && !temTelefone) {
                System.out.println("Paciente sem e-mail e sem telefone cadastrado - não foi possível enviar lembrete.");
            }
        }
    }
    
    /**
     * Simula o envio de e-mail para o paciente.
     * @param paciente
     * @param consulta
     */
    public void enviarEmail(Paciente paciente, Consulta consulta) {
        System.out.println("  E-MAIL SIMULADO → " + paciente.getEmail());
        System.out.println("  Assunto: Lembrete de consulta");
        System.out.println("  Mensagem: Olá, " + paciente.getNome() + "Você tem uma consulta amanhã (" +
                consulta.getData() + ") às " + consulta.getHorario() +
                " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }
    
    /**
     * Simula o envio de SMS para o paciente.
     * @param paciente
     * @param consulta
     */
    public void enviarSMS(Paciente paciente, Consulta consulta) {
        System.out.println("  SMS → " + paciente.getTelefone());
        System.out.println("  Mensagem: Consulta amanhã (" +
                consulta.getData() + ") às " + consulta.getHorario() +
                " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }
    
    /**
     * Filtra as consultas de uma data específica.
     * @param consultas
     * @param data
     * @return 
     */
    private List<Consulta> filtrarConsultasPorData(List<Consulta> consultas, String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getData().equals(data)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
}
