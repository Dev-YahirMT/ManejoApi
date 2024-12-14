import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.GsonBuilder;
import excepcion.ErrorEnConversionDeDuraciónException;
import modelos.Titulo;
import modelos.TituloOmdb;

import javax.annotation.processing.SupportedSourceVersion;

public class api {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        List<Titulo> titulos = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (true){
            System.out.println("Escribe el nombre de una pelicula:");
            var busqueda = lectura.nextLine();

            if(busqueda.equalsIgnoreCase("salir")){
                break;
            }

            String clave = "-----------";

            String direccion = "http://www.omdbapi.com/?apikey=" + clave + "&t=" + busqueda.replace(" ", "+");
            try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(direccion))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println(response.body());

                    TituloOmdb miTituloOmdb = gson.fromJson(response.body(), TituloOmdb.class);
                    System.out.println(miTituloOmdb);

                    Titulo miTitulo = new Titulo(miTituloOmdb);
                    System.out.println(miTitulo);

                    titulos.add(miTitulo);
            }catch(NumberFormatException e){
                System.out.println("Ocurrio un error: *");
                System.out.println(e.getMessage());
            }catch(IllegalArgumentException e){
                System.out.println("Error en la URI, Verifique la dirección");
            }catch(ErrorEnConversionDeDuraciónException e)
            {
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println("Ocurrio un error indesperado");
            }finally{
                System.out.println("Finalizó!");
            }
        }
        System.out.println(titulos);
        FileWriter escritura = new FileWriter("peliculas.json");
        escritura.write(gson.toJson(titulos));
        escritura.close();
    }
}
