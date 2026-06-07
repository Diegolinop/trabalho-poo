package repositories;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {

    private List<T> elementos;

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
        return new ArrayList<>(elementos);
    }
    
    protected List<T> getElementos() {
        return new ArrayList<>(elementos);
    }
}

