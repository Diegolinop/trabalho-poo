package repositories;

import models.Secretaria;

/**
 * Repositório de secretárias. Oferece buscas por CPF e por matrícula.
 */
public class SecretariaRepository extends Repository<Secretaria> {
    
    /**
     * Busca uma secretária pelo CPF.
     * @param cpf CPF no formato XXX.XXX.XXX-XX.
     * @return secretária encontrada ou null.
     */
    public Secretaria buscarPorCpf(String cpf) {
        for (Secretaria secretaria : getElementos()) {
            if (secretaria.getCpf().equals(cpf)) {
                return secretaria;
            }
        }
        return null;
    }
    
    /**
     * Busca uma secretária pela matrícula.
     * @param matricula matrícula no formato SEC-XXX.
     * @return secretária encontrada ou null.
     */
    public Secretaria buscarPorMatricula(String matricula) {
        for (Secretaria secretaria : getElementos()) {
            if (secretaria.getMatricula().equals(matricula)) {
                return secretaria;
            }
        }
        return null;
    }
}