package repositories;

import models.Medico;

/**
 * Repositório de médicos. Oferece buscas por CPF e por CRM.
 */
public class MedicoRepository extends Repository<Medico> {
    
    /**
     * Busca um médico pelo CPF.
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return médico encontrado ou null.
     */
    public Medico buscarPorCpf(String cpf) {
        for (Medico medico : getElementos()) {
            if (medico.getCpf().equals(cpf)) {
                return medico;
            }
        }
        return null;
    }
    
    /**
     * Busca um médico pelo CRM.
     * @param crm CRM do médico.
     * @return médico encontrado ou null.
     */
    public Medico buscarPorCrm(String crm) {
        for (Medico medico : getElementos()) {
            if (medico.getCrm().equals(crm)) {
                return medico;
            }
        }
        return null;
    }
}