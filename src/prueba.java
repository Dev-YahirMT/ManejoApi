import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class prueba {
    public static void main(String[] args) {
        // Manejo de excepciones con try-catch
        try {
            File file = new File("peliculas.txt");
            FileReader reader = new FileReader(file);

            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            reader.close();
        } catch (IOException e) {
            // Manejo de errores si no se encuentra el archivo o hay problemas al leerlo
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}