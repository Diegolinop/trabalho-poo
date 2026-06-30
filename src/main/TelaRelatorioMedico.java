package main;

import models.Medico;
import models.Paciente;
import models.Prontuario;
import services.MedicoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Tela responsável pela geração de relatórios médicos do médico
 * autenticado.
 * Permite gerar receitas, atestados, declarações de acompanhamento e
 * relatórios de atendimentos realizados em um mês específico.
 */
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

    /**
     * Constrói a tela de relatórios médicos para o médico autenticado.
     * @param medicoService serviço utilizado para gerar os relatórios
     *                       e consultar os dados necessários.
     * @param medico médico autenticado para o qual os relatórios serão
     *               gerados.
     */
    public TelaRelatorioMedico(MedicoService medicoService, Medico medico) {
        this.medicoService = medicoService;
        this.medico = medico;

        initComponents();

        setTitle("Saúde & Cia - Relatórios Médicos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa e organiza os componentes visuais da tela, incluindo os
     * rótulos de título e médico, os botões de geração de relatórios e o
     * layout responsável pelo posicionamento de cada elemento.
     */
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

    /**
     * Solicita o CPF de um paciente por meio de uma caixa de diálogo e
     * retorna o paciente correspondente, caso encontrado.
     * @param titulo título exibido na caixa de diálogo de entrada.
     * @return o paciente correspondente ao CPF informado, ou null caso o
     *         usuário cancele a operação ou o paciente não seja encontrado.
     */
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

    /**
     * Exibe um texto em uma caixa de diálogo, utilizando uma área de
     * texto monoespaçada e não editável, dentro de um painel rolável.
     * @param titulo título exibido na janela de diálogo.
     * @param texto conteúdo a ser exibido na área de texto.
     */
    private void exibirTextoEmDialog(String titulo, String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Gera uma receita médica para um paciente. 
     * Solicita o CPF do paciente e a prescrição a ser registrada,
     * exibindo o resultado em uma caixa de diálogo.
     */
    private void gerarReceita() {
        Paciente paciente = buscarPaciente("Receita");
        if (paciente == null) return;

        String prescricao = JOptionPane.showInputDialog(this, "Prescrição:",
            "Receita", JOptionPane.QUESTION_MESSAGE);
        if (prescricao == null) return;

        exibirTextoEmDialog("Receita Médica", medicoService.gerarReceita(medico, paciente, prescricao));
    }

    /**
     * Gera um atestado médico para um paciente. 
     * Solicita o CPF dopaciente, a quantidade de dias de repouso e o
     * motivo ou CID,exibindo o resultado em uma caixa de diálogo. Exibe
     * uma mensagem de erro caso o número de dias informado seja inválido.
     */
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

    /**
     * Gera uma declaração de acompanhamento para um paciente.
     * Solicita o CPF do paciente, o nome do acompanhante e a data da
     * consulta, exibindo o resultado em uma caixa de diálogo.
     */
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

    /**
     * Gera um relatório com os atendimentos realizados pelo médico em
     * um mês e ano específicos, exibindo a data, o paciente e o
     * diagnóstico de cada atendimento em uma caixa de diálogo. 
     * Exibe uma mensagem caso nenhum atendimento seja encontrado para
     * o período informado.
     */
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