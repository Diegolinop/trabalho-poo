package main;

import java.util.Scanner;

import services.GerenciadorMensagensService;

/**
 * Menu responsável por disparar o envio de lembretes de consultas para os pacientes.
 */
public class MenuGerenciadorMensagens {

    /** Scanner para leitura de dados do usuário. */
    private final Scanner leitura;
    
    /** Serviço responsável pelo envio de mensagens e lembretes. */
    private final GerenciadorMensagensService gerenciadorMensagensService;

    /**
     * Cria o menu do gerenciador de mensagens.
     * @param leitura Scanner de entrada.
     * @param gerenciadorMensagensService Serviço de envio de mensagens.
     */
    public MenuGerenciadorMensagens(Scanner leitura, GerenciadorMensagensService gerenciadorMensagensService) {
        this.leitura = leitura;
        this.gerenciadorMensagensService = gerenciadorMensagensService;
    }

    /**
     * Exibe a interface para envio de mensagens e solicita a data alvo.
     * Envio de e-mails ou SMS para os pacientes agendados na data informada.
     */
    public void exibir() {
        System.out.println("\n--- GERENCIADOR DE MENSAGENS ---");
        System.out.print("Digite a data do dia seguinte para enviar lembretes (dd/mm/aaaa): ");
        gerenciadorMensagensService.enviarMensagens(leitura.nextLine());
    }
}