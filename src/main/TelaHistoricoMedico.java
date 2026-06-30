package main;

import models.HistoricoMedico;
import models.Paciente;
import services.MedicoService;

import javax.swing.*;
import java.awt.*;

/**
 * Tela responsável pelo gerenciamento do histórico médico de um paciente.
 * Permite cadastrar, atualizar, remover e exibir o histórico médico,
 * incluindo condições de saúde booleanas (fuma, bebe, colesterol, diabetes,
 * doença cardíaca) e listas de cirurgias e alergias.
 */
public class TelaHistoricoMedico extends JFrame {

    private final MedicoService medicoService;
    private final Paciente paciente;

    private JLabel lblTitulo;
    private JLabel lblPaciente;
    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnRemover;
    private JButton btnMostrar;
    private JButton btnVoltar;

    /**
     * Constrói a tela de histórico médico para o paciente informado, já
     * recebido após a busca por CPF realizada em outra tela.
     * @param medicoService serviço utilizado para consultar e manipular o
     *                       histórico médico do paciente.
     * @param paciente paciente cujo histórico médico será exibido e gerenciado.
     */
    public TelaHistoricoMedico(MedicoService medicoService, Paciente paciente) {
        this.medicoService = medicoService;
        this.paciente = paciente;

        initComponents();

        setTitle("Saúde & Cia - Histórico Médico");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa e organiza os componentes visuais da tela, incluindo os
     * rótulos de título e paciente, os botões de ação e o layout responsável
     * pelo posicionamento de cada elemento.
     */
    private void initComponents() {
        lblTitulo   = new JLabel("Histórico Médico");
        lblTitulo.setFont(new Font("Cantarell", Font.BOLD, 24));
        lblPaciente = new JLabel("Paciente: " + paciente.getNomeCompleto());

        btnCadastrar = new JButton("Cadastrar Histórico");
        btnAtualizar = new JButton("Atualizar Histórico");
        btnRemover   = new JButton("Remover Histórico");
        btnMostrar   = new JButton("Mostrar Histórico");
        btnVoltar    = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrarHistorico());
        btnAtualizar.addActionListener(e -> atualizarHistorico());
        btnRemover.addActionListener(e -> removerHistorico());
        btnMostrar.addActionListener(e -> mostrarHistorico());
        btnVoltar.addActionListener(e -> dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitulo)
                    .addComponent(lblPaciente)
                    .addComponent(btnCadastrar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover,   GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar,   GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar,    GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPaciente)
                .addGap(40, 40, 40)
                .addComponent(btnCadastrar)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizar)
                .addGap(18, 18, 18)
                .addComponent(btnRemover)
                .addGap(18, 18, 18)
                .addComponent(btnMostrar)
                .addGap(40, 40, 40)
                .addComponent(btnVoltar)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Cadastra um novo histórico médico para o paciente.
     * Caso já exista um histórico cadastrado, solicita confirmação do
     * usuário antes de sobrescrevê-lo. Exibe uma caixa de diálogo com
     * checkboxes para as condições de saúde booleanas e, em seguida,
     * solicita em loop a inclusão de cirurgias e alergias.
     */
    private void cadastrarHistorico() {
        if (medicoService.verificarHistoricoMedico(paciente)) {
            int confirmacao = JOptionPane.showConfirmDialog(this,
                "O paciente já possui histórico. Deseja sobrescrever?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacao != JOptionPane.YES_OPTION) return;
        }

        JCheckBox chkFuma           = new JCheckBox("Fuma");
        JCheckBox chkBebe           = new JCheckBox("Bebe");
        JCheckBox chkColesterol     = new JCheckBox("Colesterol alto");
        JCheckBox chkDiabetes       = new JCheckBox("Diabetes");
        JCheckBox chkDoencaCardiaca = new JCheckBox("Doença cardíaca");

        JPanel painel = new JPanel(new GridLayout(5, 1, 5, 5));
        painel.add(chkFuma);
        painel.add(chkBebe);
        painel.add(chkColesterol);
        painel.add(chkDiabetes);
        painel.add(chkDoencaCardiaca);

        int resultado = JOptionPane.showConfirmDialog(this, painel,
            "Condições do Paciente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        medicoService.cadastrarHistoricoMedico(paciente,
            chkFuma.isSelected(), chkBebe.isSelected(), chkColesterol.isSelected(),
            chkDiabetes.isSelected(), chkDoencaCardiaca.isSelected());

        adicionarItensEmLoop("cirurgia", true);
        adicionarItensEmLoop("alergia", false);

        JOptionPane.showMessageDialog(this, "Histórico cadastrado com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Solicita repetidamente ao usuário a inclusão de itens (cirurgias ou
     * alergias) por meio de caixas de diálogo, encerrando o loop quando o
     * usuário cancelar ou deixar o campo em branco.
     * @param tipo nome do tipo de item sendo adicionado, usado nas mensagens
     *             exibidas ao usuário (ex: "cirurgia", "alergia").
     * @param ehCirurgia indica se o item a ser adicionado é uma cirurgia
     *                   (true) ou uma alergia (false).
     */
    private void adicionarItensEmLoop(String tipo, boolean ehCirurgia) {
        while (true) {
            String item = JOptionPane.showInputDialog(this,
                "Digite uma " + tipo + " (cancele para parar):",
                "Adicionar " + tipo, JOptionPane.PLAIN_MESSAGE);
            if (item == null || item.isBlank()) break;
            if (ehCirurgia) medicoService.adicionarCirurgiaPaciente(paciente, item.trim());
            else            medicoService.adicionarAlergiaPaciente(paciente, item.trim());
        }
    }

    /**
     * Permite atualizar um dado específico do histórico médico do paciente.
     * Exibe um menu de opções com os campos disponíveis e direciona para o
     * método apropriado conforme a escolha: campo booleano, lista de
     * cirurgias ou lista de alergias. Exibe um aviso caso o paciente ainda
     * não possua histórico cadastrado.
     */
    private void atualizarHistorico() {
        if (!medicoService.verificarHistoricoMedico(paciente)) {
            JOptionPane.showMessageDialog(this, "O paciente não possui histórico médico.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] opcoes = {"Fuma", "Bebe", "Colesterol alto", "Diabetes", "Doença cardíaca", "Cirurgias", "Alergias"};

        int opcao = JOptionPane.showOptionDialog(this, "Qual dado deseja atualizar?", "Atualizar Histórico",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao == JOptionPane.CLOSED_OPTION) return;

        if (opcao < 5) {
            atualizarCampoBooleano(opcao, opcoes[opcao]);
        } else if (opcao == 5) {
            atualizarLista("cirurgia", true);
        } else {
            atualizarLista("alergia", false);
        }
    }

    /**
     * Atualiza um campo booleano específico do histórico médico do paciente,
     * exibindo uma checkbox pré-marcada com o valor atual salvo antes de
     * confirmar a alteração.
     * @param campo índice do campo booleano a ser atualizado (0 a 4,
     *              correspondendo a fuma, bebe, colesterol, diabetes e
     *              doença cardíaca, respectivamente).
     * @param nome nome do campo, usado nas mensagens exibidas ao usuário.
     */
    private void atualizarCampoBooleano(int campo, String nome) {
        HistoricoMedico h = paciente.getHistoricoMedico();

        JCheckBox chk = new JCheckBox(nome);
        switch (campo) {
            case 0 -> chk.setSelected(h.getFuma());
            case 1 -> chk.setSelected(h.getBebe());
            case 2 -> chk.setSelected(h.getColesterol());
            case 3 -> chk.setSelected(h.getDiabetes());
            case 4 -> chk.setSelected(h.getDoencaCardiaca());
        }

        int resultado = JOptionPane.showConfirmDialog(this, chk, "Atualizar " + nome,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        boolean valor = chk.isSelected();
        switch (campo) {
            case 0 -> medicoService.atualizarFumaPaciente(paciente, valor);
            case 1 -> medicoService.atualizarBebePaciente(paciente, valor);
            case 2 -> medicoService.atualizarColesterolPaciente(paciente, valor);
            case 3 -> medicoService.atualizarDiabetesPaciente(paciente, valor);
            case 4 -> medicoService.atualizarDoencaCardiacaPaciente(paciente, valor);
        }

        JOptionPane.showMessageDialog(this, nome + " atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permite adicionar ou remover um item (cirurgia ou alergia) da lista
     * correspondente do histórico médico do paciente, conforme a ação
     * escolhida pelo usuário.
     * @param tipo nome do tipo de item sendo gerenciado, usado nas mensagens
     *             exibidas ao usuário (ex: "cirurgia", "alergia").
     * @param ehCirurgia indica se o item a ser gerenciado é uma cirurgia
     *                   (true) ou uma alergia (false).
     */
    private void atualizarLista(String tipo, boolean ehCirurgia) {
        String[] acoes = {"Adicionar", "Remover"};
        int acao = JOptionPane.showOptionDialog(this, "O que deseja fazer?", "Gerenciar " + tipo + "s",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, acoes, acoes[0]);
        if (acao == JOptionPane.CLOSED_OPTION) return;

        String item = JOptionPane.showInputDialog(this, "Digite o nome da " + tipo + ":",
            "Gerenciar " + tipo, JOptionPane.PLAIN_MESSAGE);
        if (item == null || item.isBlank()) return;

        if (acao == 0) {
            if (ehCirurgia) medicoService.adicionarCirurgiaPaciente(paciente, item.trim());
            else            medicoService.adicionarAlergiaPaciente(paciente, item.trim());
            JOptionPane.showMessageDialog(this, tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean removido = ehCirurgia
                ? medicoService.removerCirurgiaPaciente(paciente, item.trim())
                : medicoService.removerAlergiaPaciente(paciente, item.trim());
            if (removido) {
                JOptionPane.showMessageDialog(this, tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " não encontrada no histórico.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Remove o histórico médico do paciente, mediante confirmação do
     * usuário. Exibe um aviso caso o paciente ainda não possua histórico
     * cadastrado.
     */
    private void removerHistorico() {
        if (!medicoService.verificarHistoricoMedico(paciente)) {
            JOptionPane.showMessageDialog(this, "O paciente não possui histórico médico.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja remover o histórico de " + paciente.getNomeCompleto() + "?",
            "Confirmar remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacao != JOptionPane.YES_OPTION) return;

        medicoService.removerHistoricoMedico(paciente);
        JOptionPane.showMessageDialog(this, "Histórico removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe em uma caixa de diálogo, em formato de texto monoespaçado, o
     * histórico médico completo do paciente, incluindo as condições de
     * saúde booleanas e as listas de cirurgias e alergias. 
     * Exibe um aviso caso o paciente ainda não possua histórico cadastrado.
     */
    private void mostrarHistorico() {
        if (!medicoService.verificarHistoricoMedico(paciente)) {
            JOptionPane.showMessageDialog(this, "O paciente não possui histórico médico.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HistoricoMedico h = paciente.getHistoricoMedico();
        StringBuilder sb = new StringBuilder();
        sb.append("--- HISTÓRICO MÉDICO DE ").append(paciente.getNomeCompleto()).append(" ---\n\n");
        sb.append("Fuma: ").append(h.getFuma() ? "Sim" : "Não").append("\n");
        sb.append("Bebe: ").append(h.getBebe() ? "Sim" : "Não").append("\n");
        sb.append("Colesterol alto: ").append(h.getColesterol() ? "Sim" : "Não").append("\n");
        sb.append("Diabetes: ").append(h.getDiabetes() ? "Sim" : "Não").append("\n");
        sb.append("Doença cardíaca: ").append(h.getDoencaCardiaca() ? "Sim" : "Não").append("\n\n");

        sb.append("Cirurgias:\n");
        if (h.getCirurgias().isEmpty()) {
            sb.append("  Nenhuma cirurgia registrada.\n");
        } else {
            for (String c : h.getCirurgias()) sb.append("  - ").append(c).append("\n");
        }

        sb.append("\nAlergias:\n");
        if (h.getAlergias().isEmpty()) {
            sb.append("  Nenhuma alergia registrada.\n");
        } else {
            for (String a : h.getAlergias()) sb.append("  - ").append(a).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this, scroll, "Histórico Médico", JOptionPane.PLAIN_MESSAGE);
    }
}