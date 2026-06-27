package repositories;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;

/**
 * Repositório de consultas. Oferece buscas específicas por data,
 * CPF do paciente e disponibilidade de contato (e-mail/celular).
 */
public class ConsultaRepository extends Repository<Consulta> {

    public ConsultaRepository() {
        super(Consulta.class);
    }

    /**
     * Busca todas as consultas de um paciente pelo CPF.
     *
     * @param cpf CPF do paciente no formato XXX.XXX.XXX-XX.
     * @return lista de consultas do paciente.
     */
    public List<Consulta> buscarPorCpfPaciente(String cpf) {
        return em.createQuery("SELECT c FROM Consulta c WHERE c.paciente.cpf = :cpf", Consulta.class)
                .setParameter("cpf", cpf)
                .getResultList();
    }

    /**
     * Busca consultas de uma data cujos pacientes possuem e-mail ou celular cadastrado.
     *
     * @param data data.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorData(String data) {
        return em.createQuery("SELECT c FROM Consulta c WHERE c.data = :data", Consulta.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca consultas de uma data cujos pacientes não possuem e-mail nem celular cadastrado.
     *
     * @param data data no formato dd/mm/aaaa.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorDataComEmailOuCelular(String data) {
        return em.createQuery("""
        SELECT c
        FROM Consulta c
        JOIN c.paciente p
        WHERE c.data = :data
          AND (
                (p.email IS NOT NULL AND p.email <> '')
                OR
                (p.telefone IS NOT NULL AND p.telefone <> '')
              )
        """, Consulta.class)
                .setParameter("data", data)
                .getResultList();
    }

    /**
     * Busca consultas de uma data cujos pacientes não possuem e-mail nem celular cadastrado.
     *
     * @param data data.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorDataSemEmailESemCelular(String data) {
        return em.createQuery("SELECT c FROM Consulta c WHERE c.data = :data AND (c.paciente.email IS NULL OR c.paciente.email = '') AND (c.paciente.telefone IS NULL OR c.paciente.telefone = '')", Consulta.class)
                .setParameter("data", data)
                .getResultList();
    }
}