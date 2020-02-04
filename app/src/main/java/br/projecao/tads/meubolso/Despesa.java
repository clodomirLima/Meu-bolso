package br.projecao.tads.meubolso;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Despesa implements Parcelable, Serializable {

    private float valor;
    private String name;
    private String origem;
    private String data;

    public Despesa(float valor, String name, String origem, String data) {
       this.data = data;
       this.name = name;
       this.origem = origem;
       this.valor = valor;
    }

    protected Despesa(Parcel in) {
        valor = in.readFloat();
        name = in.readString();
        origem = in.readString();
        data = in.readString();
    }

    public static final Creator<Despesa> CREATOR = new Creator<Despesa>() {
        @Override
        public Despesa createFromParcel(Parcel in) {
            return new Despesa(in);
        }

        @Override
        public Despesa[] newArray(int size) {
            return new Despesa[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(valor);
        dest.writeString(name);
        dest.writeString(origem);
        dest.writeString(data);
    }

    public float getValor() { return valor; }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
