package br.projecao.tads.meubolso;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void, Void, DadosCambio> {

    @Override
    protected DadosCambio doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        DadosCambio dc = new DadosCambio();
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=BRL");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                resposta.append(scanner.next());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject obj=null;
        try {
            obj = new JSONObject(resposta.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String rates = obj.getString("rates");

            JSONObject objrates = null;
            objrates = new JSONObject(rates);

            dc.setDate(obj.getString("date"));
            dc.setUsd(objrates.getString("USD"));
            dc.setEur(objrates.getString("EUR"));
            dc.setGbp(objrates.getString("GBP"));
            dc.setJpy(objrates.getString("JPY"));

            Log.d("CSI401", objrates.getString("USD"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dc;
    }


}


