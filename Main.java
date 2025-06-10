import Estructuras.*;

public class Main {
    public static void main(String[] args) 
    { 
        //Prueba de que todo funciona
       Grafo<String> grafo = new Grafo<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("A", "C", 10);
        grafo.agregarArista("B", "C", 2);

        Algoritmos.dijkstra(grafo, "A");

    }
}
