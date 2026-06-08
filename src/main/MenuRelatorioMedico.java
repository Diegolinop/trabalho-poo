package main;

import java.util.List;
import java.util.Scanner;

import models.Medico;
import models.Paciente;
import models.Prontuario;
import services.MedicoService;

/**
 * Menu responsável pela geração de documentos e relatórios médicos.
 * Permite a emissão de receitas, atestados, declarações e consultas de atendimentos.
 */
public class MenuRelatorioMedico {

    /** Scanner para leitura de dados do usuário. */
    private final Scanner leitura;
    
    /** Serviço com a lógica de negócio do médico. */
    private final MedicoService medicoService;
    
    /** Médico atualmente autenticado no sistema. */
    private final Medico medicoLogin;

    /**
     * Cria o menu de relatórios médicos.
     * @param leitura Scanner de entrada.
     * @param medicoService Serviço do médico.
     * @param medicoLogin Médico logado que está gerando os relatórios.
     */
    public MenuRelatorioMedico(Scanner leitura, MedicoService medicoService, Medico medicoLogin) {
        this.leitura = leitura;
        this.medicoService = medicoService;
        this.medicoLogin = medicoLogin;
    }

    /**
     * Exibe as opções de relatórios disponíveis e processa a escolha do usuário.
     */
    public void exibir() {
        System.out.println("\n--- GERAR RELATÓRIOS MÉDICOS ---");

        System.out.println("1 - Receita");
        System.out.println("2 - Atestado");
        System.out.println("3 - Declaração de Acompanhamento");
        System.out.println("4 - Clientes atendidos no mês");
        System.out.print("Escolha o relatório: ");
        int opcaoRelatorio = leitura.nextInt();
        leitura.nextLine();

        switch (opcaoRelatorio) {
            case 1:
                gerarReceita();
                break;
            case 2:
                gerarAtestado();
                break;
            case 3:
                gerarDeclaracaoAcompanhamento();
                break;
            case 4:
                imprimirAtendimentosMes();
                break;
            default:
                System.out.println("Opção de relatório inválida.");
                break;
        }
    }

    /**
     * Coleta os dados necessários e gera uma receita médica para o paciente.
     */
    private void gerarReceita() {
        System.out.print("Digite o CPF do paciente para receita: ");
        String cpfReceita = leitura.nextLine();
        Paciente pacienteReceita = medicoService.buscarPacientePorCpf(cpfReceita);
        
        if (pacienteReceita == null) {
            System.out.println("Não foi encontrado paciente com o cpf digitado");
            return;
        }
        
        System.out.print("Digite a prescrição para imprimir na receita: ");
        System.out.println("\n" + medicoService.gerarReceita(medicoLogin, pacienteReceita, leitura.nextLine()));
    }

    /**
     * Coleta os dados necessários e gera um atestado médico.
     */
    private void gerarAtestado() {
        System.out.print("Digite o CPF do paciente para o atestado: ");
        String cpfRAtestado = leitura.nextLine();
        Paciente pacienteAtestado = medicoService.buscarPacientePorCpf(cpfRAtestado);
        
        if (pacienteAtestado == null) {
            System.out.println("Não foi encontrado paciente com o cpf digitado");
            return;
        }
        
        System.out.print("Dias de repouso: ");
        int dias = leitura.nextInt();
        leitura.nextLine();
        
        System.out.print("Motivo/CID: ");
        System.out.println("\n" + medicoService.gerarAtestado(medicoLogin, pacienteAtestado, dias, leitura.nextLine()));
    }

    /**
     * Coleta os dados necessários e gera uma declaração de comparecimento para um acompanhante.
     */
    private void gerarDeclaracaoAcompanhamento() {
        System.out.print("Digite o CPF do paciente para realizar a declaração de acompanhante: ");
        String cpfAcompanhado = leitura.nextLine();
        Paciente pacienteAcompanhado = medicoService.buscarPacientePorCpf(cpfAcompanhado);
        
        if (pacienteAcompanhado == null) {
            System.out.println("Não foi encontrado paciente com o cpf digitado");
            return;
        }
        
        System.out.print("Nome do acompanhante: ");
        String nomeAcompanhante = leitura.nextLine();
        
        System.out.print("Data da consulta: ");
        String dataConsulta = leitura.nextLine();
        
        System.out.println("\n" + medicoService.gerarDeclaracaoAcompanhamento(
                medicoLogin, pacienteAcompanhado, nomeAcompanhante, dataConsulta));
    }

    /**
     * Solicita um mês e ano específicos e imprime a lista de atendimentos realizados pelo médico.
     */
    private void imprimirAtendimentosMes() {
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
}