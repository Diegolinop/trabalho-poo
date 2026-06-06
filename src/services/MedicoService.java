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

    public boolean verificarHistoricoMedico(Paciente paciente) {
        return paciente.getHistoricoMedico() != null;
    }

    public void cadastrarHistoricoMedico(Paciente paciente) {
        if (!verificarHistoricoMedico(paciente)) {
            paciente.setHistoricoMedico(new HistoricoMedico());
        }
    }

    public void atualizarHistoricoMedico(Paciente paciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabete, boolean doencaCardiaca) {
        cadastrarHistoricoMedico(paciente);

        HistoricoMedico dados = paciente.getHistoricoMedico();
        dados.setFuma(fuma);
        dados.setBebe(bebe);
        dados.setColesterol(colesterol);
        dados.setDiabete(diabete);
        dados.setDoencaCardiaca(doencaCardiaca);
    }

    public void removerHistoricoMedico(Paciente paciente) {
        if (verificarHistoricoMedico(paciente)) {
            paciente.setHistoricoMedico(null);
        }
    }

    public void adicionarCirurgiaPaciente(Paciente paciente, String cirurgia) {
        cadastrarHistoricoMedico(paciente);
        paciente.getHistoricoMedico().adicionarCirurgia(cirurgia);
    }

    public void removerCirurgiaPaciente(Paciente paciente, String cirurgia) {
        if (verificarHistoricoMedico(paciente)) {
            paciente.getHistoricoMedico().removerCirurgia(cirurgia);
        }
    }

    public void adicionarAlergiaPaciente(Paciente paciente, String alergia) {
        cadastrarHistoricoMedico(paciente);
        paciente.getHistoricoMedico().adicionarAlergia(alergia);
    }

    public void removerAlergiaPaciente(Paciente paciente, String alergia) {
        if (verificarHistoricoMedico(paciente)) {
            paciente.getHistoricoMedico().removerAlergia(alergia);
        }
    }

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
