package br.com.andrewesteves.travelling;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.adaptadores.FotoImageAdapter;
import br.com.andrewesteves.travelling.modelos.basicas.Foto;
import br.com.andrewesteves.travelling.modelos.repositorios.FotoDados;

public class FotosActivity extends AppCompatActivity {

    Toolbar appToolbar;
    GridView fotosGridviewAf;
    ArrayList<Foto> fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Fotos");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);
        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FotoDados fotoDados = new FotoDados(this);
        this.fotos = fotoDados.listar();
        this.fotosGridviewAf = (GridView) findViewById(R.id.fotos_gridview_af);
        this.fotosGridviewAf.setAdapter(new FotoImageAdapter(this, this.fotos));

        this.fotosGridviewAf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                remover(view, i);
            }
        });
    }

    private void remover(final View view, final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Deseja realmente remover a foto?").setTitle("Atenção");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    FotoDados fotosDados = new FotoDados(FotosActivity.this);
                    fotosDados.remover(fotosDados.unico(fotos.get(i).getId()));
                    Intent intent = new Intent(view.getContext(), ViagensListarActivity.class);
                    startActivity(intent);
                }catch(Exception e) {
                    Toast.makeText(FotosActivity.this, "Opss, não foi possível remover a foto", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
