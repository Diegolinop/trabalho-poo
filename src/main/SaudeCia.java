package main;

import java.util.List;
import java.util.Scanner;

import models.Consulta;
import models.Medico;
import models.Paciente;
import models.Prontuario;
import models.Secretaria;
import repositories.ConsultaRepository;
import repositories.MedicoRepository;
import repositories.PacienteRepository;
import repositories.ProntuarioRepository;
import services.GerenciadorMensagensService;
import services.MedicoService;
import services.SecretariaService;

public class SaudeCia {

    public static void main(String[] args) {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        SecretariaService secretariaService = new SecretariaService(pacienteRepository, consultaRepository, medicoRepository);
        MedicoService medicoService = new MedicoService(medicoRepository, prontuarioRepository, pacienteRepository);
        GerenciadorMensagensService gerenciadorMensagensService = new GerenciadorMensagensService(consultaRepository);

        Secretaria secretaria = new Secretaria(
                "123.456.789-00",
                "Diego",
                "Suárez",
                "(67) 99999-9999",
                "secretaria@gmail.com",
                "Rua XXX, 123",
                "SEC-100"
        );

        Medico medicoNovo = new Medico(
                "123.456.789-00",
                "Diego",
                "Suárez",
                "(67) 99999-9999",
                "medico@gmail.com",
                "Rua XXX, 123",
                "Cardiologista",
                "12345"
        );

        medicoService.cadastrarMedico(medicoNovo);

        Scanner leitura = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Menu da Secretária");
            System.out.println("2 - Menu do Médico");
            System.out.println("3 - Enviar mensagens");
            System.out.println("0 - Sair do Sistema\n");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    menuSecretaria(secretariaService, secretaria);
                    break;
                case 2:
                    menuMedico(medicoService);
                    break;
                case 3:
                    menuGerenciadorMensagens(gerenciadorMensagensService);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void menuMedico(MedicoService medicoService) {
        Scanner leitura = new Scanner(System.in);
        System.out.println("\nInsira o crm do médico para obter acesso ao sistema: ");
        String crmMedico = leitura.nextLine();

        Medico medicoLogin = medicoService.buscarMedicoPorCrm(crmMedico);
        if (medicoLogin == null) {
            return;
        }

        int opcao;

        do {
            System.out.println("\n--- MENU MÉDICO ----");
            System.out.println("1 - Listar todos os pacientes");
            System.out.println("2 - Gerenciar histórico médico de um paciente");
            System.out.println("3 - Gerenciar prontuários");
            System.out.println("4 - Gerar relatórios médicos");
            System.out.println("0 - Voltar ao menu principal\n");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    listarPacientes(medicoService.listarPacientes());
                    break;
                case 2:
                    gerenciarHistoricoMedico(leitura, medicoService);
                    break;
                case 3:
                    gerenciarProntuarios(leitura, medicoService, medicoLogin);
                    break;
                case 4:
                    gerarRelatoriosMedicos(leitura, medicoService, medicoLogin);
                    break;
                case 0:
                    System.out.println("Fazendo logout do perfil médico...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void menuSecretaria(SecretariaService secretariaService, Secretaria secretaria) {
        Scanner leitura = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU DA SECRETÁRIA (" + secretaria.getNomeCompleto() + ") ---");
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Listar Pacientes");
            System.out.println("3 - Remover Paciente");
            System.out.println("4 - Agendar Consulta");
            System.out.println("5 - Cancelar Consulta");
            System.out.println("0 - Voltar ao Menu Principal\n");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPaciente(leitura, secretariaService);
                    break;
                case 2:
                    listarPacientes(secretariaService.listarPacientes());
                    break;
                case 3:
                    System.out.println("\n--- REMOVER PACIENTE ---");
                    System.out.print("Digite o CPF do paciente a ser removido: ");
                    secretariaService.removerPaciente(leitura.nextLine());
                    break;
                case 4:
                    agendarConsulta(leitura, secretariaService);
                    break;
                case 5:
                    cancelarConsulta(leitura, secretariaService);
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarPaciente(Scanner leitura, SecretariaService secretariaService) {
        System.out.println("\n--- CADASTRAR PACIENTE ---");
        System.out.print("CPF: ");
        String cpf = leitura.nextLine();
        System.out.print("Nome: ");
        String nome = leitura.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = leitura.nextLine();
        System.out.print("Telefone: ");
        String telefone = leitura.nextLine();
        System.out.print("Email: ");
        String email = leitura.nextLine();
        System.out.print("Endereço: ");
        String endereco = leitura.nextLine();
        System.out.print("Tipo de Convênio (Particular / Plano de Saúde): ");
        String tipoConvenio = leitura.nextLine();
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        String dataNascimento = leitura.nextLine();

        Paciente novoPaciente = new Paciente(cpf, nome, sobrenome, telefone, email, endereco, tipoConvenio, dataNascimento);
        secretariaService.cadastrarPaciente(novoPaciente);
    }

    private static void listarPacientes(List<Paciente> pacientes) {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado no sistema");
            return;
        }

        for (Paciente paciente : pacientes) {
            System.out.println("CPF: " + paciente.getCpf()
                    + " | Nome: " + paciente.getNomeCompleto()
                    + " | Convênio: " + paciente.getTipoConvenio());
        }
    }

    private static void agendarConsulta(Scanner leitura, SecretariaService secretariaService) {
        System.out.println("\n--- AGENDAR CONSULTA ---");
        if (!secretariaService.existemMedicosEPacientesCadastrados()) {
            System.out.println("Cadastre pelo menos um médico e um paciente antes de agendar.");
            return;
        }

        System.out.print("Digite o CPF do paciente: ");
        Paciente pacienteConsulta = secretariaService.buscarPacientePorCpf(leitura.nextLine());
        if (pacienteConsulta == null) {
            return;
        }

        System.out.print("Digite o CRM do médico: ");
        Medico medicoConsulta = secretariaService.buscarMedicoPorCrm(leitura.nextLine());
        if (medicoConsulta == null) {
            System.out.println("Médico com CRM informado não foi localizado.");
            return;
        }

        System.out.print("Data da consulta (dd/mm/aaaa): ");
        String data = leitura.nextLine();
        System.out.print("Horário: ");
        String horario = leitura.nextLine();
        System.out.print("Tipo da consulta (Normal / Retorno): ");
        String tipo = leitura.nextLine();

        Consulta novaConsulta = new Consulta(data, horario, medicoConsulta, pacienteConsulta, tipo);
        secretariaService.agendarConsulta(novaConsulta);
    }

    private static void cancelarConsulta(Scanner leitura, SecretariaService secretariaService) {
        System.out.println("\n--- CANCELAR CONSULTA ---");
        System.out.print("Digite o CPF do paciente para localizar a consulta: ");
        List<Consulta> consultasDoPaciente = secretariaService.buscarConsultasPorCpfPaciente(leitura.nextLine());

        if (consultasDoPaciente.isEmpty()) {
            System.out.println("Nenhuma consulta localizada para o CPF informado.");
            return;
        }

        System.out.println("Consultas encontradas para este paciente:");
        for (int i = 0; i < consultasDoPaciente.size(); i++) {
            Consulta consulta = consultasDoPaciente.get(i);
            System.out.println((i + 1) + " - Data: " + consulta.getData()
                    + " | Horário: " + consulta.getHorario()
                    + " | Médico(a): " + consulta.getMedico().getNomeCompleto());
        }

        int opcaoCancelar;
        do {
            System.out.print("\nDigite o número da consulta que deseja cancelar (ou 0 para desistir): ");
            opcaoCancelar = leitura.nextInt();
            leitura.nextLine();

            if (opcaoCancelar == 0) {
                System.out.println("Operação cancelada.");
                return;
            }

            if (opcaoCancelar > 0 && opcaoCancelar <= consultasDoPaciente.size()) {
                secretariaService.cancelarConsulta(consultasDoPaciente.get(opcaoCancelar - 1));
                return;
            }

            System.out.println("Opção inválida. Tente novamente.");
        } while (true);
    }

    private static void gerenciarHistoricoMedico(Scanner leitura, MedicoService medicoService) {
        System.out.println("\n--- GERENCIAR HISTÓRICO MÉDICO ---");
        System.out.print("Digite o CPF do paciente: ");
        Paciente pacienteBuscado = medicoService.buscarPacientePorCpf(leitura.nextLine());

        if (pacienteBuscado == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.println("O paciente fuma? (true/false): ");
        boolean fuma = leitura.nextBoolean();
        System.out.println("O paciente bebe? (true/false): ");
        boolean bebe = leitura.nextBoolean();
        System.out.println("O paciente tem colesterol alto? (true/false): ");
        boolean colesterol = leitura.nextBoolean();
        System.out.println("O paciente tem diabete? (true/false): ");
        boolean diabete = leitura.nextBoolean();
        System.out.print("O paciente tem doença cardíaca? (true/false): ");
        boolean doencaCardiaca = leitura.nextBoolean();
        leitura.nextLine();

        medicoService.atualizarHistoricoMedico(pacienteBuscado, fuma, bebe, colesterol, diabete, doencaCardiaca);
        System.out.println("Dados de saúde do paciente " + pacienteBuscado.getNomeCompleto() + " atualizados com sucesso!");
    }

    private static void gerenciarProntuarios(Scanner leitura, MedicoService medicoService, Medico medicoLogin) {
        System.out.println("\n--- GERENCIAR PRONTUÁRIOS ---");
        System.out.print("Digite o CPF do paciente: ");
        Paciente pacienteProntuario = medicoService.buscarPacientePorCpf(leitura.nextLine());

        if (pacienteProntuario == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.print("Digite a data do atendimento (ex: 04/06/2026): ");
        Prontuario novoProntuario = new Prontuario(pacienteProntuario, medicoLogin, leitura.nextLine());

        System.out.println("Digite os sintomas relatados - digite 'fim' para parar: ");
        while (true) {
            System.out.print("Sintoma: ");
            String sintoma = leitura.nextLine();

            if (sintoma.equalsIgnoreCase("fim")) {
                break;
            }

            novoProntuario.adicionarSintoma(sintoma);
        }

        System.out.print("Diagnóstico: ");
        novoProntuario.setDiagnostico(leitura.nextLine());
        System.out.print("Prescrição de tratamento: ");
        novoProntuario.setPrescricao(leitura.nextLine());

        medicoService.registrarProntuario(novoProntuario);
        System.out.println("Prontuário registrado com sucesso!");
    }

    private static void gerarRelatoriosMedicos(Scanner leitura, MedicoService medicoService, Medico medicoLogin) {
        System.out.println("\n--- GERAR RELATÓRIOS MÉDICOS ---");
        System.out.print("Digite o CPF do paciente para os relatórios (ou deixe em branco para 'atendidos no mês'): ");
        String cpfRelatorio = leitura.nextLine();

        Paciente pacienteRelatorio = null;
        if (!cpfRelatorio.isBlank()) {
            pacienteRelatorio = medicoService.buscarPacientePorCpf(cpfRelatorio);
        }

        System.out.println("1 - Receita");
        System.out.println("2 - Atestado");
        System.out.println("3 - Declaração de Acompanhamento");
        System.out.println("4 - Clientes atendidos no mês");
        System.out.print("Escolha o relatório: ");
        int opcaoRelatorio = leitura.nextInt();
        leitura.nextLine();

        if (opcaoRelatorio >= 1 && opcaoRelatorio <= 3 && pacienteRelatorio == null) {
            System.out.println("Erro: Para este relatório, o paciente precisa ser encontrado.");
            return;
        }

        switch (opcaoRelatorio) {
            case 1:
                System.out.print("Digite a prescrição para imprimir na receita: ");
                System.out.println("\n" + medicoService.gerarReceita(medicoLogin, pacienteRelatorio, leitura.nextLine()));
                break;
            case 2:
                System.out.print("Dias de repouso: ");
                int dias = leitura.nextInt();
                leitura.nextLine();
                System.out.print("Motivo/CID: ");
                System.out.println("\n" + medicoService.gerarAtestado(medicoLogin, pacienteRelatorio, dias, leitura.nextLine()));
                break;
            case 3:
                System.out.print("Nome do acompanhante: ");
                String nomeAcompanhante = leitura.nextLine();
                System.out.print("Data da consulta: ");
                String dataConsulta = leitura.nextLine();
                System.out.println("\n" + medicoService.gerarDeclaracaoAcompanhamento(
                        medicoLogin, pacienteRelatorio, nomeAcompanhante, dataConsulta));
                break;
            case 4:
                imprimirAtendimentosMes(leitura, medicoService, medicoLogin);
                break;
            default:
                System.out.println("Opção de relatório inválida.");
                break;
        }
    }

    private static void imprimirAtendimentosMes(Scanner leitura, MedicoService medicoService, Medico medicoLogin) {
        System.out.print("Digite o mês/ano para consultar (ex: 06/2026): ");
        String mesAno = leitura.nextLine();
        List<Prontuario> atendimentos = medicoService.gerarRelatorioAtendimentosMes(medicoLogin, mesAno);

        System.out.println("\n--- ATENDIMENTOS EM " + mesAno + " ---");
        if (atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento encontrado para este período.");
            return;
        }

        System.out.println("Total de atendimentos: " + atendimentos.size());
        System.out.println("--------------------------------------");
        for (Prontuario prontuario : atendimentos) {
            System.out.println("Data: " + prontuario.getData()
                    + " | Paciente: " + prontuario.getPaciente().getNomeCompleto()
                    + " | Diagnóstico: " + prontuario.getDiagnostico());
        }
    }

    private static void menuGerenciadorMensagens(GerenciadorMensagensService gerenciadorMensagensService) {
        Scanner leitura = new Scanner(System.in);

        System.out.println("\n--- GERENCIADOR DE MENSAGENS ---");
        System.out.print("Digite a data do dia seguinte para enviar lembretes (dd/mm/aaaa): ");
        gerenciadorMensagensService.enviarMensagens(leitura.nextLine());
    }
}
