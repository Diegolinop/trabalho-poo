package main;

import java.util.Scanner;

import models.Medico;
import models.Secretaria;
import repositories.*;
import services.*;

public class SaudeCia {

    public static void main(String[] args) {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        SecretariaRepository secretariaRepository = new SecretariaRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        SecretariaService secretariaService = new SecretariaService(pacienteRepository, consultaRepository, medicoRepository, secretariaRepository);
        MedicoService medicoService = new MedicoService(medicoRepository, prontuarioRepository, pacienteRepository);
        GerenciadorMensagensService gerenciadorMensagensService = new GerenciadorMensagensService(consultaRepository);

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

        medicoService.cadastrarMedico(medicoNovo);
        secretariaService.cadastrarSecretaria(secretaria);

        Scanner leitura = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                leitura,
                secretariaService,
                medicoService,
                gerenciadorMensagensService,
                secretaria
        );

        menuPrincipal.exibir();
    }
}
