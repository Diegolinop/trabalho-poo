package main;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import models.Paciente;
import services.SecretariaService;

public class TelaPacientes extends JFrame {

    private final SecretariaService secretariaService;
    private final DefaultTableModel modeloTabela;
    private final JTable tabela;
    private List<Paciente> pacientes;
    private boolean atualizandoProgramaticamente;

    private static final String[] COLUNAS = {
            "CPF", "Nome", "Sobrenome", "Telefone", "Email", "Endereço", "Tipo de Convênio", "Data de Nascimento"
    };

    public TelaPacientes(SecretariaService secretariaService) {
        super("Gerenciar Pacientes");
        this.secretariaService = secretariaService;

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(COLUNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(24);

        JComboBox<String> comboConvenio = new JComboBox<>(new String[]{
                "Particular", "Plano de Saúde", "Convênio Empresarial", "SUS"
        });
        tabela.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboConvenio));

        carregarPacientes();

        modeloTabela.addTableModelListener(this::onCelulaEditada);

        JPanel botoes = new JPanel();
        JButton botaoAtualizar = new JButton("Atualizar Lista");
        JButton botaoRemover = new JButton("Remover Selecionado");
        JButton botaoVoltar = new JButton("Voltar");

        botaoAtualizar.addActionListener(e -> carregarPacientes());
        botaoRemover.addActionListener(e -> removerSelecionado());
        botaoVoltar.addActionListener(e -> dispose());

        botoes.add(botaoAtualizar);
        botoes.add(botaoRemover);
        botoes.add(botaoVoltar);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private void carregarPacientes() {
        atualizandoProgramaticamente = true;
        pacientes = secretariaService.listarPacientes();
        modeloTabela.setRowCount(0);

        for (Paciente paciente : pacientes) {
            modeloTabela.addRow(new Object[]{
                    paciente.getCpf(),
                    paciente.getNome(),
                    paciente.getSobrenome(),
                    paciente.getTelefone(),
                    paciente.getEmail(),
                    paciente.getEndereco(),
                    paciente.getTipoConvenio(),
                    paciente.getDataNascimento()
            });
        }
        atualizandoProgramaticamente = false;
    }

    private void onCelulaEditada(TableModelEvent evento) {
        if (atualizandoProgramaticamente || evento.getType() != TableModelEvent.UPDATE) {
            return;
        }

        int linha = evento.getFirstRow();
        int coluna = evento.getColumn();
        if (linha < 0 || linha >= pacientes.size()) {
            return;
        }

        Paciente paciente = pacientes.get(linha);
        String novoValor = String.valueOf(modeloTabela.getValueAt(linha, coluna));

        try {
            switch (coluna) {
                case 1:
                    secretariaService.atualizarNomePaciente(paciente, novoValor);
                    break;
                case 2:
                    secretariaService.atualizarSobrenomePaciente(paciente, novoValor);
                    break;
                case 3:
                    secretariaService.atualizarTelefonePaciente(paciente, novoValor);
                    break;
                case 4:
                    secretariaService.atualizarEmailPaciente(paciente, novoValor);
                    break;
                case 5:
                    secretariaService.atualizarEnderecoPaciente(paciente, novoValor);
                    break;
                case 6:
                    secretariaService.atualizarTipoConvenioPaciente(paciente, novoValor);
                    break;
                case 7:
                    secretariaService.atualizarDataNascimentoPaciente(paciente, novoValor);
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            carregarPacientes();
        }
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para remover.");
            return;
        }

        Paciente paciente = pacientes.get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Remover o paciente " + paciente.getNomeCompleto() + "?",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            secretariaService.removerPaciente(paciente.getCpf());
            carregarPacientes();
        }
    }
}