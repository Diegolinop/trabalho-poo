package repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Classe genérica abstrata que serve de base para todos os repositórios do sistema.
 * Armazena elementos em memória primária usando uma lista interna, oferecendo
 * operações básicas de persistência.
 *
 * @param <T> tipo do elemento gerenciado pelo repositório.
 */
public abstract class Repository<T> {

    private final Class<T> classeEntidade;
    protected final EntityManager em;

    /**
     * Inicializa o repositório com uma lista vazia.
     */
    public Repository(Class<T> classeEntidade, EntityManager em) {
        this.classeEntidade = classeEntidade;
        this.em = em;
    }

    /**
     * Adiciona um elemento ao repositório.
     *
     * @param elemento elemento a ser salvo.
     */
    public void salvar(T elemento) {
        em.getTransaction().begin();
        em.persist(elemento);
        em.getTransaction().commit();
    }
    
    public void atualizar(T elemento) {
        em.getTransaction().begin();
        em.merge(elemento);
        em.getTransaction().commit();
    }

    /**
     * Remove um elemento do repositório.
     *
     * @param elemento elemento a ser removido.
     * @return true se removido com sucesso, false se não encontrado.
     */
    public boolean remover(T elemento) {
        try {
            em.getTransaction().begin();
            T elementoGerido = em.merge(elemento);
            em.remove(elementoGerido);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retorna uma cópia da lista com todos os elementos do repositório.
     *
     * @return lista de todos os elementos.
     */
    public List<T> buscarTodos() {
        return em.createQuery("SELECT e FROM " + classeEntidade.getSimpleName() + " e", classeEntidade).getResultList();
    }

    /**
     * Retorna uma cópia da lista interna para uso nas subclasses.
     * Protege a lista original contra modificações externas.
     *
     * @return cópia da lista de elementos.
     */
    protected List<T> getElementos() {
        return buscarTodos();
    }
}