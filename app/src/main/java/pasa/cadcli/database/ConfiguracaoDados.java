package pasa.cadcli.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pasa.cadcli.entities.Configuracao;
import pasa.cadcli.R;
import pasa.cadcli.util.UtilDatabase;

public class ConfiguracaoDados extends SQLiteOpenHelper {

    private static final String TABELA_CONFIGURACOES = "CONFIGURACOES";

    private static final String CODIGO_CONFIGURACAO = "CODIGO_CONFIGURACAO";
    private static final String MODULO = "MODULO";
    private static final String CONFIGURACAO = "CONFIGURACAO";
    private static final String VALOR_CONFIGURACAO = "VALOR_CONFIGURACAO";

    private static final String[] COLUNAS = {CODIGO_CONFIGURACAO, MODULO, CONFIGURACAO, VALOR_CONFIGURACAO};

    private Context mContext;

    public ConfiguracaoDados(Context context) {
        super(context, UtilDatabase.getDatabaseName(), null, UtilDatabase.getDatabaseVersion());
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    public String getValorDaConfiguracao(String modulo, String configuracao){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(
                TABELA_CONFIGURACOES,
                COLUNAS,
                MODULO + " = ? and " + CONFIGURACAO + " = ?",
                new String[] { String.valueOf(modulo), String.valueOf(configuracao) },
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            return pegarValorAlternativoDaUFPadrao("", modulo, configuracao);
        } else {
            cursor.moveToFirst();
            Configuracao config = cursorToConfiguracao(cursor);
            return pegarValorAlternativoDaUFPadrao(config.getValor(), modulo, configuracao);
        }
    }

    private String pegarValorAlternativoDaUFPadrao(String valor, String modulo, String configuracao) {
        String valorAux = valor;
        if ((modulo == UtilDatabase.getModuloClientes()) && (configuracao == UtilDatabase.getConfiguracaoClientesUfPadrao())) {
            if ((valor.equals("")) || (valor == null)){
                valorAux = mContext.getResources().getString(R.string.UFPadrao);
            }
        }

        return valorAux;
    }

    private Configuracao cursorToConfiguracao(Cursor cursor) {
        Configuracao configuracao = new Configuracao();

        configuracao.setCodigo(Integer.parseInt(cursor.getString(0)));
        configuracao.setModulo(cursor.getString(1));
        configuracao.setConfiguracao(cursor.getString(2));
        configuracao.setValor(cursor.getString(3));

        return configuracao;
    }

    public int atualizarConfiguracao(String modulo, String configuracao, String valor) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(VALOR_CONFIGURACAO, valor);

        int i = database.update(
                TABELA_CONFIGURACOES,
                values,
                MODULO + " = ? and " + CONFIGURACAO + " = ?",
                new String[] { String.valueOf(modulo), String.valueOf(configuracao) }
        );
        database.close();

        return i;
    }
}
