package br.com.andrewesteves.travelling.modelos.basicas;

import android.os.Parcel;
import android.os.Parcelable;

public class Viagem implements Parcelable {

    private int id;
    private String titulo;

    private String partida;
    private String chegada;
    private String descricao;

    public Viagem() {}

    public Viagem(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        partida = in.readString();
        chegada = in.readString();
        descricao = in.readString();
    }

    public static final Creator<Viagem> CREATOR = new Creator<Viagem>() {
        @Override
        public Viagem createFromParcel(Parcel in) {
            return new Viagem(in);
        }

        @Override
        public Viagem[] newArray(int size) {
            return new Viagem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getChegada() {
        return chegada;
    }

    public void setChegada(String chegada) {
        this.chegada = chegada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.getTitulo() + "\n" + this.getPartida() + " até " + this.getChegada();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(partida);
        dest.writeString(chegada);
        dest.writeString(descricao);
    }
}
