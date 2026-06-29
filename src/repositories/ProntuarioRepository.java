package repositories;

import java.util.ArrayList;
import java.util.List;

import models.Medico;
import models.Prontuario;
import javax.persistence.EntityManager;

/**
 * Repositório de prontuários. Oferece buscas por médico,
 * por médico e ID, e por médico e mês de atendimento.
 */
public class ProntuarioRepository extends Repository<Prontuario> {

    public ProntuarioRepository(EntityManager em) {
        super(Prontuario.class, em);
    }

    /**
     * Busca todos os prontuários de um médico em um determinado mês.
     * O mês é extraído da data do prontuário no formato dd/mm/aaaa,
     * comparando a partir do índice 3 (mm/aaaa).
     *
     * @param medico médico a ser filtrado.
     * @param mes    mês no formato MM/aaaa.
     * @return lista de prontuários do período.
     */
    public List<Prontuario> buscarPorMedicoEMes(Medico medico, String mes) {
        List<Prontuario> todosDoMedico = buscarPorMedico(medico);
        List<Prontuario> resultado = new ArrayList<>();
        for (Prontuario prontuario : todosDoMedico) {
            if (prontuario.getData().substring(3).equals(mes)) {
                resultado.add(prontuario);
            }
        }
        return resultado;
    }

    /**
     * Busca todos os prontuários registrados por um médico.
     *
     * @param medico médico a ser filtrado.
     * @return lista de prontuários do médico.
     */
    public List<Prontuario> buscarPorMedico(Medico medico) {
        return em.createQuery("SELECT p FROM Prontuario p WHERE p.medico = :medico", Prontuario.class)
                .setParameter("medico", medico)
                .getResultList();
    }

    /**
     * Busca um prontuário específico pelo médico responsável e pelo ID.
     *
     * @param medico médico responsável.
     * @param id     ID do prontuário.
     * @return prontuário encontrado ou null.
     */
    public Prontuario buscarPorMedicoEId(Medico medico, int id) {
        try {
            List<Prontuario> resultados = em.createQuery("SELECT p FROM Prontuario p WHERE p.medico = :medico AND p.id = :id", Prontuario.class)
                    .setParameter("medico", medico)
                    .setParameter("id", id)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}