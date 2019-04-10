package br.com.andrewesteves.travelling;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import br.com.andrewesteves.travelling.modelos.basicas.Lugar;
import br.com.andrewesteves.travelling.modelos.repositorios.LugarJson;

public class LugaresActivity extends AppCompatActivity {

    private Toolbar appToolbar;
    private EditText localEdittextAl;
    private Button buscarButtonAl;
    private String localizacao;
    private ListView listarListviewAl;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Sugestão de lugares");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);

        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.localEdittextAl = (EditText) findViewById(R.id.local_edittext_al);
        this.buscarButtonAl = (Button) findViewById(R.id.buscar_button_al);
        this.listarListviewAl = (ListView) findViewById(R.id.listar_listview_al);

        if(getIntent().hasExtra("local")) {
            this.localEdittextAl.setText(getIntent().getStringExtra("local"));
        }

        this.buscarButtonAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(localEdittextAl.getText().toString().trim().equals("")) {
                        toast("Preencha o campo de local");
                    }else {
                        localizacao = localizacaoEndereco(getApplicationContext(), localEdittextAl.getText().toString());
                        MinhaTask minhaTask = new MinhaTask();
                        minhaTask.execute(localizacao);
                    }
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Endereço inválido, digite novamente.", Toast.LENGTH_LONG).show();
                    Log.d("DEPURAR", e.getMessage());
                }
            }
        });
    }

    public String localizacaoEndereco(Context context, String endereco) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> enderecos;
        String resLatLng = null;
        try{
            enderecos = geocoder.getFromLocationName(endereco, 5);
            if(enderecos == null) {
                throw new Exception("Retorno nulo");
            }
            if(enderecos.size() == 0) {
                throw new Exception("Sem resultados");
            }
            Address localizacao = enderecos.get(0);
            resLatLng = Double.toString(localizacao.getLatitude()) + "," + Double.toString(localizacao.getLongitude());
        }catch(Exception e) {
            Log.d("DEPURAR", e.getMessage());
        }
        return resLatLng;
    }

    private class MinhaTask extends AsyncTask<String, Void, List<Lugar>> {
        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(LugaresActivity.this, "Por favor, Aguarde", "Consultando restaurantes próximos");
        }

        @Override
        protected List<Lugar> doInBackground(String... params) {
            List<Lugar> lugaresRes = new ArrayList<Lugar>();
            LugarJson lugarJson = new LugarJson(params[0]);
            try{
                lugaresRes = lugarJson.listar();
            }catch(Exception e) {
                Log.d("DEPURAR", e.getMessage());
            }
            return lugaresRes;
        }

        @Override
        protected void onPostExecute(List<Lugar> lugares) {
            ArrayAdapter adapter = new ArrayAdapter<Lugar>(getApplicationContext(), R.layout.listview_row, lugares);
            listarListviewAl.setAdapter(adapter);
            load.dismiss();
        }
    }

    private void toast(String mensagem) {
        Toast toast = Toast.makeText(LugaresActivity.this, mensagem, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}
