package repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe genérica abstrata que serve de base para todos os repositórios do sistema.
 * Armazena elementos em memória primária usando uma lista interna, oferecendo 
 * operações básicas de persistência.
 *
 * @param <T> tipo do elemento gerenciado pelo repositório.
 */
public abstract class Repository<T> {

    /** Lista interna de elementos armazenados em memória. */
    private final List<T> elementos;

    /**
     * Inicializa o repositório com uma lista vazia.
     */
    public Repository() {
        this.elementos = new ArrayList<>();
    }

    /**
     * Adiciona um elemento ao repositório.
     * @param elemento elemento a ser salvo.
     */
    public void salvar(T elemento) {
        elementos.add(elemento);
    }

    /**
     * Remove um elemento do repositório.
     * @param elemento elemento a ser removido.
     * @return true se removido com sucesso, false se não encontrado.
     */
    public boolean remover(T elemento) {
        return elementos.remove(elemento);
    }

    /**
     * Retorna uma cópia da lista com todos os elementos do repositório.
     * @return lista de todos os elementos.
     */
    public List<T> buscarTodos() {
        return new ArrayList<>(elementos);
    }

    /**
     * Retorna uma cópia da lista interna para uso nas subclasses.
     * Protege a lista original contra modificações externas.
     * @return cópia da lista de elementos.
     */
    protected List<T> getElementos() {
        return new ArrayList<>(elementos);
    }
}

