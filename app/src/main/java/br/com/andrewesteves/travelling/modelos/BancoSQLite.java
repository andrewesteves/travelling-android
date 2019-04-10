package br.com.andrewesteves.travelling.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSQLite extends SQLiteOpenHelper {
    private static final String NOME = "travelling.db";
    private static final int VERSAO  = 8;

    public BancoSQLite(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE viagem (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR (255) NOT NULL, partida DATETIME, chegada DATETIME, descricao TEXT)");
        db.execSQL("CREATE TABLE foto (id INTEGER PRIMARY KEY AUTOINCREMENT, viagem_id INTEGER, imagem BLOB, caminho TEXT, FOREIGN KEY(viagem_id) REFERENCES viagem(id))");
        db.execSQL("CREATE TABLE roteiro (id INTEGER PRIMARY KEY AUTOINCREMENT, viagem_id INTEGER, local TEXT, dia VARCHAR(255), descricao TEXT, FOREIGN KEY(viagem_id) REFERENCES viagem(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS viagem");
        db.execSQL("DROP TABLE IF EXISTS foto");
        db.execSQL("DROP TABLE IF EXISTS roteiro");
        onCreate(db);
    }
}
