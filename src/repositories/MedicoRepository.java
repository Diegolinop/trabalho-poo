/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import models.Medico;

/**
 *
 * @author peres
 */
public class MedicoRepository extends Repository<Medico> {

    public Medico buscarPorCrm(String crm) {
        for (Medico medico : elementos) {
            if (medico.getCrm().equals(crm)) {
                return medico;
            }
        }
        return null;
    }
}
