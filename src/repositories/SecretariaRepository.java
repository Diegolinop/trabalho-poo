package repositories;

import models.Secretaria;
import javax.persistence.EntityManager;

import java.util.List;

/**
 * Repositório de secretárias. Oferece buscas por CPF e por matrícula.
 */
public class SecretariaRepository extends Repository<Secretaria> {

    public SecretariaRepository(EntityManager em) {
        super(Secretaria.class, em);
    }

    /**
     * Busca uma secretária pelo CPF.
     *
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return secretária encontrada ou null.
     */
    public Secretaria buscarPorCpf(String cpf) {
        try {
            return em.find(Secretaria.class, cpf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Busca uma secretária pela matrícula.
     *
     * @param matricula matrícula no formato SEC-XXX.
     * @return secretária encontrada ou null.
     */
    public Secretaria buscarPorMatricula(String matricula) {
        try {
            List<Secretaria> resultados = em.createQuery("SELECT s FROM Secretaria s WHERE s.matricula = :matricula", Secretaria.class)
                    .setParameter("matricula", matricula)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.getFirst();
        } catch (Exception e) {
            return null;
        }
    }
}