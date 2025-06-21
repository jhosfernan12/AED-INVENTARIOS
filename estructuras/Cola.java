package estructuras;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public Cola() {
        frente = fin = null;
        tamaño = 0;
    }

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamaño++;
    }

    public T desencolar() {
        if (frente == null) return null;

        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamaño--;
        return dato;
    }

    public T frente() {
        if (frente == null) return null;
        return frente.dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamaño() {
        return tamaño;
    }
}