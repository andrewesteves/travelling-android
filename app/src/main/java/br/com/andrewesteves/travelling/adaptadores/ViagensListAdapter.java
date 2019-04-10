package br.com.andrewesteves.travelling.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.R;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;

public class ViagensListAdapter extends BaseAdapter {
    private ArrayList<Viagem> viagens;
    private LayoutInflater layoutInflater;

    public ViagensListAdapter(Context context, ArrayList<Viagem> viagens) {
        this.viagens = viagens;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return viagens.size();
    }

    @Override
    public Object getItem(int position) {
        return viagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_row_viagens, null);
            holder = new ViewHolder();
            holder.titulo = (TextView) convertView.findViewById(R.id.titulo_listview_layout);
            holder.partida = (TextView) convertView.findViewById(R.id.partida_listview_layout);
            holder.chegada = (TextView) convertView.findViewById(R.id.chegada_listview_layout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titulo.setText(this.viagens.get(position).getTitulo());
        holder.partida.setText("Partida: " + this.viagens.get(position).getPartida());
        holder.chegada.setText("Chegada: " + this.viagens.get(position).getChegada());
        return convertView;
    }

    static class ViewHolder {
        TextView titulo;
        TextView partida;
        TextView chegada;
    }
}
