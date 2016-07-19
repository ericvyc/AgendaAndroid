package br.com.alura.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by eric.castanheira on 14/07/2016.
 */
public class FormularioHelper {

    //Criando os campos do formulario como atributo da classe
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;

    //Cria atributo aluno
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        //Recebe formulario como parametro e instancia os campos locais como
        // atributos da classe atraves do formulario
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);

        //Instancia aluno
        aluno = new Aluno();

    }

    public Aluno pegaAluno() {

        //Seta valores do formulario nos atributos do aluno
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {

        //Pega os valores do aluno passado como parâmetro, instancia nos campos
        // do formulario e instancia o aluno passado no aluno local
        // (Para usar na edição em outra ação)
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {

        //Testa se o caminhoFoto é diferente de nulo, ou seja, caso haja
        // uma imagem para esse Aluno
        if(caminhoFoto != null) {

            //Cria bitmap atraves do caminho da imagem
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            //Reduz imagem para caber no ImageView
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

            //Seta foto reduziada no ImageView
            campoFoto.setImageBitmap(bitmapReduzido);

            //Encaixar imagem na altura e largura disponível no ImageView
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

            //Setando na foto o caminho da imagem tirada para ser recupera em outros
            // momentos, usando o metodo setTag, onde é vinculado alguma coisa à View
            // que está sendo manipulada
            campoFoto.setTag(caminhoFoto);

        }
    }
}