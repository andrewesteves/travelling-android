package br.com.andrewesteves.travelling;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;
import br.com.andrewesteves.travelling.modelos.repositorios.RoteiroDados;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoteirosEditarFragment extends Fragment {

    View meuLayout;
    Roteiro roteiro;
    private EditText localEdittextFre;
    private EditText diaEdittextFre;
    private EditText descricaoEdittextFre;
    private Button adicionarButtonFre;
    private Button removerButtonFre;
    private Button buscarLugarFre;

    // Required empty public constructor
    public RoteirosEditarFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null) {
            this.roteiro = bundle.getParcelable("roteiro");
        }

        this.meuLayout = inflater.inflate(R.layout.fragment_roteiros_editar, container, false);

        this.localEdittextFre = (EditText) this.meuLayout.findViewById(R.id.local_edittext_fre);
        this.diaEdittextFre = (EditText) this.meuLayout.findViewById(R.id.dia_edittext_fre);
        this.descricaoEdittextFre = (EditText) this.meuLayout.findViewById(R.id.descricao_edittext_fre);

        this.localEdittextFre.setText(this.roteiro.getLocal());
        this.diaEdittextFre.setText(this.roteiro.getDia());
        this.descricaoEdittextFre.setText(this.roteiro.getDescricao());

        this.adicionarButtonFre = (Button) this.meuLayout.findViewById(R.id.adicionar_button_fre);
        this.removerButtonFre = (Button) this.meuLayout.findViewById(R.id.remover_button_fre);
        this.buscarLugarFre = (Button) this.meuLayout.findViewById(R.id.buscar_lugar_fre);

        this.adicionarButtonFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localEdittextFre.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de local");
                }else if(diaEdittextFre.getText().toString().equals("")) {
                    toast("Preencha o campo de dia");
                }else if(descricaoEdittextFre.getText().toString().trim().equals("")) {
                    toast("Preencha o campo de descrição");
                }else {
                    RoteiroDados roteiroDados = new RoteiroDados(getView().getContext());
                    roteiro.setLocal(localEdittextFre.getText().toString());
                    roteiro.setDia(diaEdittextFre.getText().toString());
                    roteiro.setDescricao(descricaoEdittextFre.getText().toString());
                    roteiroDados.atualizar(roteiro);
                    Toast.makeText(getActivity(), "Roteiro atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), RoteirosListarActivity.class);
                    intent.putExtra("viagem", roteiro.getViagem());
                    startActivity(intent);
                }
            }
        });

        this.removerButtonFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remover(v);
            }
        });

        this.buscarLugarFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roteiro.getLocal().trim().equals("")) {
                    toast("Preencha o campo de local");
                }else {
                    Intent intent = new Intent(v.getContext(), LugaresActivity.class);
                    intent.putExtra("local", roteiro.getLocal());
                    startActivity(intent);
                }
            }
        });

        return this.meuLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void remover(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Deseja realmente remover?")
                .setTitle("Atenção");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    RoteiroDados roteiroDados = new RoteiroDados(getView().getContext());
                    roteiroDados.remover(roteiro);
                    Intent intent = new Intent(getView().getContext(), RoteirosListarActivity.class);
                    intent.putExtra("viagem", roteiro.getViagem());
                    startActivity(intent);
                    Toast.makeText(getContext(), "Roteiro removido com sucesso",
                            Toast.LENGTH_SHORT).show();
                }catch(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void toast(String mensagem) {
        Toast toast = Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}
