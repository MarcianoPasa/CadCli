package pasa.cadcli.entities;

import java.io.Serializable;

public class Configuracao implements Serializable {
    private int codigo;
    private String modulo;
    private String configuracao;
    private String valor;

    public Configuracao(){

    }

    public Configuracao(int codigo, String modulo, String configuracao, String valor) {
        this.codigo = codigo;
        this.modulo = modulo;
        this.configuracao = configuracao;
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(String configuracao) {
        this.configuracao = configuracao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
