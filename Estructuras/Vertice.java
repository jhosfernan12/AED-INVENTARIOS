package Estructuras;

public class Vertice<T> {
    private T valor;
    private Arista<T> aristas;
    private Vertice<T> siguiente;

    public Vertice(T valor) {
        this.valor = valor;
        this.aristas = null;
        this.siguiente = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public Arista<T> getAristas() {
        return aristas;
    }

    public void setAristas(Arista<T> aristas) {
        this.aristas = aristas;
    }

    public Vertice<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Vertice<T> siguiente) {
        this.siguiente = siguiente;
    }

    public void agregarArista(Vertice<T> destino, int peso) {
        Arista<T> nueva = new Arista<>(destino, peso);
        if (aristas == null) {
            aristas = nueva;
        } else {
            Arista<T> temp = aristas;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nueva);
        }
    }
}
