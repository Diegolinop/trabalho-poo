package main;

import models.Medico;
import models.Paciente;
import models.Prontuario;
import services.MedicoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRelatorioMedico extends JFrame {

    private final MedicoService medicoService;
    private final Medico medico;

    private JLabel lblTitulo;
    private JLabel lblMedico;
    private JButton btnReceita;
    private JButton btnAtestado;
    private JButton btnDeclaracao;
    private JButton btnAtendimentos;
    private JButton btnVoltar;

    public TelaRelatorioMedico(MedicoService medicoService, Medico medico) {
        this.medicoService = medicoService;
        this.medico = medico;

        initComponents();

        setTitle("Saúde & Cia - Relatórios Médicos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        lblTitulo = new JLabel("Relatórios Médicos");
        lblTitulo.setFont(new Font("Cantarell", Font.BOLD, 24));
        lblMedico = new JLabel("Dr(a). " + medico.getNomeCompleto() + " — " + medico.getEspecialidade());

        btnReceita      = new JButton("Receita");
        btnAtestado     = new JButton("Atestado");
        btnDeclaracao   = new JButton("Declaração de Acompanhamento");
        btnAtendimentos = new JButton("Atendimentos no Mês");
        btnVoltar       = new JButton("Voltar");

        btnReceita.addActionListener(e ->      gerarReceita());
        btnAtestado.addActionListener(e ->     gerarAtestado());
        btnDeclaracao.addActionListener(e ->   gerarDeclaracao());
        btnAtendimentos.addActionListener(e -> verAtendimentosMes());
        btnVoltar.addActionListener(e ->       dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitulo)
                    .addComponent(lblMedico)
                    .addComponent(btnReceita,      GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtestado,     GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeclaracao,   GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtendimentos, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar,       GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMedico)
                .addGap(40, 40, 40)
                .addComponent(btnReceita)
                .addGap(18, 18, 18)
                .addComponent(btnAtestado)
                .addGap(18, 18, 18)
                .addComponent(btnDeclaracao)
                .addGap(18, 18, 18)
                .addComponent(btnAtendimentos)
                .addGap(40, 40, 40)
                .addComponent(btnVoltar)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    private Paciente buscarPaciente(String titulo) {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do paciente:",
            titulo, JOptionPane.QUESTION_MESSAGE);
        if (cpf == null) return null;

        Paciente paciente = medicoService.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return paciente;
    }

    private void exibirTextoEmDialog(String titulo, String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    private void gerarReceita() {
        Paciente paciente = buscarPaciente("Receita");
        if (paciente == null) return;

        String prescricao = JOptionPane.showInputDialog(this, "Prescrição:",
            "Receita", JOptionPane.QUESTION_MESSAGE);
        if (prescricao == null) return;

        exibirTextoEmDialog("Receita Médica", medicoService.gerarReceita(medico, paciente, prescricao));
    }

    private void gerarAtestado() {
        Paciente paciente = buscarPaciente("Atestado");
        if (paciente == null) return;

        String diasStr = JOptionPane.showInputDialog(this, "Dias de repouso:",
            "Atestado", JOptionPane.QUESTION_MESSAGE);
        if (diasStr == null) return;

        int dias;
        try {
            dias = Integer.parseInt(diasStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de dias inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String motivo = JOptionPane.showInputDialog(this, "Motivo/CID:",
            "Atestado", JOptionPane.QUESTION_MESSAGE);
        if (motivo == null) return;

        exibirTextoEmDialog("Atestado Médico", medicoService.gerarAtestado(medico, paciente, dias, motivo));
    }

    private void gerarDeclaracao() {
        Paciente paciente = buscarPaciente("Declaração de Acompanhamento");
        if (paciente == null) return;

        String nomeAcompanhante = JOptionPane.showInputDialog(this, "Nome do acompanhante:",
            "Declaração de Acompanhamento", JOptionPane.QUESTION_MESSAGE);
        if (nomeAcompanhante == null) return;

        String dataConsulta = JOptionPane.showInputDialog(this, "Data da consulta (ex: 04/06/2026):",
            "Declaração de Acompanhamento", JOptionPane.QUESTION_MESSAGE);
        if (dataConsulta == null) return;

        exibirTextoEmDialog("Declaração de Acompanhamento",
            medicoService.gerarDeclaracaoAcompanhamento(medico, paciente, nomeAcompanhante, dataConsulta));
    }

    private void verAtendimentosMes() {
        String mesAno = JOptionPane.showInputDialog(this, "Mês/ano (ex: 06/2026):",
            "Atendimentos no Mês", JOptionPane.QUESTION_MESSAGE);
        if (mesAno == null) return;

        List<Prontuario> atendimentos = medicoService.gerarRelatorioAtendimentosMes(medico, mesAno);

        StringBuilder sb = new StringBuilder();
        sb.append("--- ATENDIMENTOS EM ").append(mesAno).append(" ---\n\n");

        if (atendimentos.isEmpty()) {
            sb.append("Nenhum atendimento encontrado para este período.");
        } else {
            sb.append("Total de atendimentos: ").append(atendimentos.size()).append("\n");
            sb.append("--------------------------------------\n");
            for (Prontuario p : atendimentos) {
                sb.append("Data: ").append(p.getData())
                  .append(" | Paciente: ").append(p.getPaciente().getNomeCompleto())
                  .append(" | Diagnóstico: ").append(p.getDiagnostico()).append("\n");
            }
        }

        exibirTextoEmDialog("Atendimentos no Mês", sb.toString());
    }
}