package br.com.alura.agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        //Cria lista dos topicos da prova de portugues
        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");

        //Instancia prova de portugues passando os dados da prova nos parametros
        // do construtor
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);

        //Cria lista dos topicos da prova de matematcia
        List<String> topicosMat = Arrays.asList("Equacoes de segundo grau", "Trigonometria");

        //Instancia prova de matematica passando os dados da prova nos parametros
        // do construtor
        Prova provaMatematica = new Prova("Matematica", "27/05/2016", topicosMat);

        //Cria uma lista de provas, inserindo as provas de portugues e matematica
        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        //Cria um adapter para a lista, usando um layout padrão de lista e
        // passando a lista de provas como parametro
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this,
                android.R.layout.simple_list_item_1, provas);

        //Pega o componente ListView da lista de provas e seta
        // em uma variavel
        ListView lista = (ListView) findViewById(R.id.provas_lista);

        //Seta o adapter com a lista de provas no componente de lista (ListView)
        lista.setAdapter(adapter);

        //Cria um listener para à ação de clique em um item da lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Pega a prova clicada através da posição do item da lista clicado
                // e do parent (ListView) passado como parâmetro
                Prova prova = (Prova) parent.getItemAtPosition(position);

                //Chama um Toast com o nome da prova clicado
                Toast.makeText(ProvasActivity.this, "Clicou na prova de " + prova,
                        Toast.LENGTH_SHORT).show();

            }
        });

    }
}
