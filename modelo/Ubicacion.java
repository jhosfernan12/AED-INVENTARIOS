package modelo;

import estructuras.Iterador;
import estructuras.ListaEnlazada;
import estructuras.arbolb.ArbolB;

public class Ubicacion {
    private String nombre;
    private ArbolB<Producto> productos; // Arbol B+ para gestionar productos


    public Ubicacion(String nombre, int ordenArbol) {
        this.nombre = nombre;
        this.productos = new ArbolB<>(ordenArbol); // Inicializamos el Arbol B+
    }

    // Metodo para agregar un producto al arbol B+
    public void agregarProducto(Producto producto) {
    // 1 Verificamos si existe producto con ese codigo
    ListaEnlazada<Producto> todos = productos.obtenerTodos();
    for (int i = 0; i < todos.tamaño(); i++) {
        if (todos.obtener(i).getCodigo() == producto.getCodigo()) {
            System.out.println(" Ya existe un producto con el codigo " + producto.getCodigo());
            return;
        }
    }
    // Si no existe, insertamos
    productos.insertar(producto);
    System.out.println("Producto agregado correctamente");
}

    // Obtener el arbol de productos
    public ArbolB<Producto> getProductos() {
        return productos;
    }

    // Obtener el nombre de la ubicación
    public String getNombre() {
        return nombre;
    }

   public boolean eliminarProducto(int codigo) {
    // Obtiene todos los productos
    ListaEnlazada<Producto> todos = productos.obtenerTodos();

    // Encuentra la instancia EXACTA
    Producto productoAEliminar = null;
    for (int i = 0; i < todos.tamaño(); i++) {
        Producto p = todos.obtener(i);
        if (p.getCodigo() == codigo) {
            productoAEliminar = p;
            break;
        }
    }

    // Ahora elimina DIRECTAMENTE la instancia encontrada
    if (productoAEliminar != null) {
        productos.eliminar(productoAEliminar);
        return true;
    }

    return false;
}


        public boolean existeProducto(int codigo) {
            ListaEnlazada<Producto> todos = productos.obtenerTodos();
            Iterador<Producto> it = todos.iterador();
            while (it.hasNext()) {
                Producto p = it.next();
                if (p.getCodigo() == codigo) {
                    return true;
                }
            }
            return false;
        }




    @Override
    public String toString() {
        return nombre; // Solo retornamos el nombre de la ubicacion
    }
}
