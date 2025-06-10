package Estructuras;

public class Pila<T> {
    public Nodo<T> tope;

    public Pila() {
        tope = null;
    }

    public void apilar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.siguiente = tope;
        tope = nuevo;
    }

    public T desapilar() {
        if (estaVacia()) return null;
        T valor = tope.valor;
        tope = tope.siguiente;
        return valor;
    }

    public boolean estaVacia() {
        return tope == null;
    }
}
