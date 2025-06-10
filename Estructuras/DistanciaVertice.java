package Estructuras;

public class DistanciaVertice<T> 
{
    public Vertice<T> vertice;
    public int distancia;

    public DistanciaVertice(Vertice<T> vertice, int distancia)
     {
        this.vertice = vertice;
        this.distancia = distancia;
    }
}
