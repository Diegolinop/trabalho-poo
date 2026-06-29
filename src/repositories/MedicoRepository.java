package repositories;

import models.Medico;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Repositório de médicos. Oferece buscas por CPF e por CRM.
 */
public class MedicoRepository extends Repository<Medico> {

    public MedicoRepository(EntityManager em) {
        super(Medico.class, em);
    }

    /**
     * Busca um médico pelo CPF.
     *
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return médico encontrado ou null.
     */
    public Medico buscarPorCpf(String cpf) {
        try {
            return em.find(Medico.class, cpf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca um médico pelo CRM.
     *
     * @param crm CRM do médico.
     * @return médico encontrado ou null.
     */
    public Medico buscarPorCrm(String crm) {
        try {
            List<Medico> resultados = em.createQuery("SELECT m FROM Medico m WHERE m.crm = :crm", Medico.class)
                    .setParameter("crm", crm)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.getFirst();
        } catch (Exception e) {
            return null;
        }
    }
}