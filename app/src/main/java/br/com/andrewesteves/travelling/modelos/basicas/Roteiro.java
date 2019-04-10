package br.com.andrewesteves.travelling.modelos.basicas;

import android.os.Parcel;
import android.os.Parcelable;

public class Roteiro implements Parcelable {
    private int id;
    private Viagem viagem;
    private String local;
    private String dia;
    private String descricao;

    public Roteiro() {}

    protected Roteiro(Parcel in) {
        id = in.readInt();
        viagem = in.readParcelable(Viagem.class.getClassLoader());
        local = in.readString();
        dia = in.readString();
        descricao = in.readString();
    }

    public static final Creator<Roteiro> CREATOR = new Creator<Roteiro>() {
        @Override
        public Roteiro createFromParcel(Parcel in) {
            return new Roteiro(in);
        }

        @Override
        public Roteiro[] newArray(int size) {
            return new Roteiro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.getDia() + "  " + this.getLocal();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(viagem, flags);
        dest.writeString(local);
        dest.writeString(dia);
        dest.writeString(descricao);
    }
}
