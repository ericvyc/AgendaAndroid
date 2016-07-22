package br.com.alura.agenda;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        //Pega o fragmentManager e seta em uma variavel
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Inicia a transação do fragmentManager e retorna em uma variavel
        FragmentTransaction tx = fragmentManager.beginTransaction();

        //Substitui o frame principal por uma instancia do ListaProvasFragment
        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        //Comita a transação do fragmentManager
        tx.commit();

    }

}
