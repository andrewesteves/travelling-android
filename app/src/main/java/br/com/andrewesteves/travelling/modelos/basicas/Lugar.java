package br.com.andrewesteves.travelling.modelos.basicas;

import android.os.Parcel;
import android.os.Parcelable;

public class Lugar implements Parcelable {
    private String titulo;
    private String endereco;

    public Lugar() {}

    public Lugar(String titulo, String endereco) {
        this.setTitulo(titulo);
        this.setEndereco(endereco);
    }

    protected Lugar(Parcel in) {
        setTitulo(in.readString());
        setEndereco(in.readString());
    }

    public static final Creator<Lugar> CREATOR = new Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel in) {
            return new Lugar(in);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitulo());
        dest.writeString(getEndereco());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return this.titulo + " | " + this.endereco;
    }
}
