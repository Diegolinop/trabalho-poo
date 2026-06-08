package main;

import java.util.Scanner;

import models.Medico;
import models.Secretaria;
import repositories.*;
import services.*;

/**
 * Classe principal responsável por iniciar a aplicação Saúde & Cia.
 * * Ela realiza a configuração inicial do sistema, instanciando os repositórios,
 * os serviços e cadastrando dados iniciais de funcionários. Por fim, 
 * inicializa e exibe o menu principal para a interação com o usuário.
 */
public class SaudeCia {

    /**
     * Ponto de entrada principal do programa.
     * Configura as dependências do sistema e inicia o laço do menu principal.
     *
     * @param args argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Inicializa os repositórios responsáveis pelo armazenamento dos dados.
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        SecretariaRepository secretariaRepository = new SecretariaRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();
        
        // Inicializa os serviços injetando as dependências dos repositórios.
        SecretariaService secretariaService = new SecretariaService(pacienteRepository, consultaRepository, medicoRepository, secretariaRepository);
        MedicoService medicoService = new MedicoService(medicoRepository, prontuarioRepository, pacienteRepository);
        GerenciadorMensagensService gerenciadorMensagensService = new GerenciadorMensagensService(consultaRepository);
        
        // Instancia os dados de uma secretária.
        Secretaria secretaria = new Secretaria(
                "123.456.789-00",
                "Diego",
                "Suárez",
                "(67) 99999-9999",
                "secretaria1@gmail.com",
                "Rua XXX, 123",
                "SEC-100"
        );
        
        // Instancia os dados de uma segunda secretária.
        Secretaria secretaria02 = new Secretaria(
                "777.888.999-00",
                "Bruna",
                "Scheffel",
                "(67) 99999-9999",
                "secretaria2@gmail.com",
                "Rua XXX, 123",
                "SEC-110"
        );
            
        // Instancia os dados mockados de um médico.
        Medico medicoNovo = new Medico(
                "111.222.333-00",
                "Jose Luis",
                "Peres",
                "(67) 99999-9999",
                "joseluisinho@gmail.com",
                "Rua XXX, 123",
                "Cardiologista",
                "12345"
        );

        // Efetua o cadastro dos funcionários padrão no sistema.
        medicoService.cadastrarMedico(medicoNovo);
        secretariaService.cadastrarSecretaria(secretaria);
        secretariaService.cadastrarSecretaria(secretaria02);
        
        // Prepara as ferramentas de entrada e configura o menu.
        Scanner leitura = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                leitura,
                secretariaService,
                medicoService,
                gerenciadorMensagensService,
                secretaria
        );
        
       // Inicia a execução do sistema exibindo a tela principal.
        menuPrincipal.exibir();
    }
}
