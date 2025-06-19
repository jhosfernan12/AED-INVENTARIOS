public class Grafo<T> {
    private ListaEnlazada<Vertice<T>> vertices;

    public Grafo() {
        this.vertices = new ListaEnlazada<>();
    }

    public Vertice<T> agregarVertice(T dato) {
        Vertice<T> nuevo = new Vertice<>(dato);
        vertices.agregar(nuevo);
        return nuevo;
    }

    public boolean eliminarVertice(Vertice<T> vertice) {
        // Primero eliminamos todas las aristas que llegan a este vértice
        Iterador<Vertice<T>> iter = vertices.iterador();
        while (iter.hasNext()) {
            Vertice<T> v = iter.next();
            // Eliminamos aristas que apuntan a vertice
            ListaEnlazada<Arista<T>> aristasAEliminar = new ListaEnlazada<>();
            Iterador<Arista<T>> iterAristas = v.getAristasSalientes().iterador();
            while (iterAristas.hasNext()) {
                Arista<T> a = iterAristas.next();
                if (a.getDestino().equals(vertice)) {
                    aristasAEliminar.agregar(a);
                }
            }
            // Quitamos aristas
            Iterador<Arista<T>> iterEliminar = aristasAEliminar.iterador();
            while (iterEliminar.hasNext()) {
                v.eliminarAristaSaliente(iterEliminar.next());
            }
        }
        return vertices.eliminar(vertice);
    }

    public Arista<T> agregarArista(Vertice<T> origen, Vertice<T> destino, double peso) {
        if (!vertices.contiene(origen) || !vertices.contiene(destino)) {
            return null;
        }
        Arista<T> arista = new Arista<>(origen, destino, peso);
        origen.agregarAristaSaliente(arista);
        destino.agregarAristaEntrante(arista);
        return arista;
    }

    public boolean eliminarArista(Arista<T> arista) {
        boolean eliminadaSaliente = arista.getOrigen().eliminarAristaSaliente(arista);
        boolean eliminadaEntrante = arista.getDestino().eliminarAristaEntrante(arista);
        return eliminadaSaliente && eliminadaEntrante;
    }

    public ListaEnlazada<Vertice<T>> getVertices() {
        return vertices;
    }

    public ListaEnlazada<Arista<T>> getAristas() {
        ListaEnlazada<Arista<T>> todasAristas = new ListaEnlazada<>();
        Iterador<Vertice<T>> iter = vertices.iterador();
        while (iter.hasNext()) {
            Vertice<T> v = iter.next();
            Iterador<Arista<T>> iterAristas = v.getAristasSalientes().iterador();
            while (iterAristas.hasNext()) {
                todasAristas.agregar(iterAristas.next());
            }
        }
        return todasAristas;
    }

    public int cantidadVertices() {
        return vertices.tamaño();
    }

    public int cantidadAristas() {
        return getAristas().tamaño();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo con ").append(cantidadVertices()).append(" vértices y ")
          .append(cantidadAristas()).append(" aristas:\n");

        Iterador<Vertice<T>> iter = vertices.iterador();
        while (iter.hasNext()) {
            Vertice<T> v = iter.next();
            sb.append(v.getDato()).append(" -> ");
            Iterador<Arista<T>> iterAristas = v.getAristasSalientes().iterador();
            boolean primero = true;
            while (iterAristas.hasNext()) {
                if (!primero) sb.append(", ");
                Arista<T> a = iterAristas.next();
                sb.append(a.getDestino().getDato()).append("(").append(a.getPeso()).append(")");
                primero = false;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}