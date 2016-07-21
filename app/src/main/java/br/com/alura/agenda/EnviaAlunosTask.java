package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by eric.castanheira on 20/07/2016.
 */
public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        //Instancia um AlunoDAO
        AlunoDAO dao = new AlunoDAO(context);

        //Pega a lista de alunos do banco de dados
        List<Aluno> alunos = dao.buscaAlunos();

        //Fecha o DAO
        dao.close();

        //Instancia classe AlunoConverter
        AlunoConverter conversor = new AlunoConverter();

        //Chama o metodo converterParaJSON da classe AlunoConverter, pega a String json
        // retornada e seta em uma variavel
        String json = conversor.converteParaJSON(alunos);

        //Instancia um WebClient
        WebClient client = new WebClient();

        //Chama o método de post do WebClient passando o json dos alunos do banco
        // como parâmetro
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String o) {

        dialog.dismiss();

        //Chama um Toast com o JSON devolvido do servidor para a Thread secundária,
        // essa Thread secundária devolve o return como parâmetro para esse método
        // onPostExecute, que é executado na Thread principal ou primária
        Toast.makeText(context, o, Toast.LENGTH_LONG).show();
    }

    //Executado antes do doInBackground e na Thread principal
    @Override
    protected void onPreExecute() {

        //Barra de progresso mostrada ao usuário com 2 parâmetros boolean, o primeiro
        // pergunta se o tempo de execução é indeterminado (Sim) e se a ação é cancelável (Sim),
        // ou seja, se o usuário pode cancelar a ação e continuar usando a aplicação
        dialog = ProgressDialog.show(context,"Aguarde", "Enviando alunos...", true, true);
    }
}
