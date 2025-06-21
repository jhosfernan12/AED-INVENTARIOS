import javax.swing.*;
import java.awt.*;
import estructuras.Iterador;
import estructuras.ListaEnlazada;
import estructuras.grafo.*;
import modelo.*;
import algoritmos.Dijkstra;

public class Main {
    public static void main(String[] args) {
        // ==================================================
        // CONFIGURAR LOOK AND FEEL
        // ==================================================
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer Nimbus, se usará el look por defecto.");
        }

        // ==================================================
        // CREAR ALMACEN Y UBICACIONES
        // ==================================================
        Almacen almacen = new Almacen();
        Vertice<Ubicacion> a = almacen.agregarUbicacion("a");
        Vertice<Ubicacion> b = almacen.agregarUbicacion("b");
        Vertice<Ubicacion> c = almacen.agregarUbicacion("c");
        Vertice<Ubicacion> d = almacen.agregarUbicacion("d");
        Vertice<Ubicacion> e = almacen.agregarUbicacion("e");
        Vertice<Ubicacion> f = almacen.agregarUbicacion("f"); // Aislada

        // ==================================================
        // CONEXIONES ENTRE UBICACIONES
        // ==================================================
        almacen.conectarUbicaciones(a, b, 10);
        almacen.conectarUbicaciones(a, c, 3);
        almacen.conectarUbicaciones(b, d, 2);
        almacen.conectarUbicaciones(c, d, 8);
        almacen.conectarUbicaciones(c, e, 2);
        almacen.conectarUbicaciones(d, e, 7);

        // ==================================================
        // AGREGAR PRODUCTOS DE PRUEBA EN UBICACIÓN 'a'
        // ==================================================
        Producto teclado = new Producto("Teclado", 101, 15);
        Producto mouse = new Producto("Mouse", 102, 30);
        almacen.agregarProducto(a, teclado);
        almacen.agregarProducto(a, mouse);

        // ==================================================
        // CREAR VENTANA PRINCIPAL
        // ==================================================
        JFrame ventana = new JFrame("Visualización del Almacén");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VisualizadorAlmacen visualizador = new VisualizadorAlmacen(almacen.getGrafo());

        // ==================================================
        // MENUS
        // ==================================================
        JMenu menuProductos = new JMenu("Productos");
        MenuHandler menuHandler = new MenuHandler(almacen);

        JMenuItem itemListarProductos = new JMenuItem("Listar productos en ubicación");
        itemListarProductos.addActionListener(evt -> menuHandler.listarProductos());
        menuProductos.add(itemListarProductos);

        JMenuItem itemAgregarProducto = new JMenuItem("Agregar producto");
        itemAgregarProducto.addActionListener(evt -> menuHandler.agregarProducto());
        menuProductos.add(itemAgregarProducto);

        JMenuItem itemEliminarProducto = new JMenuItem("Eliminar producto");
        itemEliminarProducto.addActionListener(evt -> menuHandler.eliminarProducto());
        menuProductos.add(itemEliminarProducto);

       JMenuItem itemVisualizarArbolB = new JMenuItem("Visualizar Árbol B de productos");
        itemVisualizarArbolB.addActionListener(evt -> {
            String nombreUbicacion = JOptionPane.showInputDialog("Nombre de la ubicación para mostrar productos:");
            if (nombreUbicacion != null && !nombreUbicacion.trim().isEmpty()) {
                Vertice<Ubicacion> v = almacen.buscarVerticePorNombre(nombreUbicacion.trim());
                if (v != null) {
                    mostrarArbolB(v); // Ahora llamamos al método arreglado
                } else {
                    JOptionPane.showMessageDialog(null, "❌ No existe la ubicación.");
                }
            }
        });
        menuProductos.add(itemVisualizarArbolB);


        JMenu menuOperaciones = new JMenu("Operaciones");
        JMenuItem itemRutaCorta = new JMenuItem("Ruta más corta");
        itemRutaCorta.addActionListener(evt -> {
            String origenNombre = JOptionPane.showInputDialog("Ubicación de origen:");
            String destinoNombre = JOptionPane.showInputDialog("Ubicación de destino:");
            if (origenNombre != null && destinoNombre != null && !origenNombre.trim().isEmpty() && !destinoNombre.trim().isEmpty()) {
                Vertice<Ubicacion> origen = almacen.buscarVerticePorNombre(origenNombre.trim());
                Vertice<Ubicacion> destino = almacen.buscarVerticePorNombre(destinoNombre.trim());
                if (origen != null && destino != null) {
                    mostrarRutaCorta(almacen, origen, destino);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Una de las ubicaciones no existe.");
                }
            }
        });
        menuOperaciones.add(itemRutaCorta);

        JMenuItem itemDetectarCiclos = new JMenuItem("Detectar ciclos");
        itemDetectarCiclos.addActionListener(evt -> {
            JOptionPane.showMessageDialog(null,
                    "¿Hay ciclos? " + (almacen.detectarCiclo() ? "Sí" : "No"));
        });
        menuOperaciones.add(itemDetectarCiclos);

        JMenuItem itemMostrarComponentes = new JMenuItem("Mostrar componentes");
        itemMostrarComponentes.addActionListener(evt -> mostrarComponentes(almacen));
        menuOperaciones.add(itemMostrarComponentes);

        JMenu menuNodos = new JMenu("Nodos");
        JMenuItem itemAgregarNodo = new JMenuItem("Agregar nodo");
        itemAgregarNodo.addActionListener(evt -> {
            String nombre = JOptionPane.showInputDialog("Nombre de la ubicación:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                Vertice<Ubicacion> nuevo = almacen.agregarUbicacion(nombre.trim());
                if (nuevo == null) {
                    JOptionPane.showMessageDialog(null, "No se creó la ubicación (ya existe).");
                    return;
                }
                while (true) {
                    String nodoDestino = JOptionPane.showInputDialog("¿A qué nodo quieres conectarlo? (dejar en blanco para terminar)");
                    if (nodoDestino == null || nodoDestino.trim().isEmpty()) {
                        break;
                    }
                    Vertice<Ubicacion> destino = almacen.buscarVerticePorNombre(nodoDestino.trim());
                    if (destino != null) {
                        String pesoStr = JOptionPane.showInputDialog("Distancia o peso:");
                        try {
                            double peso = Double.parseDouble(pesoStr);
                            almacen.conectarUbicaciones(nuevo, destino, peso);
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null, "❌ El peso no es válido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ No existe el nodo destino.");
                    }
                }
                actualizarVisualizacion(ventana, almacen);
            }
        });
        menuNodos.add(itemAgregarNodo);

        JMenuItem itemEliminarNodo = new JMenuItem("Eliminar nodo");
        itemEliminarNodo.addActionListener(evt -> {
            String nombre = JOptionPane.showInputDialog("Nombre de la ubicación a eliminar:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                boolean eliminado = almacen.eliminarUbicacion(nombre.trim());
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "✅ Ubicación eliminada correctamente.");
                    actualizarVisualizacion(ventana, almacen);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ No existe la ubicación.");
                }
            }
        });
        menuNodos.add(itemEliminarNodo);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuProductos);
        menuBar.add(menuOperaciones);
        menuBar.add(menuNodos);

        ventana.setJMenuBar(menuBar);
        ventana.add(visualizador);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    // ==================================================
    // FUNCIONES DE AYUDA
    // ==================================================
    public static void mostrarRutaCorta(Almacen almacen, Vertice<Ubicacion> inicio, Vertice<Ubicacion> fin) {
        ListaEnlazada<Vertice<Ubicacion>> camino = almacen.caminoMasCorto(inicio, fin);
        if (camino == null || camino.tamaño() == 0) {
            JOptionPane.showMessageDialog(null, "❌ No existe camino.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Ruta más corta de ").append(inicio.getDato().getNombre())
            .append(" a ").append(fin.getDato().getNombre()).append(":\n");
        for (int i = 0; i < camino.tamaño(); i++) {
            sb.append(camino.obtener(i).getDato().getNombre());
            if (i < camino.tamaño() - 1) {
                sb.append(" -> ");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void mostrarComponentes(Almacen almacen) {
        var componentes = almacen.encontrarComponentesConexas();
        StringBuilder sb = new StringBuilder("Componentes Conexas:\n");
        for (int i = 0; i < componentes.tamaño(); i++) {
            sb.append("Componente ").append(i + 1).append(": ");
            var componente = componentes.obtener(i);
            for (int j = 0; j < componente.tamaño(); j++) {
                sb.append(componente.obtener(j).getDato().getNombre()).append(" ");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void actualizarVisualizacion(JFrame ventana, Almacen almacen) {
        ventana.getContentPane().removeAll();
        VisualizadorAlmacen visualizador = new VisualizadorAlmacen(almacen.getGrafo());
        ventana.add(visualizador);
        ventana.revalidate();
        ventana.repaint();
    }

    // ==================================================
    // MOSTRAR ÁRBOL B DE PRODUCTOS EN UNA UBICACIÓN
    // ==================================================
   public static void mostrarArbolB(Vertice<Ubicacion> ubicacion) {
    // Crear nueva ventana
    JFrame ventanaArbol = new JFrame("Árbol B de Productos en " + ubicacion.getDato().getNombre());
    ventanaArbol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventanaArbol.setSize(600, 400);
    ventanaArbol.setLocationRelativeTo(null);

    // Aquí asumimos que tienes un JPanel o componente para representar el Árbol B
    // Por ejemplo:
    VisualizadorArbolB panelArbol = new VisualizadorArbolB(ubicacion.getDato().getProductos());

    // ✅ Añadir EL PANEL, NO OTRO JFRAME
    ventanaArbol.add(panelArbol);
    ventanaArbol.setVisible(true);
}








    public static void mostrarProductos(Ubicacion ubicacion) {
    StringBuilder sb = new StringBuilder();
    sb.append("Productos en ").append(ubicacion.getNombre()).append(":\n");
    ListaEnlazada<Producto> productos = ubicacion.getProductos().obtenerTodos();
    Iterador<Producto> it = productos.iterador();
    while (it.hasNext()) {
        sb.append(it.next().toString()).append("\n");
    }
    JOptionPane.showMessageDialog(null, sb.toString());
}

}
