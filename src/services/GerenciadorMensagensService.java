package services;

import java.util.List;

import models.Consulta;
import models.Paciente;
import repositories.ConsultaRepository;

/**
 * Serviço responsável por simular o envio de mensagens de lembrete
 * para pacientes com consultas agendadas no dia seguinte.
 * 
 * O envio é feito por e-mail e/ou SMS conforme os contatos cadastrados.
 */
public class GerenciadorMensagensService {
   
    /** Repositório de consultas utilizado para buscar os agendamentos do dia. */
    private final ConsultaRepository consultaRepository;
    
    /**
     * Cria o serviço de gerenciamento de mensagens.
     * @param consultaRepository repositório de consultas.
     */
    public GerenciadorMensagensService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }
    
    /**
     * Envia lembretes para todos os pacientes com consultas na data informada.
     * Para cada paciente, envia e-mail se tiver e-mail cadastrado,
     * SMS se tiver telefone cadastrado, ou exibe aviso caso não tenha nenhum contato.
     *
     * @param diaSeguinte data das consultas.
     */
    public void enviarMensagens(String diaSeguinte) {
        // Armazena as consultas do dia.
        List<Consulta> consultasDoDia = consultaRepository.buscarPorData(diaSeguinte);
        
        // Se não houver, exibe um aviso.
        if (consultasDoDia.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o dia " + diaSeguinte + ".");
            return;
        }
        
        // Layout para o lembrete.
        System.out.println("\n--- ENVIANDO LEMBRETES PARA " + diaSeguinte + " ---");
        System.out.println("Total de consultas: " + consultasDoDia.size());
        System.out.println("--------------------------------------");

        //  Manda para cada cada paciente que tem consulta.
        for (Consulta consulta : consultasDoDia) {
            Paciente paciente = consulta.getPaciente();
            boolean temEmail = paciente.getEmail() != null && !paciente.getEmail().isBlank();
            boolean temTelefone = paciente.getTelefone() != null && !paciente.getTelefone().isBlank();    

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
    
    public String gerarTextoMensagens(String diaSeguinte) {
        List<Consulta> consultasDoDia = consultaRepository.buscarPorData(diaSeguinte);
        StringBuilder sb = new StringBuilder();

        if (consultasDoDia.isEmpty()) {
            return "Nenhuma consulta encontrada para o dia " + diaSeguinte + ".";
        }

        sb.append("--- LEMBRETES PARA ").append(diaSeguinte).append(" ---\n");
        sb.append("Total de consultas: ").append(consultasDoDia.size()).append("\n");
        sb.append("--------------------------------------\n");

        for (Consulta consulta : consultasDoDia) {
            Paciente paciente = consulta.getPaciente();
            boolean temEmail = paciente.getEmail() != null && !paciente.getEmail().isBlank();
            boolean temTelefone = paciente.getTelefone() != null && !paciente.getTelefone().isBlank();

            sb.append("\nPaciente: ").append(paciente.getNomeCompleto()).append("\n");
            sb.append("Consulta: ").append(consulta.getData())
              .append(" às ").append(consulta.getHorario())
              .append(" | Dr(a). ").append(consulta.getMedico().getNomeCompleto())
              .append(" | Duração: ").append(consulta.getDuracao()).append("\n");

            if (temEmail) {
                sb.append("  [EMAIL -> ").append(paciente.getEmail()).append("]\n");
                sb.append("  Olá, ").append(paciente.getNome())
                  .append("! Você tem uma consulta amanhã (").append(consulta.getData())
                  .append(") às ").append(consulta.getHorario())
                  .append(" com Dr(a). ").append(consulta.getMedico().getNomeCompleto())
                  .append(".\n");
            }
            if (temTelefone) {
                sb.append("  [SMS -> ").append(paciente.getTelefone()).append("]\n");
                sb.append("  Consulta amanhã (").append(consulta.getData())
                  .append(") às ").append(consulta.getHorario())
                  .append(" com Dr(a). ").append(consulta.getMedico().getNomeCompleto())
                  .append(".\n");
            }
            if (!temEmail && !temTelefone) {
                sb.append("  Sem contato cadastrado - lembrete não enviado.\n");
            }
        }

        return sb.toString();
    }
    
    /**
     * Simula o envio de um e-mail de lembrete para o paciente.
     *
     * @param paciente paciente destinatário do e-mail.
     * @param consulta consulta.
     */
    public void enviarEmail(Paciente paciente, Consulta consulta) {
        System.out.println("  E-MAIL SIMULADO -> " + paciente.getEmail());
        System.out.println("  Assunto: Lembrete de consulta");
        System.out.println("  Mensagem: Olá, " + paciente.getNome() + " Você tem uma consulta amanhã ("
                + consulta.getData() + ") às " + consulta.getHorario()
                + " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }
    
    /**
     * Simula o envio de um SMS de lembrete para o paciente.
     *
     * @param paciente paciente destinatário do SMS.
     * @param consulta consulta.
     */
    public void enviarSMS(Paciente paciente, Consulta consulta) {
        System.out.println("  SMS -> " + paciente.getTelefone());
        System.out.println("  Mensagem: Consulta amanhã ("
                + consulta.getData() + ") às " + consulta.getHorario()
                + " com Dr(a). " + consulta.getMedico().getNomeCompleto() + ".");
    }
}
