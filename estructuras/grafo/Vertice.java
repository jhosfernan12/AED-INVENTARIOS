
public class Vertice<T> {
    private T dato;
    private boolean visitado; // Nuevo campo
    private ListaEnlazada<Arista<T>> aristasSalientes;
    private ListaEnlazada<Arista<T>> aristasEntrantes;

    public Vertice(T dato) {
        this.dato = dato;
        this.visitado = false;
        this.aristasSalientes = new ListaEnlazada<>();
        this.aristasEntrantes = new ListaEnlazada<>();
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void agregarAristaSaliente(Arista<T> arista) {
        aristasSalientes.agregar(arista);
    }

    public void agregarAristaEntrante(Arista<T> arista) {
        aristasEntrantes.agregar(arista);
    }

    public boolean eliminarAristaSaliente(Arista<T> arista) {
        return aristasSalientes.eliminar(arista);
    }

    public boolean eliminarAristaEntrante(Arista<T> arista) {
        return aristasEntrantes.eliminar(arista);
    }

    public ListaEnlazada<Arista<T>> getAristasSalientes() {
        return aristasSalientes;
    }

    public ListaEnlazada<Arista<T>> getAristasEntrantes() {
        return aristasEntrantes;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}