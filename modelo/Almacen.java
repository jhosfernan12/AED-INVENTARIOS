package modelo;

import estructuras.grafo.Arista;
import estructuras.grafo.Grafo;
import estructuras.grafo.Vertice;
import estructuras.Iterador;
import estructuras.ListaEnlazada;
import estructuras.ListaEnlazada.Nodo;
import algoritmos.Dijkstra;

public class Almacen {
    private Grafo<Ubicacion> grafo;

    public Almacen() {
        this.grafo = new Grafo<>();
    }

    public Grafo<Ubicacion> getGrafo() {
        return this.grafo;
    }


    public Vertice<Ubicacion> agregarUbicacion(String nombre) {
        Ubicacion ubicacion = new Ubicacion(nombre, 3);
        return grafo.agregarVertice(ubicacion);
    }

    public void conectarUbicaciones(Vertice<Ubicacion> origen, Vertice<Ubicacion> destino, double peso) {
    grafo.agregarArista(origen, destino, peso);
    grafo.agregarArista(destino, origen, peso);
}



    public void mostrarGrafo() {
        System.out.println(grafo);
    }

    public void mostrarProductosEnUbicacion(Vertice<Ubicacion> v) {
        System.out.println("Productos en " + v.getDato().getNombre() + ":");
        v.getDato().getProductos().imprimirTodos();
    }

    public void cerrarPasillo(Vertice<Ubicacion> origen, Vertice<Ubicacion> destino) {
        grafo.eliminarArista(new Arista<>(origen, destino, 0));
    }

    public void agregarProducto(Vertice<Ubicacion> ubicacion, Producto producto) {
        ubicacion.getDato().agregarProducto(producto);
    }

    public boolean eliminarUbicacion(Vertice<Ubicacion> ubicacion) {
        // Elimina todas las aristas entrantes y salientes
        for (int i = 0; i < grafo.cantidadVertices(); i++) {
            Vertice<Ubicacion> v = grafo.getVertices().obtener(i);
            // Elimina todas las aristas que apunten a esta ubicación
            v.getAristasSalientes().eliminarSi(arista -> arista.getDestino().equals(ubicacion));
        }
        // Ahora elimina el vértice en sí
        return grafo.eliminarVertice(ubicacion);
    }

        public void modificarArista(Vertice<Ubicacion> origen, Vertice<Ubicacion> destino, double nuevoPeso) {
        Nodo<Arista<Ubicacion>> nodo = origen.getAristasSalientes().getCabeza();
        while (nodo != null) {
            if (nodo.dato.getDestino().equals(destino)) {
                nodo.dato.setPeso(nuevoPeso);
                return;
            }
            nodo = nodo.siguiente;
        }
        System.out.println("Arista no encontrada para modificacion");
    }




    public boolean detectarCiclo() {
        return hayCiclo();
    }

    private boolean hayCiclo() {
        ListaEnlazada<Vertice<Ubicacion>> visitados = new ListaEnlazada<>();
        ListaEnlazada<Vertice<Ubicacion>> enRecursion = new ListaEnlazada<>();

        ListaEnlazada<Vertice<Ubicacion>> verts = grafo.getVertices();
        for (int i = 0; i < verts.tamaño(); i++) {
            Vertice<Ubicacion> v = verts.obtener(i);
            if (!visitados.contiene(v) && dfs(v, visitados, enRecursion))
                return true;
        }
        return false;
    }

    private boolean dfs(Vertice<Ubicacion> actual,
                        ListaEnlazada<Vertice<Ubicacion>> visitados,
                        ListaEnlazada<Vertice<Ubicacion>> enRecursion) {
        visitados.agregar(actual);
        enRecursion.agregar(actual);

        for (int i = 0; i < actual.getAristasSalientes().tamaño(); i++) {
            Arista<Ubicacion> arista = actual.getAristasSalientes().obtener(i);
            Vertice<Ubicacion> vecino = arista.getDestino();

            if (!visitados.contiene(vecino)) {
                if (dfs(vecino, visitados, enRecursion))
                    return true;
            } else if (enRecursion.contiene(vecino)) {
                return true;
            }
        }

        enRecursion.eliminar(actual);
        return false;
    }

    public ListaEnlazada<ListaEnlazada<Vertice<Ubicacion>>> encontrarComponentesConexas() {
        ListaEnlazada<Vertice<Ubicacion>> visitados = new ListaEnlazada<>();
        ListaEnlazada<ListaEnlazada<Vertice<Ubicacion>>> componentes = new ListaEnlazada<>();

        ListaEnlazada<Vertice<Ubicacion>> verts = grafo.getVertices();
        for (int i = 0; i < verts.tamaño(); i++) {
            Vertice<Ubicacion> v = verts.obtener(i);
            if (!visitados.contiene(v)) {
                ListaEnlazada<Vertice<Ubicacion>> componente = new ListaEnlazada<>();
                dfsComponente(v, visitados, componente);
                componentes.agregar(componente);
            }
        }
        return componentes;
    }

    private void dfsComponente(Vertice<Ubicacion> actual,
                            ListaEnlazada<Vertice<Ubicacion>> visitados,
                            ListaEnlazada<Vertice<Ubicacion>> componente) {
        visitados.agregar(actual);
        componente.agregar(actual);

        for (int i = 0; i < actual.getAristasSalientes().tamaño(); i++) {
            Arista<Ubicacion> arista = actual.getAristasSalientes().obtener(i);
            Vertice<Ubicacion> vecino = arista.getDestino();
            if (!visitados.contiene(vecino)) {
                dfsComponente(vecino, visitados, componente);
            }
        }
    }

    

    public ListaEnlazada<Vertice<Ubicacion>> encontrarZonasAisladas() {
        ListaEnlazada<Vertice<Ubicacion>> aisladas = new ListaEnlazada<>();

        ListaEnlazada<Vertice<Ubicacion>> verts = grafo.getVertices();
        for (int i = 0; i < verts.tamaño(); i++) {
            Vertice<Ubicacion> v = verts.obtener(i);
            if (v.getAristasEntrantes().tamaño() == 0 && v.getAristasSalientes().tamaño() == 0) {
                aisladas.agregar(v);
            }
        }

        return aisladas;
    }

    public ListaEnlazada<Vertice<Ubicacion>> caminoMasCorto(Vertice<Ubicacion> inicio, Vertice<Ubicacion> fin) {
        Dijkstra dijkstra = new Dijkstra();
        return dijkstra.caminoMasCorto(grafo, inicio, fin);

    }

    public Vertice<Ubicacion> buscarVerticePorNombre(String nombre) {
        Iterador<Vertice<Ubicacion>> iter = this.grafo.getVertices().iterador();
        while (iter.hasNext()) {
            Vertice<Ubicacion> v = iter.next();
            if (v.getDato().getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }



    public boolean eliminarUbicacion(String nombre) {
        Vertice<Ubicacion> v = buscarVerticePorNombre(nombre);
        if (v != null) {
            this.grafo.eliminarVertice(v);
            return true;
        } else {
            System.out.println("No existe la ubicación " + nombre);
            return false;
        }
    }


    // 2️⃣ Conectar ubicaciones por nombre
    public void conectarUbicacionesPorNombre(String nombreOrigen, String nombreDestino, double peso) {
        Vertice<Ubicacion> origen = buscarVerticePorNombre(nombreOrigen);
        Vertice<Ubicacion> destino = buscarVerticePorNombre(nombreDestino);
        if (origen != null && destino != null) {
            this.grafo.agregarArista(origen, destino, peso);
        } else {
            System.out.println("No existe una de las ubicaciones");
        }
    }

    // 3️⃣ Cerrar pasillo por nombre
    public void cerrarPasilloPorNombre(String nombreOrigen, String nombreDestino) {
        Vertice<Ubicacion> origen = buscarVerticePorNombre(nombreOrigen);
        Vertice<Ubicacion> destino = buscarVerticePorNombre(nombreDestino);
        if (origen != null && destino != null) {
            this.grafo.eliminarArista(new Arista<>(origen, destino, 0));
        } else {
            System.out.println("No existe una de las ubicaciones para cerrar el pasillo");
        }
    }

    // 4️⃣ Agregar producto por nombre
    public void agregarProductoPorNombre(String nombreUbicacion, Producto producto) {
        Vertice<Ubicacion> v = buscarVerticePorNombre(nombreUbicacion);
        if (v != null) {
            v.getDato().agregarProducto(producto);
        } else {
            System.out.println("No existe la ubicacion " + nombreUbicacion);
        }
    }

    // 5️⃣ Eliminar producto por nombre
    public void eliminarProductoPorNombre(String nombreUbicacion, int codigoProducto) {
        Vertice<Ubicacion> v = buscarVerticePorNombre(nombreUbicacion);
        if (v != null) {
            v.getDato().eliminarProducto(codigoProducto);
        } else {
            System.out.println("No existe la ubicacion " + nombreUbicacion);
        }
    }
    




}
