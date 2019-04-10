package br.com.andrewesteves.travelling.adaptadores;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.modelos.basicas.Foto;

public class FotoImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Foto> fotos;

    public FotoImageAdapter(Context context, ArrayList<Foto> fotos) {
        this.context = context;
        this.fotos = fotos;
    }

    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //imageView.setImageBitmap(BitmapFactory.decodeFile(fotos.get(position).getCaminho()));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(fotos.get(position).getImagem(), 0, fotos.get(position).getImagem().length));
        return imageView;
    }
}
