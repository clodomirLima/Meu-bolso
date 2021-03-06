package br.projecao.tads.meubolso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DespesaFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "DespesaFragment";

    private Button add, plus, less;
    private ListView lvStudents;
    private TextView tvEmptyList;
    private TextView receitaText;
    private TextView mes, ano;

    

    @Override
    public void onResume() {
        super.onResume();
        receitaText.setText(SharedResources.getInstance().convert((SharedResources.getInstance().calculaDespesa())));
        final DespesaAdapter adapter = new DespesaAdapter(getContext(),SharedResources.getInstance().getDespesa());
        lvStudents.setAdapter(adapter);
        mes.setText(SharedResources.getInstance().mes());
        ano.setText(SharedResources.getInstance().ano());

        String filtro = mes.getText()+"/"+ano.getText();
        adapter.getFilter().filter(filtro);

        //SetLongClicable
        lvStudents.setLongClickable(true);
        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Aviso!")
                        .setMessage("Deseja deletar esta entrada?")
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //não faz nada
                            }
                        })
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(getActivity(), EditDespesa.class);
                                SharedResources.getInstance().getDespesa()
                                        .remove(position);
                                SharedResources.getInstance()
                                        .saveDespesa(getContext());
                                Toast.makeText(getActivity(), "Despesa removida com sucesso", Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                        })
                        .show();
                return true;
            }
        });

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(
                        getActivity(),
                        EditDespesa.class);
                it.putExtra("position", position);
                startActivity(it);
            }
        });
        if(SharedResources.getInstance().getDespesa().isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else
            tvEmptyList.setVisibility(View.INVISIBLE);

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
                String filtro1 = mes.getText()+"/"+ano.getText();
                adapter.getFilter().filter(filtro1);
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
                String filtro2 = mes.getText()+"/"+ano.getText();
                adapter.getFilter().filter(filtro2);
            }
        });

    }

    public DespesaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.despesa_fragment, container, false);
        lvStudents = view.findViewById(R.id.lvStudents);
        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        receitaText = view.findViewById(R.id.receitaText);
        receitaText.setTextColor(Color.rgb(255,0,0));
        plus = view.findViewById(R.id.plus);
        less = view.findViewById(R.id.less);
        mes = view.findViewById(R.id.idMes);
        ano = view.findViewById(R.id.idAno);
        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        add = view.findViewById(R.id.addDespesa);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastroDespesa.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
