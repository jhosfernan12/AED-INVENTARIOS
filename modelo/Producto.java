package modelo;
public class Producto implements Comparable<Producto> {
    private String nombre;
    private int codigo;
    private int cantidad;

    public Producto(String nombre, int codigo, int cantidad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public int compareTo(Producto otro) {
        int comparacionCodigo = Integer.compare(this.codigo, otro.codigo);
        if (comparacionCodigo != 0) {
            return comparacionCodigo;
        }
        return this.nombre.compareTo(otro.nombre); // si los códigos son iguales, compara por nombre
    }

    
    @Override
    public String toString() {
        return nombre + " (codigo: " + codigo + ", cantidad: " + cantidad + ")";
    }
}
