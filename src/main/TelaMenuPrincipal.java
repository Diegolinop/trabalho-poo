package main;

import services.GerenciadorMensagensService;
import services.MedicoService;
import services.SecretariaService;
import models.Medico;

import javax.swing.JOptionPane;

public class TelaMenuPrincipal extends javax.swing.JFrame {
    private SecretariaService secretariaService;
    private MedicoService medicoService;
    private GerenciadorMensagensService gerenciadorMensagensService;

    /**
     * Construtor atualizado para receber os serviços, 
     * assim como o seu MenuPrincipal do console fazia.
     * 
     * @param secretariaService TODO: escrever essas porra de param
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

    private void btnSecretariaActionPerformed(java.awt.event.ActionEvent evt) {
        String crm = JOptionPane.showInputDialog(null, "Digite a matrícula:", "", JOptionPane.QUESTION_MESSAGE);
    }

    private void btnMedicoActionPerformed(java.awt.event.ActionEvent evt) {
        String crm = JOptionPane.showInputDialog(this, "Digite o CRM:", "", JOptionPane.QUESTION_MESSAGE);
        if (crm == null) return;

        Medico medico = medicoService.buscarMedicoPorCrm(crm);
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "Médico não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        new TelaMenuMedico(medicoService, medico).setVisible(true);
    }
    
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