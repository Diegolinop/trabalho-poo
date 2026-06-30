package main;

import java.util.Scanner;
import javax.swing.SwingUtilities;

import models.Medico;
import models.Secretaria;
import repositories.*;
import services.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe principal responsável por iniciar a aplicação Saúde e Cia.
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SaudeCiaPU");
        EntityManager em = emf.createEntityManager();
        
        // Inicializa os repositórios responsáveis pelo armazenamento dos dados.
        PacienteRepository pacienteRepository = new PacienteRepository(em);
        ConsultaRepository consultaRepository = new ConsultaRepository(em);
        MedicoRepository medicoRepository = new MedicoRepository(em);
        SecretariaRepository secretariaRepository = new SecretariaRepository(em);
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository(em);

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
        // Verifica se já existem antes de cadastrar para evitar erro de duplicidade no banco
        if (medicoService.buscarMedicoPorCrm(medicoNovo.getCrm()) == null) {
            medicoService.cadastrarMedico(medicoNovo);
        }
        if (secretariaService.buscarSecretariaPorMatricula(secretaria.getMatricula()) == null) {
            secretariaService.cadastrarSecretaria(secretaria);
        }
        if (secretariaService.buscarSecretariaPorMatricula(secretaria02.getMatricula()) == null) {
            secretariaService.cadastrarSecretaria(secretaria02);
        }

        // Prepara as ferramentas de entrada e configura o menu.
        Scanner leitura = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                leitura,
                secretariaService,
                medicoService,
                gerenciadorMensagensService
        );

        // Inicia a execução do sistema exibindo a tela principal.
        SwingUtilities.invokeLater(() -> {
            new TelaMenuPrincipal(secretariaService, medicoService, gerenciadorMensagensService).setVisible(true);
        });
        menuPrincipal.exibir();
    }
}
