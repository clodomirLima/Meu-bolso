package br.projecao.tads.meubolso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditDespesa extends AppCompatActivity {

    private Despesa despesa;
    private int position;
    private EditText etValor;
    private EditText etName;
    private Spinner etOrigem;
    private EditText etData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_despesa);
        Intent it = getIntent();
        position = it.getIntExtra("position",
                0);
        despesa = SharedResources.getInstance()
                .getDespesa().get(position);
        etValor = findViewById(R.id.editValor);
        etName = findViewById(R.id.editName);
        etOrigem = findViewById(R.id.editOrigem);
        etData = findViewById(R.id.editDataD);
        if(despesa != null) {
            //Fill text fields
            etValor.setText((String.valueOf(despesa.getValor())));
            etName.setText(despesa.getName());

            //Set Spinner Text
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.despesas_org, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            etOrigem.setAdapter(adapter);
            if (despesa.getOrigem() != null) {
                int spinnerPosition = adapter.getPosition(despesa.getOrigem());
                etOrigem.setSelection(spinnerPosition);
            }

            etData.setText(despesa.getData());
            etData.addTextChangedListener(SharedResources.getInstance().insert("##/##/####", etData));
        }
    }


    public void confirmEdit(View view) {
        //Update despesa's info
        if (!etValor.getText().toString().equals("")&& !etValor.getText().toString().equals(".")) {
            if (Double.parseDouble(etValor.getText().toString()) > 0) {
                if (SharedResources.getInstance().verificaData(etData.getText().toString())) {
                    if(!(etOrigem.getSelectedItem().toString().equals("Outros") && etName.getText().toString().equals(""))){
                        despesa.setValor(Float.parseFloat(etValor.getText().toString()));
                        despesa.setName(etName.getText().toString());
                        despesa.setOrigem(etOrigem.getSelectedItem().toString());
                        despesa.setData(etData.getText().toString());
                        SharedResources.getInstance().getDespesa()
                                .set(position, despesa);
                        SharedResources.getInstance()
                                .saveDespesa(this);
                        Toast.makeText(this, "Despesa editada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "Digite a descrição!", Toast.LENGTH_SHORT).show();
                        etName.requestFocus();
                    }

                } else {
                    Toast.makeText(this, "Data incorreta!", Toast.LENGTH_SHORT).show();
                    etData.setText("");
                    etData.requestFocus();
                }
            } else {
                Toast.makeText(this, "Valor incorreto!", Toast.LENGTH_SHORT).show();
                etValor.setText("");
                etValor.requestFocus();
            }
        }else {
            Toast.makeText(this, "Valor incorreto!", Toast.LENGTH_SHORT).show();
            etValor.setText("");
            etValor.requestFocus();
        }
    }

}

