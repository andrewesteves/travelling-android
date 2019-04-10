package br.com.andrewesteves.travelling.modelos.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.modelos.BancoSQLite;
import br.com.andrewesteves.travelling.modelos.basicas.Foto;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;

public class FotoDados extends BancoSQLite implements IDados<Foto> {

    private static final String TABELA    = "foto";
    private static final String VIAGEM_ID = "viagem_id";
    private static final String IMAGEM    = "imagem";
    private static final String CAMINHO   = "caminho";
    private Context context;

    public FotoDados(Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Foto> listar() {
        ArrayList<Foto> fotos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA, null);
        if(cursor.moveToFirst()) {
            do{
                Foto foto = new Foto();
                ViagemDados viagemDados = new ViagemDados(this.context);
                Viagem viagem = viagemDados.unico(cursor.getInt(cursor.getColumnIndex(VIAGEM_ID)));

                foto.setId(cursor.getInt(cursor.getColumnIndex("id")));
                foto.setViagem(viagem);
                foto.setImagem(cursor.getBlob(cursor.getColumnIndex(IMAGEM)));
                foto.setCaminho(cursor.getString(cursor.getColumnIndex(CAMINHO)));

                fotos.add(foto);
            }while(cursor.moveToNext());
        }
        return fotos;
    }

    public Foto unico(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " WHERE id = ? ", new
                String[]{ Integer.toString(id) });
        Foto foto = new Foto();
        if(cursor.moveToFirst()) {
            do{
                ViagemDados viagemDados = new ViagemDados(this.context);
                Viagem viagem = viagemDados.unico(cursor.getInt(cursor.getColumnIndex(VIAGEM_ID)));
                foto.setId(cursor.getInt(cursor.getColumnIndex("id")));
                foto.setViagem(viagem);
                foto.setImagem(cursor.getBlob(cursor.getColumnIndex(IMAGEM)));
                foto.setCaminho(cursor.getString(cursor.getColumnIndex(CAMINHO)));
            }while(cursor.moveToNext());
        }
        return foto;
    }

    public boolean inserir(Foto foto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIAGEM_ID, foto.getViagem().getId());
        contentValues.put(IMAGEM, foto.getImagem());
        contentValues.put(CAMINHO, foto.getCaminho());
        return db.insert(TABELA, null, contentValues) >= 0;
    }

    public boolean atualizar(Foto foto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIAGEM_ID, foto.getViagem().getId());
        contentValues.put(IMAGEM, foto.getImagem());
        contentValues.put(CAMINHO, foto.getCaminho());
        return db.update(TABELA, contentValues, "id = ? ", new String[] { Integer.toString(foto
                .getId()) }) > 0;
    }

    public boolean remover(Foto foto) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABELA, "id = ? ", new String[] { Integer.toString(foto.getId()) }) > 0;
    }
}
