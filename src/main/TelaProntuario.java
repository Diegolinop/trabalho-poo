package main;

import models.Medico;
import models.Paciente;
import models.Prontuario;
import services.MedicoService;

import javax.swing.*;
import java.awt.*;

/**
 * Tela responsável pelo gerenciamento dos prontuários de um médico.
 * Permite cadastrar, atualizar, remover, exibir e listar os prontuários
 * associados ao médico autenticado.
 */
public class TelaProntuario extends JFrame {

    private final MedicoService medicoService;
    private final Medico medico;

    private JLabel lblTitulo;
    private JLabel lblMedico;
    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnRemover;
    private JButton btnMostrar;
    private JButton btnListar;
    private JButton btnVoltar;

    /**
     * Constrói a tela de gerenciamento de prontuários para o médico
     * autenticado.
     * @param medicoService serviço utilizado para consultar e manipular
     *                       os prontuários do médico.
     * @param medico médico autenticado cujos prontuários serão gerenciados.
     */
    public TelaProntuario(MedicoService medicoService, Medico medico) {
        this.medicoService = medicoService;
        this.medico = medico;

        initComponents();

        setTitle("Saúde & Cia - Prontuários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa e organiza os componentes visuais da tela, incluindo os
     * rótulos de título e médico, os botões de ação e o layout responsável
     * pelo posicionamento de cada elemento.
     */
    private void initComponents() {
        lblTitulo = new JLabel("Prontuários");
        lblTitulo.setFont(new Font("Cantarell", Font.BOLD, 24));
        lblMedico = new JLabel("Dr(a). " + medico.getNomeCompleto() + " — " + medico.getEspecialidade());

        btnCadastrar = new JButton("Cadastrar Prontuário");
        btnAtualizar = new JButton("Atualizar Prontuário");
        btnRemover   = new JButton("Remover Prontuário");
        btnMostrar   = new JButton("Mostrar Prontuário");
        btnListar    = new JButton("Listar Prontuários");
        btnVoltar    = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrarProntuario());
        btnAtualizar.addActionListener(e -> atualizarProntuario());
        btnRemover.addActionListener(e ->   removerProntuario());
        btnMostrar.addActionListener(e ->   mostrarProntuario());
        btnListar.addActionListener(e ->    listarProntuarios());
        btnVoltar.addActionListener(e ->    dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitulo)
                    .addComponent(lblMedico)
                    .addComponent(btnCadastrar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover,   GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar,   GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListar,    GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar,    GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
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
                .addComponent(btnCadastrar)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizar)
                .addGap(18, 18, 18)
                .addComponent(btnRemover)
                .addGap(18, 18, 18)
                .addComponent(btnMostrar)
                .addGap(18, 18, 18)
                .addComponent(btnListar)
                .addGap(40, 40, 40)
                .addComponent(btnVoltar)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Solicita o CPF de um paciente por meio de uma caixa de diálogo e
     * retorna o paciente correspondente, caso encontrado.
     *
     * @return o paciente correspondente ao CPF informado, ou null caso o
     *         usuário cancele a operação ou o paciente não seja encontrado.
     */
    private Paciente buscarPaciente() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do paciente:",
            "Buscar Paciente", JOptionPane.QUESTION_MESSAGE);
        if (cpf == null) return null;

        Paciente paciente = medicoService.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return paciente;
    }

    /**
     * Solicita ao usuário o ID de um prontuário por meio de uma caixa de
     * diálogo e converte o valor informado para um número inteiro.
     *
     * @param titulo título exibido na caixa de diálogo de entrada.
     * @return o ID informado, ou null caso o usuário cancele a operação
     *         ou informe um valor inválido.
     */
    private Integer pedirId(String titulo) {
        String input = JOptionPane.showInputDialog(this, "Digite o ID do prontuário:", titulo, JOptionPane.QUESTION_MESSAGE);
        if (input == null) return null;
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Cadastra um novo prontuário para um paciente, associado ao médico
     * autenticado.
     * Solicita o paciente, o ID do prontuário, a data do atendimento,
     * os sintomas em loop, o diagnóstico e a prescrição. Verifica
     * previamente se o ID informado já está em uso pelo médico.
     */
    private void cadastrarProntuario() {
        Paciente paciente = buscarPaciente();
        if (paciente == null) return;

        Integer id = pedirId("Cadastrar Prontuário");
        if (id == null) return;

        if (!medicoService.verificarDisponibilidadeIdProntuario(medico, id)) {
            JOptionPane.showMessageDialog(this, "O médico já possui um prontuário com esse ID.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String data = JOptionPane.showInputDialog(this, "Data do atendimento (ex: 04/06/2026):",
            "Cadastrar Prontuário", JOptionPane.QUESTION_MESSAGE);
        if (data == null) return;

        Prontuario novo = new Prontuario(paciente, medico, data, id);

        while (true) {
            String sintoma = JOptionPane.showInputDialog(this,
                "Digite um sintoma (cancele para parar):", "Sintomas", JOptionPane.PLAIN_MESSAGE);
            if (sintoma == null || sintoma.isBlank()) break;
            novo.adicionarSintoma(sintoma.trim());
        }

        String diagnostico = JOptionPane.showInputDialog(this, "Diagnóstico:",
            "Cadastrar Prontuário", JOptionPane.QUESTION_MESSAGE);
        if (diagnostico == null) return;
        novo.setDiagnostico(diagnostico);

        String prescricao = JOptionPane.showInputDialog(this, "Prescrição de tratamento:",
            "Cadastrar Prontuário", JOptionPane.QUESTION_MESSAGE);
        if (prescricao == null) return;
        novo.setPrescricao(prescricao);

        medicoService.registrarProntuario(novo);
        JOptionPane.showMessageDialog(this, "Prontuário com ID " + id + " cadastrado com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Permite atualizar um dado específico de um prontuário existente do
     * médico, identificado pelo ID informado.
     * Exibe um menu de opções com os campos disponíveis para atualização:
     * sintomas, diagnóstico ou prescrição. Exibe uma mensagem de erro caso
     * o prontuário não seja encontrado.
     */
    private void atualizarProntuario() {
        Integer id = pedirId("Atualizar Prontuário");
        if (id == null) return;

        Prontuario prontuario = medicoService.buscarProntuarioPorMedicoEId(medico, id);
        if (prontuario == null) {
            JOptionPane.showMessageDialog(this, "Prontuário não encontrado.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opcoes = {"Sintomas", "Diagnóstico", "Prescrição"};
        int opcao = JOptionPane.showOptionDialog(this, "Qual dado deseja atualizar?", "Atualizar Prontuário",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        if (opcao == JOptionPane.CLOSED_OPTION) return;

        switch (opcao) {
            case 0 -> atualizarSintomas(prontuario);
            case 1 -> {
                String diagnostico = JOptionPane.showInputDialog(this, "Novo diagnóstico:",
                    "Diagnóstico", JOptionPane.QUESTION_MESSAGE);
                if (diagnostico == null) return;
                medicoService.atualizarDiagnostico(prontuario, diagnostico);
                JOptionPane.showMessageDialog(this, "Diagnóstico atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            case 2 -> {
                String prescricao = JOptionPane.showInputDialog(this, "Nova prescrição:",
                    "Prescrição", JOptionPane.QUESTION_MESSAGE);
                if (prescricao == null) return;
                medicoService.atualizarPreescricao(prontuario, prescricao);
                JOptionPane.showMessageDialog(this, "Prescrição atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Permite adicionar ou remover um sintoma do prontuário informado,
     * conforme a ação escolhida pelo usuário.
     * @param prontuario prontuário cujos sintomas serão gerenciados.
     */
    private void atualizarSintomas(Prontuario prontuario) {
        String[] acoes = {"Adicionar", "Remover"};
        int acao = JOptionPane.showOptionDialog(this, "O que deseja fazer?", "Gerenciar Sintomas",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, acoes, acoes[0]);
        if (acao == JOptionPane.CLOSED_OPTION) return;

        String sintoma = JOptionPane.showInputDialog(this, "Digite o sintoma:",
            "Gerenciar Sintomas", JOptionPane.PLAIN_MESSAGE);
        if (sintoma == null || sintoma.isBlank()) return;

        if (acao == 0) {
            medicoService.adicionarSintomaProntuario(prontuario, sintoma.trim());
            JOptionPane.showMessageDialog(this, "Sintoma adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean removido = medicoService.removerSintomaProntuario(prontuario, sintoma.trim());
            if (removido) {
                JOptionPane.showMessageDialog(this, "Sintoma removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sintoma não encontrado no prontuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Remove um prontuário do médico autenticado, identificado pelo ID
     * informado, mediante confirmação do usuário. 
     * Exibe uma mensagem de erro caso o prontuário não seja encontrado.
     */
    private void removerProntuario() {
        Integer id = pedirId("Remover Prontuário");
        if (id == null) return;

        Prontuario prontuario = medicoService.buscarProntuarioPorMedicoEId(medico, id);
        if (prontuario == null) {
            JOptionPane.showMessageDialog(this, "Prontuário não encontrado.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja remover o prontuário de ID " + id + "?",
            "Confirmar remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacao != JOptionPane.YES_OPTION) return;

        medicoService.removerProntuario(medico, id);
        JOptionPane.showMessageDialog(this, "Prontuário removido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe em uma caixa de diálogo, em formato de texto monoespaçado, os
     * detalhes completos de um prontuário do médico, identificado pelo ID
     * informado.
     */
    private void mostrarProntuario() {
        Integer id = pedirId("Mostrar Prontuário");
        if (id == null) return;

        String texto = medicoService.gerarTextoProntuario(medico, id);

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this, scroll, "Prontuário", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Exibe em uma caixa de diálogo, em formato de texto monoespaçado, a
     * lista completa de prontuários associados ao médico autenticado.
     */
    private void listarProntuarios() {
        String texto = medicoService.gerarTextoListaProntuarios(medico);

        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this, scroll, "Lista de Prontuários", JOptionPane.PLAIN_MESSAGE);
    }
}