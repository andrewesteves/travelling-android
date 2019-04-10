package br.com.andrewesteves.travelling.modelos.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.andrewesteves.travelling.modelos.BancoSQLite;
import br.com.andrewesteves.travelling.modelos.basicas.Roteiro;
import br.com.andrewesteves.travelling.modelos.basicas.Viagem;

public class RoteiroDados extends BancoSQLite implements IDados<Roteiro> {

    private static final String TABELA    = "roteiro";
    private static final String VIAGEM_ID = "viagem_id";
    private static final String LOCAL     = "local";
    private static final String DIA       = "dia";
    private static final String DESCRICAO = "descricao";
    private Context context;

    public RoteiroDados(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ArrayList<Roteiro> listar() {
        ArrayList<Roteiro> roteiros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " ORDER BY id DESC", null);
        if(cursor.moveToFirst()) {
            do {
                Roteiro roteiro = new Roteiro();
                ViagemDados viagemDados = new ViagemDados(this.context);
                Viagem viagem = viagemDados.unico(cursor.getInt(cursor.getColumnIndex(VIAGEM_ID)));

                roteiro.setId(cursor.getInt(cursor.getColumnIndex("id")));
                roteiro.setViagem(viagem);
                roteiro.setLocal(cursor.getString(cursor.getColumnIndex(LOCAL)));
                roteiro.setDia(cursor.getString(cursor.getColumnIndex(DIA)));
                roteiro.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

                roteiros.add(roteiro);
            }while(cursor.moveToNext());
        }
        return roteiros;
    }

    @Override
    public Roteiro unico(int id) {
        ArrayList<Roteiro> roteiros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " WHERE id = ? ", new String[] { Integer.toString(id) });
        Roteiro roteiro = new Roteiro();
        if(cursor.moveToFirst()) {
            do{
                RoteiroDados roteiroDados = new RoteiroDados(this.context);
                ViagemDados viagemDados = new ViagemDados(this.context);
                Viagem viagem = viagemDados.unico(cursor.getInt(cursor.getColumnIndex(VIAGEM_ID)));

                roteiro.setId(cursor.getInt(cursor.getColumnIndex("id")));
                roteiro.setViagem(viagem);
                roteiro.setLocal(cursor.getString(cursor.getColumnIndex(LOCAL)));
                roteiro.setDia(cursor.getString(cursor.getColumnIndex(DIA)));
                roteiro.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
            }while(cursor.moveToNext());
        }
        return roteiro;
    }

    @Override
    public boolean inserir(Roteiro roteiro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIAGEM_ID, roteiro.getViagem().getId());
        contentValues.put(LOCAL, roteiro.getLocal());
        contentValues.put(DIA, roteiro.getDia());
        contentValues.put(DESCRICAO, roteiro.getDescricao());
        return db.insert(TABELA, null, contentValues) > 0;
    }

    @Override
    public boolean atualizar(Roteiro roteiro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIAGEM_ID, roteiro.getViagem().getId());
        contentValues.put(LOCAL, roteiro.getLocal());
        contentValues.put(DIA, roteiro.getDia());
        contentValues.put(DESCRICAO, roteiro.getDescricao());
        return db.update(TABELA, contentValues, " id = ? ", new String[] { Integer.toString(roteiro.getId()) }) > 0;
    }

    @Override
    public boolean remover(Roteiro roteiro) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABELA, "id = ? ", new String[] { Integer.toString(roteiro.getId()) }) > 0;
    }

    public ArrayList<Roteiro> listarViagem(String where) {
        ArrayList<Roteiro> roteiros = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + " WHERE viagem_id = ? ORDER BY " + DIA + " DESC", new String[] { where });
        if(cursor.moveToFirst()) {
            do {
                Roteiro roteiro = new Roteiro();
                ViagemDados viagemDados = new ViagemDados(this.context);
                Viagem viagem = viagemDados.unico(cursor.getInt(cursor.getColumnIndex(VIAGEM_ID)));

                roteiro.setId(cursor.getInt(cursor.getColumnIndex("id")));
                roteiro.setViagem(viagem);
                roteiro.setLocal(cursor.getString(cursor.getColumnIndex(LOCAL)));
                roteiro.setDia(cursor.getString(cursor.getColumnIndex(DIA)));
                roteiro.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));

                roteiros.add(roteiro);
            }while(cursor.moveToNext());
        }
        return roteiros;
    }
}
