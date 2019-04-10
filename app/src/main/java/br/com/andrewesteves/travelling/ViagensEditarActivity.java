package br.com.andrewesteves.travelling;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.andrewesteves.travelling.modelos.basicas.Viagem;
import br.com.andrewesteves.travelling.modelos.repositorios.ViagemDados;

public class ViagensEditarActivity extends AppCompatActivity {

    Toolbar appToolbar;
    EditText tituloEditTextAve;;
    EditText partidaEditTextAve;
    EditText chegadaEditTextAve;
    EditText descricaoEditTextAve;
    Button atualizarButtonAve;
    Button removerButtonAve;
    Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagens_editar);

        this.viagem = getIntent().getExtras().getParcelable("viagem");
        this.tituloEditTextAve = (EditText) findViewById(R.id.titulo_edittext_ave);
        this.partidaEditTextAve = (EditText) findViewById(R.id.partida_edittext_ave);
        this.chegadaEditTextAve = (EditText) findViewById(R.id.chegada_edittext_ave);
        this.descricaoEditTextAve = (EditText) findViewById(R.id.descricao_edittext_ave);
        this.atualizarButtonAve = (Button) findViewById(R.id.atualizar_button_ave);
        this.removerButtonAve = (Button) findViewById(R.id.remover_button_ave);

        this.appToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        this.appToolbar.setTitle("Editar viagem");
        this.appToolbar.setNavigationIcon(R.drawable.ic_back_black_24dp);
        this.appToolbar.inflateMenu(R.menu.menu_viagens);
        this.appToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_viagens_fotos:
                        intent = new Intent(getApplicationContext(), FotosAdicionarActivity
                                .class);
                        startActivity(intent);
                        break;
                    case R.id.menu_viagens_roteiro:
                        intent = new Intent(getApplicationContext(), RoteirosListarActivity.class);
                        intent.putExtra("viagem", viagem);
                        startActivity(intent);
                        break;

                    case R.id.menu_viagens_lugares:
                        intent = new Intent(getApplicationContext(), LugaresActivity.class);
                        startActivity(intent);
                }
                return false;
            }
        });
        this.appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.tituloEditTextAve.setText(viagem.getTitulo());
        this.partidaEditTextAve.setText(viagem.getPartida());
        this.chegadaEditTextAve.setText(viagem.getChegada());
        this.descricaoEditTextAve.setText(viagem.getDescricao());

        this.atualizarButtonAve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tituloEditTextAve.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de título");
                }else if(partidaEditTextAve.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de partida");
                }else if(chegadaEditTextAve.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de chegada");
                }else if(descricaoEditTextAve.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de descrição");
                }else {
                    ViagemDados viagemDados = new ViagemDados(ViagensEditarActivity.this);
                    viagem.setTitulo(tituloEditTextAve.getText().toString());
                    viagem.setPartida(partidaEditTextAve.getText().toString());
                    viagem.setChegada(chegadaEditTextAve.getText().toString());
                    viagem.setDescricao(descricaoEditTextAve.getText().toString());
                    viagemDados.atualizar(viagem);
                    Toast.makeText(getApplicationContext(), "Viagem atualizada com sucesso", Toast
                            .LENGTH_SHORT).show();
                }
            }
        });

        this.removerButtonAve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remover(view);
            }
        });
    }

    private void remover(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Deseja realmente remover?")
               .setTitle("Atenção");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ViagemDados viagemDados = new ViagemDados(ViagensEditarActivity.this);
                    viagemDados.remover(viagem);
                    Intent intent = new Intent(getApplicationContext(), ViagensListarActivity.class);
                    startActivity(intent);
                    Toast.makeText(ViagensEditarActivity.this, "Viagem removida com sucesso",
                            Toast.LENGTH_SHORT).show();
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void toast(String mensagem) {
        Toast toast = Toast.makeText(ViagensEditarActivity.this, mensagem, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}
