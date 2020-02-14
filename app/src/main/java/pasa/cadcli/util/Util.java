package pasa.cadcli.util;

public class Util {
    private final String[] listaDeUFs = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR", "PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SE", "SP", "TO"};

    private static final String TIUTLO_LISTA_DE_CLIENTES = "Lista de clientes";
    private static final String TIUTLO_CADASTRO_DE_CLIENTE = "Cadastro de cliente";
    private static final String TIUTLO_CONFIGURACOES = "Configurações";

    public int pegarPosicaoDaUF(String uf){
        int posicao = 0;
        for (int i = 0; i < listaDeUFs.length; i++) {
            if (listaDeUFs[i].equals(uf)) {
                posicao = i;
                break;
            }
        }
        return posicao;
    }

    public String[] getListaDeUFs() {
        return listaDeUFs;
    }

    public static String getTiutloListaDeClientes() {
        return TIUTLO_LISTA_DE_CLIENTES;
    }

    public static String getTiutloCadastroDeCliente() {
        return TIUTLO_CADASTRO_DE_CLIENTE;
    }

    public static String getTiutloConfiguracoes() {
        return TIUTLO_CONFIGURACOES;
    }
}
