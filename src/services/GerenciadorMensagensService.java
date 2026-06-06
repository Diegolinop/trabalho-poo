package services;

import java.util.List;

import models.Consulta;
import models.Paciente;
import repositories.ConsultaRepository;

/**
 * Responsavel por simular o envio de mensagens para pacientes com consultas.
 */
public class GerenciadorMensagensService {

    private ConsultaRepository consultaRepository;

    public GerenciadorMensagensService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void enviarMensagens(String diaSeguinte) {
        List<Consulta> consultasDoDia = consultaRepository.buscarPorData(diaSeguinte);

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
            System.out.println("Consulta: " + consulta.getData() + " às " + consulta.getHorario()
                    + " | Dr(a). " + consulta.getMedico().getNomeCompleto()
                    + " | Duração: " + consulta.getDuracao());

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

    public void enviarEmail(Paciente paciente, Consulta consulta) {
        System.out.println("  E-MAIL SIMULADO -> " + paciente.getEmail());
        System.out.println("  Assunto: Lembrete de consulta");
        System.out.println("  Mensagem: Olá, " + paciente.getNome() + " Você tem uma consulta amanhã ("
                + consulta.getData() + ") às " + consulta.getHorario()
                + " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }

    public void enviarSMS(Paciente paciente, Consulta consulta) {
        System.out.println("  SMS -> " + paciente.getTelefone());
        System.out.println("  Mensagem: Consulta amanhã ("
                + consulta.getData() + ") às " + consulta.getHorario()
                + " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }
}
