package Estructuras;

public class Arista<T> {
    private Vertice<T> destino;
    private int peso;
    private Arista<T> siguiente;

    public Arista(Vertice<T> destino, int peso) {
        this.destino = destino;
        this.peso = peso;
        this.siguiente = null;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Arista<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Arista<T> siguiente) {
        this.siguiente = siguiente;
    }
}
