package repositories;

import models.Paciente;

/**
 * Repositório de pacientes. Oferece busca por CPF.
 */
public class PacienteRepository extends Repository<Paciente> {

    public PacienteRepository() {
        super(Paciente.class);
    }

    /**
     * Busca um paciente pelo CPF.
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return paciente encontrado ou null.
     */
    public Paciente buscarPorCpf(String cpf) {
        try {
            return em.find(Paciente.class, cpf);
        } catch (Exception e) {
            return null;
        }
    }
}