/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.List;

import models.Medico;
import models.Prontuario;

/**
 * Repositório de prontuários. Oferece buscas por médico,
 * por médico e ID, e por médico e mês de atendimento.
 */
public class ProntuarioRepository extends Repository<Prontuario> {

    /**
     * Busca todos os prontuários de um médico em um determinado mês.
     * O mês é extraído da data do prontuário no formato dd/mm/aaaa,
     * comparando a partir do índice 3 (mm/aaaa).
     * @param medico médico a ser filtrado.
     * @param mes mês no formato MM/aaaa.
     * @return lista de prontuários do período.
     */
    public List<Prontuario> buscarPorMedicoEMes(Medico medico, String mes) {
        List<Prontuario> resultado = new ArrayList<>();
        for (Prontuario prontuario : getElementos()) {
            if (prontuario.getMedico().equals(medico)
                    && prontuario.getData().substring(3).equals(mes)) {
                resultado.add(prontuario);
            }
        }
        return resultado;
    }
    
    /**
     * Busca todos os prontuários registrados por um médico.
     * @param medico médico a ser filtrado.
     * @return lista de prontuários do médico.
     */
    public List<Prontuario> buscarPorMedico(Medico medico) {
        List<Prontuario> resultado = new ArrayList<>();
        for (Prontuario prontuario : getElementos()) {
            if (prontuario.getMedico().equals(medico)) {
                resultado.add(prontuario);
            }
        }
        return resultado;
    }
    
    /**
     * Busca um prontuário específico pelo médico responsável e pelo ID.
     * @param medico médico responsável.
     * @param id ID do prontuário.
     * @return prontuário encontrado ou null.
     */
    public Prontuario buscarPorMedicoEId(Medico medico, int id) {
        for (Prontuario prontuario : getElementos()) {
            if (prontuario.getMedico().equals(medico)
                    && prontuario.getId() == id) {
                return prontuario;
            }
        }
        return null;
    }
}
