package pasa.cadcli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pasa.cadcli.activity.CadastroDeClienteActivity;
import pasa.cadcli.fragments.ConfiguracoesFragment;
import pasa.cadcli.fragments.ListaDeClientesFragment;
import pasa.cadcli.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        abrirListaDeClientes(false);
    }

    public void abrirListaDeClientes(boolean excluirClientes) {
        ListaDeClientesFragment listaDeClientesFragment = new ListaDeClientesFragment();
        listaDeClientesFragment.setExcluirClientes(excluirClientes);
        getSupportActionBar().setSubtitle(Util.getTiutloListaDeClientes());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linearLayoutPrincipal, listaDeClientesFragment)
                //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                //.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                //.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left)
                .show(listaDeClientesFragment)
                .commit();
    }

    private void abrirCadastroDeClientes() {
        Intent intent = new Intent(MainActivity.this, CadastroDeClienteActivity.class);
        startActivity(intent);
    }

    public void abrirConfiguracoes() {
        getSupportActionBar().setSubtitle(Util.getTiutloConfiguracoes());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.linearLayoutPrincipal, new ConfiguracoesFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cadastro_de_clientes) {
            abrirCadastroDeClientes();
            return true;
        }
        else
        if (id == R.id.action_home) {
            abrirListaDeClientes(false);
            return true;
        }
        else
        if (id == R.id.action_configuracoes) {
            abrirConfiguracoes();
            return true;
        }
        else
        if (id == R.id.action_excluir_clientes) {
            abrirListaDeClientes(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
