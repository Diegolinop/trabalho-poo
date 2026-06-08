package main;

import java.util.Scanner;

import models.Secretaria;
import services.GerenciadorMensagensService;
import services.MedicoService;
import services.SecretariaService;

/**
 * Menu principal do sistema.
 * Responsável por direcionar o usuário para os submenus específicos de secretária, médico ou gerenciamento de mensagens.
 */
public class MenuPrincipal {

    /** Scanner para leitura de dados do usuário. */
    private final Scanner leitura;
    
    /** Serviço com a lógica de negócio da secretária. */
    private final SecretariaService secretariaService;
    
    /** Serviço com a lógica de negócio do médico. */
    private final MedicoService medicoService;
    
    /** Serviço responsável pelo envio de mensagens e lembretes. */
    private final GerenciadorMensagensService gerenciadorMensagensService;
   
    /**
     * Cria o menu principal do sistema.
     * @param leitura Scanner de entrada.
     * @param secretariaService Serviço da secretária.
     * @param medicoService Serviço do médico.
     * @param gerenciadorMensagensService Serviço de envio de mensagens.
     * @param secretaria Secretária padrão do sistema.
     */
    public MenuPrincipal(Scanner leitura, SecretariaService secretariaService, MedicoService medicoService,
                         GerenciadorMensagensService gerenciadorMensagensService) {
        this.leitura = leitura;
        this.secretariaService = secretariaService;
        this.medicoService = medicoService;
        this.gerenciadorMensagensService = gerenciadorMensagensService;
    }

    /**
     * Exibe as opções do menu principal e gerencia a navegação para os demais submenus do sistema.
     */
    public void exibir() {
        int opcao;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Menu da Secretária");
            System.out.println("2 - Menu do Médico");
            System.out.println("3 - Enviar mensagens");
            System.out.println("0 - Sair do Sistema\n");

            System.out.print("Escolha uma opção: ");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    new MenuSecretaria(leitura, secretariaService).exibir();
                    break;
                case 2:
                    new MenuMedico(leitura, medicoService).exibir();
                    break;
                case 3:
                    new MenuGerenciadorMensagens(leitura, gerenciadorMensagensService).exibir();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }
}