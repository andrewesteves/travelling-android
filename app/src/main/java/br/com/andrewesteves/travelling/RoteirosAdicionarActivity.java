package br.com.andrewesteves.travelling;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;
import br.com.andrewesteves.travelling.modelos.repositorios.RoteiroDados;

public class RoteirosAdicionarActivity extends AppCompatActivity {

    private Toolbar appToolbar;
    private TextView tituloViagemAra;
    private EditText localEdittextAra;
    private EditText diaEdittextAra;
    private EditText descricaoEdittextAra;
    private Button adicionarButtonAra;
    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiros_adicionar);

        this.viagem = getIntent().getExtras().getParcelable("viagem");
        this.tituloViagemAra = (TextView) findViewById(R.id.titulo_viagem_ara);
        this.tituloViagemAra.setText(this.viagem.getTitulo());

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Adicionar roteiro");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);

        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.localEdittextAra = (EditText) findViewById(R.id.local_edittext_ara);
        this.diaEdittextAra = (EditText) findViewById(R.id.dia_edittext_ara);
        this.descricaoEdittextAra = (EditText) findViewById(R.id.descricao_edittext_ara);
        this.adicionarButtonAra = (Button) findViewById(R.id.adicionar_button_ara);

        this.adicionarButtonAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(localEdittextAra.getText().toString().trim().equals("")) {
                        toast("Preencha o campo de local");
                    }else if(diaEdittextAra.getText().toString().equals("")) {
                        toast("Preencha o campo de dia");
                    }else if(descricaoEdittextAra.getText().toString().trim().equals("")) {
                        toast("Preencha o campo de descrição");
                    }else {
                        Roteiro roteiro = new Roteiro();
                        roteiro.setLocal(localEdittextAra.getText().toString());
                        roteiro.setViagem(viagem);
                        roteiro.setDia(diaEdittextAra.getText().toString());
                        roteiro.setDescricao(descricaoEdittextAra.getText().toString());

                        RoteiroDados roteiroDados = new RoteiroDados(view.getContext());
                        roteiroDados.inserir(roteiro);
                        Toast.makeText(RoteirosAdicionarActivity.this, "Roteiro inserido com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), RoteirosListarActivity.class);
                        intent.putExtra("viagem", viagem);
                        startActivity(intent);
                    }
                }catch(Exception e) {
                    Log.d("DEBUGGER", e.getMessage());
                }
            }
        });
    }

    private void toast(String mensagem) {
        Toast toast = Toast.makeText(RoteirosAdicionarActivity.this, mensagem, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}
