package br.projecao.tads.meubolso;


public class DadosCambio {
    private String usd;
    private String eur;
    private String gbp;
    private String jpy;
    private String date;

    public DadosCambio() {
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    public String getGbp() {
        return gbp;
    }

    public void setGbp(String gbp) {
        this.gbp = gbp;
    }

    public String getJpy() {
        return jpy;
    }

    public void setJpy(String jpy) {
        this.jpy = jpy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {


        return "USD: "+getUsd()+"\n" +
                "EUR: "+getEur()+"\n" +
                "GBP: "+getGbp()+"\n" +
                "JPY: "+getJpy()+"\n";
    }
}