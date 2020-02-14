package pasa.cadcli.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;

import pasa.cadcli.entities.Cliente;
import pasa.cadcli.util.UtilDatabase;

public class ClienteDados extends SQLiteOpenHelper {

    private static final String TABELA_CLIENTES = "CLIENTES";

    private static final String CODIGO_CLIENTE = "CODIGO_CLIENTE";
    private static final String NOME_CLIENTE = "NOME_CLIENTE";
    private static final String RUA = "RUA";
    private static final String NUMERO = "NUMERO";
    private static final String COMPLEMENTO = "COMPLEMENTO";
    private static final String CEP = "CEP";
    private static final String BAIRRO = "BAIRRO";
    private static final String CIDADE = "CIDADE";
    private static final String UF = "UF";
    private static final String FONE_1_DDD = "FONE_1_DDD";
    private static final String FONE_1_PARTE_1 = "FONE_1_PARTE_1";
    private static final String FONE_1_PARTE_2 = "FONE_1_PARTE_2";
    private static final String FONE_2_DDD = "FONE_2_DDD";
    private static final String FONE_2_PARTE_1 = "FONE_2_PARTE_1";
    private static final String FONE_2_PARTE_2 = "FONE_2_PARTE_2";


    private static final String[] COLUNAS = {
            CODIGO_CLIENTE,
            NOME_CLIENTE,
            RUA,
            NUMERO,
            COMPLEMENTO,
            CEP,
            BAIRRO,
            CIDADE,
            UF,
            FONE_1_DDD,
            FONE_1_PARTE_1,
            FONE_1_PARTE_2,
            FONE_2_DDD,
            FONE_2_PARTE_1,
            FONE_2_PARTE_2
    };

    private Context mContext;

    public ClienteDados(Context context) {
        super(context, UtilDatabase.getDatabaseName(), null, UtilDatabase.getDatabaseVersion());
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (int i = 1; i <= UtilDatabase.getDatabaseVersion(); i++) {
            executaMigracao(database, i);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (int i = oldVersion; i <= newVersion; i++) {
            executaMigracao(database, i);
        }
    }

    private void executaMigracao(SQLiteDatabase db, int version) {
        String migration = null;
        try {
            migration = IOUtils.toString(mContext.getAssets().open("migrations/" + version + ".sql"), "UTF-8");
            String[] commands = migration.split(";");
            for (String command : commands) {
                if(command == null || command.isEmpty()) continue;
                db.execSQL(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserirCliente(Cliente clientes) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOME_CLIENTE, clientes.getNome());
        values.put(RUA, clientes.getRua());
        values.put(NUMERO, clientes.getNumero());
        values.put(COMPLEMENTO, clientes.getComplemento());
        values.put(CEP, clientes.getCep());
        values.put(BAIRRO, clientes.getBairro());
        values.put(CIDADE, clientes.getCidade());
        values.put(UF, clientes.getUf());
        values.put(FONE_1_DDD, clientes.getFone1DDD());
        values.put(FONE_1_PARTE_1, clientes.getFone1Parte1());
        values.put(FONE_1_PARTE_2, clientes.getFone1Parte2());
        values.put(FONE_2_DDD, clientes.getFone2DDD());
        values.put(FONE_2_PARTE_1, clientes.getFone2Parte1());
        values.put(FONE_2_PARTE_2, clientes.getFone2Parte2());

        database.insert(TABELA_CLIENTES, null, values);
        database.close();
    }

    public Cliente buscarCliente(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                TABELA_CLIENTES, // a. tabela
                COLUNAS, // b. colunas
                " CODIGO_CLIENTE = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            Cliente cliente = cursorToCliente(cursor);
            return cliente;
        }
    }

    private Cliente cursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();

        cliente.setCodigo(Integer.parseInt(cursor.getString(0)));
        cliente.setNome(cursor.getString(1));
        cliente.setRua(cursor.getString(2));
        cliente.setNumero(cursor.getString(3));
        cliente.setComplemento(cursor.getString(4));
        cliente.setCep(Integer.parseInt(cursor.getString(5)));
        cliente.setBairro(cursor.getString(6));
        cliente.setCidade(cursor.getString(7));
        cliente.setUf(cursor.getString(8));
        cliente.setFone1DDD(cursor.getString(9));
        cliente.setFone1Parte1(cursor.getString(10));
        cliente.setFone1Parte2(cursor.getString(11));
        cliente.setFone2DDD(cursor.getString(12));
        cliente.setFone2Parte1(cursor.getString(13));
        cliente.setFone2Parte2(cursor.getString(14));

        return cliente;
    }

    public ArrayList<Cliente> buscarTodosOsClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        String query = "SELECT * FROM " + TABELA_CLIENTES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = cursorToCliente(cursor);
                listaClientes.add(cliente);
            } while (cursor.moveToNext());
        }
        return listaClientes;
    }

    public ArrayList<Cliente> buscarPrimeirosXClientes(int quantidade) {
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        String query = "SELECT * FROM " + TABELA_CLIENTES + " LIMIT " + String.valueOf(quantidade);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = cursorToCliente(cursor);
                listaClientes.add(cliente);
            } while (cursor.moveToNext());
        }
        return listaClientes;
    }

    public int numeroTotalDeClientes(){
        String query = "SELECT COUNT(CODIGO_CLIENTE) TOTAL_DE_CLIENTES FROM " + TABELA_CLIENTES;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        }
        else {
            return 0;
        }
    }

    public int atualizarCliente(Cliente cliente) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOME_CLIENTE, cliente.getNome());
        values.put(RUA, cliente.getRua());
        values.put(NUMERO, cliente.getNumero());
        values.put(COMPLEMENTO, cliente.getComplemento());
        values.put(CEP, cliente.getCep());
        values.put(BAIRRO, cliente.getBairro());
        values.put(CIDADE, cliente.getCidade());
        values.put(UF, cliente.getUf());
        values.put(FONE_1_DDD, cliente.getFone1DDD());
        values.put(FONE_1_PARTE_1, cliente.getFone1Parte1());
        values.put(FONE_1_PARTE_2, cliente.getFone1Parte2());
        values.put(FONE_2_DDD, cliente.getFone2DDD());
        values.put(FONE_2_PARTE_1, cliente.getFone2Parte1());
        values.put(FONE_2_PARTE_2, cliente.getFone2Parte2());

        int i = database.update(
                TABELA_CLIENTES, //tabela
                values, // valores
                CODIGO_CLIENTE + " = ?", // colunas para comparar
                new String[] { String.valueOf(cliente.getCodigo()) }
        ); //parâmetros
        database.close();

        return i; // número de linhas modificadas
    }

    public int excluirCliente(Cliente cliente) {
        SQLiteDatabase database = this.getWritableDatabase();

        int i = database.delete(
                TABELA_CLIENTES, //tabela
                CODIGO_CLIENTE + " = ?", // colunas para comparar
                new String[] { String.valueOf(cliente.getCodigo()) }
        );
        database.close();

        return i; // número de linhas excluídas
    }
}