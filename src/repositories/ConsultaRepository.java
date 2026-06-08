/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.util.List;

import models.Consulta;

/**
 * Repositório de consultas. Oferece buscas específicas por data,
 * CPF do paciente e disponibilidade de contato (e-mail/celular).
 */
public class ConsultaRepository extends Repository<Consulta> {
    
    /**
     * Busca todas as consultas de um paciente pelo CPF.
     * @param cpf CPF do paciente no formato XXX.XXX.XXX-XX.
     * @return lista de consultas do paciente.
     */
    public List<Consulta> buscarPorCpfPaciente(String cpf) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            if (consulta.getPaciente().getCpf().equals(cpf)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
    
    /**
     * Busca consultas de uma data cujos pacientes possuem e-mail ou celular cadastrado.
     * @param data data.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorData(String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            if (consulta.getData().equals(data)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
    
    /**
     * Busca consultas de uma data cujos pacientes não possuem e-mail nem celular cadastrado.
     * @param data data no formato dd/mm/aaaa.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorDataComEmailOuCelular(String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            boolean temEmail = temValor(consulta.getPaciente().getEmail());
            boolean temCelular = temValor(consulta.getPaciente().getTelefone());

            if (consulta.getData().equals(data) && (temEmail || temCelular)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
    
    /**
     * Busca consultas de uma data cujos pacientes não possuem e-mail nem celular cadastrado.
     * @param data data.
     * @return lista de consultas filtradas.
     */
    public List<Consulta> buscarPorDataSemEmailESemCelular(String data) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : getElementos()) {
            boolean temEmail = temValor(consulta.getPaciente().getEmail());
            boolean temCelular = temValor(consulta.getPaciente().getTelefone());

            if (consulta.getData().equals(data) && !temEmail && !temCelular) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
    
    /**
     * Verifica se uma string possui valor não nulo e não vazio.
     * @param valor string a ser verificada.
     * @return true se possuir valor, false caso contrário.
     */
    private boolean temValor(String valor) {
        return valor != null && !valor.isBlank();
    }
}
