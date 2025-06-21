package estructuras.arbolb;

import estructuras.ListaEnlazada;

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

        if (k <= orden / 2) {
            insertarEnNodo(nodo, clave, derecho, k);
        } else {
            insertarEnNodo(nuevoDerecha, clave, derecho, k - posMediana);
        }

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
                System.out.println(clave + " se encuentra en el nodo " + actual.IDNodo + " en la posicion " + pos[0]);
                return true;
            }
            actual = actual.hijos.get(pos[0]);
        }
        return false;
    }

    public void eliminar(E clave) {
        if (raiz == null) {
            System.out.println("Arbol vacio.");
            return;
        }
        eliminarRecursivo(raiz, clave);
        if (raiz.contador == 0 && !raiz.esHoja()) {
            raiz = raiz.hijos.get(0);
        }
    }

        private void eliminarRecursivo(NodoB<E> nodo, E clave) {
        int pos[] = new int[1];
        boolean encontrado = nodo.buscarNodo(clave, pos);
        if (encontrado) {
            if (nodo.esHoja()) {
                // Si es hoja, simplemente elimina
                for (int i = pos[0]; i < nodo.contador - 1; i++) {
                    nodo.claves.set(i, nodo.claves.get(i + 1));
                }
                nodo.contador--;
            } else {
                // Aqui iria logica para reemplazar por predecesor/sucesor,
                // para simplificacion asumimos solo eliminacion en hojas.
                System.out.println("La eliminacion de claves en nodos internos no esta implementada.");
            }
        } else {
            if (nodo.esHoja()) {
                // No encontrado
                return;
            } else {
                eliminarRecursivo(nodo.hijos.get(pos[0]), clave);
            }
        }
    }



    // Mostrar arbol en forma preorden
    public void imprimir() {
        if (estaVacio()) {
            System.out.println("El arbol B esta vacio.");
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

    public void imprimirTodos() {
        imprimirTodosRec(raiz);
    }

    private void imprimirTodosRec(NodoB<E> nodo) {
        if (nodo != null) {
            for (int i = 0; i < nodo.contador; i++) {
                if (nodo.hijos.get(i) != null) {
                    imprimirTodosRec(nodo.hijos.get(i));
                }
                System.out.println(nodo.claves.get(i).toString());
            }
            if (nodo.hijos.get(nodo.contador) != null) {
                imprimirTodosRec(nodo.hijos.get(nodo.contador));
            }
        }
    }


    public ListaEnlazada<E> obtenerTodos() {
        ListaEnlazada<E> todos = new ListaEnlazada<>();
        obtenerTodosRec(raiz, todos);
        return todos;
    }

    private void obtenerTodosRec(NodoB<E> nodo, ListaEnlazada<E> todos) {
        if (nodo != null) {
            for (int i = 0; i < nodo.contador; i++) {
                if (nodo.hijos.get(i) != null) {
                    obtenerTodosRec(nodo.hijos.get(i), todos);
                }
                todos.agregar(nodo.claves.get(i));
            }
            if (nodo.hijos.get(nodo.contador) != null) {
                obtenerTodosRec(nodo.hijos.get(nodo.contador), todos);
            }
        }
    }
}
