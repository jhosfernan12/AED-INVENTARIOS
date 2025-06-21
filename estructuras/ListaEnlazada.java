package estructuras;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza;
    private int tamaño;

    public static class Nodo<T> {
    public T dato;
    public Nodo<T> siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public interface Condicion<T> {
        boolean test(T dato);
    }



    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    public Nodo<T> getCabeza()
    {
        return this.cabeza;
    }

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }

    public boolean eliminar(T dato) {
        if (cabeza == null) return false;

        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(dato)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            return true;
        }
        return false;
    }

    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public int tamaño() {
        return tamaño;
    }

    public T obtener(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public boolean eliminar(int index) {
        if (index < 0 || index >= tamaño) return false;

        if (index == 0) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }

        Nodo<T> actual = cabeza;
        for (int i = 0; i < index - 1; i++) {
            actual = actual.siguiente;
        }

        actual.siguiente = actual.siguiente.siguiente;
        tamaño--;
        return true;
    }

        public void eliminarSi(Condicion<T> condicion) {
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        while (actual != null) {
            if (condicion.test(actual.dato)) {
                // Eliminar
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamaño--;
            } else {
                anterior = actual;
            }
            actual = actual.siguiente;
        }
    }

    


    public Iterador<T> iterador() {
        return new IteradorLista(cabeza);
    }

    private class IteradorLista implements Iterador<T> {
        private Nodo<T> actual;

        public IteradorLista(Nodo<T> cabeza) {
            actual = cabeza;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            T dato = actual.dato;
            actual = actual.siguiente;
            return dato;
        }
    }
}