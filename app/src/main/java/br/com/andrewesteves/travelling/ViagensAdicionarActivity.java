package br.com.andrewesteves.travelling;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.andrewesteves.travelling.modelos.basicas.Viagem;
import br.com.andrewesteves.travelling.modelos.repositorios.ViagemDados;

public class ViagensAdicionarActivity extends AppCompatActivity {

    Toolbar appToolbar;
    EditText tituloEditTextAva;;
    EditText partidaEditTextAva;
    EditText chegadaEditTextAva;
    EditText descricaoEditTextAva;
    Button adicionarButtonAva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens_adicionar);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Adicionar viagem");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);

        this.tituloEditTextAva = (EditText) findViewById(R.id.titulo_edittext_ava);
        this.partidaEditTextAva = (EditText) findViewById(R.id.partida_edittext_ava);
        this.chegadaEditTextAva = (EditText) findViewById(R.id.chegada_edittext_ava);
        this.descricaoEditTextAva = (EditText) findViewById(R.id.descricao_edittext_ava);
        this.adicionarButtonAva = (Button) findViewById(R.id.adicionar_button_ava);

        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.adicionarButtonAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tituloEditTextAva.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de título");
                }else if(partidaEditTextAva.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de partida");
                }else if(chegadaEditTextAva.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de chegada");
                }else if(descricaoEditTextAva.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de descrição");
                }else {
                    Viagem viagem = new Viagem();
                    viagem.setTitulo(tituloEditTextAva.getText().toString());
                    viagem.setPartida(partidaEditTextAva.getText().toString());
                    viagem.setChegada(chegadaEditTextAva.getText().toString());
                    viagem.setDescricao(descricaoEditTextAva.getText().toString());

                    ViagemDados viagemDados = new ViagemDados(view.getContext());
                    viagemDados.inserir(viagem);

                    Toast.makeText(ViagensAdicionarActivity.this, "Viagem inserida com sucesso",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), ViagensListarActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void toast(String mensagem) {
        Toast toast = Toast.makeText(ViagensAdicionarActivity.this, mensagem, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}
