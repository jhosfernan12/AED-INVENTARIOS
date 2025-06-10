package Estructuras;

public class ListaEnlazada<T> {
    private Nodo<T> inicio;

    public ListaEnlazada() {
        inicio = null;
    }

    public void agregar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            Nodo<T> actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public boolean contiene(T valor) {
        Nodo<T> actual = inicio;
        while (actual != null) {
            if (actual.valor.equals(valor)) return true;
            actual = actual.siguiente;
        }
        return false;
    }

    public Nodo<T> getInicio() {
        return inicio;
    }
}