package main;

import java.util.Scanner;

import models.Medico;
import models.Paciente;
import models.Prontuario;
import services.MedicoService;

public class MenuProntuario {

    private Scanner leitura;
    private MedicoService medicoService;
    private Medico medicoLogin;

    public MenuProntuario(Scanner leitura, MedicoService medicoService, Medico medicoLogin) {
        this.leitura = leitura;
        this.medicoService = medicoService;
        this.medicoLogin = medicoLogin;
    }

    public void exibir() {
        System.out.println("\n--- GERENCIAR PRONTUÁRIOS ---");
        System.out.print("Digite o CPF do paciente: ");
        Paciente pacienteProntuario = medicoService.buscarPacientePorCpf(leitura.nextLine());

        if (pacienteProntuario == null) {
            System.out.println("Não foi encontrado paciente registrado com o cpf digitado");
            return;
        }

        int opcao;

        do {
            System.out.println("\n");
            System.out.println("1 - Cadastrar prontuário");
            System.out.println("2 - Atualizar prontuário");
            System.out.println("3 - Remover prontuário");
            System.out.println("4 - Mostrar prontuário");
            System.out.println("5 - Mostrar lista de prontuários");
            System.out.println("0 - Voltar");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProntuario(pacienteProntuario);
                    break;
                case 2:
                    System.out.print("Digite o id do prontuário a ser atualizado: ");
                    int idAtualizar = leitura.nextInt();
                    leitura.nextLine();
                    atualizarProntuario(idAtualizar);
                    break;
                case 3:
                    System.out.print("Digite o id do prontuário a ser removido: ");
                    int idRemovido = leitura.nextInt();
                    leitura.nextLine();
                    medicoService.removerProntuario(medicoLogin, idRemovido);
                    break;
                case 4:
                    System.out.print("Digite o id do prontuário a ser procurado: ");
                    int idProcurar = leitura.nextInt();
                    leitura.nextLine();
                    medicoService.mostrarProntuario(medicoLogin, idProcurar);
                    break;
                case 5:
                    medicoService.mostrarListaProntuarios(medicoLogin);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private void cadastrarProntuario(Paciente pacienteProntuario) {
        System.out.print("Digite o id do novo prontuário: ");
        int id = leitura.nextInt();
        leitura.nextLine();

        if (!medicoService.verificarDisponibilidadeIdProntuario(medicoLogin, id)) {
            System.out.println("O médico já possui um prontuário com esse ID");
            return;
        }

        System.out.print("Digite a data do atendimento (ex: 04/06/2026): ");
        String data = leitura.nextLine();

        Prontuario novoProntuario = new Prontuario(pacienteProntuario, medicoLogin, data, id);

        System.out.println("Digite os sintomas relatados - digite 'fim' para parar: ");
        String sintoma;
        do {
            System.out.print("Sintoma: ");
            sintoma = leitura.nextLine();
            if (!sintoma.equalsIgnoreCase("fim")) {
                novoProntuario.adicionarSintoma(sintoma);
            }
        } while (!sintoma.equalsIgnoreCase("fim"));

        System.out.print("Diagnóstico: ");
        novoProntuario.setDiagnostico(leitura.nextLine());
        System.out.print("Prescrição de tratamento: ");
        novoProntuario.setPrescricao(leitura.nextLine());

        medicoService.registrarProntuario(novoProntuario);
        System.out.println("Prontuário com id " + id + " cadastrado com sucesso!");
    }

    private void atualizarProntuario(int id) {
        System.out.println("\n--- Atualizar Prontuário com ID " + id + " ---");

        Prontuario prontuario = medicoService.buscarProntuarioPorMedicoEId(medicoLogin, id);
        if (prontuario == null) {
            System.out.println("O paciente não tem histórico médico");
            return;
        }

        int opcao;

        do {
            System.out.println("Qual dado deseja atualizar?");
            System.out.println("1 - Sintomas");
            System.out.println("2 - Diagnóstico");
            System.out.println("3 - Preescrição");
            System.out.println("0 - Cancelar atualização");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    atualizarSintomas(prontuario);
                    break;
                case 2:
                    System.out.print("Digite o diagnóstico");
                    String diagnostico = leitura.nextLine();
                    medicoService.atualizarDiagnostico(prontuario, diagnostico);
                    break;
                case 3:
                    System.out.print("Digite a preescrição");
                    String preescricao = leitura.nextLine();
                    medicoService.atualizarPreescricao(prontuario, preescricao);
                    break;
                default:
                    break;
            }
        } while (opcao != 0);

        System.out.println("Prontuário com ID " + id + " atualizado com sucesso!");
    }

    private void atualizarSintomas(Prontuario prontuario) {
        System.out.println("Deseja adicionar ou remover um sintoma?");
        System.out.println("1 - Adicionar");
        System.out.println("2 - Remover");
        System.out.println("0 - Cancelar atualização de sintomas");

        System.out.print("Escolha uma opção: ");
        int opcaoSintoma = leitura.nextInt();
        leitura.nextLine();
        if (opcaoSintoma == 0) {
            return;
        }

        String sintoma;

        switch (opcaoSintoma) {
            case 1:
                System.out.print("Digite o sintoma a ser adicionado: ");
                sintoma = leitura.nextLine();
                medicoService.adicionarSintomaProntuario(prontuario, sintoma);
                break;
            case 2:
                System.out.print("Digite o sintoma a ser removido: ");
                sintoma = leitura.nextLine();
                medicoService.removerSintomaProntuario(prontuario, sintoma);
                break;
            default:
                break;
        }
    }
}
