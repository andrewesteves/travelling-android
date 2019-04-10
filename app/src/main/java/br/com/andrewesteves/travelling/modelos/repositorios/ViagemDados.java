package br.com.andrewesteves.travelling.modelos.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.modelos.BancoSQLite;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;

public class ViagemDados extends BancoSQLite implements IDados<Viagem> {

    private static final String TABELA    = "viagem";
    private static final String TITULO    = "titulo";
    private static final String PARTIDA   = "partida";
    private static final String CHEGADA   = "chegada";
    private static final String DESCRICAO = "descricao";
    private static final String TABELA_FOTO = "foto";
    private static final String TABELA_ROTEIRO = "roteiro";

    public ViagemDados(Context context) {
        super(context);
    }

    public ArrayList<Viagem> listar() {
        ArrayList<Viagem> viagens = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " ORDER BY partida DESC", null);
        if(cursor.moveToFirst()) {
            do {
                Viagem viagem = new Viagem();
                viagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                viagem.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
                viagem.setPartida(cursor.getString(cursor.getColumnIndex("partida")));
                viagem.setChegada(cursor.getString(cursor.getColumnIndex("chegada")));
                viagem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                viagens.add(viagem);
            }while(cursor.moveToNext());
        }
        return viagens;
    }

    public boolean inserir(Viagem viagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITULO, viagem.getTitulo());
        contentValues.put(PARTIDA, viagem.getPartida());
        contentValues.put(CHEGADA, viagem.getChegada());
        contentValues.put(DESCRICAO, viagem.getDescricao());
        return db.insert(TABELA, null, contentValues) >= 0;
    }

    public boolean atualizar(Viagem viagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", viagem.getId());
        contentValues.put(TITULO, viagem.getTitulo());
        contentValues.put(PARTIDA, viagem.getPartida());
        contentValues.put(CHEGADA, viagem.getChegada());
        contentValues.put(DESCRICAO, viagem.getDescricao());
        return db.update(TABELA, contentValues, "id = ?", new String[] { Integer.toString(viagem
                .getId()) }) > 0;
    }

    public Viagem unico(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " WHERE id = ?", new String[]{
                Integer.toString(id)
        });
        Viagem viagem = new Viagem();
        if(cursor.moveToFirst()) {
            do {
                viagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                viagem.setTitulo(cursor.getString(cursor.getColumnIndex(TITULO)));
                viagem.setPartida(cursor.getString(cursor.getColumnIndex(PARTIDA)));
                viagem.setChegada(cursor.getString(cursor.getColumnIndex(CHEGADA)));
                viagem.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
            }while(cursor.moveToNext());
        }
        return viagem;
    }

    public boolean remover(Viagem viagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_FOTO, "viagem_id = ? ", new String[] { Integer.toString(viagem.getId()) });
        db.delete(TABELA_ROTEIRO, "viagem_id = ? ", new String[] { Integer.toString(viagem.getId()) });
        return db.delete(TABELA, "id = ? ", new String[] { Integer.toString(viagem.getId()) }) > 0;
    }
}
