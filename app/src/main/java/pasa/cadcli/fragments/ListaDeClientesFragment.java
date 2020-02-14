package pasa.cadcli.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pasa.cadcli.R;
import pasa.cadcli.activity.CadastroDeClienteActivity;
import pasa.cadcli.adapters.ClienteAdapter;
import pasa.cadcli.database.ClienteDados;
import pasa.cadcli.entities.Cliente;

public class ListaDeClientesFragment extends Fragment {

    private boolean mExcluirClientes = false;
    private static final int INTERVALO_DE_BUSCA_DE_CLIENTES = 7;
    private int mQuantidadeDeClientesParaBuscar = 0;
    private int mNumeroTotalDeClientes = 0;
    private int mTotalDeItensNoListView = 0;
    private int mUltimaPosicao = -1;

    //private ArrayList<Cliente> mArrayListClientes;
    //private ListView mListViewClientes;
    //private ClienteDados mClienteDados;

    public ListaDeClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mQuantidadeDeClientesParaBuscar = INTERVALO_DE_BUSCA_DE_CLIENTES;

        View view = inflater.inflate(R.layout.fragment_lista_de_clientes, container, false);

        final ClienteDados clienteDados = new ClienteDados(getContext());
        //mClienteDados = new ClienteDados(getContext());

        mNumeroTotalDeClientes = clienteDados.numeroTotalDeClientes();

        final ListView listViewClientes = (ListView) view.findViewById(R.id.listViewListaDeClientes);
        //mListViewClientes = (ListView) view.findViewById(R.id.listViewListaDeClientes);

        final ArrayList<Cliente> arrayListClientes = clienteDados.buscarPrimeirosXClientes(mQuantidadeDeClientesParaBuscar);
        //mArrayListClientes = mClienteDados.buscarPrimeirosXClientes(mQuantidadeDeClientesParaBuscar);

        Button buttonExcluir = (Button) view.findViewById(R.id.buttonExcluir);
        Button buttonCancelar = (Button) view.findViewById(R.id.buttonCancelar);

        LinearLayout linearLayoutBotoes = (LinearLayout) view.findViewById(R.id.LinearLayoutBotoes);
        if (mExcluirClientes) {
            linearLayoutBotoes.setVisibility(View.VISIBLE);
        } else{
            linearLayoutBotoes.setVisibility(View.GONE);
        }

        final ArrayAdapter arrayAdapterClientes = new ClienteAdapter(getContext(), arrayListClientes, mExcluirClientes);
        //mArrayAdapterClientes = new ClienteAdapter(getContext(), mArrayListClientes, mExcluirClientes);
        listViewClientes.setAdapter(arrayAdapterClientes);
        //mListViewClientes.setAdapter(arrayAdapterClientes);

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mExcluirClientes) {
                    Intent intent = new Intent(getContext().getApplicationContext(), CadastroDeClienteActivity.class);

                    Bundle bundleDados = new Bundle();
                    bundleDados.putSerializable("cliente", arrayListClientes.get(i));
                    intent.putExtras(bundleDados);

                    startActivity(intent);
                }
            }
        });

        listViewClientes.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                if (mTotalDeItensNoListView < mNumeroTotalDeClientes) {
//                    if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                        mQuantidadeDeClientesParaBuscar = mQuantidadeDeClientesParaBuscar + INTERVALO_DE_BUSCA_DE_CLIENTES;
//
//                        final ArrayList<Cliente> arrayListClientes = clienteDados.buscarPrimeirosXClientes(mQuantidadeDeClientesParaBuscar);
//                        final ArrayAdapter arrayAdapterClientes = new ClienteAdapter(getContext(), arrayListClientes, mExcluirClientes);
//                        listViewClientes.setAdapter(arrayAdapterClientes);
//
//                        //Toast.makeText(getContext(), Integer.toString(mQuantidadeDeClientesParaBuscar), Toast.LENGTH_SHORT).show();
//                    }
//                }
            }

            @Override
            public void onScroll(AbsListView absListView, int primeiroItemVisivel, int totalDeItensVisiveis, int totalDeItens) {
                //mTotalDeItensNoListView = totalDeItens;

                if ((primeiroItemVisivel + totalDeItensVisiveis) == totalDeItens) {
                    if (totalDeItens < mNumeroTotalDeClientes) {
                        mQuantidadeDeClientesParaBuscar = mQuantidadeDeClientesParaBuscar + INTERVALO_DE_BUSCA_DE_CLIENTES;

                        final ArrayList<Cliente> arrayListClientes = clienteDados.buscarPrimeirosXClientes(mQuantidadeDeClientesParaBuscar);
                        final ArrayAdapter arrayAdapterClientes = new ClienteAdapter(getContext(), arrayListClientes, mExcluirClientes);
                        listViewClientes.setAdapter(arrayAdapterClientes);

                        Toast.makeText(getContext(), Integer.toString(mQuantidadeDeClientesParaBuscar), Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getContext(), Integer.toString(primeiroItemVisivel), Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getContext(), Integer.toString(primeiroItemVisivel), Toast.LENGTH_SHORT).show();

                //mUltimaPosicao
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean existemClientesMarcadso = false;

                for (int i = 0; i < arrayListClientes.size(); i++) {
                    if (arrayListClientes.get(i).isMarcado()) {
                        existemClientesMarcadso = true;
                        break;
                    }
                }

                if (existemClientesMarcadso) {
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Alerta")
                            .setMessage("Você tem certeza que deseja excluir os clientes selecionados?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    for (int i = 0; i < arrayListClientes.size(); i++) {
                                        if (arrayListClientes.get(i).isMarcado()){
                                            clienteDados.excluirCliente(arrayListClientes.get(i));
                                        }
                                    }

                                    if (arrayListClientes.size() == 1) {
                                        CancelarExclusao();
                                    }
                                    else {
                                        AtualizarListaAposExclusao();
                                    }

                                    Toast.makeText(
                                            getContext(),
                                            "Clientes selecionados excluídos com sucesso",
                                            Toast.LENGTH_SHORT
                                    ).show();
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
                else {
                    Toast.makeText(
                            getContext(),
                            "Você precisa selecionar um ou mais clientes para excluir",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelarExclusao();
            }
        });

        return view;
    }

    private void CancelarExclusao() {
        ListaDeClientesFragment listViewClientesFragment = new ListaDeClientesFragment();
        listViewClientesFragment.setExcluirClientes(!mExcluirClientes);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.linearLayoutPrincipal, listViewClientesFragment)
                .commit();
    }

    private void AtualizarListaAposExclusao() {
        getFragmentManager()
                .beginTransaction()
                .detach(ListaDeClientesFragment.this)
                .attach(ListaDeClientesFragment.this)
                .commit();
    }

    public void setExcluirClientes(boolean excluirClientes) {
        this.mExcluirClientes = excluirClientes;
    }
}
