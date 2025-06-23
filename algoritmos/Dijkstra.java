package algoritmos;

import estructuras.grafo.*;
import estructuras.ColaPrioridad;
import estructuras.ListaEnlazada;

public class Dijkstra {


     public <T> ListaEnlazada<Vertice<T>> caminoMasCorto(Grafo<T> grafo,
                                                          Vertice<T> inicio,
                                                          Vertice<T> fin) {
        int n = grafo.cantidadVertices();
        Vertice<T>[] vertices = new Vertice[n];
        double[] dist = new double[n];
        Vertice<T>[] previo = new Vertice[n];

        // Inicializamos arreglos
        for (int i = 0; i < n; i++) {
            vertices[i] = grafo.getVertices().obtener(i);
            dist[i] = Double.POSITIVE_INFINITY;
            previo[i] = null;
        }

        // Distancia inicial
        int indiceInicio = obtenerIndice(vertices, inicio);
        dist[indiceInicio] = 0;

        // Cola de prioridad
        ColaPrioridad<Par> cola = new ColaPrioridad<>();
        cola.encolar(new Par(indiceInicio, 0.0), 0.0);

        // Bucle principal
        while (!cola.estaVacia()) {
            Par actual = cola.desencolar();
            int u = actual.indice;

            // Ahora ITERAMOS sin usar java.util.Iterator
            ListaEnlazada.Nodo<Arista<T>> nodo = vertices[u].getAristasSalientes().getCabeza();
            while (nodo != null) {
                Arista<T> a = nodo.dato;
                int v = obtenerIndice(vertices, a.getDestino());
                double nuevaDistancia = dist[u] + a.getPeso();

                if (nuevaDistancia < dist[v]) {
                    dist[v] = nuevaDistancia;
                    previo[v] = vertices[u];
                    cola.encolar(new Par(v, nuevaDistancia), nuevaDistancia);
                }

                nodo = nodo.siguiente;
            }
        }

        // Reconstruir camino
        int indiceDestino = obtenerIndice(vertices, fin);
        if (dist[indiceDestino] == Double.POSITIVE_INFINITY) {
            return new ListaEnlazada<>();
        }

        ListaEnlazada<Vertice<T>> camino = new ListaEnlazada<>();
        construirCamino(vertices, previo, indiceDestino, camino);
        return camino;
    }

    private <T> void construirCamino(Vertice<T>[] vertices, Vertice<T>[] previo, int indiceDestino, ListaEnlazada<Vertice<T>> camino) {
        if (previo[indiceDestino] != null) {
            int indicePredecesor = obtenerIndice(vertices, previo[indiceDestino]);
            construirCamino(vertices, previo, indicePredecesor, camino);
        }
        camino.agregar(vertices[indiceDestino]);
    }

    private <T> int obtenerIndice(Vertice<T>[] vertices, Vertice<T> v) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].equals(v))
                return i;
        }
        return -1;
    }

    private static class Par {
        int indice;
        double distancia;

        Par(int indice, double distancia) {
            this.indice = indice;
            this.distancia = distancia;
        }
    }
}
