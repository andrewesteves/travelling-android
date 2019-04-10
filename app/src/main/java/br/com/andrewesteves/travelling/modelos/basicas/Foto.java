package br.com.andrewesteves.travelling.modelos.basicas;

import android.os.Parcel;
import android.os.Parcelable;

public class Foto implements Parcelable {
    private int id;
    private byte[] imagem;
    private Viagem viagem;
    private String caminho;

    public Foto() {}

    public Foto(Parcel in) {
        id = in.readInt();
        imagem = in.createByteArray();
        viagem = in.readParcelable(Viagem.class.getClassLoader());
        setCaminho(in.readString());
    }

    public static final Creator<Foto> CREATOR = new Creator<Foto>() {
        @Override
        public Foto createFromParcel(Parcel in) {
            return new Foto(in);
        }

        @Override
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByteArray(imagem);
        dest.writeParcelable(viagem, flags);
        dest.writeString(getCaminho());
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
