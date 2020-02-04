package br.projecao.tads.meubolso;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import java.util.ArrayList;

public class ReceitaAdapter extends BaseAdapter {

    private Context contextR;
    //Lista com todos os itens
    private ArrayList<Receita> receitas;
    //Lista com itens para exoboção
    private ArrayList<Receita> receitas_exb;



    public ReceitaAdapter(Context contextR, ArrayList<Receita> receitas) {
        this.contextR = contextR;
        this.receitas = receitas;
        this.receitas_exb = receitas;
    }
    @Override
    public int getCount() {
        return receitas_exb.size();
    }
    @Override
    public Object getItem(int position) {
        return receitas_exb.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Receita receita = receitas_exb.get(position);
        LayoutInflater inflater = (LayoutInflater)
                contextR.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(
                R.layout.despesa_receita, null);
            //Update layout elements according to the receita's info
        TextView tvOrigem = v.findViewById(R.id.origem);
        tvOrigem.setText(receita.getOrigem()+
                    "\n" + SharedResources.getInstance().convert(receita.getValor()) +
                    "\n" + String.valueOf(receita.getData()));
        tvOrigem.setTextColor(Color.BLACK);
        ImageView img = v.findViewById(R.id.imageView1);
        img.setImageResource(R.drawable.receitar);
        return v;
    }



    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence filtro) {
                FilterResults results = new FilterResults();
                //se não foi realizado nenhum filtro insere todos os itens.
                if (filtro == null || filtro.length() == 0) {
                    results.count = receitas.size();
                    results.values = receitas;
                } else {
                    //cria um array para armazenar os objetos filtrados.
                    ArrayList<Receita> itens_filtrados = new ArrayList<Receita>();

                    //percorre toda lista verificando se contem a palavra do filtro na descricao do objeto.
                    for (int i = 0; i < receitas.size(); i++) {
                        Receita data = receitas.get(i);
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
                receitas_exb = (ArrayList<Receita>) results.values; // Valores filtrados.
                notifyDataSetChanged();  // Notifica a lista de alteração
            }

        };
        return filter;
    }


}






