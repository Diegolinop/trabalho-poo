/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import models.Paciente;

/**
 *
 * @author peres
 */
public class PacienteRepository extends Repository<Paciente> {

    public Paciente buscarPorCpf(String cpf) {
        for (Paciente paciente : elementos) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        return null;
    }
}
