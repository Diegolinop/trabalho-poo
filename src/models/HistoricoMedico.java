package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa o histórico médico de um paciente.
 * Armazena informações de saúde como condições clínicas,
 * cirurgias e alergias.
 * * Este cadastro é de acesso exclusivo do médico.
 */
@Entity
@Table(name = "HISTORICO_MEDICO")
public class HistoricoMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * Indica se o paciente é fumante.
     */
    private boolean fuma;

    /**
     * Indica se o paciente consome bebida alcoólica.
     */
    private boolean bebe;

    /**
     * Indica se o paciente possui colesterol alto.
     */
    private boolean colesterol;

    /**
     * Indica se o paciente possui diabetes.
     */
    private boolean diabetes;

    /**
     * Indica se o paciente possui doença cardíaca.
     */
    private boolean doencaCardiaca;

    /**
     * Conjunto de cirurgias realizadas pelo paciente.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "HISTORICO_CIRURGIAS", joinColumns = @JoinColumn(name = "HISTORICO_ID"))
    private Set<String> cirurgias;

    /**
     * Conjunto de alergias conhecidas do paciente.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "HISTORICO_ALERGIAS", joinColumns = @JoinColumn(name = "HISTORICO_ID"))
    private Set<String> alergias;

    /**
     * Cria um histórico médico vazio, com os conjuntos de cirurgias e alergias inicializados.
     */
    public HistoricoMedico() {
        this.cirurgias = new HashSet<>();
        this.alergias = new HashSet<>();
    }

    /**
     * Retorna se o paciente é fumante.
     *
     * @return true se fuma, false caso contrário.
     */
    public boolean getFuma() {
        return this.fuma;
    }

    /**
     * Define se o paciente é fumante.
     *
     * @param fuma true se fuma, false caso contrário.
     */
    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    /**
     * Retorna se o paciente consome bebida alcoólica.
     *
     * @return true se bebe, false caso contrário.
     */
    public boolean getBebe() {
        return this.bebe;
    }

    /**
     * Define se o paciente consome bebida alcoólica.
     *
     * @param bebe true se bebe, false caso contrário.
     */
    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    /**
     * Retorna se o paciente possui colesterol alto.
     *
     * @return true se tem colesterol alto, false caso contrário.
     */
    public boolean getColesterol() {
        return this.colesterol;
    }

    /**
     * Define se o paciente possui colesterol alto.
     *
     * @param colesterol true se tem colesterol alto, false caso contrário.
     */
    public void setColesterol(boolean colesterol) {
        this.colesterol = colesterol;
    }

    /**
     * Retorna se o paciente possui diabetes.
     *
     * @return true se tem diabetes, false caso contrário.
     */
    public boolean getDiabetes() {
        return this.diabetes;
    }

    /**
     * Define se o paciente possui diabetes.
     *
     * @param diabetes true se tem diabetes, false caso contrário.
     */
    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    /**
     * Retorna se o paciente possui doença cardíaca.
     *
     * @return true se tem doença cardíaca, false caso contrário.
     */
    public boolean getDoencaCardiaca() {
        return this.doencaCardiaca;
    }

    /**
     * Define se o paciente possui doença cardíaca.
     *
     * @param doencaCardiaca true se tem doença cardíaca, false caso contrário.
     */
    public void setDoencaCardiaca(boolean doencaCardiaca) {
        this.doencaCardiaca = doencaCardiaca;
    }

    /**
     * Adiciona uma cirurgia ao histórico do paciente.
     *
     * @param cirurgia nome da cirurgia realizada.
     */
    public void adicionarCirurgia(String cirurgia) {
        this.cirurgias.add(cirurgia);
    }

    /**
     * Remove uma cirurgia do histórico do paciente.
     *
     * @param cirurgia nome da cirurgia a ser removida.
     * @return true se a cirurgia foi removida, false caso não exista.
     */
    public boolean removerCirurgia(String cirurgia) {
        return this.cirurgias.remove(cirurgia);
    }

    /**
     * Retorna uma cópia da lista de cirurgias do paciente.
     *
     * @return lista de cirurgias realizadas.
     */
    public List<String> getCirurgias() {
        return new ArrayList<>(this.cirurgias);
    }

    /**
     * Adiciona uma alergia ao histórico do paciente.
     *
     * @param alergia nome da alergia a ser adicionada.
     */
    public void adicionarAlergia(String alergia) {
        this.alergias.add(alergia);
    }

    /**
     * Remove uma alergia do histórico do paciente.
     *
     * @param alergia nome da alergia a ser removida.
     * @return true se a alergia foi removida, false caso não exista.
     */
    public boolean removerAlergia(String alergia) {
        return this.alergias.remove(alergia);
    }

    /**
     * Retorna uma cópia da lista de alergias do paciente.
     *
     * @return lista de alergias conhecidas.
     */
    public List<String> getAlergias() {
        return new ArrayList<>(this.alergias);
    }
}