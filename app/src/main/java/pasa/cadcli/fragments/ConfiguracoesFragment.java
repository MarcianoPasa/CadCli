package pasa.cadcli.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import pasa.cadcli.R;
import pasa.cadcli.database.ConfiguracaoDados;
import pasa.cadcli.util.Util;
import pasa.cadcli.util.UtilDatabase;

import static pasa.cadcli.R.id.editTextCidade;
import static pasa.cadcli.R.id.spinnerUFs;

public class ConfiguracoesFragment extends Fragment {
    private Spinner mUF;
    private EditText mCidadePadrao;
    private Util mUtil;

    public ConfiguracoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        mUtil = new Util();

        final Spinner spinnerUFs = (Spinner) view.findViewById(R.id.spinnerUFs);
        ArrayAdapter arrayAdapterUFs = new ArrayAdapter(getContext().getApplicationContext(), R.layout.spinner_item, mUtil.getListaDeUFs());
        spinnerUFs.setAdapter(arrayAdapterUFs);
        spinnerUFs.getBackground().setColorFilter(Color.parseColor("#00cc66"), PorterDuff.Mode.SRC_ATOP);

        atribuirCamposDaTelaAosAtributos(view);
        atribuirCamposDoBancoDeDadosAosAtributos();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        salvarConfiguracoes();
    }

    private void atribuirCamposDaTelaAosAtributos(View view) {
        mUF = (Spinner) view.findViewById(spinnerUFs);
        mCidadePadrao = (EditText) view.findViewById(editTextCidade);
    }

    private void atribuirCamposDoBancoDeDadosAosAtributos() {
        ConfiguracaoDados configuracaoDados = new ConfiguracaoDados(getContext());

        mUF.setSelection(mUtil.pegarPosicaoDaUF(
                configuracaoDados.getValorDaConfiguracao(
                        UtilDatabase.getModuloClientes(),
                        UtilDatabase.getConfiguracaoClientesUfPadrao()
                )
        ));

        mCidadePadrao.setText(
                configuracaoDados.getValorDaConfiguracao(
                        UtilDatabase.getModuloClientes(),
                        UtilDatabase.getConfiguracaoClientesCidadePadrao()
                )
        );
    }

    private void salvarConfiguracoes() {
        ConfiguracaoDados configuracaoDados = new ConfiguracaoDados(getContext());

        configuracaoDados.atualizarConfiguracao(
                UtilDatabase.getModuloClientes(),
                UtilDatabase.getConfiguracaoClientesUfPadrao(),
                mUF.getSelectedItem().toString()
        );

        configuracaoDados.atualizarConfiguracao(
                UtilDatabase.getModuloClientes(),
                UtilDatabase.getConfiguracaoClientesCidadePadrao(),
                mCidadePadrao.getText().toString()
        );
    }
}
