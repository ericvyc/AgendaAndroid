package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        //Pega a Intent que fez a chamada para esta activity
        Intent intent = getIntent();

        //Pega a prova que estava no extra da Intent que chamou esta activity
        Prova prova = (Prova) intent.getSerializableExtra("prova");

        //Pega o campo materia do layout
        TextView materia = (TextView) findViewById(R.id.detalhes_prova_materia);

        //Pega o campo data do layout
        TextView data = (TextView) findViewById(R.id.detalhes_prova_data);

        //Pega o campo topicos do layout
        ListView listaTopicos = (ListView) findViewById(R.id.detalhes_prova_topicos);

        //Seta a materia da prova como texto do campo materia
        materia.setText(prova.getMateria());

        //Seta a data da prova como texto do campo data
        data.setText(prova.getData());

        //Cria adapter pra setar os topicos da prova no ListView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());

        //Seta no campo de listaTopicos o adapter com a lista de topicos da prova
        listaTopicos.setAdapter(adapter);

    }
}
