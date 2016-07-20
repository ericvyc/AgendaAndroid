package br.com.alura.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by eric.castanheira on 20/07/2016.
 */
public class AlunoConverter {

    //Converte a lista de alunos para JSON
    public String converteParaJSON(List<Aluno> alunos) {

        //Instancia um JSONStringer
        JSONStringer js = new JSONStringer();

        //Abre um try catch para tratar JSONException
        try {

            //Abre um objeto json e cria a estrtutura de um objeto JSON para
            // comunicação com o servidor
            js.object().key("list").array().object().key("aluno").array();

            //Itera a lista de alunos
            for(Aluno aluno : alunos) {

                //Cria um objeto na lista do json para cada aluno e seta o nome
                // e a nota nesse objeto usando key - value
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());

                //Fecha o objeto JSON aluno da lista de alunos
                js.endObject();
            }

            //Fecha a estrutura do JSON
            js.endArray().endObject().endArray().endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Retorna o JSON gerado em uma String
        return js.toString();
    }

}
