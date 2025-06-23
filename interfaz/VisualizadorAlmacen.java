import javax.swing.*;
import java.awt.*;
import estructuras.Iterador;
import estructuras.grafo.*;
import modelo.*;

public class VisualizadorAlmacen extends JPanel {
    private final Grafo<Ubicacion> grafo;

    public VisualizadorAlmacen(Grafo<Ubicacion> grafo) {
        this.grafo = grafo;
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int n = grafo.getVertices().tamaño();
        Point[] positions = new Point[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (getWidth() / 2 + 250 * Math.cos(angle));
            int y = (int) (getHeight() / 2 + 250 * Math.sin(angle));
            positions[i] = new Point(x, y);
        }

        // Dibujar vértices
        for (int i = 0; i < n; i++) {
            Vertice<Ubicacion> v = grafo.getVertices().obtener(i);
            g.setColor(Color.GREEN);
            g.fillOval(positions[i].x - 20, positions[i].y - 20, 40, 40);
            g.setColor(Color.BLACK);
            g.drawOval(positions[i].x - 20, positions[i].y - 20, 40, 40);
            g.drawString(v.getDato().getNombre(), positions[i].x - 5, positions[i].y + 5);
        }

        //  Dibujar todas las aristas
        for (int i = 0; i < n; i++) {
            Vertice<Ubicacion> origen = grafo.getVertices().obtener(i);
            Iterador<Arista<Ubicacion>> it = origen.getAristasSalientes().iterador();
            while (it.hasNext()) {
                Arista<Ubicacion> arista = it.next();
                int j = obtenerIndice(grafo, arista.getDestino());

                g.setColor(Color.GRAY);
                g.drawLine(positions[i].x, positions[i].y, positions[j].x, positions[j].y);

                int etiquetaX = (positions[i].x + positions[j].x) / 2;
                int etiquetaY = (positions[i].y + positions[j].y) / 2;

                g.setColor(Color.RED);
                g.drawString(String.valueOf(arista.getPeso()),
                             etiquetaX, etiquetaY);
            }
        }
    }

    private int obtenerIndice(Grafo<Ubicacion> grafo, Vertice<Ubicacion> v) 
    {
        for (int i = 0; i < grafo.getVertices().tamaño(); i++) {
            if (grafo.getVertices().obtener(i) == v) {
                return i;
            }
        }
        return -1;
    }
}
