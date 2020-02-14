package pasa.cadcli.util;

public class UtilDatabase {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CadCli.db";

    private static final String MODULO_CLIENTES = "Clientes";
    private static final String CONFIGURACAO_CLIENTES_UF_PADRAO = "UFPadrao";
    private static final String CONFIGURACAO_CLIENTES_CIDADE_PADRAO = "CidadePadrao";

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getModuloClientes() {
        return MODULO_CLIENTES;
    }

    public static String getConfiguracaoClientesUfPadrao() {
        return CONFIGURACAO_CLIENTES_UF_PADRAO;
    }

    public static String getConfiguracaoClientesCidadePadrao() {
        return CONFIGURACAO_CLIENTES_CIDADE_PADRAO;
    }
}
