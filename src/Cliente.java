package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Cliente implements Serializable {
    private String nome, codigo;
    private ArrayList<Venda> vendas;

    public Cliente(String nome, String codigo){
        this.nome = nome;
        this.codigo = codigo;
        vendas = new ArrayList<>();
        addVendas();
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void addVendas(){
        String filePath = Paths.get("VENDAS.txt").toAbsolutePath().toString();
        filePath = filePath.replace("src","");
        Path path = Paths.get(filePath);
        boolean haCliente =false;
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String linha = null;
            int quantidade=0;
            while ((linha = br.readLine()) != null) {
                String [] venda = linha.split(";");
                if (venda[2].equalsIgnoreCase(codigo)){
                    this.vendas.add(new Venda(venda[0],venda[1],venda[2],venda[3]));
                    haCliente=true;
                }
            }
        }
        catch (IOException e) {
            System.err.format("Erro de leitura do arquivo", e);
        }
    }

    private String vendasString(){
        String vendasString = " ";
        if (vendas.isEmpty())vendasString="Não há vendas";
        for (int i=0; i<vendas.size(); i++){
            vendasString = vendasString + vendas.get(i).toString();
        }
        return vendasString;
    }
    @Override
    public String toString() {
        return "Cliente: " +
                "\nNome: " + nome +
                "\nCodigo: " + codigo +
                "\nVendas:" + vendasString();
    }
}
