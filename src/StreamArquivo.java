package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class StreamArquivo {

    public void leituraArquivo() {    
        Path path = Paths.get("CLIENTES.TXT");
         try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
           String linha = null;
           while ((linha = br.readLine()) != null) {   
              // separador: :
              Scanner sc = new Scanner(linha).useDelimiter(":");
              System.out.println(sc.next()+" - "+sc.next());
           }
        } 
        catch (IOException e) {
        		System.err.format("Erro de E/S: %s%n", e);
        } 
    }

}
