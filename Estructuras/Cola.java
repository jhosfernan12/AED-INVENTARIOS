package Estructuras;

public class Cola<T> {
    public Nodo<T> frente;
    public Nodo<T> fin;

    public Cola() {
        frente = fin = null;
    }

    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
    }

    public T desencolar() {
        if (estaVacia()) return null;
        T valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        return valor;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}