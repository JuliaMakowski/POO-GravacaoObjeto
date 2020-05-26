package src;

import java.io.Serializable;

public class Venda implements Serializable{
    private String codigoVenda, codigoCliente, quantidade, valor;

    public Venda (String codigoVenda, String quantidade, String codigoCliente, String valor){
        this.codigoCliente = codigoCliente;
        this.codigoVenda = codigoVenda;
        this.quantidade=quantidade;
        this.valor=valor;
    }

    @Override
    public String toString() {
        return  "\nCodigo Venda: " + codigoVenda +
                "\nQuantidade: " + quantidade +
                "\nValor Total: " + valor +
                "\n";
    }
}
