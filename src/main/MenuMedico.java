package main;

import java.util.List;
import java.util.Scanner;

import models.Medico;
import models.Paciente;
import services.MedicoService;

/**
 * Menu interativo exclusivo para o médico.
 * Controla o acesso aos dados dos pacientes, histórico médico, prontuários e relatórios.
 */
public class MenuMedico {

    /** Scanner para leitura de dados do usuário. */
    private final Scanner leitura;
    
    /** Serviço com a lógica de negócio do médico. */
    private final MedicoService medicoService;

    /**
     * Cria o menu do médico.
     * @param leitura Scanner de entrada.
     * @param medicoService Serviço do médico.
     */
    public MenuMedico(Scanner leitura, MedicoService medicoService) {
        this.leitura = leitura;
        this.medicoService = medicoService;
    }

    /**
     * Exibe o menu do médico e processa as opções escolhidas.
     * Exige a inserção do CRM para validar o acesso ao sistema.
     */
    public void exibir() {
        System.out.println("\nInsira o crm do médico para obter acesso ao sistema: ");
        String crmMedico = leitura.nextLine();

        Medico medicoLogin = medicoService.buscarMedicoPorCrm(crmMedico);
        if (medicoLogin == null) {
            System.out.println("Médico não encontrado no sistema!");
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
                    new MenuHistoricoMedico(leitura, medicoService).exibir();
                    break;
                case 3:
                    new MenuProntuario(leitura, medicoService, medicoLogin).exibir();
                    break;
                case 4:
                    new MenuRelatorioMedico(leitura, medicoService, medicoLogin).exibir();
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

    /**
     * Exibe a lista de pacientes cadastrados no sistema.
     * @param pacientes lista de pacientes.
     */
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
}