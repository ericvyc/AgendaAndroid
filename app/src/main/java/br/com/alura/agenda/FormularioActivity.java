package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instancia FormularioHelper
        helper = new FormularioHelper(this);

        //Pega intent caso tenha sido colocado ao chamar a Activity (Usado na edição)
        Intent intent = getIntent();
        //Seta aluno que foi passado na Intent
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        //Testa se o aluno é nulo (Usado na edição)
        if(aluno != null) {
            //Se o aluno não for nulo, instancia seus valores nos campos do formulario
            helper.preencheFormulario(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Instancia menu no cabeçalho
        MenuInflater inflater = getMenuInflater();
        //Pega XML e coloca como layout
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Switch para saber qual opção do menu foi clicado
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                //Se a opção clicada for a opção salvar chama o helper para instanciar o aluno
                // pelos campos do formulario
                Aluno aluno = helper.pegaAluno();
                //Instancia DAO
                AlunoDAO dao = new AlunoDAO(this);

                //Testa se o id do aluno é nulo ou não
                if(aluno.getId() != null) {
                    //Se o id não for nulo, chama o metodo de alteração
                    dao.altera(aluno);
                } else {
                    //Se o id for nulo, cria um novo aluno
                    dao.insere(aluno);
                }

                //Fecha o DAO
                dao.close();

                //Chama o Toast com o nome do aluno que foi salvo
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();

                //Termina Activity
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
