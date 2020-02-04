package br.projecao.tads.meubolso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroReceita extends AppCompatActivity {

    private EditText etValor;
    private EditText etName;
    private Spinner etOrigem;
    private EditText etData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_receita);
        etValor = findViewById(R.id.addValor);
        etName = findViewById(R.id.addName);
        etOrigem = findViewById(R.id.addOrigem);
        etData = findViewById(R.id.addData);
        etData.addTextChangedListener(SharedResources.getInstance().insert("##/##/####", etData));
        etData.setText(SharedResources.getInstance().dataAtual());
    }

    public void confirmAdd(View view) {
        if (!etValor.getText().toString().equals("") && !etValor.getText().toString().equals(".")) {
            if (!etOrigem.getSelectedItem().toString().equals("Selecione a Origem")) {
                if (SharedResources.getInstance().verificaData(etData.getText().toString())) {
                    if(!(etOrigem.getSelectedItem().toString().equals("Outros") && etName.getText().toString().equals(""))){
                        float valor = Float.parseFloat(etValor.getText().toString());
                        String name = etName.getText().toString();
                        String origem = etOrigem.getSelectedItem().toString();
                        String data = etData.getText().toString();
                        //Create student object
                        Receita s = new Receita(valor, name, origem, data);
                        //Add on singleton list
                        SharedResources.getInstance()
                                .getReceita().add(s);
                        SharedResources.getInstance()
                                .saveReceita(this);
                        Toast.makeText(this, "Receita adicionada com sucesso!", Toast.LENGTH_SHORT).show();
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
            }else{
                Toast.makeText(this, "Selecione uma Origem!", Toast.LENGTH_SHORT).show();
                etOrigem.requestFocus();
            }
        }else {
            Toast.makeText(this, "Valor incorreto!", Toast.LENGTH_SHORT).show();
            etValor.setText("");
            etValor.requestFocus();
        }

    }
}


