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
 * Serviço responsável pelas operações do perfil médico no sistema.
 * Gerencia o acesso a pacientes, históricos médicos, prontuários
 * e geração de documentos médicos como receitas e atestados.
 */
public class MedicoService {

    /** Repositório de médicos. */
    private final MedicoRepository medicoRepository;

    /** Repositório de prontuários. */
    private final ProntuarioRepository prontuarioRepository;

    /** Repositório de pacientes. */
    private final PacienteRepository pacienteRepository;
    
    /**
     * Cria o serviço médico com os repositórios necessários.
     *
     * @param medicoRepository repositório de médicos.
     * @param prontuarioRepository repositório de prontuários.
     * @param pacienteRepository repositório de pacientes.
     */
    public MedicoService(MedicoRepository medicoRepository, ProntuarioRepository prontuarioRepository, PacienteRepository pacienteRepository) {
        this.medicoRepository = medicoRepository;
        this.prontuarioRepository = prontuarioRepository;
        this.pacienteRepository = pacienteRepository;
    }
    
    /**
     * Cadastra um novo médico no repositório.
     * @param medico médico a ser cadastrado.
     */
    public void cadastrarMedico(Medico medico) {
        medicoRepository.salvar(medico);
    }
    
    /**
     * Busca um médico pelo CRM.
     * @param crm CRM do médico.
     * @return médico encontrado ou null.
     */
    public Medico buscarMedicoPorCrm(String crm) {
        return medicoRepository.buscarPorCrm(crm);
    }
    
    /**
     * Retorna a lista de todos os pacientes cadastrados no sistema.
     * @return lista de pacientes.
     */
    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }
    
    /**
     * Busca um paciente pelo CPF.
     * @param cpf CPF do paciente no formato XXX.XXX.XXX-XX.
     * @return paciente encontrado ou null.
     */
    public Paciente buscarPacientePorCpf(String cpf) {
        return pacienteRepository.buscarPorCpf(cpf);
    }
    
    // HISTÓRICO MÉDICO ========================== 
    
    /**
     * Verifica se o paciente possui histórico médico cadastrado.
     * @param paciente paciente a ser verificado.
     * @return true se possuir histórico, false caso contrário.
     */
    public boolean verificarHistoricoMedico(Paciente paciente) {
        return paciente.getHistoricoMedico() != null;
    }
    
    /**
     * Cadastra ou sobrescreve o histórico médico do paciente.
     * Cria um novo {@link HistoricoMedico}, descartando qualquer dado anterior.
     *
     * @param paciente paciente a ter o histórico cadastrado.
     * @param fuma indica se o paciente é fumante.
     * @param bebe indica se o paciente consome bebida alcoólica.
     * @param colesterol indica se o paciente tem colesterol alto.
     * @param diabetes indica se o paciente tem diabetes.
     * @param doencaCardiaca indica se o paciente tem doença cardíaca.
     */
    public void cadastrarHistoricoMedico(Paciente paciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabetes, boolean doencaCardiaca) {
        paciente.setHistoricoMedico(new HistoricoMedico());

        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setFuma(fuma);
        historicoMedico.setBebe(bebe);
        historicoMedico.setColesterol(colesterol);
        historicoMedico.setDiabetes(diabetes);
        historicoMedico.setDoencaCardiaca(doencaCardiaca);
    }
    
    /**
     * Atualiza o campo "fuma" do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param fuma novo valor.
     */
    public void atualizarFumaPaciente(Paciente paciente, boolean fuma) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setFuma(fuma);
    }
    
    /**
     * Atualiza o campo "bebe" do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param bebe novo valor.
     */
    public void atualizarBebePaciente(Paciente paciente, boolean bebe) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setBebe(bebe);
    }

    /**
     * Atualiza o campo "colesterol" do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param colesterol novo valor.
     */
    public void atualizarColesterolPaciente(Paciente paciente, boolean colesterol) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setColesterol(colesterol);
    }
    
    /**
     * Atualiza o campo "diabetes" do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param diabetes novo valor.
     */
    public void atualizarDiabetesPaciente(Paciente paciente, boolean diabetes) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setDiabetes(diabetes);
    }
    
    
    /**
     * Atualiza o campo "doença cardíaca" do histórico médico do paciente.
     * @param paciente       paciente a ser atualizado.
     * @param doencaCardiaca novo valor.
     */
    public void atualizarDoencaCardiacaPaciente(Paciente paciente, boolean doencaCardiaca) {
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        historicoMedico.setDoencaCardiaca(doencaCardiaca);
    }

    /**
     * Remove o histórico médico do paciente, caso exista.
     * @param paciente paciente a ter o histórico removido.
     */
    public void removerHistoricoMedico(Paciente paciente) {
        if (verificarHistoricoMedico(paciente)) {
            paciente.setHistoricoMedico(null);
        }
    }

     /**
     * Adiciona uma cirurgia ao histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param cirurgia nome da cirurgia a ser adicionada.
     */
    public void adicionarCirurgiaPaciente(Paciente paciente, String cirurgia) {
        paciente.getHistoricoMedico().adicionarCirurgia(cirurgia);
    }

    /**
     * Remove uma cirurgia do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param cirurgia nome da cirurgia a ser removida.
     * @return true se foi removida, false se não existia.
     */
    public boolean removerCirurgiaPaciente(Paciente paciente, String cirurgia) {
        return paciente.getHistoricoMedico().removerCirurgia(cirurgia);
    }

    /**
     * Adiciona uma alergia ao histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param alergia nome da alergia a ser adicionada.
     */
    public void adicionarAlergiaPaciente(Paciente paciente, String alergia) {
        paciente.getHistoricoMedico().adicionarAlergia(alergia);
    }

    /**
     * Remove uma alergia do histórico médico do paciente.
     * @param paciente paciente a ser atualizado.
     * @param alergia nome da alergia a ser removida.
     * @return true se foi removida, false se não existia.
     */
    public boolean removerAlergiaPaciente(Paciente paciente, String alergia) {
        return paciente.getHistoricoMedico().removerAlergia(alergia);
    }
    
    /**
     * Exibe no console o histórico médico completo do paciente,
     * incluindo hábitos, condições clínicas, cirurgias e alergias.
     * @param paciente paciente a ter o histórico exibido.
     */
    public void mostrarHistoricoMedico(Paciente paciente) {
        if (!verificarHistoricoMedico(paciente)) {
            System.out.println("Este paciente não possui histórico médico cadastrado.");
            return;
        }
        
        HistoricoMedico historicoMedico = paciente.getHistoricoMedico();
        
        System.out.println("--- HISTÓRICO MÉDICO DE " + paciente.getNomeCompleto() + " ---");
        System.out.println("Fuma: " + (historicoMedico.getFuma() ? "Sim" : "Não"));
        System.out.println("Bebe: " + (historicoMedico.getBebe() ? "Sim" : "Não"));
        System.out.println("Colesterol alto: " + (historicoMedico.getColesterol() ? "Sim" : "Não"));
        System.out.println("Diabetes: " + (historicoMedico.getDiabetes() ? "Sim" : "Não"));
        System.out.println("Doença cardíaca: " + (historicoMedico.getDoencaCardiaca() ? "Sim" : "Não"));
        
        System.out.println("\nCirurgias:");
        if (historicoMedico.getCirurgias().isEmpty()) {
            System.out.println("Nenhuma cirurgia registrada.");
        } else {
            for (String cirurgia : historicoMedico.getCirurgias()) {
                System.out.println(" - " + cirurgia);
            }
        }
        System.out.println("\nAlergias:");
        if (historicoMedico.getAlergias().isEmpty()) {
            System.out.println("Nenhuma alergia registrada.");
        } else {
            for (String alergia : historicoMedico.getAlergias()) {
                System.out.println(" - " + alergia);
            }
        }
        System.out.println("--------------------------------------");
    }
    
    // =======================================
    
    // PRONTUÁRIOS ===========================
    
    /**
     * Busca um prontuário pelo médico responsável e pelo ID.
     * @param medico médico responsável pelo prontuário.
     * @param id ID do prontuário.
     * @return prontuário encontrado ou null.
     */
    public Prontuario buscarProntuarioPorMedicoEId(Medico medico, int id) {
        return prontuarioRepository.buscarPorMedicoEId(medico, id);
    }
    
    /**
     * Verifica se um ID de prontuário está disponível para o médico informado.
     * @param medico médico a ser verificado.
     * @param id ID a ser verificado.
     * @return true se o ID estiver disponível, false se já estiver em uso.
     */
    public boolean verificarDisponibilidadeIdProntuario(Medico medico, int id) {
        List<Prontuario> prontuarios = prontuarioRepository.buscarPorMedico(medico);
        
        for (Prontuario prontuario : prontuarios) {
           if (prontuario.getId() == id) {
               return false;
           }
        }
        return true;
    }
    
    /**
     * Registra um prontuário no repositório e o associa ao médico e ao paciente.
     * @param prontuario prontuário a ser registrado.
     */
    public void registrarProntuario(Prontuario prontuario) {
        prontuarioRepository.salvar(prontuario);
        prontuario.getMedico().adicionarProntuario(prontuario);
        prontuario.getPaciente().adicionarProntuario(prontuario);
    }
    
    /**
     * Remove um sintoma do prontuário informado.
     * @param prontuario prontuário a ser atualizado.
     * @param sintoma sintoma a ser removido.
     * @return true se foi removido, false se não existia.
     */
    public boolean removerSintomaProntuario(Prontuario prontuario, String sintoma) {
        return prontuario.removerSintoma(sintoma);
    }
    
    /**
     * Adiciona um sintoma ao prontuário informado.
     * @param prontuario prontuário a ser atualizado.
     * @param sintoma sintoma a ser adicionado.
     */
    public void adicionarSintomaProntuario(Prontuario prontuario, String sintoma) {
        prontuario.adicionarSintoma(sintoma);
    }
    
    /**
     * Atualiza o diagnóstico do prontuário informado.
     * @param prontuario prontuário a ser atualizado.
     * @param diagnoistico novo diagnóstico.
     */
    public void atualizarDiagnostico(Prontuario prontuario, String diagnoistico) {
        prontuario.setDiagnostico(diagnoistico);
    }
    
    /**
     * Atualiza a prescrição de tratamento do prontuário informado.
     * @param prontuario prontuário a ser atualizado.
     * @param preescricao nova prescrição.
     */
    public void atualizarPreescricao(Prontuario prontuario, String preescricao) {
        prontuario.setPrescricao(preescricao);
    }
    
    /**
     * Remove um prontuário do repositório.
     * Exibe mensagem caso o prontuário não seja encontrado.
     * @param medico médico responsável pelo prontuário.
     * @param id ID do prontuário a ser removido.
     */
    public void removerProntuario(Medico medico, int id) {
        Prontuario prontuario = buscarProntuarioPorMedicoEId(medico, id);
        
        if (prontuario == null) {
            System.out.println("Não foi encontrado prontuário com o ID " + id);
            return;
        }
        
        if (prontuarioRepository.remover(prontuario)) {
            prontuario.getMedico().removerProntuario(prontuario);
            prontuario.getPaciente().removerProntuario(prontuario);
            System.out.println("Prontuário removido com sucesso.");
        }
    }
    
    /**
     * Exibe no console os dados completos de um prontuário identificado pelo ID.
     * @param medico médico responsável pelo prontuário.
     * @param id ID do prontuário a ser exibido.
     */
    public void mostrarProntuario(Medico medico, int id) {
        Prontuario prontuario = prontuarioRepository.buscarPorMedicoEId(medico, id);
        
        if (prontuario == null) {
            System.out.println("O médico não há prontuário com esse ID");
            return;
        }
        
        System.out.println("--- PRONTUÁRIOS DO " + prontuario.getPaciente().getNomeCompleto() + " ---");
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
    
    /**
     * Exibe no console de todos os prontuários do médico.
     * @param medico médico a ter os prontuários listados.
     */
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
    
    /**
     * Gera um relatório com todos os atendimentos do médico em um determinado mês.
     * @param medico médico a ser consultado.
     * @param mes mês no formato MM/aaaa.
     * @return lista de prontuários do período.
     */
    public List<Prontuario> gerarRelatorioAtendimentosMes(Medico medico, String mes) {
        return prontuarioRepository.buscarPorMedicoEMes(medico, mes);
    }
    
    /**
     * Gera uma receita médica formatada para impressão.
     * @param medico médico emissor da receita.
     * @param paciente paciente.
     * @param prescricao prescrição a ser incluída na receita.
     * @return string formatada com o conteúdo da receita.
     */
    public String gerarReceita(Medico medico, Paciente paciente, String prescricao) {
        return """
                ---------------- RECEITA MÉDICA ---------------- 
                Médico(a): """ + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" + "Paciente: " + paciente.getNomeCompleto() + "\n" + "------------------------------------------------------\n" + "Prescrição:\n" + prescricao + "\n" + "------------------------------------------------------";
    }
    
    /**
     * Gera um atestado médico formatado para impressão.
     * @param medico médico emissor do atestado.
     * @param paciente paciente.
     * @param diasDeRepouso número de dias de repouso prescritos.
     * @param motivo motivo ou CID do afastamento.
     * @return string formatada com o conteúdo do atestado.
     */
    public String gerarAtestado(Medico medico, Paciente paciente, int diasDeRepouso, String motivo) {
        return """
                ---------------- ATESTADO MÉDICO ----------------
                Médico(a): """ + medico.getNomeCompleto() + " | CRM: " + medico.getCrm() + "\n" + "Paciente: " + paciente.getNomeCompleto() + "\n" + "------------------------------------------------------\n" + "Atesto para os devidos fins que o(a) paciente acima citado(a)\n" + "necessita de " + diasDeRepouso + " dias de repouso absoluto\n" + "por motivo de: " + motivo + ".\n" + "======================================================";
    }
    
    /**
     * Gera uma declaração de acompanhamento médico formatada para impressão.
     * @param medico médico emissor da declaração.
     * @param paciente paciente.
     * @param nomeAcompanhante nome do acompanhante.
     * @param dataAcompanhamento data da consulta.
     * @return string formatada com o conteúdo da declaração.
     */
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