package main;

import services.GerenciadorMensagensService;
import services.MedicoService;
import services.SecretariaService;
import models.Medico;
import models.Secretaria;

import javax.swing.JOptionPane;

/**
 * Tela inicial do sistema Saúde e Cia. 
 * responsável por direcionar o usuário para o menu correspondente ao
 * seu perfil (secretária ou médico), além de permitir o gerenciamento de
 * mensagens de lembrete de consultas e o encerramento do sistema.
 */
public class TelaMenuPrincipal extends javax.swing.JFrame {
    private SecretariaService secretariaService;
    private MedicoService medicoService;
    private GerenciadorMensagensService gerenciadorMensagensService;

/**
     * Construtor atualizado para receber os serviços, assim como o
     * MenuPrincipal do console fazia.
     * @param secretariaService serviço utilizado para as operações da
     *                          secretária.
     * @param medicoService serviço utilizado para as operações do médico.
     * @param gerenciadorMensagensService serviço utilizado para o envio
     *                                    e a geração de texto de
     *                                    lembretes de consulta.
     */
    public TelaMenuPrincipal(SecretariaService secretariaService, MedicoService medicoService, GerenciadorMensagensService gerenciadorMensagensService) {
        
        this.secretariaService = secretariaService;
        this.medicoService = medicoService;
        this.gerenciadorMensagensService = gerenciadorMensagensService;
        
        initComponents();
        
        setTitle("Saúde & Cia - Menu Principal");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa e organiza os componentes visuais da tela, incluindo os
     * rótulos de título e subtítulo, os botões de navegação e o layout
     * responsável pelo posicionamento de cada elemento.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        btnSecretaria = new javax.swing.JButton();
        btnMedico = new javax.swing.JButton();
        btnMensagens = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Cantarell", 1, 24));
        lblTitulo.setText("Saude & Cia");

        lblSubtitulo.setText("Selecione o seu perfil:");

        btnSecretaria.setText("Sou Secretário(a)");
        btnSecretaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSecretariaActionPerformed(evt);
            }
        });

        btnMedico.setText("Sou Médico(a)");
        btnMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicoActionPerformed(evt);
            }
        });

        btnMensagens.setText("Gerenciar Mensagens");
        btnMensagens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMensagensActionPerformed(evt);
            }
        });

        btnSair.setText("Sair do Sistema");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblTitulo)
                    .addComponent(lblSubtitulo)
                    .addComponent(btnSecretaria, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMensagens, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(230, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitulo)
                .addGap(40, 40, 40)
                .addComponent(btnSecretaria)
                .addGap(18, 18, 18)
                .addComponent(btnMedico)
                .addGap(18, 18, 18)
                .addComponent(btnMensagens)
                .addGap(40, 40, 40)
                .addComponent(btnSair)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Solicita a matrícula da secretária por meio de uma caixa de
     * diálogo e, caso encontrada, abre o menu correspondente. Exibe
     * uma mensagem de erro caso a secretária não seja encontrada.
     * @param evt evento de clique disparado pelo botão de secretária.
     */
    private void btnSecretariaActionPerformed(java.awt.event.ActionEvent evt) {
        String matricula = JOptionPane.showInputDialog(null, "Digite a matrícula:", "", JOptionPane.QUESTION_MESSAGE);
        if (matricula == null) return;

        Secretaria secretaria = secretariaService.buscarSecretariaPorMatricula(matricula);
        if (secretaria == null) {
            JOptionPane.showMessageDialog(this, "Secretário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        new TelaMenuSecretaria(secretariaService, secretaria).setVisible(true);
    }

    /**
     * Solicita o CRM do médico por meio de uma caixa de diálogo e, caso
     * encontrado, abre o menu correspondente. Exibe uma mensagem de erro
     * caso o médico não seja encontrado.
     * @param evt evento de clique disparado pelo botão de médico.
     */
    private void btnMedicoActionPerformed(java.awt.event.ActionEvent evt) {
        String crm = JOptionPane.showInputDialog(this, "Digite o CRM:", "", JOptionPane.QUESTION_MESSAGE);
        if (crm == null) return;

        Medico medico = medicoService.buscarMedicoPorCrm(crm);
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "Médico não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        new TelaMenuMedico(medicoService, medico).setVisible(true);
    }
    
    /**
     * Solicita a data do dia seguinte por meio de uma caixa de diálogo,
     * dispara o envio dos lembretes de consulta e exibe o resultado
     * formatado em uma caixa de diálogo com rolagem.
     * @param evt evento de clique disparado pelo botão de mensagens.
     */
    private void btnMensagensActionPerformed(java.awt.event.ActionEvent evt) {
        String data = JOptionPane.showInputDialog(this, 
            "Digite a data do dia seguinte para enviar lembretes (dd/mm/aaaa):", 
            "", JOptionPane.QUESTION_MESSAGE);
        if (data == null) return;

        gerenciadorMensagensService.enviarMensagens(data); // mantém o print no console

        String texto = gerenciadorMensagensService.gerarTextoMensagens(data);

        javax.swing.JTextArea area = new javax.swing.JTextArea(texto);
        area.setEditable(false);
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(area);
        scroll.setPreferredSize(new java.awt.Dimension(500, 350));

        JOptionPane.showMessageDialog(this, scroll, 
            "Lembretes Enviados - " + data, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Encerra a execução do sistema.
     * @param evt evento de clique disparado pelo botão de saída.
     */
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private javax.swing.JButton btnMedico;
    private javax.swing.JButton btnMensagens;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSecretaria;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
}