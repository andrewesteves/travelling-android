package br.com.andrewesteves.travelling;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.adaptadores.ViagensListAdapter;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;
import br.com.andrewesteves.travelling.modelos.repositorios.ViagemDados;

public class ViagensListarActivity extends AppCompatActivity {

    private Toolbar appToolbar;
    private FloatingActionButton adicionarFloatButtonAvl;
    private ArrayList<Viagem> viagens = new ArrayList<>();
    private ListView listarListViewAvl;
    ViagensListAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens_listar);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Minhas viagens");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);

        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navegacao_rodape);
        this.bottomNavigationView.setSelectedItemId(R.id.menu_viagens);
        Navegacao.iniciar(getApplicationContext(), this.bottomNavigationView);

        this.adicionarFloatButtonAvl = (FloatingActionButton) findViewById(R.id
                .adicionar_floatbutton_avl);
        this.adicionarFloatButtonAvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViagensAdicionarActivity.class);
                startActivity(intent);
            }
        });

        this.listar();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        this.carregarViagens();
        this.adapter.notifyDataSetChanged();
    }

    private void carregarViagens() {
        ViagemDados viagemDados = new ViagemDados(ViagensListarActivity.this);
        this.viagens.clear();
        for(Viagem v: viagemDados.listar()) {
            this.viagens.add(v);
        }
    }

    private void listar() {
        this.carregarViagens();

        this.adapter = new ViagensListAdapter(this, viagens);
        this.listarListViewAvl = (ListView) findViewById(R.id.listar_listview_avl);
        this.listarListViewAvl.setAdapter(adapter);

        this.listarListViewAvl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Viagem viagem = viagens.get(position);
                Intent intent = new Intent(view.getContext(), ViagensEditarActivity.class);
                intent.putExtra("viagem", viagem);
                startActivity(intent);
            }
        });
    }
}
