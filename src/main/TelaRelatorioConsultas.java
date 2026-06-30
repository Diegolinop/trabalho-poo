package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import models.Consulta;
import models.Paciente;
import services.SecretariaService;

public class TelaRelatorioConsultas extends JFrame {

    private final SecretariaService secretariaService;
    private final JTextField campoData;
    private final JComboBox<String> campoFiltro;
    private final DefaultTableModel modeloTabela;

    private static final String[] COLUNAS = {
            "Paciente", "CPF", "E-mail", "Celular", "Data", "Horário", "Médico", "Tipo", "Duração"
    };

    public TelaRelatorioConsultas(SecretariaService secretariaService) {
        super("Relatório de Consultas");
        this.secretariaService = secretariaService;

        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel();
        campoData = new JTextField(10);
        campoFiltro = new JComboBox<>(new String[]{
                "Todas as consultas",
                "Pacientes com e-mail ou celular",
                "Pacientes sem e-mail e sem celular"
        });
        JButton botaoGerar = new JButton("Gerar Relatório");

        painelTopo.add(new JLabel("Data (dd/mm/aaaa):"));
        painelTopo.add(campoData);
        painelTopo.add(campoFiltro);
        painelTopo.add(botaoGerar);

        modeloTabela = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(24);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> dispose());

        botaoGerar.addActionListener(e -> gerarRelatorio());

        JPanel painelInferior = new JPanel();
        painelInferior.add(botaoVoltar);

        add(painelTopo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void gerarRelatorio() {
        String data = campoData.getText();
        if (data.isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe a data.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Consulta> consultas;
        switch (campoFiltro.getSelectedIndex()) {
            case 1:
                consultas = secretariaService.gerarRelatorioConsultasComEmailOuCelular(data);
                break;
            case 2:
                consultas = secretariaService.gerarRelatorioConsultasSemEmailESemCelular(data);
                break;
            default:
                consultas = secretariaService.gerarRelatorioTodasConsultas(data);
                break;
        }

        modeloTabela.setRowCount(0);

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada para o filtro informado.");
            return;
        }

        for (Consulta consulta : consultas) {
            Paciente paciente = consulta.getPaciente();
            modeloTabela.addRow(new Object[]{
                    paciente.getNomeCompleto(),
                    paciente.getCpf(),
                    mostrarContato(paciente.getEmail()),
                    mostrarContato(paciente.getTelefone()),
                    consulta.getData(),
                    consulta.getHorario(),
                    consulta.getMedico().getNomeCompleto(),
                    consulta.getTipo(),
                    consulta.getDuracao()
            });
        }
    }

    private String mostrarContato(String contato) {
        if (contato == null || contato.isBlank()) {
            return "não cadastrado";
        }
        return contato;
    }
}