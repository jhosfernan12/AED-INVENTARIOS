import java.util.ArrayList;

public class NodoB<E extends Comparable<E>> {
    protected ArrayList<E> claves;   
    protected ArrayList<NodoB<E>> hijos;  
    protected int contador; 
    protected int IDNodo;
    private static int IDsiguienteNodo = 0;

    public NodoB (int n){ 
        this.claves = new ArrayList<E>(n); 
        this.hijos = new ArrayList<NodoB<E>>(n + 1);
        this.contador = 0; 
        this.IDNodo = IDsiguienteNodo++;
 
        for(int i=0; i < n; i++){
                this.claves.add(null); 
        } 

        for (int i = 0; i <= n; i++) 
        { 
            this.hijos.add(null); 
        }
    } 

    //Pregunta si el nodo actual esta lleno
    public boolean nodoLleno () { 
        return this.contador == this.claves.size(); 
    }

    public boolean nodeLleno (int n) { 
        return this.contador == n; 
    }

    //Pregunta si el nodo actual esta vaciox    
    public boolean nodoVacio () { 
        return this.contador == 0; 
    } 

    //Busca un nodo, pero solo verifica si la clave existe o no
    public boolean buscarNodo (E clave) { 
        for (int i = 0; i < this.contador; i++) { 
            if (this.claves.get(i).equals(clave)) { 
                return true; 
            } 
        } 
        return false; 
    }

    //Busca un nodo y devuelve la posicion donde se encuentra, si no se encuentra devuelve la posicion donde deberia estar
    public boolean buscarNodo (E clave, int[] pos) { 
        for (int i = 0; i < this.contador; i++) { 
            if (this.claves.get(i).equals(clave)) { 
                pos[0] = i; 
                return true; 
            } else if (this.claves.get(i).compareTo(clave) > 0) { 
                pos[0] = i; 
                return false; 
            } 
        } 
        pos[0] = this.contador; 
        return false; 
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node ID: ").append(this.IDNodo).append(" | ");
        sb.append("Keys: ");
        for (int i = 0; i < this.contador; i++) {
            sb.append(this.claves.get(i)).append(" ");
        }
        return sb.toString();
    }
}
