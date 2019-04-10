package br.com.andrewesteves.travelling;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;
import br.com.andrewesteves.travelling.modelos.repositorios.RoteiroDados;

public class RoteirosListarActivity extends AppCompatActivity {

    private Toolbar appToolbar;
    private BottomNavigationView bottomNavigationView;
    private TextView tituloViagemArl;
    private FloatingActionButton adicionarFloatButtonArl;
    private Viagem viagem;
    private ArrayList<Roteiro> roteiros;
    private ListView listarListviewArl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiros_listar);

        this.viagem = getIntent().getExtras().getParcelable("viagem");
        this.roteiros = new ArrayList<>();

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Meus roteiros");
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

        this.adicionarFloatButtonArl = (FloatingActionButton) findViewById(R.id.adicionar_floatbutton_arl);
        this.adicionarFloatButtonArl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoteirosAdicionarActivity.class);
                intent.putExtra("viagem", viagem);
                startActivity(intent);
            }
        });

        this.tituloViagemArl = (TextView) findViewById(R.id.titulo_viagem_arl);
        this.tituloViagemArl.setText(this.viagem.getTitulo());

        this.listar();
    }

    private void listar() {
        RoteiroDados roteiroDados = new RoteiroDados(this);
        for(Roteiro r : roteiroDados.listarViagem(Integer.toString(this.viagem.getId()))) {
            this.roteiros.add(r);
        }
        ArrayAdapter adapter = new ArrayAdapter<Roteiro>(this, R.layout.listview_row, this.roteiros);
        this.listarListviewArl = (ListView) findViewById(R.id.listar_listview_arl);
        this.listarListviewArl.setAdapter(adapter);
        this.listarListviewArl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    Roteiro roteiro = roteiros.get(position);
                    Intent intent = new Intent(view.getContext(), RoteirosEditarActivity.class);
                    intent.putExtra("roteiro", roteiro);
                    startActivity(intent);
                }catch(Exception e) {
                    Log.d("DEBUGGER", e.getMessage());
                }
            }
        });
    }
}
