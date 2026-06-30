package main;

import javax.swing.*;
import java.awt.*;

import models.Paciente;
import models.Secretaria;
import services.SecretariaService;

public class TelaMenuSecretaria extends JFrame {

    private final SecretariaService secretariaService;
    private final Secretaria secretaria;

    public TelaMenuSecretaria(SecretariaService secretariaService, Secretaria secretaria) {
        super("Secretária - " + secretaria.getNomeCompleto());
        this.secretariaService = secretariaService;
        this.secretaria = secretaria;

        setSize(420, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Menu da Secretária");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botaoCadastrarPaciente = new JButton("Cadastrar Paciente");
        JButton botaoGerenciarPacientes = new JButton("Gerenciar Pacientes");
        JButton botaoGerenciarConsultas = new JButton("Gerenciar Consultas");
        JButton botaoRelatorio = new JButton("Relatório de Consultas");
        JButton botaoVoltar = new JButton("Voltar");

        for (JButton botao : new JButton[]{botaoCadastrarPaciente, botaoGerenciarPacientes,
                botaoGerenciarConsultas, botaoRelatorio, botaoVoltar}) {
            botao.setAlignmentX(Component.CENTER_ALIGNMENT);
            botao.setMaximumSize(new Dimension(250, 35));
        }

        botaoCadastrarPaciente.addActionListener(e -> cadastrarPaciente());
        botaoGerenciarPacientes.addActionListener(e -> new TelaPacientes(secretariaService).setVisible(true));
        botaoGerenciarConsultas.addActionListener(e -> new TelaConsultas(secretariaService).setVisible(true));
        botaoRelatorio.addActionListener(e -> new TelaRelatorioConsultas(secretariaService).setVisible(true));
        botaoVoltar.addActionListener(e -> dispose());

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(botaoCadastrarPaciente);
        painel.add(Box.createVerticalStrut(10));
        painel.add(botaoGerenciarPacientes);
        painel.add(Box.createVerticalStrut(10));
        painel.add(botaoGerenciarConsultas);
        painel.add(Box.createVerticalStrut(10));
        painel.add(botaoRelatorio);
        painel.add(Box.createVerticalStrut(20));
        painel.add(botaoVoltar);

        add(painel);
    }

    private void cadastrarPaciente() {
        JTextField cpf = new JTextField();
        JTextField nome = new JTextField();
        JTextField sobrenome = new JTextField();
        JTextField telefone = new JTextField();
        JTextField email = new JTextField();
        JTextField endereco = new JTextField();
        JComboBox<String> tipoConvenio = new JComboBox<>(new String[]{
                "Particular", "Plano de Saúde", "Convênio Empresarial", "SUS"
        });
        JTextField dataNascimento = new JTextField();

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("CPF:"));
        painel.add(cpf);
        painel.add(new JLabel("Nome:"));
        painel.add(nome);
        painel.add(new JLabel("Sobrenome:"));
        painel.add(sobrenome);
        painel.add(new JLabel("Telefone:"));
        painel.add(telefone);
        painel.add(new JLabel("Email:"));
        painel.add(email);
        painel.add(new JLabel("Endereço:"));
        painel.add(endereco);
        painel.add(new JLabel("Tipo de Convênio:"));
        painel.add(tipoConvenio);
        painel.add(new JLabel("Data de Nascimento (dd/mm/aaaa):"));
        painel.add(dataNascimento);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Cadastrar Paciente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Paciente novoPaciente = new Paciente(
                    cpf.getText(),
                    nome.getText(),
                    sobrenome.getText(),
                    telefone.getText().isBlank() ? null : telefone.getText(),
                    email.getText().isBlank() ? null : email.getText(),
                    endereco.getText(),
                    (String) tipoConvenio.getSelectedItem(),
                    dataNascimento.getText());
            secretariaService.cadastrarPaciente(novoPaciente);
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}