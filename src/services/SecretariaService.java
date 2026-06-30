package services;

import java.util.List;

import models.Consulta;
import models.Secretaria;
import models.Medico;
import models.Paciente;
import repositories.ConsultaRepository;
import repositories.MedicoRepository;
import repositories.SecretariaRepository;
import repositories.PacienteRepository;

/**
 * Serviço responsável pelas operações do perfil secretária no sistema.
 * Gerencia pacientes, consultas e geração de relatórios de agendamento.
 */
public class SecretariaService {

    /** Repositório de pacientes. */
    private final PacienteRepository pacienteRepository;

    /** Repositório de consultas. */
    private final ConsultaRepository consultaRepository;

    /** Repositório de médicos. */
    private final MedicoRepository medicoRepository;

    /** Repositório de secretárias. */
    private final SecretariaRepository secretariaRepository;


    /**
     * Cria o serviço da secretária com os repositórios necessários.
     *
     * @param pacienteRepository repositório de pacientes.
     * @param consultaRepository repositório de consultas.
     * @param medicoRepository repositório de médicos.
     * @param secretariaRepository repositório de secretárias.
     */
    public SecretariaService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository,
                             MedicoRepository medicoRepository, SecretariaRepository secretariaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.secretariaRepository = secretariaRepository;
    }

    /**
     * Busca uma secretária pela matrícula. Exibe mensagem caso não seja encontrada.
     * @param matricula matrícula no formato SEC-XXX.
     * @return secretária encontrada ou null.
     */
    public Secretaria buscarSecretariaPorMatricula(String matricula) {
        Secretaria secretaria = secretariaRepository.buscarPorMatricula(matricula);
        if (secretaria == null) {
            System.out.println("Insira uma matrícula válido para entrar no sistema (Ex.: SEC-XXX)");
        }
        return secretaria;
    }
    
    /**
     * Cadastra uma nova secretária no repositório.
     * @param secretaria secretária a ser cadastrada.
     */
    public void cadastrarSecretaria(Secretaria secretaria) {
        secretariaRepository.salvar(secretaria);
    }

    /**
     * Verifica se um CPF já está cadastrado em qualquer perfil do sistema
     * (paciente, médico ou secretária), evitando duplicidade.
     * @param cpf CPF a ser verificado.
     * @return true se o CPF já estiver cadastrado, false caso contrário.
     */
    private boolean cpfJaCadastrado(String cpf) {
        return pacienteRepository.buscarPorCpf(cpf) != null
            || medicoRepository.buscarPorCpf(cpf) != null
            || secretariaRepository.buscarPorCpf(cpf) != null;
    }
    
    /**
     * Cadastra um novo paciente no sistema, verificando se o CPF já existe.
     * @param novoPaciente paciente a ser cadastrado.
     */
    public void cadastrarPaciente(Paciente novoPaciente) {
        if (cpfJaCadastrado(novoPaciente.getCpf())) {
            System.out.println("\nErro: Já existe uma pessoa cadastrada com o CPF " + novoPaciente.getCpf());
            return;
        }
        
        pacienteRepository.salvar(novoPaciente);
        System.out.println("Paciente cadastrado com sucesso!");
    }
    
    /**
     * Remove um paciente do sistema pelo CPF.
     * Exibe mensagem caso o paciente não seja encontrado.
     * @param cpf CPF do paciente a ser removido.
     */
    public void removerPaciente(String cpf) {
        // Busca o paciente pelo CPF. 
        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);
        if (paciente != null) {
            boolean sucesso = pacienteRepository.remover(paciente);
            
            // Verifica se a exclusão no banco realmente funcionou
            if (sucesso) {
                System.out.println("Paciente removido com sucesso.");
            } else {
                throw new IllegalArgumentException("Erro ao remover: O paciente possui consultas ou prontuários vinculados a ele.");
            }
            
            pacienteRepository.remover(paciente);
            System.out.println("Paciente removido com sucesso.");
        } else {
            System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
        }
    }
    
    /**
     * Busca um paciente pelo CPF. Exibe mensagem caso não seja encontrado.
     * @param cpf CPF do paciente.
     * @return paciente encontrado ou null.
     */
    public Paciente buscarPacientePorCpf(String cpf) {
        // Busca o paciente por id, pelo repositório.
        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);
        if (paciente == null) {
            System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
        }
        return paciente;
    }

   /**
     * Retorna a lista de todos os pacientes cadastrados no sistema.
     * @return lista de pacientes.
     */
    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }

    /**
     * Verifica se há pelo menos um médico e um paciente cadastrados,
     * condição necessária para agendar consultas.
     * @return true se houver médicos e pacientes cadastrados, false caso contrário.
     */
    public boolean existemMedicosEPacientesCadastrados() {
        return !pacienteRepository.buscarTodos().isEmpty() && !medicoRepository.buscarTodos().isEmpty();
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
     * Busca todas as consultas de um paciente pelo CPF.
     * @param cpf CPF do paciente.
     * @return lista de consultas do paciente.
     */
    public List<Consulta> buscarConsultasPorCpfPaciente(String cpf) {
        return consultaRepository.buscarPorCpfPaciente(cpf);
    }
    
    /**
     * Gera relatório de consultas do dia seguinte para pacientes
     * que possuem e-mail ou celular cadastrado.
     * @param diaSeguinte data. 
     * @return lista de consultas filtradas.
     */
    public List<Consulta> gerarRelatorioConsultasComEmailOuCelular(String diaSeguinte) {
        return consultaRepository.buscarPorDataComEmailOuCelular(diaSeguinte);
    }
    
    /**
     * Gera relatório de consultas do dia seguinte para pacientes
     * que não possuem e-mail nem celular cadastrado.
     * @param diaSeguinte data.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> gerarRelatorioConsultasSemEmailESemCelular(String diaSeguinte) {
        return consultaRepository.buscarPorDataSemEmailESemCelular(diaSeguinte);
    }

    /**
     * Gera relatório com todas as consultas agendadas para o dia seguinte.
     * @param diaSeguinte data no formato.
     * @return lista de todas as consultas do dia.
     */
    public List<Consulta> gerarRelatorioTodasConsultas(String diaSeguinte) {
        return consultaRepository.buscarPorData(diaSeguinte);
    }

    /**
     * Atualiza o nome do paciente.
     * @param paciente paciente a ser atualizado.
     * @param nome novo nome.
     */
    public void atualizarNomePaciente(Paciente paciente, String nome) {
        paciente.setNome(nome);
        pacienteRepository.atualizar(paciente);
    }
    
    /**
     * Atualiza o sobrenome do paciente.
     * @param paciente  paciente a ser atualizado.
     * @param sobrenome novo sobrenome.
     */
    public void atualizarSobrenomePaciente(Paciente paciente, String sobrenome) {
        paciente.setSobrenome(sobrenome);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Atualiza o telefone do paciente.
     * @param paciente paciente a ser atualizado.
     * @param telefone novo telefone.
     */
    public void atualizarTelefonePaciente(Paciente paciente, String telefone) {
        paciente.setTelefone(telefone);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Atualiza o e-mail do paciente.
     * @param paciente paciente a ser atualizado.
     * @param email    novo e-mail.
     */
    public void atualizarEmailPaciente(Paciente paciente, String email) {
        paciente.setEmail(email);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Atualiza o endereço do paciente.
     * @param paciente paciente a ser atualizado.
     * @param endereco novo endereço.
     */
    public void atualizarEnderecoPaciente(Paciente paciente, String endereco) {
        paciente.setEndereco(endereco);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Atualiza o tipo de convênio do paciente.
     * @param paciente     paciente a ser atualizado.
     * @param tipoConvenio novo tipo de convênio.
     */
    public void atualizarTipoConvenioPaciente(Paciente paciente, String tipoConvenio) {
        paciente.setTipoConvenio(tipoConvenio);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Atualiza a data de nascimento do paciente.
     * @param paciente       paciente a ser atualizado.
     * @param dataNascimento nova data no formato dd/mm/aaaa.
     */
    public void atualizarDataNascimentoPaciente(Paciente paciente, String dataNascimento) {
        paciente.setDataNascimento(dataNascimento);
        pacienteRepository.atualizar(paciente);
    }

    /**
     * Agenda uma nova consulta salvando-a no repositório.
     * @param novaConsulta consulta a ser agendada.
     */
    public void agendarConsulta(Consulta novaConsulta) {
        consultaRepository.salvar(novaConsulta);
        System.out.println("Consulta de " + novaConsulta.getPaciente().getNomeCompleto()
                + " com Dr(a). " + novaConsulta.getMedico().getNomeCompleto() + " agendada com sucesso!");
    }

    /**
     * Atualiza a data de uma consulta.
     * @param consulta consulta a ser atualizada.
     * @param data nova data.
     */
    public void atualizarDataConsulta(Consulta consulta, String data) {
        consulta.setData(data);
        consultaRepository.atualizar(consulta);
    }
    
    /**
     * Atualiza o horário de uma consulta.
     * @param consulta consulta a ser atualizada.
     * @param horario novo horário.
     */
    public void atualizarHorarioConsulta(Consulta consulta, String horario) {
        consulta.setHorario(horario);
        consultaRepository.atualizar(consulta);
    }
    
    /**
     * Atualiza o médico responsável por uma consulta.
     * @param consulta consulta a ser atualizada.
     * @param medico novo médico responsável.
     */
    public void atualizarMedicoConsulta(Consulta consulta, Medico medico) {
        consulta.setMedico(medico);
        consultaRepository.atualizar(consulta);
    }
    
    /**
     * Atualiza o tipo de uma consulta.
     * @param consulta consulta a ser atualizada.
     * @param tipo novo tipo: "Normal" ou "Retorno".
     */
    public void atualizarTipoConsulta(Consulta consulta, String tipo) {
        consulta.setTipo(tipo);
        consultaRepository.atualizar(consulta);
    }
    
    /**
     * Cancela uma consulta removendo-a do repositório.
     * Exibe mensagem de sucesso ou falha conforme o resultado.
     * @param consulta consulta a ser cancelada.
     */
    public void cancelarConsulta(Consulta consulta) {
        boolean removido = consultaRepository.remover(consulta);

        if (removido) {
            System.out.println("Consulta cancelada com sucesso.");
        } else {
            System.out.println("Não foi possível localizar a consulta para cancelamento.");
        }
    }
}
