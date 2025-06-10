package Estructuras;

public class Algoritmos {

    public static <T> void DFS(Grafo<T> grafo, T inicioValor) {
        Vertice<T> inicio = grafo.buscarVertice(inicioValor);
        if (inicio == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        Pila<Vertice<T>> pila = new Pila<>();

        pila.apilar(inicio);

        System.out.print("Recorrido DFS: ");
        while (!pila.estaVacia()) {
            Vertice<T> actual = pila.desapilar();
            T valorActual = actual.getValor();

            if (!visitados.contiene(valorActual)) {
                System.out.print(valorActual + " ");
                visitados.agregar(valorActual);

                Arista<T> arista = actual.getAristas();
                while (arista != null) {
                    Vertice<T> vecino = arista.getDestino();
                    if (!visitados.contiene(vecino.getValor())) {
                        pila.apilar(vecino);
                    }
                    arista = arista.getSiguiente();
                }
            }
        }

        System.out.println();
    }

    public static <T> void BFS(Grafo<T> grafo, T inicioValor) {
        Vertice<T> inicio = grafo.buscarVertice(inicioValor);
        if (inicio == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        Cola<Vertice<T>> cola = new Cola<>();

        cola.encolar(inicio);

        System.out.print("Recorrido BFS: ");
        while (!cola.estaVacia()) {
            Vertice<T> actual = cola.desencolar();
            T valorActual = actual.getValor();

            if (!visitados.contiene(valorActual)) {
                System.out.print(valorActual + " ");
                visitados.agregar(valorActual);

                Arista<T> arista = actual.getAristas();
                while (arista != null) {
                    Vertice<T> vecino = arista.getDestino();
                    if (!visitados.contiene(vecino.getValor())) {
                        cola.encolar(vecino);
                    }
                    arista = arista.getSiguiente();
                }
            }
        }

        System.out.println();
    }

    public static <T> void dijkstra(Grafo<T> grafo, T inicioValor) {
        Vertice<T> inicio = grafo.buscarVertice(inicioValor);
        if (inicio == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        ListaEnlazada<DistanciaVertice<T>> distancias = new ListaEnlazada<>();

        // Inicializar distancias
        Vertice<T> actual = grafo.getInicio();
        while (actual != null) {
            int d = (actual == inicio) ? 0 : Integer.MAX_VALUE;
            distancias.agregar(new DistanciaVertice<>(actual, d));
            actual = actual.getSiguiente();
        }

        while (true) {
            // Elegir el vértice no visitado con menor distancia
            DistanciaVertice<T> menor = null;
            Nodo<DistanciaVertice<T>> nodo = distancias.getInicio();
            while (nodo != null) {
                DistanciaVertice<T> dv = nodo.valor;
                if (!visitados.contiene(dv.vertice.getValor()) && 
                    (menor == null || dv.distancia < menor.distancia)) {
                    menor = dv;
                }
                nodo = nodo.siguiente;
            }

            if (menor == null) break; // Todos visitados o inaccesibles

            visitados.agregar(menor.vertice.getValor());

            // Relajar las aristas
            Arista<T> arista = menor.vertice.getAristas();
            while (arista != null) {
                Vertice<T> vecino = arista.getDestino();
                if (!visitados.contiene(vecino.getValor())) {
                    int nuevaDist = menor.distancia + arista.getPeso();
                    Nodo<DistanciaVertice<T>> temp = distancias.getInicio();
                    while (temp != null) {
                        if (temp.valor.vertice == vecino && nuevaDist < temp.valor.distancia) {
                            temp.valor.distancia = nuevaDist;
                        }
                        temp = temp.siguiente;
                    }
                }
                arista = arista.getSiguiente();
            }
        }

        // Mostrar resultados
        System.out.println("Distancias mínimas desde: " + inicioValor);
        Nodo<DistanciaVertice<T>> temp = distancias.getInicio();
        while (temp != null) {
            System.out.println("Hasta " + temp.valor.vertice.getValor() + ": " +
                (temp.valor.distancia == Integer.MAX_VALUE ? "∞" : temp.valor.distancia));
            temp = temp.siguiente;
        }
    }
}
