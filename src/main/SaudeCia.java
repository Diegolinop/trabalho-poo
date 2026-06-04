package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entidades.*;

public class SaudeCia { 
    public static void main(String[] args) {
        ArrayList<Consulta> consultas = new ArrayList<>();
        ArrayList<Medico> medicos = new ArrayList<>();
        ArrayList<Paciente> pacientes = new ArrayList<>();
        
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
        
        adicionarMedico(medicos, medicoNovo);
        
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
                    menuSecretaria(medicos, pacientes, consultas, secretaria);
                    break; 
                case 2:
                    menuMedico(medicos, pacientes);
                    break; 
                case 3:
                    menuGerenciadorMensagens(consultas);
                    break; 
                case 0:
                    break;
            }
        } while (opcao != 0);
    }
   
    /**
     * 
     * @param medicos
     * @param pacientes 
     */
    private static void menuMedico(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes) {
        Scanner leitura = new Scanner(System.in);
        System.out.println("\nInsira o crm do médico para obter acesso ao sistema: ");
        String crmMedico = leitura.nextLine();
        
        Medico medicoLogin = null;
        for (Medico medico : medicos) {
            if (medico.getCrm().equals(crmMedico)) {
                medicoLogin = medico;
                break;
            }
        }
        
        if (medicoLogin == null) {
            System.out.println("Insira um CRM válido para entrar no sistema");
            return;
        }
        
        // Menu do médico
        int opcao;
        
        do {
            System.out.println("\n--- MENU MÉDICO ----");
            System.out.println("1 - Listar todos os pacientes");
            System.out.println("2 - Gerenciar dados adicionais de um paciente");
            System.out.println("3 - Gerenciar prontuários");
            System.out.println("4 - Gerar relatórios médicos");
            System.out.println("0 - Voltar ao menu principal\n");
            
            System.out.print("Escolha uma opção: ");            
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- LISTA DE PACIENTES ---");
                    if (pacientes.isEmpty()) {
                        System.out.println("Nenhum paciente encontrado no sistema");
                    } else {
                        for (Paciente paciente : pacientes) {
                            System.out.println("Nome: " + paciente.getNomeCompleto() + " | CPF: " + paciente.getCpf());
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n--- GERENCIAR DADOS ADICIONAIS ---");
                    System.out.print("Digite o CPF do paciente: ");
                    String cpfBusca = leitura.nextLine();
                    
                    Paciente pacienteBuscado = null;
                    for (Paciente paciente : pacientes) {
                        if (paciente.getCpf().equals(cpfBusca)) {
                            pacienteBuscado = paciente;
                            break;
                        }
                    }
                    
                    if (pacienteBuscado != null) {
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
                        
                        medicoLogin.atualizarDadosAdicionais(pacienteBuscado, fuma, bebe, colesterol, diabete, doencaCardiaca);
                        System.out.println("Dados de saúde do paciente " + pacienteBuscado.getNomeCompleto() + " atualizados com sucesso!");
                    } else {
                        System.out.println("Não foi possível encontrar um paciente com cpf " + cpfBusca);
                    }
                    break;
                case 3:
                    System.out.println("\n--- GERENCIAR PRONTUÁRIOS ---");
                    System.out.print("Digite o CPF do paciente: ");
                    String cpfProntuario = leitura.nextLine();
                    
                    Paciente pacienteProntuario = null;
                    for (Paciente paciente : pacientes) {
                        if (paciente.getCpf().equals(cpfProntuario)) {
                            pacienteProntuario = paciente;
                            break;
                        }
                    }
                    
                    if (pacienteProntuario != null) {
                        System.out.print("Digite a data do atendimento (ex: 04/06/2026): ");
                        String dataAtendimento = leitura.nextLine();
                        
                        Prontuario novoProntuario = new Prontuario(pacienteProntuario, medicoLogin, dataAtendimento);
                        
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
                        String diagnostico = leitura.nextLine();
                        System.out.print("Prescrição de tratamento: ");
                        String prescricao = leitura.nextLine();
                        
                        novoProntuario.setDiagnostico(diagnostico);
                        novoProntuario.setPrescricao(prescricao);
                        
                        medicoLogin.adicionarProntuario(novoProntuario);
                        pacienteProntuario.adicionarProntuario(novoProntuario);
                        
                        System.out.println("Prontuário registrado com sucesso!");
                    } else {
                        System.out.println("Paciente não encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- GERAR RELATÓRIOS MÉDICOS ---");
                    System.out.print("Digite o CPF do paciente para os relatórios (ou deixe em branco para 'atendidos no mês'): ");
                    String cpfRelatorio = leitura.nextLine();
                    
                    Paciente pacienteRelatorio = null;
                    for (Paciente paciente : pacientes) {
                        if (paciente.getCpf().equals(cpfRelatorio)) {
                            pacienteRelatorio = paciente;
                            break;
                        }
                    }
                    
                    System.out.println("1 - Receita");
                    System.out.println("2 - Atestado");
                    System.out.println("3 - Declaração de Acompanhamento");
                    System.out.println("4 - Clientes atendidos no mês");
                    System.out.print("Escolha o relatório: ");
                    int opcaoRelatorio = leitura.nextInt();
                    leitura.nextLine(); // Limpar buffer
                    
                    if (opcaoRelatorio >= 1 && opcaoRelatorio <= 3 && pacienteRelatorio == null) {
                        System.out.println("Erro: Para este relatório, o paciente precisa ser encontrado.");
                        break;
                    }
                    
                    switch (opcaoRelatorio) {
                        case 1:
                            System.out.print("Digite a prescrição para imprimir na receita: ");
                            String prescricaoReceita = leitura.nextLine();
                            System.out.println("\n" + medicoLogin.gerarReceita(pacienteRelatorio, prescricaoReceita));
                            break;
                        case 2:
                            System.out.print("Dias de repouso: ");
                            int dias = leitura.nextInt();
                            leitura.nextLine();
                            System.out.print("Motivo/CID: ");
                            String motivo = leitura.nextLine();
                            System.out.println("\n" + medicoLogin.gerarAtestado(pacienteRelatorio, dias, motivo));
                            break;
                        case 3:
                            System.out.print("Nome do acompanhante: ");
                            String nomeAcompanhante = leitura.nextLine();
                            System.out.print("Data da consulta: ");
                            String dataConsulta = leitura.nextLine();
                            System.out.println("\n" + medicoLogin.gerarDeclaracaoAcompanhamento(pacienteRelatorio, nomeAcompanhante, dataConsulta));
                            break;
                        case 4:
                            System.out.print("Digite o mês/ano para consultar (ex: 06/2026): ");
                            String mesAno = leitura.nextLine();
                            List<Prontuario> atendimentos = medicoLogin.gerarRelatorioAtendimentosMes(mesAno);

                            System.out.println("\n--- ATENDIMENTOS EM " + mesAno + " ---");
                            if (atendimentos.isEmpty()) {
                                System.out.println("Nenhum atendimento encontrado para este período.");
                            } else {
                                System.out.println("Total de atendimentos: " + atendimentos.size());
                                System.out.println("--------------------------------------");
                                for (Prontuario p : atendimentos) {
                                    System.out.println("Data: " + p.getData() + 
                                        " | Paciente: " + p.getPaciente().getNomeCompleto() +
                                        " | Diagnóstico: " + p.getDiagnostico());
                                }
                            }
                            break;
                        default:
                            System.out.println("Opção de relatório inválida.");
                            break;
                    }
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
    
    private static void menuSecretaria(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes, ArrayList<Consulta> consultas, Secretaria secretaria){
        Scanner leitura = new Scanner(System.in);
        
        // Menu do médico
        int opcao;
        
        do {
            System.out.println("\n--- MENU DA SECRETÁRIA (" + secretaria.getNomeCompleto()+ ") ---");
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Listar Pacientes");
            System.out.println("3 - Remover Paciente");
            System.out.println("4 - Agendar Consulta");
            System.out.println("5 - Cancelar Consulta");
            System.out.println("0 - Voltar ao Menu Principal\n");
            
            System.out.print("Escolha uma opção: ");            
            opcao = leitura.nextInt();
            leitura.nextLine(); // Limpar buffer do teclado
            
            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRAS PACIENTE ---");
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
                    secretaria.cadastrarPaciente(pacientes, novoPaciente);
                    System.out.println("Paciente " + novoPaciente.getNomeCompleto() + " adicionado ao sistema.");
                    break;
                case 2:
                    System.out.println("\n--- LISTA DE PACIENTES ---");
                    if (pacientes.isEmpty()) {
                        System.out.println("Nenhum paciente cadastrado");
                    } else {
                        for (Paciente paciente : pacientes) {
                            System.out.println("CPF: " + paciente.getCpf() + " | Nome: " + paciente.getNomeCompleto() + " | Convênio: " + paciente.getTipoConvenio());
                        }
                    }
                    break;
                case 3:
                    System.out.println("\n--- REMOVER PACIENTE ---");
                    System.out.print("Digite o CPF do paciente a ser removido: ");
                    String cpfRemover = leitura.nextLine();
                    secretaria.removerPaciente(pacientes, cpfRemover);
                    break;
                case 4:
                    System.out.println("\n--- AGENDAR CONSULTA ---");
                    if (pacientes.isEmpty() || medicos.isEmpty()) {
                        System.out.println("Cadastre pelo menos um médico e um paciente antes de agendar.");
                        break;
                    }
                    
                    System.out.print("Digite o CPF do paciente: ");
                    String cpfPaciente = leitura.nextLine();
                    Paciente pacienteConsulta = secretaria.buscarPacientePorCpf(pacientes, cpfPaciente);
                    
                    if (pacienteConsulta == null) {
                        break;
                    }
                    
                    System.out.print("Digite o CRM do médico: ");
                    String crmMedico  = leitura.nextLine();
                    Medico medicoConsulta = null;
                    for (Medico medico : medicos) {
                        if (medico.getCrm().equals(crmMedico)) {
                            medicoConsulta = medico;
                            break;
                        }
                    }
                    
                    if (medicoConsulta == null) {
                        System.out.println("Médico com CRM informado não foi localizado.");
                        break;
                    }
                    
                    System.out.print("Data da consulta (dd/mm/aaaa): ");
                    String data = leitura.nextLine();
                    System.out.print("Horário: ");
                    String horario = leitura.nextLine();
                    System.out.print("Tipo da consulta (Normal / Retorno): ");
                    String tipo = leitura.nextLine();
                            
                    Consulta novaConsulta = new Consulta(data, horario, medicoConsulta, pacienteConsulta, tipo);
                    secretaria.agendarConsulta(consultas, novaConsulta);
                    break;
                case 5:
                    System.out.println("\n--- CANCELAR CONSULTA ---");
                    System.out.print("Digite o CPF do paciente para localizar a consulta: ");
                    String cpfCancelar = leitura.nextLine();
                    
                    // Cria uma lista temporária só para guardar as consultas desse paciente
                    ArrayList<Consulta> consultasDoPaciente = new ArrayList<>();
                    for (Consulta consulta : consultas) {
                        if (consulta.getPaciente().getCpf().equals(cpfCancelar)) {
                            consultasDoPaciente.add(consulta);
                        }
                    }
                    
                    if (consultasDoPaciente.isEmpty()) {
                        System.out.println("Nenhuma consulta localizada para o CPF informado.");
                    } else {
                        System.out.println("Consultas encontradas para este paciente:");
                        // Imprime todas as consultas
                        for (int i = 0; i < consultasDoPaciente.size(); i++) {
                            Consulta consultaIterada = consultasDoPaciente.get(i);
                            System.out.println((i + 1) + " - Data: " + consultaIterada.getData() + 
                                    " | Horário: " + consultaIterada.getHorario() + 
                                    " | Médico(a): " + consultaIterada.getMedico().getNomeCompleto());
                        }
                        
                        int opcaoCancelar;
                        boolean cancelou = false; 
                        
                        do {
                            System.out.print("\nDigite o número da consulta que deseja cancelar (ou 0 para desistir): ");
                            opcaoCancelar = leitura.nextInt();
                            leitura.nextLine(); 
                            
                            // Sai do laço se ela digitar 0
                            if (opcaoCancelar == 0) {
                                System.out.println("Operação cancelada.");
                                break; 
                            } 
                            
                            if (opcaoCancelar > 0 && opcaoCancelar <= consultasDoPaciente.size()) {
                                Consulta consultaCancelar = consultasDoPaciente.get(opcaoCancelar - 1);
                                secretaria.cancelarConsulta(consultas, consultaCancelar);
                                cancelou = true; 
                                break; 
                            } else {
                                // Se não for 0 e não for um número válido, avisa e o laço repete
                                System.out.println("Opção inválida. Tente novamente.");
                            }

                        } while (!cancelou);
                    }
                    break;
                case 0:
                    System.out.println("Retornando ao menu principal...");
                    break;
            }
        } while (opcao != 0);
    }
    
    public static void adicionarMedico(ArrayList<Medico> medicos, Medico medicoNovo) {
        medicos.add(medicoNovo);
    }
    
    private static void menuGerenciadorMensagens(ArrayList<Consulta> consultas) {
        Scanner leitura = new Scanner(System.in);
        GerenciadorMensagens gerenciador = new GerenciadorMensagens();

        System.out.println("\n--- GERENCIADOR DE MENSAGENS ---");
        System.out.print("Digite a data do dia seguinte para enviar lembretes (dd/mm/aaaa): ");
        String diaSeguinte = leitura.nextLine();

        gerenciador.enviarMensagens(consultas, diaSeguinte);
    }
}
