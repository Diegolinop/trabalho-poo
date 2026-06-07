package main;

import java.util.Scanner;

import models.Secretaria;
import services.GerenciadorMensagensService;
import services.MedicoService;
import services.SecretariaService;

public class MenuPrincipal {

    private Scanner leitura;
    private SecretariaService secretariaService;
    private MedicoService medicoService;
    private GerenciadorMensagensService gerenciadorMensagensService;
    private Secretaria secretaria;

    public MenuPrincipal(Scanner leitura, SecretariaService secretariaService, MedicoService medicoService,
                         GerenciadorMensagensService gerenciadorMensagensService, Secretaria secretaria) {
        this.leitura = leitura;
        this.secretariaService = secretariaService;
        this.medicoService = medicoService;
        this.gerenciadorMensagensService = gerenciadorMensagensService;
        this.secretaria = secretaria;
    }

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
                    new MenuSecretaria(leitura, secretariaService, secretaria).exibir();
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
