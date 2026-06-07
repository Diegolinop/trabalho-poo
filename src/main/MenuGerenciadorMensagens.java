package main;

import java.util.Scanner;

import services.GerenciadorMensagensService;

public class MenuGerenciadorMensagens {

    private Scanner leitura;
    private GerenciadorMensagensService gerenciadorMensagensService;

    public MenuGerenciadorMensagens(Scanner leitura, GerenciadorMensagensService gerenciadorMensagensService) {
        this.leitura = leitura;
        this.gerenciadorMensagensService = gerenciadorMensagensService;
    }

    public void exibir() {
        System.out.println("\n--- GERENCIADOR DE MENSAGENS ---");
        System.out.print("Digite a data do dia seguinte para enviar lembretes (dd/mm/aaaa): ");
        gerenciadorMensagensService.enviarMensagens(leitura.nextLine());
    }
}
