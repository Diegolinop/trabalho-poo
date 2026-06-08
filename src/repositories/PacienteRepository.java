package repositories;

import models.Paciente;

/**
 * Repositório de pacientes. Oferece busca por CPF.
 */
public class PacienteRepository extends Repository<Paciente> {
    
    /**
     * Busca um paciente pelo CPF.
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return paciente encontrado ou null.
     */
    public Paciente buscarPorCpf(String cpf) {
        for (Paciente paciente : getElementos()) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        return null;
    }
}