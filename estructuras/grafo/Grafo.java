

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
        // Primero eliminamos todas las aristas relacionadas
        for (Vertice<T> v : vertices) {
            ListaEnlazada<Arista<T>> aristasAEliminar = new ListaEnlazada<>();
            
            for (Arista<T> a : v.getAristasSalientes()) {
                if (a.getDestino().equals(vertice)) {
                    aristasAEliminar.agregar(a);
                }
            }
            
            for (Arista<T> a : aristasAEliminar) {
                v.eliminarAristaSaliente(a);
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
        
        for (Vertice<T> v : vertices) {
            for (Arista<T> a : v.getAristasSalientes()) {
                todasAristas.agregar(a);
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
        
        for (Vertice<T> v : vertices) {
            sb.append(v.getDato()).append(" -> ");
            boolean primero = true;
            for (Arista<T> a : v.getAristasSalientes()) {
                if (!primero) sb.append(", ");
                sb.append(a.getDestino().getDato()).append("(").append(a.getPeso()).append(")");
                primero = false;
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}