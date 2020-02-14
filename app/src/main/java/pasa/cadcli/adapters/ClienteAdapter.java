package pasa.cadcli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import pasa.cadcli.R;
import pasa.cadcli.entities.Cliente;

public class ClienteAdapter extends ArrayAdapter<Cliente> {

    private final Context mContext;
    private final ArrayList<Cliente> mElementos;
    private boolean mExcluirClientes = false;

    public ClienteAdapter(Context context, ArrayList<Cliente> elementos, boolean excluirClientes) {
        super(context, R.layout.fragment_lista_de_clientes, elementos);
        this.mContext = context;
        this.mElementos = elementos;
        this.mExcluirClientes = excluirClientes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.linha_da_lista_de_clientes, parent, false);

        TextView codigoDoCliente = (TextView) rowView.findViewById(R.id.textViewCodigo);
        codigoDoCliente.setText(Integer.toString(mElementos.get(position).getCodigo()));

        TextView nomeDoCliente = (TextView) rowView.findViewById(R.id.textViewNome);
        nomeDoCliente.setText(mElementos.get(position).getNome());

        final CheckBox checkBoxCliente = (CheckBox) rowView.findViewById(R.id.checkBoxCliente);
        if (mExcluirClientes) {
            checkBoxCliente.setVisibility(View.VISIBLE);
        } else {
            checkBoxCliente.setVisibility(View.GONE);
        }

        if (mExcluirClientes) {
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBoxCliente.performClick();
                }
            });

            checkBoxCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mElementos.get(position).setMarcado(checkBoxCliente.isChecked());
                }
            });
        }

        return rowView;
    }
}
