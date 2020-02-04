package br.projecao.tads.meubolso;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CotacaoFragment extends Fragment{
    private static final String TAG = "CotacaoFragment";
    Float a, b, c, d;
    DadosCambio retorno;
    public CotacaoFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cotacao_fragment, container, false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        String []array = new String[5];
        array[1] = "UDS";
        array[2] = "EUR";
        array[3] = "GBP";
        array[4] = "JPY";

        HTTPService service = new HTTPService();
        try {
            DadosCambio retorno = service.execute().get();
            a = Float.parseFloat(retorno.getUsd());
            a = 1/a;
            b = Float.parseFloat(retorno.getEur());
            b= 1/b;
            c = Float.parseFloat(retorno.getGbp());
            c=1/c;
            d = Float.parseFloat(retorno.getJpy());
            d=1/d;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        entries.add(new BarEntry(1, a));
        entries.add(new BarEntry(2, b));
        entries.add(new BarEntry(3, c));
        entries.add(new BarEntry(4, d));

        BarChart chart = view.findViewById(R.id.barchart);
        BarDataSet dataSet = new BarDataSet(entries,"Grafico de Cotações");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.MAGENTA);
        dataSet.setValueTextSize(14);
        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate();


        chart.setDescription(null);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextSize(14);
        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getAxisLeft().setGranularity(1f);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(array));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        return view;
    }
}
