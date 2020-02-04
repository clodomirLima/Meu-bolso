package br.projecao.tads.meubolso;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class BalancoFragment extends Fragment{
    private static final String TAG = "BalançoFragment";

    private TextView receitaText, vazio;
    private TextView mes, ano;
    private Button plus, less;
    private RadioGroup radioGroup;
    private ArrayList<Float> xData = new ArrayList();
    private ArrayList<String> yData = new ArrayList();
    private boolean label = false;
    String s="12";
    String d="2018";
    public static final int[] COLORS = {
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53),
            Color.rgb(217, 80, 138),
            Color.rgb(254, 149, 7),
            Color.rgb(254, 247, 120)
    };

    ArrayList entries = new ArrayList();
    PieChart chart;
    PieDataSet dataSet;
    PieData pieData;
    public BalancoFragment() {

    }

    public void colorText(){
        if(((SharedResources.getInstance().calculaReceita())-(SharedResources.getInstance().calculaDespesa() )>0))
            receitaText.setTextColor(0xFF429600);
        else if(((SharedResources.getInstance().calculaReceita())-(SharedResources.getInstance().calculaDespesa() )<0))
            receitaText.setTextColor(Color.rgb(255,0,0));
        else if(((SharedResources.getInstance().calculaReceita())-(SharedResources.getInstance().calculaDespesa() )==0))
            receitaText.setTextColor(Color.rgb(0,0,255));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.balanco_fragment, container, false);

        //Texto com total Receita - Despesa
        receitaText = view.findViewById(R.id.receitaText);
        vazio = view.findViewById(R.id.tvEmptyList2);
        receitaText.setText(SharedResources.getInstance().convert(
                (SharedResources.getInstance().calculaReceita())-(SharedResources.getInstance().calculaDespesa())));
        colorText();
        radioGroup = view.findViewById(R.id.radioGroup);


        plus = view.findViewById(R.id.plus);
        less = view.findViewById(R.id.less);
        mes = view.findViewById(R.id.idMes);
        ano = view.findViewById(R.id.idAno);


        //Inicializar PieChart
        chart = view.findViewById(R.id.chart);

        chart.clear();

        chart.setDescription(null);
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        chart.getLegend().setWordWrapEnabled(true);
        xData = new ArrayList<>();
        xData.add(SharedResources.getInstance().calculaReceita());
        xData.add(SharedResources.getInstance().calculaDespesa());
        yData = new ArrayList<>();
        yData.add("Receita");
        yData.add("Despesa");
        for(int i = 0;i<xData.size();i++)
            entries.add(new PieEntry(Float.parseFloat(xData.get(i).toString()),yData.get(i)));
        dataSet = new PieDataSet(entries, "");
        dataSet.setColors(COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        pieData = new PieData(dataSet);
        chart.clear();
        chart.setData(pieData);
        chart.invalidate();

        //Adiciona mes
        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int iMes, iAno;
                iMes = Integer.parseInt(mes.getText().toString());
                iAno = Integer.parseInt(ano.getText().toString());
                if(iMes==12) {
                    iMes = 1;
                    iAno = iAno+1;
                }
                else {
                    iMes = iMes +1;
                }
                if(iMes==10){
                    mes.setText("10");
                }else if(iMes==11){
                    mes.setText("11");
                }else if(iMes==12){
                    mes.setText("12");
                }else if(iMes>0 | iMes<10){
                    mes.setText("0"+String.valueOf(iMes));
                }
                ano.setText(String.valueOf(iAno));
                s = mes.getText().toString();
                d = ano.getText().toString();

                int i= radioGroup.getCheckedRadioButtonId();
                radioGroup.check(R.id.radioAll);
                radioGroup.check(i);
                if(radioGroup.getCheckedRadioButtonId()==R.id.radioAll){
                    radioGroup.check(R.id.radioDespesa);
                    radioGroup.check(R.id.radioAll);
                }

            }
        });
        //Subtrai Mes
        less.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int iMes, iAno;
                iMes = Integer.parseInt(mes.getText().toString());
                iAno = Integer.parseInt(ano.getText().toString());
                if(iMes==1) {
                    iMes = 12;
                    iAno = iAno-1;
                }
                else {
                    iMes = iMes -1;
                }
                if(iMes==10){
                    mes.setText("10");
                }else if(iMes==11){
                    mes.setText("11");
                }else if(iMes==12){
                    mes.setText("12");
                }else if(iMes>0 | iMes<10){
                    mes.setText("0"+String.valueOf(iMes));
                }
                ano.setText(String.valueOf(iAno));
                s = mes.getText().toString();
                d = ano.getText().toString();


                int i= radioGroup.getCheckedRadioButtonId();
                radioGroup.check(R.id.radioAll);
                radioGroup.check(i);
                if(radioGroup.getCheckedRadioButtonId()==R.id.radioAll){
                    radioGroup.check(R.id.radioDespesa);
                    radioGroup.check(R.id.radioAll);
                }

            }
        });

        radioGroup.check(R.id.radioReceita);
        radioGroup.check(R.id.radioAll);
        //Inicializar RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //achar qual botão foi selecionado
                if(checkedId == R.id.radioAll){
                    receitaText.setText(SharedResources.getInstance().convert(
                            (SharedResources.getInstance().calculaReceita())-(SharedResources.getInstance().calculaDespesa())));
                    colorText();
                    dataSet.clear();
                    pieData.clearValues();
                    chart.clear();
                    xData = new ArrayList<>();
                    xData.add(SharedResources.getInstance().calculaReceitaM(s, d));
                    xData.add(SharedResources.getInstance().calculaDespesaM(s, d));
                    yData = new ArrayList<>();
                    yData.add("Receita");
                    yData.add("Despesa");
                    for(int i = 0;i<xData.size();i++)
                        entries.add(new PieEntry(xData.get(i),yData.get(i)));
                    dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(10);
                    pieData = new PieData(dataSet);
                    chart.setData(pieData);
                    chart.invalidate();
                    if(xData.get(1)==0.0 && xData.get(0)==0.0){
                        label=true;
                    }else label= false;
                    if(!label){
                        vazio.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                    }else{
                        vazio.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                    receitaText.setText(String.valueOf(SharedResources.getInstance().calculaReceitaM(s, d)-SharedResources.getInstance().calculaDespesaM(s, d)));
                    if(((SharedResources.getInstance().calculaReceitaM(s, d)-SharedResources.getInstance().calculaDespesaM(s, d) )>0))
                        receitaText.setTextColor(0xFF429600);
                    else if(((SharedResources.getInstance().calculaReceitaM(s, d)-SharedResources.getInstance().calculaDespesaM(s, d) )<0))
                        receitaText.setTextColor(Color.rgb(255,0,0));
                    else if(((SharedResources.getInstance().calculaReceitaM(s, d)-SharedResources.getInstance().calculaDespesaM(s, d) )==0))
                        receitaText.setTextColor(Color.rgb(0,0,255));
                    onResume();

                }else if(checkedId == R.id.radioReceita){
                    receitaText.setText(SharedResources.getInstance().convert(
                            (SharedResources.getInstance().calculaReceita())));
                    receitaText.setTextColor(0xFF429600);
                    dataSet.clear();
                    pieData.clearValues();
                    chart.clear();
                    xData = new ArrayList<>();
                    xData =  SharedResources.getInstance().calculaValoresR(s,d);
                    yData = new ArrayList<>();
                    yData.add("Salário");
                    yData.add("Aluguel");
                    yData.add("Rendimentos");
                    yData.add("Mesada");
                    yData.add("Outros");
                    int cont = 0;
                    float somaR=0;
                    for(int i = 0;i<xData.size();i++){
                        if (xData.get(i) != 0.0) {
                            somaR=somaR+xData.get(i);
                            entries.add(new PieEntry(xData.get(i),yData.get(i)));
                        }else cont++;
                    }
                    if(cont==xData.size())
                        label=true;
                    else label=false;
                    dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(10);
                    pieData = new PieData(dataSet);
                    chart.setData(pieData);
                    chart.invalidate();
                    if(!label){
                        vazio.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                    }else{
                        vazio.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                    receitaText.setText(String.valueOf(somaR));
                    receitaText.setTextColor(0xFF429600);
                    onResume();

                }else if(checkedId == R.id.radioDespesa){
                    receitaText.setText(SharedResources.getInstance().convert(
                            (SharedResources.getInstance().calculaDespesa())));
                    receitaText.setTextColor(Color.RED);
                    dataSet.clear();
                    pieData.clearValues();
                    chart.clear();
                    xData = new ArrayList();
                    xData = SharedResources.getInstance().calculaValoresD(s,d);
                    yData = new ArrayList<>();
                    yData.add("Alimentação");
                    yData.add("Vestuário");
                    yData.add("Combustível");
                    yData.add("Contas");
                    yData.add("Outros");
                    int cont=0;
                    float somaD=0;
                    for(int i = 0;i<xData.size();i++){
                        if (xData.get(i) != 0.0) {
                            somaD=somaD+xData.get(i);
                            entries.add(new PieEntry(xData.get(i),yData.get(i)));
                        }else cont++;
                    }
                    if(cont==xData.size()) {
                        label = true;
                    }else label = false;

                    dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(10);
                    pieData = new PieData(dataSet);
                    chart.setData(pieData);
                    chart.invalidate();

                    if(!label){
                        vazio.setVisibility(View.INVISIBLE);
                        chart.setVisibility(View.VISIBLE);
                    }else{
                        vazio.setVisibility(View.VISIBLE);
                        chart.setVisibility(View.INVISIBLE);
                    }
                    receitaText.setText(String.valueOf(somaD));
                    receitaText.setTextColor(Color.RED);
                    onResume();

                }
            }
        });
        return view;
    }

}
