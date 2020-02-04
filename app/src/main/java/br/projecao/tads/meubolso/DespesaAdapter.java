package br.projecao.tads.meubolso;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DespesaAdapter extends BaseAdapter {

    private Context contextD;
    //Lista com todos os itens
    private ArrayList<Despesa> despesas;
    //Lista com itens para exibição
    private ArrayList<Despesa> despesas_exb;

    public DespesaAdapter(Context contextD, ArrayList<Despesa> despesas) {
        this.contextD = contextD;
        this.despesas = despesas;
        this.despesas_exb = despesas;
    }

    @Override
    public int getCount() {
        return despesas_exb.size();
    }

    @Override
    public Object getItem(int position) {
        return despesas_exb.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Despesa despesa = despesas_exb.get(position);
        LayoutInflater inflater = (LayoutInflater)
                contextD.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(
                R.layout.despesa_receita, null);
            //Update layout elements according to the despesa's info
        TextView tvOrigem = v.findViewById(R.id.origem);
        tvOrigem.setText(despesa.getOrigem()+
                    "\n" + SharedResources.getInstance().convert(despesa.getValor()) +
                    "\n" + String.valueOf(despesa.getData()));
        tvOrigem.setTextColor(Color.BLACK);
        ImageView img = v.findViewById(R.id.imageView1);
        img.setImageResource(R.drawable.despesar);
        return v;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = despesas.size();
                    results.values = despesas;
                } else {
                    //cria um array para armazenar os objetos filtrados.
                    ArrayList<Despesa> itens_filtrados = new ArrayList();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < despesas.size(); i++) {
                        Despesa data = despesas.get(i);
                        String condicao = data.getData();

                        if (condicao.contains(filtro)) {
                            //se conter adiciona na lista de itens filtrados.
                            itens_filtrados.add(data);
                        }
                    }
                    // Define o resultado do filtro na variavel FilterResults
                    results.count = itens_filtrados.size();
                    results.values = itens_filtrados;
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                despesas_exb = (ArrayList<Despesa>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
    }
}






