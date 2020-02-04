package br.projecao.tads.meubolso;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SharedResources {

    private static SharedResources shared = null;

    //Singleton elements
    private static ArrayList<Receita> receita;
    private static ArrayList<Despesa> despesa;

    NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static SharedResources getInstance() {
        if(shared == null) {
            shared = new SharedResources();
        }
        return shared;
    }

    private SharedResources() {
        receita = new ArrayList<Receita>();
        despesa = new ArrayList<Despesa>();
    }

    //Converter Double para mascara de string para
    public String convert(double v){
        String valor = currency.format(v);
        return valor;
    }

    //Verificar se data está dentro do padrão
    public boolean verificaData(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    // Load/Save Receitas/Despesas
    public void saveReceita(Context context) {
        try {
            FileOutputStream fos = context
                    .openFileOutput("Receitas.dat",
                            Context.MODE_PRIVATE);
            ObjectOutputStream oos = new
                    ObjectOutputStream(fos);
            oos.writeObject(receita);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadReceita(Context context) {
        try {
            FileInputStream fis = context
                    .openFileInput("Receitas.dat");
            ObjectInputStream ois =
                    new ObjectInputStream(fis);
            receita = (ArrayList<Receita>)
                    ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void saveDespesa(Context context) {
        try {
            FileOutputStream fos = context
                    .openFileOutput("Despesas.dat",
                            Context.MODE_PRIVATE);
            ObjectOutputStream oos = new
                    ObjectOutputStream(fos);
            oos.writeObject(despesa);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadDespesa(Context context) {
        try {
            FileInputStream fis = context
                    .openFileInput("Despesas.dat");
            ObjectInputStream ois =
                    new ObjectInputStream(fis);
            despesa = (ArrayList<Despesa>)
                    ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    //Somatório dos dos valores de Despesa/Receita
    public float calculaReceita(){
        float somareceita = 0;
        receita = SharedResources.getInstance().getReceita();
        for(int i = 0; i<receita.size();i++) {
            somareceita = somareceita + receita.get(i).getValor();
        }
        return somareceita;
    }
    public float calculaDespesa(){
        float somaDespesas = 0;
        despesa = SharedResources.getInstance().getDespesa();
        for(int i = 0; i<despesa.size();i++){
            somaDespesas = somaDespesas +  despesa.get(i).getValor();
        }
        return somaDespesas;
    }

    public float calculaReceitaM(String s, String x){
        float somareceita = 0;
        receita = SharedResources.getInstance().getReceita();
        for(int i = 0; i<receita.size();i++) {
            if(receita.get(i).getData().contains("/"+s+"/") && receita.get(i).getData().contains(x)){
                somareceita = somareceita + receita.get(i).getValor();
            }

        }
        return somareceita;
    }
    public float calculaDespesaM(String s, String x){
        float somaDespesas = 0;
        despesa = SharedResources.getInstance().getDespesa();
        for(int i = 0; i<despesa.size();i++){
            if(receita.get(i).getData().contains("/"+s+"/")&& receita.get(i).getData().contains(x)) {
                somaDespesas = somaDespesas + despesa.get(i).getValor();
            }
        }
        return somaDespesas;
    }

    public ArrayList<Float> calculaValoresD(String s, String x){
        ArrayList valores2 = new ArrayList();
        despesa = SharedResources.getInstance().getDespesa();
        float b=0, c=0, d=0, e=0, f=0;
        for(int i = 0;i<despesa.size();i++){
            if(receita.get(i).getData().contains("/"+s+"/") && receita.get(i).getData().contains("/"+x)){
                if(despesa.get(i).getOrigem().equals("Alimentação")){
                    b=b + despesa.get(i).getValor();
                }else if(despesa.get(i).getOrigem().equals("Vestuário")){
                    c=c + despesa.get(i).getValor();
                }else if(despesa.get(i).getOrigem().equals("Combustível")){
                    d=d + despesa.get(i).getValor();
                }else if(despesa.get(i).getOrigem().equals("Contas")){
                    e=e + despesa.get(i).getValor();
                }else if(despesa.get(i).getOrigem().equals("Outros")){
                    f=f + despesa.get(i).getValor();
                }
            }
        }
        valores2.add(b);
        valores2.add(c);
        valores2.add(d);
        valores2.add(e);
        valores2.add(f);
        return valores2;
    }
    public ArrayList<Float> calculaValoresR(String s, String x){
        ArrayList valores = new ArrayList();
        receita = SharedResources.getInstance().getReceita();
        float a=0, b=0, d=0, e=0, f=0;
        for(int i = 0;i<receita.size();i++){
            if(receita.get(i).getData().contains("/"+s+"/")&& receita.get(i).getData().contains("/"+x)){
                if(receita.get(i).getOrigem().equals("Salário")){
                    a=a + receita.get(i).getValor();
                }else if(receita.get(i).getOrigem().equals("Aluguel")){
                    b=b + receita.get(i).getValor();
                }else if(receita.get(i).getOrigem().equals("Rendimentos")){
                    d=d + receita.get(i).getValor();
                }else if(receita.get(i).getOrigem().equals("Mesada")){
                    e=e + receita.get(i).getValor();
                }else if(receita.get(i).getOrigem().equals("Outros")){
                    f=f + receita.get(i).getValor();
                }
            }
        }
        valores.add(a);
        valores.add(b);
        valores.add(d);
        valores.add(e);
        valores.add(f);
        return valores;
    }


    //Get Receita/Despesa
    public ArrayList<Receita> getReceita() {
        return receita;
    }
    public ArrayList<Despesa> getDespesa() {
        return despesa;
    }

    //Cria mascara para entrada de DATA
    public static TextWatcher insert(final String mask, final EditText et) {
        return new TextWatcher() {
            boolean isUpdating;
            String oldTxt = "";
            public void onTextChanged(
                    CharSequence s, int start, int before,int count) {
                String str = unmask(s.toString());
                String maskCurrent = "";
                if (isUpdating) {
                    oldTxt = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > oldTxt.length()) {
                        maskCurrent += m;
                        continue;
                    }
                    try {
                        maskCurrent += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                et.setText(maskCurrent);
                et.setSelection(maskCurrent.length());
            }

            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }
    private static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }
    public static String dataAtual(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        return reportDate;
    }
    public static String mes(){
        DateFormat df = new SimpleDateFormat("MM");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        return reportDate;
    }
    public static String ano(){
        DateFormat df = new SimpleDateFormat("yyyy");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        return reportDate;
    }
}
