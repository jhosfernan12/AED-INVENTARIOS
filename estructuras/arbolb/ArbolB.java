public class ArbolB<E extends Comparable<E>> {
    private NodoB<E> raiz;
    private int orden;

    private boolean subir;
    private NodoB<E> nuevoDerecha;

    public ArbolB(int orden) {
        this.orden = orden;
        this.raiz = null;
    }

    public boolean estaVacio() {
        return this.raiz == null;
    }

    // Insertar una clave
    public void insertar(E clave) {
        subir = false;
        E mediana;
        NodoB<E> nuevoNodo;

        mediana = insertarRecursivo(this.raiz, clave);

        if (subir) {
            nuevoNodo = new NodoB<>(orden);
            nuevoNodo.contador = 1;
            nuevoNodo.claves.set(0, mediana);
            nuevoNodo.hijos.set(0, this.raiz);
            nuevoNodo.hijos.set(1, nuevoDerecha);
            this.raiz = nuevoNodo;
        }
    }

    private E insertarRecursivo(NodoB<E> actual, E clave) {
        int[] pos = new int[1];
        E mediana;

        if (actual == null) {
            subir = true;
            nuevoDerecha = null;
            return clave;
        } else {
            boolean encontrado = actual.buscarNodo(clave, pos);

            if (encontrado) {
                System.out.println("Clave duplicada: " + clave);
                subir = false;
                return null;
            }

            mediana = insertarRecursivo(actual.hijos.get(pos[0]), clave);

            if (subir) {
                if (actual.nodeLleno(orden - 1)) {
                    mediana = dividirNodo(actual, mediana, pos[0]);
                } else {
                    subir = false;
                    insertarEnNodo(actual, mediana, nuevoDerecha, pos[0]);
                }
            }
            return mediana;
        }
    }

    private void insertarEnNodo(NodoB<E> nodo, E clave, NodoB<E> derecho, int k) {
        for (int i = nodo.contador - 1; i >= k; i--) {
            nodo.claves.set(i + 1, nodo.claves.get(i));
            nodo.hijos.set(i + 2, nodo.hijos.get(i + 1));
        }
        nodo.claves.set(k, clave);
        nodo.hijos.set(k + 1, derecho);
        nodo.contador++;
    }

    private E dividirNodo(NodoB<E> nodo, E clave, int k) {
        NodoB<E> derecho = nuevoDerecha;
        int i, posMediana;
        posMediana = (k <= orden / 2) ? orden / 2 : orden / 2 + 1;

        nuevoDerecha = new NodoB<>(orden);

        for (i = posMediana; i < orden - 1; i++) {
            nuevoDerecha.claves.set(i - posMediana, nodo.claves.get(i));
            nuevoDerecha.hijos.set(i - posMediana + 1, nodo.hijos.get(i + 1));
        }

        nuevoDerecha.contador = (orden - 1) - posMediana;
        nodo.contador = posMediana;

        if (k <= orden / 2)
            insertarEnNodo(nodo, clave, derecho, k);
        else
            insertarEnNodo(nuevoDerecha, clave, derecho, k - posMediana);

        E mediana = nodo.claves.get(nodo.contador - 1);
        nuevoDerecha.hijos.set(0, nodo.hijos.get(nodo.contador));
        nodo.contador--;

        return mediana;
    }

    // Buscar clave en el arbol
    public boolean buscar(E clave) {
        NodoB<E> actual = this.raiz;
        int[] pos = new int[1];

        while (actual != null) {
            if (actual.buscarNodo(clave, pos)) {
                System.out.println(clave + " se encuentra en el nodo " + actual.IDNodo + " en la posición " + pos[0]);
                return true;
            }
            actual = actual.hijos.get(pos[0]);
        }
        return false;
    }

    // Mostrar árbol en forma preorden
    public void imprimir() {
        if (estaVacio()) {
            System.out.println("El árbol B está vacío.");
        } else {
            imprimirNodo(raiz, 0);
        }
    }

    private void imprimirNodo(NodoB<E> nodo, int nivel) {
        if (nodo != null) {
            System.out.println("Nivel " + nivel + " - " + nodo);
            for (int i = 0; i <= nodo.contador; i++) {
                NodoB<E> hijo = nodo.hijos.get(i);
                if (hijo != null) {
                    imprimirNodo(hijo, nivel + 1);
                }
            }
        }
    }
}
