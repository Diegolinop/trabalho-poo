package repositories;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {

    protected List<T> elementos;

    public Repository() {
        this.elementos = new ArrayList<>();
    }

    public void salvar(T elemento) {
        elementos.add(elemento);
    }

    public boolean remover(T elemento) {
        return elementos.remove(elemento);
    }

    public List<T> buscarTodos() {
        return elementos;
    }
}
