package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

/**
 * Created by eric.castanheira on 21/07/2016.
 */
public class ListaProvasFragment extends Fragment {

    //Método chamado pelo Android para criar o fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Infla o xml e retorna em forma de View para uma variavel view
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        //Cria lista dos topicos da prova de portugues
        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");

        //Instancia prova de portugues passando os dados da prova nos parametros
        // do construtor
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);

        //Cria lista dos topicos da prova de matematica
        List<String> topicosMat = Arrays.asList("Equações de segundo grau", "Trigonometria");

        //Instancia prova de matematica passando os dados da prova nos parametros
        // do construtor
        Prova provaMatematica = new Prova("Matematica", "27/05/2016", topicosMat);

        //Cria uma lista de provas, inserindo as provas de portugues e matematica
        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        //Cria um adapter para a lista, usando um layout padrão de lista e
        // passando a lista de provas como parametro
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(),
                android.R.layout.simple_list_item_1, provas);

        //Pega o componente ListView da lista de provas e seta
        // em uma variavel
        ListView lista = (ListView) view.findViewById(R.id.provas_lista);

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
                Toast.makeText(getContext(), "Clicou na prova de " + prova,
                        Toast.LENGTH_SHORT).show();

                //Cria uma Intent para ir para activity DetalhesProvaActivity
                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);

                //Coloca a prova na Intent com o método putExtra
                vaiParaDetalhes.putExtra("prova", prova);

                //Chama a Intent da activity DetalhesProvaActivity
                startActivity(vaiParaDetalhes);

            }
        });

        return view;
    }
}
