package main;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import models.Consulta;
import models.Medico;
import models.Paciente;
import services.SecretariaService;

/**
 * Tela responsável pelo gerenciamento das consultas de um paciente.
 * Permite buscar as consultas de um paciente pelo CPF, editar diretamente
 * na tabela os campos de data, horário e tipo, alterar o médico responsável,
 * cancelar uma consulta selecionada e agendar uma nova consulta.
 */
public class TelaConsultas extends JFrame {

    private final SecretariaService secretariaService;
    private final DefaultTableModel modeloTabela;
    private final JTable tabela;
    private final JTextField campoCpf;
    private List<Consulta> consultas;
    private Paciente pacienteAtual;
    private boolean atualizandoProgramaticamente;

    private static final String[] COLUNAS = {"Data", "Horário", "Médico", "Tipo"};
    
    /**
     * Constrói a tela de gerenciamento de consultas, montando o campo de
     * busca por CPF, a tabela editável de consultas e os botões de ação.
     * @param secretariaService serviço utilizado para buscar, agendar,
     *                           atualizar e cancelar consultas.
     */
    public TelaConsultas(SecretariaService secretariaService) {
        super("Gerenciar Consultas");
        this.secretariaService = secretariaService;

        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelBusca = new JPanel();
        campoCpf = new JTextField(15);
        JButton botaoBuscar = new JButton("Buscar Consultas do Paciente");
        JButton botaoAgendar = new JButton("Agendar Nova Consulta");
        painelBusca.add(new JLabel("CPF do paciente:"));
        painelBusca.add(campoCpf);
        painelBusca.add(botaoBuscar);
        painelBusca.add(botaoAgendar);

        modeloTabela = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 2;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(24);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Normal", "Retorno"});
        tabela.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboTipo));

        modeloTabela.addTableModelListener(this::onCelulaEditada);

        JPanel painelBotoes = new JPanel();
        JButton botaoAlterarMedico = new JButton("Alterar Médico");
        JButton botaoCancelar = new JButton("Cancelar Selecionada");
        JButton botaoVoltar = new JButton("Voltar");
        painelBotoes.add(botaoAlterarMedico);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoVoltar);

        botaoBuscar.addActionListener(e -> buscarConsultas());
        botaoAgendar.addActionListener(e -> agendarConsulta());
        botaoAlterarMedico.addActionListener(e -> alterarMedico());
        botaoCancelar.addActionListener(e -> cancelarSelecionada());
        botaoVoltar.addActionListener(e -> dispose());

        add(painelBusca, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    /**
     * Busca o paciente pelo CPF informado no campo de busca e, caso
     * encontrado, carrega suas consultas na tabela.
     * Exibe uma mensagem de erro caso nenhum paciente seja encontrado
     * com o CPF informado.
     */
    private void buscarConsultas() {
        String cpf = campoCpf.getText();
        pacienteAtual = secretariaService.buscarPacientePorCpf(cpf);

        if (pacienteAtual == null) {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        carregarConsultas(cpf);
    }

    /**
     * Carrega na tabela todas as consultas associadas ao CPF informado.
     * Limpa as linhas existentes antes de preencher a tabela novamente e
     * desativa temporariamente a escuta de edição de células para evitar
     * disparos indevidos do listener durante o recarregamento.
     * @param cpf CPF do paciente cujas consultas serão carregadas.
     */
    private void carregarConsultas(String cpf) {
        atualizandoProgramaticamente = true;
        consultas = secretariaService.buscarConsultasPorCpfPaciente(cpf);
        modeloTabela.setRowCount(0);

        for (Consulta consulta : consultas) {
            modeloTabela.addRow(new Object[]{
                    consulta.getData(),
                    consulta.getHorario(),
                    consulta.getMedico().getNomeCompleto(),
                    consulta.getTipo()
            });
        }
        atualizandoProgramaticamente = false;
    }

    /**
     * Trata a edição de uma célula da tabela de consultas, propagando a
     * alteração para o serviço correspondente de acordo com a coluna editada.
     * Ignora eventos disparados durante o recarregamento programático da
     * tabela. Caso a atualização seja inválida, exibe uma mensagem de erro
     * e recarrega a tabela para descartar a edição malsucedida.
     * @param evento evento de alteração disparado pelo modelo da tabela.
     */
    private void onCelulaEditada(TableModelEvent evento) {
        if (atualizandoProgramaticamente || evento.getType() != TableModelEvent.UPDATE) {
            return;
        }

        int linha = evento.getFirstRow();
        int coluna = evento.getColumn();
        if (linha < 0 || consultas == null || linha >= consultas.size()) {
            return;
        }

        Consulta consulta = consultas.get(linha);
        String novoValor = String.valueOf(modeloTabela.getValueAt(linha, coluna));

        try {
            switch (coluna) {
                case 0:
                    secretariaService.atualizarDataConsulta(consulta, novoValor);
                    break;
                case 1:
                    secretariaService.atualizarHorarioConsulta(consulta, novoValor);
                    break;
                case 3:
                    secretariaService.atualizarTipoConsulta(consulta, novoValor);
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            carregarConsultas(pacienteAtual.getCpf());
        }
    }

    /**
     * Altera o médico responsável pela consulta selecionada na tabela.
     * Solicita o CRM do novo médico por meio de uma caixa de diálogo,
     * valida sua existência e atualiza a consulta selecionada. Exibe uma
     * mensagem de erro caso nenhuma consulta esteja selecionada ou o
     * médico informado não seja encontrado.
     */
    private void alterarMedico() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta.");
            return;
        }

        String crm = JOptionPane.showInputDialog(this, "CRM do novo médico:");
        if (crm == null || crm.isBlank()) {
            return;
        }

        Medico medico = secretariaService.buscarMedicoPorCrm(crm);
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "Médico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        secretariaService.atualizarMedicoConsulta(consultas.get(linha), medico);
        carregarConsultas(pacienteAtual.getCpf());
    }
    
    /**
     * Cancela a consulta atualmente selecionada na tabela, mediante
     * confirmação do usuário.
     * Exibe uma mensagem caso nenhuma consulta esteja selecionada.
     */
    private void cancelarSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this, "Cancelar esta consulta?",
                "Confirmar cancelamento", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            secretariaService.cancelarConsulta(consultas.get(linha));
            carregarConsultas(pacienteAtual.getCpf());
        }
    }

    /**
     * Abre um formulário para agendamento de uma nova consulta, solicitando
     * o CPF do paciente, o CRM do médico, a data, o horário e o tipo da
     * consulta.
     * Valida a existência de médicos e pacientes cadastrados antes de
     * permitir o agendamento, busca o paciente e o médico informados, e
     * recarrega a tabela de consultas do paciente após o agendamento bem
     * sucedido. Exibe mensagens de erro em caso de dados inválidos ou
     * entidades não encontradas.
     */
    private void agendarConsulta() {
        if (!secretariaService.existemMedicosEPacientesCadastrados()) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos um médico e um paciente antes de agendar.");
            return;
        }

        JTextField campoCpfPaciente = new JTextField();
        JTextField campoCrmMedico = new JTextField();
        JTextField campoData = new JTextField();
        JTextField campoHorario = new JTextField();
        JComboBox<String> campoTipo = new JComboBox<>(new String[]{"Normal", "Retorno"});

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("CPF do paciente:"));
        painel.add(campoCpfPaciente);
        painel.add(new JLabel("CRM do médico:"));
        painel.add(campoCrmMedico);
        painel.add(new JLabel("Data (dd/mm/aaaa):"));
        painel.add(campoData);
        painel.add(new JLabel("Horário:"));
        painel.add(campoHorario);
        painel.add(new JLabel("Tipo:"));
        painel.add(campoTipo);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Agendar Consulta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        Paciente paciente = secretariaService.buscarPacientePorCpf(campoCpfPaciente.getText());
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Medico medico = secretariaService.buscarMedicoPorCrm(campoCrmMedico.getText());
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "Médico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Consulta novaConsulta = new Consulta(campoData.getText(), campoHorario.getText(), medico, paciente,
                    (String) campoTipo.getSelectedItem());
            secretariaService.agendarConsulta(novaConsulta);

            campoCpf.setText(paciente.getCpf());
            pacienteAtual = paciente;
            carregarConsultas(paciente.getCpf());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}