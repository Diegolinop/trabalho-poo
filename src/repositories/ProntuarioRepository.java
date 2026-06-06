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
 *
 * @author peres
 */
public class ProntuarioRepository extends Repository<Prontuario> {

    public List<Prontuario> buscarPorMedicoEMes(Medico medico, String mes) {
        List<Prontuario> resultado = new ArrayList<>();
        for (Prontuario prontuario : elementos) {
            if (prontuario.getMedico().equals(medico)
                    && prontuario.getData().substring(3).equals(mes)) {
                resultado.add(prontuario);
            }
        }
        return resultado;
    }
}
