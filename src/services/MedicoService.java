/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;

import models.HistoricoMedico;
import models.Medico;
import models.Paciente;
import models.Prontuario;
import repositories.MedicoRepository;
import repositories.PacienteRepository;
import repositories.ProntuarioRepository;

/**
 *
 * @author peres
 */
public class MedicoService {

    private MedicoRepository medicoRepository;
    private ProntuarioRepository prontuarioRepository;
    private PacienteRepository pacienteRepository;

    public MedicoService(MedicoRepository medicoRepository, ProntuarioRepository prontuarioRepository, PacienteRepository pacienteRepository) {
        this.medicoRepository = medicoRepository;
        this.prontuarioRepository = prontuarioRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public void cadastrarMedico(Medico medico) {
        medicoRepository.salvar(medico);
    }

    public Medico buscarMedicoPorCrm(String crm) {
        Medico medico = medicoRepository.buscarPorCrm(crm);
        if (medico == null) {
            System.out.println("Insira um CRM válido para entrar no sistema");
        }
        return medico;
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        return pacienteRepository.buscarPorCpf(cpf);
    }
    
    // HISTÓRICO MÉDICO ========================== 

    public boolean verificarHistoricoMedico(Paciente paciente) {
        return paciente.getHistoricoMedico() != null;
    }

    public void cadastrarHistoricoMedico(Paciente paciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabete, boolean doencaCardiaca) {
        if (!verificarHistoricoMedico(paciente)) {
            paciente.setHistoricoMedico(new HistoricoMedico());
        }
        
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setFuma(fuma);
        dados.setBebe(bebe);
        dados.setColesterol(colesterol);
        dados.setDiabete(diabete);
        dados.setDoencaCardiaca(doencaCardiaca);
    }

    public void atualizarFumaPaciente(Paciente paciente, boolean fuma) {
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setFuma(fuma);
    }

    public void atualizarBebePaciente(Paciente paciente, boolean bebe) {
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setBebe(bebe);
    }

    public void atualizarColesterolPaciente(Paciente paciente, boolean colesterol) {
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setColesterol(colesterol);
    }

    public void atualizarDiabetePaciente(Paciente paciente, boolean diabete) {
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setDiabete(diabete);
    }

    public void atualizarDoencaCardiacaPaciente(Paciente paciente, boolean doencaCardiaca) {
        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setDoencaCardiaca(doencaCardiaca);
    }

    public void removerHistoricoMedico(Paciente paciente) {
        if (verificarHistoricoMedico(paciente)) {
            paciente.setHistoricoMedico(null);
        }
    }

    public void adicionarCirurgiaPaciente(Paciente paciente, String cirurgia) {
        paciente.getHistoricoMedico().adicionarCirurgia(cirurgia);
    }

    public void removerCirurgiaPaciente(Paciente paciente, String cirurgia) {
        paciente.getHistoricoMedico().removerCirurgia(cirurgia);
    }

    public void adicionarAlergiaPaciente(Paciente paciente, String alergia) {
        paciente.getHistoricoMedico().adicionarAlergia(alergia);
    }

    public void removerAlergiaPaciente(Paciente paciente, String alergia) {
        paciente.getHistoricoMedico().removerAlergia(alergia);
    }
    
    public void mostrarHistoricoMedico(Paciente paciente) {
        if (!verificarHistoricoMedico(paciente)) {
            System.out.println("Este paciente não possui histórico médico cadastrado.");
            return;
        }
        
        HistoricoMedico dados = paciente.getHistoricoMedico();
        
        System.out.println("--- HISTÓRICO MÉDICO DE " + paciente.getNomeCompleto() + " ---");
        System.out.println("Fuma: " + (dados.getFuma() ? "Sim" : "Não"));
        System.out.println("Bebe: " + (dados.getBebe() ? "Sim" : "Não"));
        System.out.println("Colesterol alto: " + (dados.getColesterol() ? "Sim" : "Não"));
        System.out.println("Diabete: " + (dados.getDiabete() ? "Sim" : "Não"));
        System.out.println("Doença cardíaca: " + (dados.getDoencaCardiaca() ? "Sim" : "Não"));
        
        System.out.println("\nCirurgias:");
        if (dados.getCirurgias().isEmpty()) {
            System.out.println("  Nenhuma cirurgia registrada.");
        } else {
            for (String cirurgia : dados.getCirurgias()) {
                System.out.println("  - " + cirurgia);
            }
        }
        System.out.println("\nAlergias:");
        if (dados.getAlergias().isEmpty()) {
            System.out.println("  Nenhuma alergia registrada.");
        } else {
            for (String alergia : dados.getAlergias()) {
                System.out.println("  - " + alergia);
            }
        }
        System.out.println("--------------------------------------");
    }
    
    // =======================================

    public void registrarProntuario(Prontuario prontuario) {
        prontuarioRepository.salvar(prontuario);
        prontuario.getMedico().getProntuarios().add(prontuario);
        prontuario.getPaciente().getProntuarios().add(prontuario);
    }

    public void removerProntuario(Prontuario prontuario) {
        if (prontuarioRepository.remover(prontuario)) {
            prontuario.getMedico().getProntuarios().remove(prontuario);
            prontuario.getPaciente().getProntuarios().remove(prontuario);
        }
    }

    public List<Prontuario> gerarRelatorioAtendimentosMes(Medico medico, String mes) {
        return prontuarioRepository.buscarPorMedicoEMes(medico, mes);
    }

    public String gerarReceita(Medico medico, Paciente paciente, String prescricao) {
        return """
                ---------------- RECEITA MÉDICA ---------------- 
                Médico(a): """ + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" + "Paciente: " + paciente.getNomeCompleto() + "\n" + "------------------------------------------------------\n" + "Prescrição:\n" + prescricao + "\n" + "------------------------------------------------------";
    }

    public String gerarAtestado(Medico medico, Paciente paciente, int diasDeRepouso, String motivo) {
        return """
                ---------------- ATESTADO MÉDICO ----------------
                Médico(a): """ + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" + "Paciente: " + paciente.getNomeCompleto() + "\n" + "------------------------------------------------------\n" + "Atesto para os devidos fins que o(a) paciente acima citado(a)\n" + "necessita de " + diasDeRepouso + " dias de repouso absoluto\n" + "por motivo de: " + motivo + ".\n" + "======================================================";
    }

    public String gerarDeclaracaoAcompanhamento(Medico medico, Paciente paciente, String nomeAcompanhante, String dataAcompanhamento) {
        return "---------------- DECLARAÇÃO DE ACOMPANHAMENTO ----------------\n" + "Declaro para os devidos fins que o(a) Sr(a). " + nomeAcompanhante + "\n" + "atuou como acompanhante do(a) paciente " + paciente.getNomeCompleto() + "\n" + "em consulta médica realizada no dia " + dataAcompanhamento + ".\n" + "------------------------------------------------------\n" + "Médico(a): " + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" + "------------------------------------------------------";
    }
}
