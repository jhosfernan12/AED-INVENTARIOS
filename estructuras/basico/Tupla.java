
public class Tupla<K, V> {
    private final K clave;
    private V valor;

    public Tupla(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "(" + clave + ", " + valor + ")";
    }
}