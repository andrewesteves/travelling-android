package br.com.andrewesteves.travelling;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;

public class RoteirosEditarActivity extends AppCompatActivity {

    private Toolbar appToolBar;
    private Roteiro roteiro;
    private Button selecionarFragementoAre;
    private int selecionaFragmento = 1; // ÍMPAR é o formulário e PAR o mapa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiros_editar);

        this.roteiro = getIntent().getExtras().getParcelable("roteiro");

        this.appToolBar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolBar.setTitle("Detalhe de roteiro");
        this.appToolBar.setNavigationIcon(R.drawable.ic_back_black_24dp);

        this.appToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putParcelable("roteiro", this.roteiro);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapaFragment mapaFragment = new MapaFragment();
        mapaFragment.setArguments(bundle);
        transaction.add(R.id.frame_are, mapaFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        this.selecionarFragementoAre = (Button) findViewById(R.id.selecionar_fragemento_are);
        this.selecionarFragementoAre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selecionaFragmento % 2 == 0) {
                    selecionarFragementoAre.setText("Edição");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("roteiro", roteiro);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    MapaFragment mapaFragment = new MapaFragment();
                    mapaFragment.setArguments(bundle);
                    transaction.replace(R.id.frame_are, mapaFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    selecionarFragementoAre.setText("Mapa");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("roteiro", roteiro);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    RoteirosEditarFragment roteirosEditarFragment = new RoteirosEditarFragment();
                    roteirosEditarFragment.setArguments(bundle);
                    transaction.replace(R.id.frame_are, roteirosEditarFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                selecionaFragmento++;
            }
        });
    }
}
