import javax.swing.*;
import java.awt.*;
import modelo.Producto;
import estructuras.ListaEnlazada;
import estructuras.arbolb.ArbolB;

public class VisualizadorArbolB extends JPanel {

    public VisualizadorArbolB(ArbolB<Producto> arbol) {
        setLayout(new BorderLayout());

        // Obtiene todos los productos del ArbolB
        ListaEnlazada<Producto> productos = arbol.obtenerTodos();

        // Crea un modelo para el JList
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < productos.tamaño(); i++) {
            Producto p = productos.obtener(i);
            model.addElement(p.getCodigo() + " - " + p.getNombre() + " (cantidad: " + p.getCantidad() + ")");
        }

        JList<String> listado = new JList<>(model);
        listado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        listado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Añadir al panel
        add(new JScrollPane(listado), BorderLayout.CENTER);
    }
}
