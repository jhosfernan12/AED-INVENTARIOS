

public class ListaAdyacencia<T> {
    private ListaEnlazada<Tupla<Vertice<T>, ListaEnlazada<Arista<T>>>> lista;

    public ListaAdyacencia() {
        this.lista = new ListaEnlazada<>();
    }

    public void agregarVertice(Vertice<T> vertice) {
        lista.agregar(new Tupla<>(vertice, new ListaEnlazada<>()));
    }

    public void agregarArista(Vertice<T> origen, Arista<T> arista) {
        for (Tupla<Vertice<T>, ListaEnlazada<Arista<T>>> tupla : lista) {
            if (tupla.getClave().equals(origen)) {
                tupla.getValor().agregar(arista);
                return;
            }
        }
    }

    public ListaEnlazada<Arista<T>> getAristas(Vertice<T> vertice) {
        for (Tupla<Vertice<T>, ListaEnlazada<Arista<T>>> tupla : lista) {
            if (tupla.getClave().equals(vertice)) {
                return tupla.getValor();
            }
        }
        return null;
    }
}

