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
        
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setFuma(fuma);
        historicoMedico.setBebe(bebe);
        historicoMedico.setColesterol(colesterol);
        historicoMedico.setDiabete(diabete);
        historicoMedico.setDoencaCardiaca(doencaCardiaca);
    }

    public void atualizarFumaPaciente(Paciente paciente, boolean fuma) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setFuma(fuma);
    }

    public void atualizarBebePaciente(Paciente paciente, boolean bebe) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setBebe(bebe);
    }

    public void atualizarColesterolPaciente(Paciente paciente, boolean colesterol) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setColesterol(colesterol);
    }

    public void atualizarDiabetePaciente(Paciente paciente, boolean diabete) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setDiabete(diabete);
    }

    public void atualizarDoencaCardiacaPaciente(Paciente paciente, boolean doencaCardiaca) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setDoencaCardiaca(doencaCardiaca);
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
            System.out.println("Nenhuma cirurgia registrada.");
        } else {
            for (String cirurgia : dados.getCirurgias()) {
                System.out.println(" - " + cirurgia);
            }
        }
        System.out.println("\nAlergias:");
        if (dados.getAlergias().isEmpty()) {
            System.out.println("Nenhuma alergia registrada.");
        } else {
            for (String alergia : dados.getAlergias()) {
                System.out.println(" - " + alergia);
            }
        }
        System.out.println("--------------------------------------");
    }
    
    // =======================================
    
    // PRONTUÁRIOS ===========================
    
    public Prontuario buscarProntuarioPorMedicoEId(Medico medico, int id) {
        return prontuarioRepository.buscarPorMedicoEId(medico, id);
    }
    
    public boolean verificarDisponibilidadeIdProntuario(Medico medico, int id) {
        List<Prontuario> prontuarios = prontuarioRepository.buscarPorMedico(medico);
        
        for (Prontuario prontuario : prontuarios) {
           if (prontuario.getId() == id) {
               return false;
           }
        }
        return true;
    }

    public void registrarProntuario(Prontuario prontuario) {
        prontuarioRepository.salvar(prontuario);
        prontuario.getMedico().getProntuarios().add(prontuario);
        prontuario.getPaciente().getProntuarios().add(prontuario);
    }
    
    public void removerSintomaProntuario(Prontuario prontuario, String sintoma) {
        prontuario.removerSintoma(sintoma);
    }
    
    public void adicionarSintomaProntuario(Prontuario prontuario, String sintoma) {
        prontuario.adicionarSintoma(sintoma);
    }
    
    public void atualizarDiagnostico(Prontuario prontuario, String diagnoistico) {
        prontuario.setDiagnostico(diagnoistico);
    }
    
    public void atualizarPreescricao(Prontuario prontuario, String preescricao) {
        prontuario.setPrescricao(preescricao);
    }

    public void removerProntuario(Medico medico, int id) {
        Prontuario prontuario = buscarProntuarioPorMedicoEId(medico, id);
        
        if (prontuarioRepository.remover(prontuario)) {
            prontuario.getMedico().getProntuarios().remove(prontuario);
            prontuario.getPaciente().getProntuarios().remove(prontuario);
        }
    }
    
    public void mostrarProntuario(Medico medico, int id) {
        Prontuario prontuario = prontuarioRepository.buscarPorMedicoEId(medico, id);
        if (prontuario == null) {
            System.out.println("O médico não há prontuário com esse ID");
            return;
        }
        
        System.out.println("--- PRONTUÁRIO DO " + prontuario.getPaciente().getNomeCompleto() + " ---");
        System.out.println("--- FEITO PELO MÉDICO " + medico.getNomeCompleto() + " ---");
        
        System.out.println("\nSintomas:");
        if (prontuario.getSintomas().isEmpty()) {
            System.out.println("Nenhum sintoma registrado.");
        } else {
            for (String sintoma : prontuario.getSintomas()) {
                System.out.println(" - " + sintoma);
            }
        }
        
        System.out.println("--- Diagnóstco " + prontuario.getDiagnostico() + " ---");
        System.out.println("--- Prescrição " + prontuario.getPrescricao() + " ---");
    }
    
    public void mostrarListaProntuarios(Medico medico) {
        List<Prontuario> prontuarios = prontuarioRepository.buscarPorMedico(medico);
        
        if (prontuarios.isEmpty()) {
            System.out.println("Nenhum prontuário encontrado.");
            return;
        }
        
        System.out.println("--- PRONTUÁRIOS DE " + medico.getNomeCompleto() + " ---");
        for (Prontuario prontuario : prontuarios) {
            System.out.println("ID: " + prontuario.getId() + " | Paciente: " + prontuario.getPaciente().getNomeCompleto() + " | Data: " + prontuario.getData());
        }
        System.out.println("--------------------------------------");
    }

    // ======================================= 
    
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
        return """
               ---------------- DECLARAÇÃO DE ACOMPANHAMENTO ----------------
               Declaro para os devidos fins que o(a) Sr(a). """ 
                + nomeAcompanhante + "\n" + "atuou como acompanhante do(a) paciente " 
                + paciente.getNomeCompleto() + "\n" + "em consulta médica realizada no dia " 
                + dataAcompanhamento + ".\n" + "------------------------------------------------------\n" 
                + "Médico(a): " + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" 
                + "------------------------------------------------------";
    }
}
