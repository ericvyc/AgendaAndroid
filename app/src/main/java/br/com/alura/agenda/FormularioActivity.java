package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instancia FormularioHelper
        helper = new FormularioHelper(this);

        //Pega intent caso tenha sido colocado ao chamar a Activity (Usado na edição)
        final Intent intent = getIntent();
        //Seta aluno que foi passado na Intent
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        //Testa se o aluno é nulo (Usado na edição)
        if(aluno != null) {
            //Se o aluno não for nulo, instancia seus valores nos campos do formulario
            helper.preencheFormulario(aluno);
        }

        //Pega botao da foto em variavel
        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        //Seta listener para abrir câmera ao clicar no botão
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chama a ação de abrir a câmera pela intent
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //Cria o caminho da imagem e o nome com currentTimeMillis()
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                //Gera um arquivo com o caminho para salvar a foto a ser tirada com a câmera
                File arquivoFoto = new File(caminhoFoto);

                //Passa o caminho da foto a ser salva como parametro para intent
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));

                //Chama activity da câmera com o código (segundo parâmetro) a ser usado no
                // onActivityResult para tratar o retorno (callback)
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Testa se a activity aberta foi realizada com sucesso
        if(resultCode == Activity.RESULT_OK) {

            //Testa se o código que está retornando é da activity de câmera passado como parâmetro
            // no startActivityForResult
            if (requestCode == CODIGO_CAMERA) {

                //Se o código for o código da activity da câmera, pega a ImageView e
                // seta em uma variável
                ImageView foto = (ImageView) findViewById(R.id.formulario_foto);

                //Cria bitmap atraves do caminho da imagem
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

                //Reduz imagem para caber no ImageView
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

                //Seta foto reduziada no ImageView
                foto.setImageBitmap(bitmapReduzido);

                //Encaixar imagem na altura e largura disponível no ImageView
                foto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
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
