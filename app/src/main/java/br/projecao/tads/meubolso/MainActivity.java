package br.projecao.tads.meubolso;




import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Load Arquivos
        SharedResources.getInstance()
                .loadReceita(this);
        SharedResources.getInstance()
                .loadDespesa(this);
        //Tabs
        Log.d(TAG, "onCreate: Starting");
        //Set up the ViewPager with the sections adapter
        mViewPager =findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    //ViewPager para Abas
    private void setupViewPager( ViewPager viewPager){
        adapter.addFragment(new ReceitaFragment(), "Receita");
        adapter.addFragment(new DespesaFragment(), "Despesa");
        adapter.addFragment(new BalancoFragment(), "Balanço");
        adapter.addFragment(new CotacaoFragment(), "Cotação");
        viewPager.setAdapter(adapter);
    }
}
