package main;

import models.Medico;
import models.Paciente;
import services.MedicoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Tela que representa o menu principal de navegação do médico.
 * Exibe as opções de listar pacientes, acessar o histórico médico,
 * gerenciar prontuários e gerar relatórios médicos, além de informações
 * do médico autenticado.
 */
public class TelaMenuMedico extends JFrame {

    private final MedicoService medicoService;
    private final Medico medico;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JButton btnListarPacientes;
    private JButton btnHistorico;
    private JButton btnProntuario;
    private JButton btnRelatorios;
    private JButton btnVoltar;

    /**
     * Constrói o menu do médico já autenticado, recebido após a validação
     * do CRM em outra tela.
     * @param medicoService serviço utilizado para as operações disponíveis
     *                       no menu do médico.
     * @param medico médico autenticado cujo nome e especialidade são
     *               exibidos no subtítulo da tela.
     */
    public TelaMenuMedico(MedicoService medicoService, Medico medico) {
        this.medicoService = medicoService;
        this.medico = medico;

        initComponents();

        setTitle("Saúde & Cia - Menu Médico");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa e organiza os componentes visuais da tela, incluindo os
     * rótulos de título e subtítulo, os botões de navegação e o layout
     * responsável pelo posicionamento de cada elemento.
     */
    private void initComponents() {
        lblTitulo    = new JLabel("Menu Médico");
        lblTitulo.setFont(new java.awt.Font("Cantarell", java.awt.Font.BOLD, 24));
        lblSubtitulo = new JLabel("Dr(a). " + medico.getNomeCompleto() + " — " + medico.getEspecialidade());

        btnListarPacientes = new JButton("Listar Pacientes");
        btnHistorico       = new JButton("Histórico Médico");
        btnProntuario      = new JButton("Prontuários");
        btnRelatorios      = new JButton("Relatórios Médicos");
        btnVoltar          = new JButton("Voltar");

        btnListarPacientes.addActionListener(e -> listarPacientes());
        btnHistorico.addActionListener(e -> abrirHistorico());
        btnProntuario.addActionListener(e -> abrirProntuario());
        btnRelatorios.addActionListener(e -> abrirRelatorios());
        btnVoltar.addActionListener(e -> dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitulo)
                    .addComponent(lblSubtitulo)
                    .addComponent(btnListarPacientes, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistorico,       GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProntuario,      GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRelatorios,      GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar,          GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitulo)
                .addGap(40, 40, 40)
                .addComponent(btnListarPacientes)
                .addGap(18, 18, 18)
                .addComponent(btnHistorico)
                .addGap(18, 18, 18)
                .addComponent(btnProntuario)
                .addGap(18, 18, 18)
                .addComponent(btnRelatorios)
                .addGap(40, 40, 40)
                .addComponent(btnVoltar)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Exibe em uma tabela não editável a lista de todos os pacientes
     * cadastrados no sistema, com CPF, nome e tipo de convênio.
     * Exibe uma mensagem informativa caso não existam pacientes
     * cadastrados.
     */
    private void listarPacientes() {
        List<Paciente> pacientes = medicoService.listarPacientes();

        String[] colunas = {"CPF", "Nome", "Convênio"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum paciente encontrado no sistema.",
                "Pacientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Paciente p : pacientes) {
            modelo.addRow(new Object[]{p.getCpf(), p.getNomeCompleto(), p.getTipoConvenio()});
        }

        JTable tabela = new JTable(modelo);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new java.awt.Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scroll, "Lista de Pacientes", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Solicita o CPF de um paciente e, caso encontrado, abre a tela de
     * histórico médico correspondente.
     * Exibe uma mensagem de erro caso o paciente não seja encontrado.
     */
    private void abrirHistorico() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do paciente:",
            "Histórico Médico", JOptionPane.QUESTION_MESSAGE);
        if (cpf == null) return;

        Paciente paciente = medicoService.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new TelaHistoricoMedico(medicoService, paciente).setVisible(true);
    }

    /**
     * Abre a tela de gerenciamento de prontuários do médico autenticado.
     */
    private void abrirProntuario() {
        new TelaProntuario(medicoService, medico).setVisible(true);
    }

    /**
     * Abre a tela de relatórios médicos do médico autenticado.
     */
    private void abrirRelatorios() {
        new TelaRelatorioMedico(medicoService, medico).setVisible(true);
    }
}