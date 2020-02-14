package pasa.cadcli.entities;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int codigo;
    private String nome;
    private String rua;
    private String numero;
    private String complemento;
    private int cep;
    private String bairro;
    private String cidade;
    private String uf;
    private String fone1DDD;
    private String fone1Parte1;
    private String fone1Parte2;
    private String fone2DDD;
    private String fone2Parte1;
    private String fone2Parte2;
    private boolean marcado;

    public Cliente(){

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getFone1DDD() {
        return fone1DDD;
    }

    public void setFone1DDD(String fone1DDD) {
        this.fone1DDD = fone1DDD;
    }

    public String getFone1Parte1() {
        return fone1Parte1;
    }

    public void setFone1Parte1(String fone1Parte1) {
        this.fone1Parte1 = fone1Parte1;
    }

    public String getFone1Parte2() {
        return fone1Parte2;
    }

    public void setFone1Parte2(String fone1Parte2) {
        this.fone1Parte2 = fone1Parte2;
    }

    public String getFone2DDD() {
        return fone2DDD;
    }

    public void setFone2DDD(String fone2DDD) {
        this.fone2DDD = fone2DDD;
    }

    public String getFone2Parte1() {
        return fone2Parte1;
    }

    public void setFone2Parte1(String fone2Parte1) {
        this.fone2Parte1 = fone2Parte1;
    }

    public String getFone2Parte2() {
        return fone2Parte2;
    }

    public void setFone2Parte2(String fone2Parte2) {
        this.fone2Parte2 = fone2Parte2;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
}
