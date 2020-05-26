package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Menu {

    public void menu(){
        Scanner in =new Scanner(System.in);
        int opc = 0;
        do {
            System.out.println("===============================================");
            System.out.println("[1] Consulta cliente");
            System.out.println("[2] Consulta vendas de um cliente");
            System.out.println("[3] Salva vendas de um cliente");
            System.out.println("[4] Recupera vendas de um cliente");
            System.out.println("[5] Sair");
            opc = in.nextInt();
            System.out.println("===============================================");

            switch (opc){
                case 1: consultaCliente(in);
                    break;
                case 2: consultaVendas(in);
                    break;
                case 3: salvaVendas(in);
                    break;
                case 4: recuperaVendas(in);
                    break;
                case 5:
                    break;
            }
        }while (opc!=5);
    }

    private static void consultaCliente(Scanner in){
        System.out.println("Insira o codigo de um cliente:");
        String codigo = in.next();
        String filePath = Paths.get("CLIENTES.txt").toAbsolutePath().toString();
        filePath = filePath.replace("src","");
        Path path = Paths.get(filePath);
        boolean haCliente=false;
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = null;
            while ((linha = br.readLine()) != null) {
                String [] cliente = linha.split(":");
                if (cliente[0].equalsIgnoreCase(codigo)){
                    System.out.println("Nome do cliente: " + cliente[1]);
                    haCliente=true;
                    break;
                }
            }
            if ((linha = br.readLine()) == null && !haCliente) System.out.println("Codigo de cliente invalido");
        }
        catch (IOException e) {
            System.err.format("Erro de leitura do arquivo", e);
        }
    }

    private static void consultaVendas(Scanner in){
        System.out.println("Insira o codigo de um cliente:");
        String codigo = in.next();
        String filePath = Paths.get("VENDAS.txt").toAbsolutePath().toString();
        filePath = filePath.replace("src","");
        Path path = Paths.get(filePath);
        boolean haCliente=false;
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = null;
            int quantidade=0;
            double totalVendas=0;
            while ((linha = br.readLine()) != null) {
                String [] venda = linha.split(";");
                if (venda[2].equalsIgnoreCase(codigo)){
                    int qtd = Integer.parseInt(venda[1]);
                    double value = Double.parseDouble(venda[3]);
                    quantidade += qtd;
                    totalVendas += value;
                    haCliente=true;
                }
            }
            if ((linha = br.readLine()) == null && !haCliente) System.out.println("Codigo de cliente invalido");
            else System.out.println("Quantidade de vendas: "+ quantidade +"\nValor total: "+totalVendas);
        }
        catch (IOException e) {
            System.err.format("Erro de leitura do arquivo", e);
        }
    }

    private static void salvaVendas(Scanner in){
        Cliente cliente1 =new Cliente(" "," ");
        System.out.println("Insira o codigo de um cliente:");
        String codigo = in.next();

        String filePath = Paths.get("CLIENTES.txt").toAbsolutePath().toString();
        filePath = filePath.replace("src","");
        Path path = Paths.get(filePath);
        boolean haCliente=false;
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = null;
            while ((linha = br.readLine()) != null) {
                String [] cliente = linha.split(":");
                if (cliente[0].equalsIgnoreCase(codigo)){
                    cliente1 = new Cliente(cliente[1],cliente[0]);
                    haCliente=true;
                    break;
                }
            }
            if ((linha = br.readLine()) == null && !haCliente) System.out.println("Codigo de cliente invalido");
        }
        catch (IOException e) {
            System.err.format("Erro de leitura do arquivo", e);
        }
        Path arq1 = Paths.get("Resumo.bin");
        try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
            oarq.writeObject(cliente1);
        } catch (IOException e) {
            System.out.println("Problemas na gravacao do arquivo: " + e.getMessage());

        }
    }

    private static void recuperaVendas(Scanner in){
        Cliente cliente = null;
        Path arq1 = Paths.get("Resumo.bin");
        try (ObjectInputStream iarq = new
                ObjectInputStream(Files.newInputStream(arq1)))
        {
            cliente = (Cliente) iarq.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Problema na leitura do arquivo (cast): " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problema na leitura do arquivo: " + e.getMessage());
        }
        System.out.println(cliente.toString());
    }
}
