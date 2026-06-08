package main;

import java.util.List;
import java.util.Scanner;

import models.Consulta;
import models.Medico;
import models.Paciente;
import models.Secretaria;
import services.SecretariaService;

public class MenuSecretaria {

    private Scanner leitura;
    private SecretariaService secretariaService;
    private Secretaria secretaria;

    public MenuSecretaria(Scanner leitura, SecretariaService secretariaService) {
        this.leitura = leitura;
        this.secretariaService = secretariaService;
    }

    public void exibir() {
        System.out.println("\nInsira a matrícula da secretária para obter acesso ao sistema: ");
        String matriculaSecretaria = leitura.nextLine();

        Secretaria secretariaLogin = secretariaService.buscarSecretariaPorMatricula(matriculaSecretaria);
        if (secretariaLogin == null) {
            System.out.println("Acesso negado. Retornando ao menu principal.");
            return;
        }
        
        int opcao;

        do {
            System.out.println("\n--- MENU DA SECRETÁRIA (" + secretariaLogin.getNomeCompleto() + ") ---");
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Listar Pacientes");
            System.out.println("3 - Remover Paciente");
            System.out.println("4 - Atualizar Paciente");
            System.out.println("5 - Agendar Consulta");
            System.out.println("6 - Atualizar Consulta");
            System.out.println("7 - Cancelar Consulta");
            System.out.println("8 - Gerar Relatório de Consultas");
            System.out.println("0 - Voltar ao Menu Principal\n");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarPaciente();
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
                    atualizarPaciente();
                    break;
                case 5:
                    agendarConsulta();
                    break;
                case 6:
                    atualizarConsulta();
                    break;
                case 7:
                    cancelarConsulta();
                    break;
                case 8:
                    gerarRelatorioConsultas();
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

    private void cadastrarPaciente() {
        System.out.println("\n--- CADASTRAR PACIENTE ---");
        System.out.print("CPF: ");
        String cpf = leitura.nextLine();
        System.out.print("Primeiro nome: ");
        String nome = leitura.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = leitura.nextLine();
        System.out.print("Telefone (deixe em branco se não tiver): ");
        String telefone = leitura.nextLine();
        telefone = telefone.isBlank() ? null : telefone;
        System.out.print("Email (deixe em branco se não tiver): ");
        String email = leitura.nextLine();
        email = email.isBlank() ? null : email;
        System.out.print("Endereço: ");
        String endereco = leitura.nextLine();
        System.out.print("Tipo de Convênio (Particular / Plano de Saúde): ");
        String tipoConvenio = leitura.nextLine();
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        String dataNascimento = leitura.nextLine();
        
        try {
            Paciente novoPaciente = new Paciente(cpf, nome, sobrenome, telefone, email, endereco, tipoConvenio, dataNascimento);
            secretariaService.cadastrarPaciente(novoPaciente);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarPacientes(List<Paciente> pacientes) {
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

    private void atualizarPaciente() {
        System.out.println("\n--- ATUALIZAR PACIENTE ---");
        System.out.print("Digite o CPF do paciente a ser atualizado: ");
        Paciente paciente = secretariaService.buscarPacientePorCpf(leitura.nextLine());

        if (paciente == null) {
            return;
        }

        int opcao;

        do {
            System.out.println("\nQual dado deseja atualizar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Sobrenome");
            System.out.println("3 - Telefone");
            System.out.println("4 - Email");
            System.out.println("5 - Endereço");
            System.out.println("6 - Tipo de Convênio");
            System.out.println("7 - Data de Nascimento");
            System.out.println("0 - Cancelar atualização");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            if (opcao > 0 && opcao < 8) {
                System.out.print("Informe o novo valor: ");
                String novoValor = leitura.nextLine();

                switch (opcao) {
                    case 1:
                        secretariaService.atualizarNomePaciente(paciente, novoValor);
                        break;
                    case 2:
                        secretariaService.atualizarSobrenomePaciente(paciente, novoValor);
                        break;
                    case 3:
                        secretariaService.atualizarTelefonePaciente(paciente, novoValor);
                        break;
                    case 4:
                        secretariaService.atualizarEmailPaciente(paciente, novoValor);
                        break;
                    case 5:
                        secretariaService.atualizarEnderecoPaciente(paciente, novoValor);
                        break;
                    case 6:
                        secretariaService.atualizarTipoConvenioPaciente(paciente, novoValor);
                        break;
                    case 7:
                        secretariaService.atualizarDataNascimentoPaciente(paciente, novoValor);
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                        break;
                }

                System.out.println("Dados do paciente atualizados com sucesso.");
            } else if (opcao != 0) {
                System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void agendarConsulta() {
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

    private Consulta selecionarConsultaPaciente() {
        System.out.print("Digite o CPF do paciente para localizar a consulta: ");
        List<Consulta> consultasDoPaciente = secretariaService.buscarConsultasPorCpfPaciente(leitura.nextLine());

        if (consultasDoPaciente.isEmpty()) {
            System.out.println("Nenhuma consulta localizada para o CPF informado.");
            return null;
        }

        System.out.println("Consultas encontradas para este paciente:");
        for (int i = 0; i < consultasDoPaciente.size(); i++) {
            Consulta consulta = consultasDoPaciente.get(i);
            System.out.println((i + 1) + " - Data: " + consulta.getData()
                    + " | Horário: " + consulta.getHorario()
                    + " | Médico(a): " + consulta.getMedico().getNomeCompleto()
                    + " | Tipo: " + consulta.getTipo());
        }

        int opcaoConsulta;
        do {
            System.out.print("\nDigite o número da consulta desejada (ou 0 para desistir): ");
            opcaoConsulta = leitura.nextInt();
            leitura.nextLine();

            if (opcaoConsulta == 0) {
                System.out.println("Operação cancelada.");
                return null;
            }

            if (opcaoConsulta > 0 && opcaoConsulta <= consultasDoPaciente.size()) {
                return consultasDoPaciente.get(opcaoConsulta - 1);
            }

            System.out.println("Opção inválida. Tente novamente.");
        } while (true);
    }

    private void atualizarConsulta() {
        System.out.println("\n--- ATUALIZAR CONSULTA ---");
        Consulta consulta = selecionarConsultaPaciente();

        if (consulta == null) {
            return;
        }

        int opcao;

        do {
            System.out.println("\nQual dado deseja atualizar?");
            System.out.println("1 - Data");
            System.out.println("2 - Horário");
            System.out.println("3 - Médico");
            System.out.println("4 - Tipo da consulta");
            System.out.println("0 - Cancelar atualização");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nova data da consulta (dd/mm/aaaa): ");
                    secretariaService.atualizarDataConsulta(consulta, leitura.nextLine());
                    System.out.println("Consulta atualizada com sucesso.");
                    break;
                case 2:
                    System.out.print("Novo horário: ");
                    secretariaService.atualizarHorarioConsulta(consulta, leitura.nextLine());
                    System.out.println("Consulta atualizada com sucesso.");
                    break;
                case 3:
                    System.out.print("CRM do novo médico: ");
                    Medico medico = secretariaService.buscarMedicoPorCrm(leitura.nextLine());

                    if (medico == null) {
                        System.out.println("Médico com CRM informado não foi localizado.");
                        break;
                    }

                    secretariaService.atualizarMedicoConsulta(consulta, medico);
                    System.out.println("Consulta atualizada com sucesso.");
                    break;
                case 4:
                    System.out.print("Novo tipo da consulta (Normal / Retorno): ");
                    secretariaService.atualizarTipoConsulta(consulta, leitura.nextLine());
                    System.out.println("Consulta atualizada com sucesso.");
                    break;
                case 0:
                    System.out.println("Operação cancelada.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private void cancelarConsulta() {
        System.out.println("\n--- CANCELAR CONSULTA ---");
        Consulta consulta = selecionarConsultaPaciente();

        if (consulta != null) {
            secretariaService.cancelarConsulta(consulta);
        }
    }

    private void gerarRelatorioConsultas() {
        System.out.println("\n--- GERAR RELATÓRIO DE CONSULTAS ---");
        System.out.print("Digite a data do dia seguinte (dd/mm/aaaa): ");
        String diaSeguinte = leitura.nextLine();

        int opcaoRelatorio;

        do {
            System.out.println("\nEscolha o relatório:");
            System.out.println("1 - Consultas de pacientes que possuem e-mail ou celular");
            System.out.println("2 - Consultas de pacientes que não possuem e-mail nem celular");
            System.out.println("3 - Todas as consultas");
            System.out.println("0 - Cancelar");

            System.out.print("Escolha uma opção: ");
            opcaoRelatorio = leitura.nextInt();
            leitura.nextLine();

            switch (opcaoRelatorio) {
                case 1:
                    imprimirRelatorioConsultas(
                            diaSeguinte,
                            "pacientes que possuem e-mail ou celular",
                            secretariaService.gerarRelatorioConsultasComEmailOuCelular(diaSeguinte));
                    break;
                case 2:
                    imprimirRelatorioConsultas(
                            diaSeguinte,
                            "pacientes que não possuem e-mail nem celular",
                            secretariaService.gerarRelatorioConsultasSemEmailESemCelular(diaSeguinte));
                    break;
                case 3:
                    imprimirRelatorioConsultas(
                            diaSeguinte,
                            "todas as consultas",
                            secretariaService.gerarRelatorioTodasConsultas(diaSeguinte));
                    break;
                case 0:
                    System.out.println("Operação cancelada.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcaoRelatorio < 0 || opcaoRelatorio > 3);
    }

    private void imprimirRelatorioConsultas(String diaSeguinte, String filtro, List<Consulta> consultas) {
        System.out.println("\n--- RELATÓRIO DE CONSULTAS PARA " + diaSeguinte + " ---");
        System.out.println("Filtro: " + filtro);

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o filtro informado.");
            return;
        }

        System.out.println("Total de consultas: " + consultas.size());
        System.out.println("--------------------------------------");

        for (Consulta consulta : consultas) {
            Paciente paciente = consulta.getPaciente();
            System.out.println("Paciente: " + paciente.getNomeCompleto()
                    + " | CPF: " + paciente.getCpf()
                    + " | E-mail: " + mostrarContato(paciente.getEmail())
                    + " | Celular: " + mostrarContato(paciente.getTelefone()));
            System.out.println("Consulta: " + consulta.getData()
                    + " às " + consulta.getHorario()
                    + " | Médico(a): " + consulta.getMedico().getNomeCompleto()
                    + " | Tipo: " + consulta.getTipo()
                    + " | Duração: " + consulta.getDuracao());
            System.out.println("--------------------------------------");
        }
    }

    private String mostrarContato(String contato) {
        if (contato == null || contato.isBlank()) {
            return "não cadastrado";
        }

        return contato;
    }
}
