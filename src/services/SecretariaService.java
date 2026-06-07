/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;

import models.Consulta;
import models.Medico;
import models.Paciente;
import repositories.ConsultaRepository;
import repositories.MedicoRepository;
import repositories.PacienteRepository;

/**
 *
 * @author peres
 */
public class SecretariaService {

    private PacienteRepository pacienteRepository;
    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;

    public SecretariaService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository,
                             MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
    }

    public void cadastrarPaciente(Paciente novoPaciente) {
        pacienteRepository.salvar(novoPaciente);
        System.out.println("Paciente cadastrado com sucesso!");
    }

    public void removerPaciente(String cpf) {
        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);
        if (paciente != null) {
            pacienteRepository.remover(paciente);
            System.out.println("Paciente removido com sucesso.");
        } else {
            System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
        }
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);
        if (paciente == null) {
            System.out.println("Não foi possível encontrar um paciente com cpf " + cpf);
        }
        return paciente;
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.buscarTodos();
    }

    public boolean existemMedicosEPacientesCadastrados() {
        return !pacienteRepository.buscarTodos().isEmpty() && !medicoRepository.buscarTodos().isEmpty();
    }

    public Medico buscarMedicoPorCrm(String crm) {
        return medicoRepository.buscarPorCrm(crm);
    }

    public List<Consulta> buscarConsultasPorCpfPaciente(String cpf) {
        return consultaRepository.buscarPorCpfPaciente(cpf);
    }

    public List<Consulta> gerarRelatorioConsultasComEmailOuCelular(String diaSeguinte) {
        return consultaRepository.buscarPorDataComEmailOuCelular(diaSeguinte);
    }

    public List<Consulta> gerarRelatorioConsultasSemEmailESemCelular(String diaSeguinte) {
        return consultaRepository.buscarPorDataSemEmailESemCelular(diaSeguinte);
    }

    public List<Consulta> gerarRelatorioTodasConsultas(String diaSeguinte) {
        return consultaRepository.buscarPorData(diaSeguinte);
    }

    public void atualizarNomePaciente(Paciente paciente, String nome) {
        paciente.setNome(nome);
    }

    public void atualizarSobrenomePaciente(Paciente paciente, String sobrenome) {
        paciente.setSobrenome(sobrenome);
    }

    public void atualizarTelefonePaciente(Paciente paciente, String telefone) {
        paciente.setTelefone(telefone);
    }

    public void atualizarEmailPaciente(Paciente paciente, String email) {
        paciente.setEmail(email);
    }

    public void atualizarEnderecoPaciente(Paciente paciente, String endereco) {
        paciente.setEndereco(endereco);
    }

    public void atualizarTipoConvenioPaciente(Paciente paciente, String tipoConvenio) {
        paciente.setTipoConvenio(tipoConvenio);
    }

    public void atualizarDataNascimentoPaciente(Paciente paciente, String dataNascimento) {
        paciente.setDataNascimento(dataNascimento);
    }

    public void agendarConsulta(Consulta novaConsulta) {
        consultaRepository.salvar(novaConsulta);
        System.out.println("Consulta de " + novaConsulta.getPaciente().getNomeCompleto()
                + " com Dr(a). " + novaConsulta.getMedico().getNomeCompleto() + " agendada com sucesso!");
    }

    public void atualizarDataConsulta(Consulta consulta, String data) {
        consulta.setData(data);
    }

    public void atualizarHorarioConsulta(Consulta consulta, String horario) {
        consulta.setHorario(horario);
    }

    public void atualizarMedicoConsulta(Consulta consulta, Medico medico) {
        consulta.setMedico(medico);
    }

    public void atualizarTipoConsulta(Consulta consulta, String tipo) {
        consulta.setTipo(tipo);
    }

    public void cancelarConsulta(Consulta consulta) {
        boolean removido = consultaRepository.remover(consulta);

        if (removido) {
            System.out.println("Consulta cancelada com sucesso.");
        } else {
            System.out.println("Não foi possível localizar a consulta para cancelamento.");
        }
    }
}
