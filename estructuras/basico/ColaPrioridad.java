public class ColaPrioridad<T> {
    private Nodo<T> frente;

    private class Nodo<E> {
        E dato;
        double prioridad;
        Nodo<E> siguiente;

        Nodo(E dato, double prioridad) {
            this.dato = dato;
            this.prioridad = prioridad;
        }
    }

    public ColaPrioridad() {
        this.frente = null;
    }

    public void insertar(T elemento, double prioridad) {
        Nodo<T> nuevo = new Nodo<>(elemento, prioridad);
        if (frente == null || prioridad < frente.prioridad) {
            nuevo.siguiente = frente;
            frente = nuevo;
        } else {
            Nodo<T> actual = frente;
            while (actual.siguiente != null && actual.siguiente.prioridad <= prioridad) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
    }

    public T extraer() {
        if (frente == null) return null;
        T dato = frente.dato;
        frente = frente.siguiente;
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
