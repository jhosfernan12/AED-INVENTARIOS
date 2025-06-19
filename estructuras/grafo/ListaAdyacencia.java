public class ListaAdyacencia<T> {
    private Vertice<T> origen;
    private ListaEnlazada<Arista<T>> aristas;

    public ListaAdyacencia(Vertice<T> origen) {
        this.origen = origen;
        this.aristas = new ListaEnlazada<>();
    }

    public Vertice<T> getOrigen() {
        return origen;
    }

    public void agregarArista(Arista<T> arista) {
        aristas.agregar(arista);
    }

    public boolean eliminarArista(Arista<T> arista) {
        return aristas.eliminar(arista);
    }

    public ListaEnlazada<Arista<T>> getAristas() {
        return aristas;
    }

    public Iterador<Arista<T>> iterador() {
        return aristas.iterador();
    }

    public boolean estaVacia() {
        return aristas.tamaño() == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(origen.getDato()).append(" -> ");
        Iterador<Arista<T>> iter = aristas.iterador();
        boolean primero = true;
        while (iter.hasNext()) {
            if (!primero) sb.append(", ");
            Arista<T> arista = iter.next();
            sb.append(arista.getDestino().getDato()).append("(").append(arista.getPeso()).append(")");
            primero = false;
        }
        return sb.toString();
    }
}