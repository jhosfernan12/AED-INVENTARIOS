
public class Main {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE GRAFO ===");
        
        // Crear grafo de ciudades (String como tipo de dato)
        Grafo<String> grafoCiudades = new Grafo<>();
        
        // Agregar vértices (ciudades)
        Vertice<String> bogota = grafoCiudades.agregarVertice("Bogotá");
        Vertice<String> medellin = grafoCiudades.agregarVertice("Medellín");
        Vertice<String> cali = grafoCiudades.agregarVertice("Cali");
        Vertice<String> barranquilla = grafoCiudades.agregarVertice("Barranquilla");
        
        // Agregar aristas (conexiones con pesos)
        grafoCiudades.agregarArista(bogota, medellin, 250);
        grafoCiudades.agregarArista(bogota, cali, 300);
        grafoCiudades.agregarArista(medellin, cali, 180);
        grafoCiudades.agregarArista(cali, barranquilla, 700);
        grafoCiudades.agregarArista(barranquilla, bogota, 650);
        
        // Mostrar grafo
        System.out.println(grafoCiudades);
        
        // Eliminar una ciudad y mostrar de nuevo
        grafoCiudades.eliminarVertice(cali);
        System.out.println("\nDespués de eliminar Cali:");
        System.out.println(grafoCiudades);
    }
}
