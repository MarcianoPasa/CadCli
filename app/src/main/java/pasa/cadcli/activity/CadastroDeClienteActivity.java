package pasa.cadcli.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pasa.cadcli.R;
import pasa.cadcli.database.ClienteDados;
import pasa.cadcli.database.ConfiguracaoDados;
import pasa.cadcli.entities.Cliente;
import pasa.cadcli.util.Util;
import pasa.cadcli.util.UtilDatabase;

import static pasa.cadcli.R.id.spinnerUFs;

public class CadastroDeClienteActivity extends AppCompatActivity {

    private ClienteDados mBancoDeDados;
    private int mCodigoDoCliente;

    private EditText mNome;
    private EditText mRua;
    private EditText mNumero;
    private EditText mComplemento;
    private EditText mCEP;
    private EditText mBairro;
    private EditText mCidade;
    private Spinner mUF;
    private EditText mFone1DDD;
    private EditText mFone1Parte1;
    private EditText mFone1Parte2;
    private EditText mFone2DDD;
    private EditText mFone2Parte1;
    private EditText mFone2Parte2;

    private Bundle mDados = null;

    private Util mUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_cliente);

        getSupportActionBar().setSubtitle(Util.getTiutloCadastroDeCliente());

        mBancoDeDados = new ClienteDados(this);

        mUtil = new Util();

        Spinner UFs = (Spinner) findViewById(R.id.spinnerUFs);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, mUtil.getListaDeUFs());
        UFs.setAdapter(adapter);
        UFs.getBackground().setColorFilter(Color.parseColor("#00cc66"), PorterDuff.Mode.SRC_ATOP);

        AtribuirCamposDaTelaAosAtributos();

        Intent intent = getIntent();
        mDados = intent.getExtras();

        if (mDados == null) {
            ConfiguracaoDados configuracaoDados = new ConfiguracaoDados(this);

            mUF.setSelection(mUtil.pegarPosicaoDaUF(
                    configuracaoDados.getValorDaConfiguracao(
                            UtilDatabase.getModuloClientes(),
                            UtilDatabase.getConfiguracaoClientesUfPadrao()
                    )
            ));

            mCidade.setText(
                    configuracaoDados.getValorDaConfiguracao(
                            UtilDatabase.getModuloClientes(),
                            UtilDatabase.getConfiguracaoClientesCidadePadrao()
                    )
            );

            //abre o teclado automaticamente
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        } else {
            AtribuirCamposDoBancoDeDadosAosAtributos();

            //não abre o teclado automaticamente
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    public void AtribuirCamposDaTelaAosAtributos() {
        mNome = (EditText) findViewById(R.id.editTextNome);
        mRua = (EditText) findViewById(R.id.editTextRua);
        mNumero = (EditText) findViewById(R.id.editTextNumero);
        mComplemento = (EditText) findViewById(R.id.editTextComplemento);
        mCEP = (EditText) findViewById(R.id.editTextCEP);
        mBairro = (EditText) findViewById(R.id.editTextBairro);
        mCidade = (EditText) findViewById(R.id.editTextCidade);
        mUF = (Spinner) findViewById(spinnerUFs);
        mFone1DDD = (EditText) findViewById(R.id.editTextFone1DDD);
        mFone1Parte1 = (EditText) findViewById(R.id.editTextFone1Parte1);
        mFone1Parte2 = (EditText) findViewById(R.id.editTextFone1Parte2);
        mFone2DDD = (EditText) findViewById(R.id.editTextFone2DDD);
        mFone2Parte1 = (EditText) findViewById(R.id.editTextFone2Parte1);
        mFone2Parte2 = (EditText) findViewById(R.id.editTextFone2Parte2);
    }

    public void AtribuirCamposDoBancoDeDadosAosAtributos() {
        Cliente cliente = (Cliente) mDados.getSerializable("cliente");

        mCodigoDoCliente = cliente.getCodigo();

        mNome.setText(cliente.getNome().toString());
        mRua.setText(cliente.getRua().toString());
        mNumero.setText(cliente.getNumero().toString());
        mComplemento.setText(cliente.getComplemento().toString());
        if ((cliente.getCep() != -1)) {
            mCEP.setText(Integer.toString(cliente.getCep()));
        }
        mBairro.setText(cliente.getBairro().toString());
        mCidade.setText(cliente.getCidade().toString());
        mUF.setSelection(mUtil.pegarPosicaoDaUF(cliente.getUf()));
        mFone1DDD.setText(cliente.getFone1DDD().toString());
        mFone1Parte1.setText(cliente.getFone1Parte1().toString());
        mFone1Parte2.setText(cliente.getFone1Parte2().toString());
        mFone2DDD.setText(cliente.getFone2DDD().toString());
        mFone2Parte1.setText(cliente.getFone2Parte1().toString());
        mFone2Parte2.setText(cliente.getFone2Parte2().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_de_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_salvar_cliente) {
            SalvarCliente();
            return true;
        } else if (id == R.id.action_excluir_cliente) {
            ExcluirCliente();
            return true;
        } else if (id == R.id.action_cancelar) {
            CancelarCadastro();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void SalvarCliente() {
        if (validarCampos()) {
            Cliente cliente = new Cliente();

            if (mDados != null) {
                cliente = mBancoDeDados.buscarCliente(mCodigoDoCliente);
            }

            cliente.setNome(mNome.getText().toString());
            cliente.setRua(mRua.getText().toString());
            cliente.setNumero(mNumero.getText().toString());
            cliente.setComplemento(mComplemento.getText().toString());
            if (!mCEP.getText().toString().equals("")) {
                cliente.setCep(Integer.parseInt(String.valueOf(mCEP.getText())));
            } else{
                cliente.setCep(-1);
            }
            cliente.setBairro(mBairro.getText().toString());
            cliente.setCidade(mCidade.getText().toString());
            cliente.setUf(mUF.getSelectedItem().toString());
            cliente.setFone1DDD(mFone1DDD.getText().toString());
            cliente.setFone1Parte1(mFone1Parte1.getText().toString());
            cliente.setFone1Parte2(mFone1Parte2.getText().toString());
            cliente.setFone2DDD(mFone2DDD.getText().toString());
            cliente.setFone2Parte1(mFone2Parte1.getText().toString());
            cliente.setFone2Parte2(mFone2Parte2.getText().toString());

            if (mDados == null) {
                mBancoDeDados.inserirCliente(cliente);
                Toast.makeText(getApplicationContext(), "Cliente inserido com sucesso.", Toast.LENGTH_SHORT).show();
            } else {
                mBancoDeDados.atualizarCliente(cliente);
                Toast.makeText(getApplicationContext(), "Cliente atualizado com sucesso.", Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }

    private void ExcluirCliente() {
        if (mDados == null) {
            Toast.makeText(getApplicationContext(), "Só é possível excluir clientes já salvos", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(CadastroDeClienteActivity.this)
                    .setTitle("Alerta")
                    .setMessage("Você tem certeza que deseja excluir este cliente?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (mDados != null) {
                                mBancoDeDados.excluirCliente(mBancoDeDados.buscarCliente(mCodigoDoCliente));
                                finish();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    })
                    .setIcon(R.drawable.caution)
                    .create();
            dialog.show();
        }
    }

    private void CancelarCadastro() {
        finish();
    }

    private boolean validarCampos() {
        String campo = "";

        if (mNome.getText().toString().equals("")) {
            campo = "Nome";
            mNome.requestFocus();
        } else if (mRua.getText().toString().equals("")) {
            campo = "Rua";
            mRua.requestFocus();
        } else if (mBairro.getText().toString().equals("")) {
            campo = "Bairro";
            mBairro.requestFocus();
        } else if (mCidade.getText().toString().equals("")) {
            campo = "Cidade";
            mCidade.requestFocus();
        } else if (mFone1DDD.getText().toString().equals("")) {
            campo = "DDD do Fone 1";
            mFone1DDD.requestFocus();
        } else if (mFone1Parte1.getText().toString().equals("")) {
            campo = "Primeira parte do Fone 1";
            mFone1Parte1.requestFocus();
        } else if (mFone1Parte2.getText().toString().equals("")) {
            campo = "Segunda parte do Fone 1";
            mFone1Parte2.requestFocus();
        }

        if (campo.equals("")) {
            return true;
        } else {
            AlertDialog dialog = new AlertDialog.Builder(CadastroDeClienteActivity.this)
                    .setTitle("Alerta")
                    .setMessage("É necessário informar o campo '" + campo + "'")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    })
                    .setIcon(R.drawable.caution)
                    .create();
            dialog.show();

            return false;
        }
    }
}
