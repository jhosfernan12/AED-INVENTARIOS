
public class Pila<T> {
    private Nodo<T> tope;
    private int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public Pila() {
        tope = null;
        tamaño = 0;
    }

    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamaño++;
    }

    public T desapilar() {
        if (tope == null) return null;

        T dato = tope.dato;
        tope = tope.siguiente;
        tamaño--;
        return dato;
    }

    public T tope() {
        if (tope == null) return null;
        return tope.dato;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamaño() {
        return tamaño;
    }
}