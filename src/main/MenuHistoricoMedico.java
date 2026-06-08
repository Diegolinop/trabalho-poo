package main;

import java.util.Scanner;

import models.Paciente;
import services.MedicoService;

/**
 * Menu interativo para gerenciamento do histórico médico dos pacientes.
 * Permite cadastrar, atualizar, remover e visualizar os dados de saúde.
 */
public class MenuHistoricoMedico {

    /** Scanner para leitura de dados do usuário. */
    private final Scanner leitura;
    
    /** Serviço com a lógica de negócio do médico. */
    private final MedicoService medicoService;

    /**
     * Cria o menu de histórico médico.
     * @param leitura Scanner de entrada.
     * @param medicoService Serviço do médico.
     */
    public MenuHistoricoMedico(Scanner leitura, MedicoService medicoService) {
        this.leitura = leitura;
        this.medicoService = medicoService;
    }

    /**
     * Exibe o menu principal de histórico médico e processa as opções escolhidas.
     * Exige a busca prévia de um paciente pelo CPF para liberar as operações.
     */
    public void exibir() {
        System.out.println("\n--- Gerenciar Histórico Médico ---");
        System.out.print("Digite o CPF do paciente: ");
        Paciente pacienteBuscado = medicoService.buscarPacientePorCpf(leitura.nextLine());

        if (pacienteBuscado == null) {
            System.out.println("Não foi encontrado paciente registrado com o cpf digitado");
            return;
        }

        int opcao;

        do {
            System.out.println("\n");
            System.out.println("1 - Cadastrar histórico médico");
            System.out.println("2 - Atualizar histórico médico");
            System.out.println("3 - Remover histórico médico");
            System.out.println("4 - Mostrar histórico médico");
            System.out.println("0 - Voltar");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarHistoricoMedico(pacienteBuscado);
                    break;
                case 2:
                    atualizarHistoricoMedico(pacienteBuscado);
                    break;
                case 3:
                    medicoService.removerHistoricoMedico(pacienteBuscado);
                    break;
                case 4:
                    medicoService.mostrarHistoricoMedico(pacienteBuscado);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Coleta os dados e registra um novo histórico médico para o paciente informado.
     * @param paciente paciente que receberá o histórico.
     */
    private void cadastrarHistoricoMedico(Paciente paciente) {
        System.out.println("\n--- Cadastrar Histórico Médico ---");

        if (medicoService.verificarHistoricoMedico(paciente)) {
            boolean valor;

            System.out.println("O paciente já apresenta um cadastro médico, deseja sobreescreve-lo? (true/false)");
            valor = leitura.nextBoolean();
            leitura.nextLine();

            if (!valor) {
                return;
            }
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
        
        medicoService.cadastrarHistoricoMedico(paciente, fuma, bebe, colesterol, diabete, doencaCardiaca);

        System.out.println("Digite as cirurgias - digite 'fim' para parar: ");
        String cirurgia;
        do {
            System.out.print("Cirurgia: ");
            cirurgia = leitura.nextLine();
            if (!cirurgia.equalsIgnoreCase("fim")) {
                medicoService.adicionarCirurgiaPaciente(paciente, cirurgia);
            }
        } while (!cirurgia.equalsIgnoreCase("fim"));

        System.out.println("Digite as alergias - digite 'fim' para parar: ");
        String alergia;
        do {
            System.out.print("Alergia: ");
            alergia = leitura.nextLine();
            if (!alergia.equalsIgnoreCase("fim")) {
                medicoService.adicionarAlergiaPaciente(paciente, alergia);
            }
        } while (!alergia.equalsIgnoreCase("fim"));

        System.out.println("Dados de saúde do paciente " + paciente.getNomeCompleto() + " cadastrados com sucesso!\n");
    }

    /**
     * Permite a atualização dos dados de saúde do histórico médico do paciente.
     * @param paciente paciente a ter o histórico atualizado.
     */
    private void atualizarHistoricoMedico(Paciente paciente) {
        System.out.println("\n--- Atualizar Histórico Médico ---");

        if (!medicoService.verificarHistoricoMedico(paciente)) {
            System.out.println("O paciente não tem histórico médico");
            return;
        }

        int opcao;

        do {
            System.out.println("Qual dado deseja atualizar?");
            System.out.println("1 - Fuma");
            System.out.println("2 - Bebe");
            System.out.println("3 - Colesterol alto");
            System.out.println("4 - Diabete");
            System.out.println("5 - Doença cardíaca");
            System.out.println("6 - Cirurgias");
            System.out.println("7 - Alergias");
            System.out.println("0 - Cancelar atualização");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            if (opcao > 0 && opcao < 6) {
                boolean valor;

                System.out.print("Informe o novo valor (true/false): ");
                valor = leitura.nextBoolean();
                leitura.nextLine();

                switch (opcao) {
                    case 1:
                        medicoService.atualizarFumaPaciente(paciente, valor);
                        break;
                    case 2:
                        medicoService.atualizarBebePaciente(paciente, valor);
                        break;
                    case 3:
                        medicoService.atualizarColesterolPaciente(paciente, valor);
                        break;
                    case 4:
                        medicoService.atualizarDiabetePaciente(paciente, valor);
                        break;
                    case 5:
                        medicoService.atualizarDoencaCardiacaPaciente(paciente, valor);
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                        break;
                }
            } else if (opcao == 6) {
                atualizarCirurgias(paciente);
            } else if (opcao == 7) {
                atualizarAlergias(paciente);
            }
        } while (opcao != 0);

        System.out.println("Dados de saúde do paciente " + paciente.getNomeCompleto() + " atualizados com sucesso!");
    }

    /**
     * Submenu para adicionar ou remover cirurgias do histórico do paciente.
     * @param paciente paciente a ser modificado.
     */
    private void atualizarCirurgias(Paciente paciente) {
        System.out.println("Deseja adicionar ou remover uma cirurgia do cadastro?");
        System.out.println("1 - Adicionar");
        System.out.println("2 - Remover");
        System.out.println("0 - Voltar");

        System.out.print("Escolha uma opção: ");
        int opcaoCirurgia = leitura.nextInt();
        leitura.nextLine();

        switch (opcaoCirurgia) {
            case 1:
                System.out.print("Digite o nome da cirurgia a ser adicionada: ");
                medicoService.adicionarCirurgiaPaciente(paciente, leitura.nextLine());
                break;
            case 2:
                System.out.print("Digite o nome da cirurgia a ser removida: ");
                medicoService.removerCirurgiaPaciente(paciente, leitura.nextLine());
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
                break;
        }
    }

    /**
     * Submenu para adicionar ou remover alergias do histórico do paciente.
     * @param paciente paciente a ser modificado.
     */
    private void atualizarAlergias(Paciente paciente) {
        System.out.println("Deseja adicionar ou remover uma alergia do cadastro?");
        System.out.println("1 - Adicionar");
        System.out.println("2 - Remover");
        System.out.println("0 - Voltar");

        System.out.print("Escolha uma opção: ");
        int opcaoAlergia = leitura.nextInt();
        leitura.nextLine();

        switch (opcaoAlergia) {
            case 1:
                System.out.print("Digite o nome da alergia a ser adicionada: ");
                medicoService.adicionarAlergiaPaciente(paciente, leitura.nextLine());
                break;
            case 2:
                System.out.print("Digite o nome da alergia a ser removida: ");
                medicoService.removerAlergiaPaciente(paciente, leitura.nextLine());
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
                break;
        }
    }
}