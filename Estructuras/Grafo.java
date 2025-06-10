package Estructuras;

public class Grafo<T> {
    private Vertice<T> inicio;

    public Grafo() {
        this.inicio = null;
    }
    public Vertice<T> getInicio() 
    {
        return inicio;
    }
    public void agregarVertice(T valor) {
        if (buscarVertice(valor) != null) return;

        Vertice<T> nuevo = new Vertice<>(valor);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            Vertice<T> temp = inicio;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
    }

    public Vertice<T> buscarVertice(T valor) {
        Vertice<T> temp = inicio;
        while (temp != null) {
            if (temp.getValor().equals(valor)) return temp;
            temp = temp.getSiguiente();
        }
        return null;
    }

    public void agregarArista(T origen, T destino, int peso) {
        Vertice<T> vOrigen = buscarVertice(origen);
        Vertice<T> vDestino = buscarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            vOrigen.agregarArista(vDestino, peso);
        }
    }

    public void eliminarVertice(T valor) {
        if (inicio == null) return;

        // Eliminar todas las aristas que apuntan a este vértice
        Vertice<T> actual = inicio;
        while (actual != null) {
            eliminarArista(actual, valor);
            actual = actual.getSiguiente();
        }

        // Eliminar el vértice
        if (inicio.getValor().equals(valor)) {
            inicio = inicio.getSiguiente();
            return;
        }

        Vertice<T> anterior = inicio;
        actual = inicio.getSiguiente();
        while (actual != null) {
            if (actual.getValor().equals(valor)) {
                anterior.setSiguiente(actual.getSiguiente());
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
    }

    public void eliminarArista(Vertice<T> origen, T destinoValor) {
        Arista<T> actual = origen.getAristas();
        Arista<T> anterior = null;

        while (actual != null) {
            if (actual.getDestino().getValor().equals(destinoValor)) {
                if (anterior == null) {
                    origen.setAristas(actual.getSiguiente());
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                return;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
    }

    public void mostrarGrafo() {
        Vertice<T> v = inicio;
        while (v != null) {
            System.out.print("[" + v.getValor() + "] -> ");
            Arista<T> a = v.getAristas();
            while (a != null) {
                System.out.print("(" + a.getDestino().getValor() + ", " + a.getPeso() + ") ");
                a = a.getSiguiente();
            }
            System.out.println();
            v = v.getSiguiente();
        }
    }
}
