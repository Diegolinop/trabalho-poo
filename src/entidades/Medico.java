package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um médico da clínica
 * Responsável por gerenciar prontuários e dados adicionais dos pacientes.
 * Herda os dados pessoais de Pessoa.
 */
public class Medico extends Pessoa {
    
    /** Registro do conselho de medicina do médico -> Idenficação. */
    private String crm;
    
    private String especialidade;
    private List<Prontuario> prontuarios;
    
    /**
     * 
     * @param cpf
     * @param nome
     * @param sobrenome
     * @param telefone
     * @param email
     * @param endereco
     * @param especialidade
     * @param crm 
     */
    public Medico(String cpf, String nome, String sobrenome, String telefone, String email, String endereco, String especialidade, String crm) {
        super(cpf, nome, sobrenome, telefone, email, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
        this.prontuarios = new ArrayList<>();
    }
    
    public String getEspecialidade() {
        return this.especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    public String getCrm() {
        return this.crm;
    }
    
    public void adicionarProntuario(Prontuario prontuario) {
        this.prontuarios.add(prontuario);
    }
    public void removerProntuario(Prontuario prontuario) {
        if (this.prontuarios.contains(prontuario)) {
            this.prontuarios.remove(prontuario);
        }
    }
    public List<Prontuario> getProntuarios() {
        return this.prontuarios;
    }
    
    /**
     * Verifica a se existe dados adicionais no paciente
     * @param paciente
     * @return 
     */
    public boolean verificarDadosAdicionais(Paciente paciente) {
        return paciente.getDadosAdicionais() != null;
    }
    
    /**
     * Cadastra os dados adicionais de saúde de um paciente.
     * @param paciente paciente a receber os dados adicionais
     */
    public void cadastrarDadosAdicionais(Paciente paciente) {
        if (verificarDadosAdicionais(paciente) == false) {
            paciente.setDadosAdicionais(new DadosAdicionais());
        }
    }
    
    /**
     * Atualiza os dados adicionais de um paciente
     * @param paciente
     * @param fuma
     * @param bebe
     * @param colesterol
     * @param diabete
     * @param doencaCardiaca 
     */
    public void atualizarDadosAdicionais(Paciente paciente, boolean fuma, boolean bebe, boolean colesterol, boolean diabete, boolean doencaCardiaca) {
        cadastrarDadosAdicionais(paciente);
        
        DadosAdicionais dados = paciente.getDadosAdicionais();
        
        dados.setFuma(fuma);
        dados.setBebe(bebe);
        dados.setColesterol(colesterol);
        dados.setDiabete(diabete);
        dados.setDoencaCardiaca(doencaCardiaca);
    }
    
    /**
     * Remove os dados adicionais de um paciente
     * @param paciente 
     */
    public void removerDadosAdicionais(Paciente paciente) {
        if (verificarDadosAdicionais(paciente)) {
            paciente.setDadosAdicionais(null); 
        }   
    }
    
    /**
     * Adiciona uma cirurgia na lista de cirurgias de um paciente
     * @param paciente
     * @param cirurgia 
     */
    public void adicionarCirurgiaPaciente(Paciente paciente, String cirurgia) {
        cadastrarDadosAdicionais(paciente);
        paciente.getDadosAdicionais().adicionarCirurgia(cirurgia);
    }
    
    /**
     * Remove uma cirurgia na listas de cirurgias de um paciente
     * @param paciente
     * @param cirurgia 
     */
    public void removerCirurgiaPaciente(Paciente paciente, String cirurgia) {
        if (verificarDadosAdicionais(paciente)) {
            paciente.getDadosAdicionais().removerCirurgia(cirurgia);
        }
    }
    
    /**
     * Adiciona uma alergia na lista de cirurgia de um paciente
     * @param paciente
     * @param alergia 
     */
    public void adicionarAlergiaPaciente(Paciente paciente, String alergia) {
        cadastrarDadosAdicionais(paciente);
        paciente.getDadosAdicionais().adicionarAlergia(alergia);
    }
    
    public void removerAlergiaPaciente(Paciente paciente, String cirurgia) {
        if (verificarDadosAdicionais(paciente)) {
            paciente.getDadosAdicionais().removerAlergia(cirurgia);
        }
    }
    
    /**
     * Gera um relatório dos atendimentos do mês
     * @param mes
     * @return 
     */
    public List<Prontuario> gerarRelatorioAtendimentosMes(String mes) {
        List<Prontuario> resultado = new ArrayList<>();
        for (Prontuario prontuario : this.prontuarios) {
            if (prontuario.getData().substring(3).equals(mes)) {
                resultado.add(prontuario);
            }
        }
        return resultado;
    }
    
    /**
     * Gera uma receita médica para o paciente.
     * @param paciente
     * @param prescricao
     * @return 
     */
    public String gerarReceita(Paciente paciente, String prescricao) {
        return """
               ---------------- RECEITA MÉDICA ---------------- 
               Médico(a): """ + this.getNomeCompleto() + " | CRM: " + this.crm + "\n" +
               "Paciente: " + paciente.getNomeCompleto() + "\n" +
               "------------------------------------------------------\n" +
               "Prescrição:\n" + prescricao + "\n" +
               "------------------------------------------------------";
    }
    
    /**
     * Gera um atestado médico de afastamento para o paciente.
     * @param paciente 
     * @param diasDeRepouso 
     * @param motivo 
     * @return 
     */
    public String gerarAtestado(Paciente paciente, int diasDeRepouso, String motivo) {
        return """
               ---------------- ATESTADO MÉDICO ----------------
               Médico(a): """ + this.getNomeCompleto() + " | CRM: " + this.crm + "\n" +
               "Paciente: " + paciente.getNomeCompleto() + "\n" +
               "------------------------------------------------------\n" +
               "Atesto para os devidos fins que o(a) paciente acima citado(a)\n" +
               "necessita de " + diasDeRepouso + " dias de repouso absoluto\n" +
               "por motivo de: " + motivo + ".\n" +
               "======================================================";
    }

    /**
     * Gera uma declaração de acompanhamento médico.
     * @param paciente 
     * @param nomeAcompanhante 
     * @param dataAcompanhamento 
     * @return 
     */
    public String gerarDeclaracaoAcompanhamento(Paciente paciente, String nomeAcompanhante, String dataAcompanhamento) {
        return "---------------- DECLARAÇÃO DE ACOMPANHAMENTO ----------------\n" +
               "Declaro para os devidos fins que o(a) Sr(a). " + nomeAcompanhante + "\n" +
               "atuou como acompanhante do(a) paciente " + paciente.getNomeCompleto() + "\n" +
               "em consulta médica realizada no dia " + dataAcompanhamento + ".\n" +
               "------------------------------------------------------\n" +
               "Médico(a): " + this.getNomeCompleto() + " | CRM: " + this.crm + "\n" +
               "------------------------------------------------------";
    }
} 

