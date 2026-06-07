/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;

/**
 *
 * @author peres
 */
public class ConsultaRepository extends Repository<Consulta> {

    public List<Consulta> buscarPorCpfPaciente(String cpf) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            if (consulta.getPaciente().getCpf().equals(cpf)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    public List<Consulta> buscarPorData(String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            if (consulta.getData().equals(data)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
}
