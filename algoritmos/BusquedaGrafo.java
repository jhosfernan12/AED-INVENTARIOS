public class BusquedaGrafo {


  // Realiza un recorrido BFS a partir de un vértice inicial

    public <T> void bfs(Grafo<T> grafo, Vertice<T> inicio) {
        // Reiniciamos la marca de visitado en todos los vertices
        reiniciarVisitados(grafo);

        Cola<Vertice<T>> cola = new Cola<>();
        inicio.setVisitado(true);
        cola.encolar(inicio);

        while (!cola.estaVacia()) {
            Vertice<T> actual = cola.desencolar();
            System.out.println(actual.getDato());

            // Obtenemos aristas salientes del vertice actual
            Iterador<Arista<T>> iter = actual.getAristasSalientes().iterador();
            while (iter.hasNext()) {
                Arista<T> arista = iter.next();
                Vertice<T> vecino = arista.getDestino();
                if (!vecino.isVisitado()) {
                    vecino.setVisitado(true);
                    cola.encolar(vecino);
                }
            }
        }
    }


    //Reinicia la marca visitado en todos los vertices del grafo

    private <T> void reiniciarVisitados(Grafo<T> grafo) {
        Iterador<Vertice<T>> iter = grafo.getVertices().iterador();
        while (iter.hasNext()) {
            iter.next().setVisitado(false);
        }
    }
}