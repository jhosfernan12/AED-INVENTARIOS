// MenuHandler.java
import javax.swing.*;
import modelo.*;
import estructuras.arbolb.ArbolB;
import estructuras.grafo.Vertice;

public class MenuHandler {

    private Almacen almacen;

    public MenuHandler(Almacen almacen) {
        this.almacen = almacen;
    }

    // ------------------------------------------------
    // Listar productos en cualquier ubicación
    // ------------------------------------------------
    public void listarProductos() {
        String nombreUbicacion = JOptionPane.showInputDialog("Nombre de la ubicación:");
        if (nombreUbicacion != null && !nombreUbicacion.trim().isEmpty()) {
            Vertice<Ubicacion> v = almacen.buscarVerticePorNombre(nombreUbicacion.trim());
            if (v != null) {
                Main.mostrarProductos(v.getDato());
            } else {
                JOptionPane.showMessageDialog(null, "❌ No existe la ubicación " + nombreUbicacion);
            }
        }
    }

    // ------------------------------------------------
    // Agregar producto a cualquier ubicación
    // ------------------------------------------------
    public void agregarProducto() {
    String nombreUbicacion = JOptionPane.showInputDialog("Nombre de la ubicación:");
    if (nombreUbicacion != null && !nombreUbicacion.trim().isEmpty()) {
        Vertice<Ubicacion> v = almacen.buscarVerticePorNombre(nombreUbicacion.trim());
        if (v != null) {
            String nombreProd = JOptionPane.showInputDialog("Nombre del producto:");
            String codigoStr = JOptionPane.showInputDialog("Código:");
            String cantidadStr = JOptionPane.showInputDialog("Cantidad:");
            if (nombreProd != null && codigoStr != null && cantidadStr != null) {
                try {
                    int codigo = Integer.parseInt(codigoStr);
                    int cantidad = Integer.parseInt(cantidadStr);

                    //  Verificación antes de agregar
                    if (v.getDato().existeProducto(codigo)) {
                        JOptionPane.showMessageDialog(null, 
                            "Ya existe un producto con el código " + codigo);
                        return;
                    }

                    v.getDato().agregarProducto(new Producto(nombreProd, codigo, cantidad));
                    JOptionPane.showMessageDialog(null, " Producto agregado correctamente");
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, " Error en formato numerico");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, " No existe la ubicacion " + nombreUbicacion);
        }
    }
}

    // ------------------------------------------------
    // Eliminar producto de cualquier ubicación
    // ------------------------------------------------
    public void eliminarProducto() {
    String nombreUbicacion = JOptionPane.showInputDialog("Nombre de la ubicacion:");
    if (nombreUbicacion != null && !nombreUbicacion.trim().isEmpty()) {
        Vertice<Ubicacion> v = almacen.buscarVerticePorNombre(nombreUbicacion.trim());
        if (v != null) {
            String codigoStr = JOptionPane.showInputDialog("Ingrese el código del producto a eliminar:");
            if (codigoStr != null) {
                try {
                    int codigo = Integer.parseInt(codigoStr);
                    // ✅ Verificación primero
                    if (!v.getDato().existeProducto(codigo)) {
                        JOptionPane.showMessageDialog(null, " No existe un producto con este codigo");
                        return;
                    }
                    // ✅ Ahora eliminamos
                    boolean eliminado = v.getDato().eliminarProducto(codigo);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto");
                    }
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "Error en formato numerico");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe la ubicacion " + nombreUbicacion);
        }
    }
}

public void mostrarArbolProductos() {
    String nombreUbicacion = JOptionPane.showInputDialog("Nombre de la ubicación:");
    if (nombreUbicacion != null && !nombreUbicacion.trim().isEmpty()) {
        Vertice<Ubicacion> v = almacen.buscarVerticePorNombre(nombreUbicacion.trim());
        if (v != null) {
            ArbolB<Producto> arbol = v.getDato().getProductos();
            if (arbol != null && !arbol.estaVacio()) {
                VisualizadorArbolB ventana = new VisualizadorArbolB(arbol);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No hay productos en esta ubicacion");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe la ubicacion");
        }
    }
}


    
}
