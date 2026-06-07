/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import models.Secretaria;

/**
 *
 * @author peres
 */
public class SecretariaRepository extends Repository<Secretaria> {

    public Secretaria buscarPorMatricula(String matricula) {
        for (Secretaria secretaria : elementos) {
            if (secretaria.getMatricula().equals(matricula)) {
                return secretaria;
            }
        }
        return null;
    }
}