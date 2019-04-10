package br.com.andrewesteves.travelling;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import br.com.andrewesteves.travelling.adaptadores.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    Toolbar appToolbar;
    Button adicionarViagemAM;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPagerAc;
    ViewPagerAdapter viewPagerAdapter;
    String dispositivo;
    Button lugaresViagemAm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Travelling");

        this.dispositivo = getString(R.string.aparelho);

        if(this.dispositivo.equals("smartphone")) {
            this.viewPagerAc = (ViewPager) findViewById(R.id.view_pager_ac);
            this.viewPagerAdapter = new ViewPagerAdapter(this);
            this.viewPagerAc.setAdapter(this.viewPagerAdapter);
        }else if(this.dispositivo.equals("tablet7") || this.dispositivo.equals("tablet10")) {
            this.lugaresViagemAm = (Button) findViewById(R.id.lugares_viagem_am);
            this.lugaresViagemAm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), LugaresActivity.class);
                    startActivity(intent);
                }
            });
        }

        this.adicionarViagemAM = (Button) findViewById(R.id.adicionar_viagem_am);
        this.adicionarViagemAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViagensAdicionarActivity.class);
                startActivity(intent);
            }
        });

        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navegacao_rodape);
        this.bottomNavigationView.setSelectedItemId(R.id.menu_inicio);
        Navegacao.iniciar(getApplicationContext(), this.bottomNavigationView);
    }
}
