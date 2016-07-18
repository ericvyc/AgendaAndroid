package br.com.alura.agenda;

import android.widget.EditText;
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

    //Cria atributo aluno
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        //Recebe formulario como parametro e instancia os campos locais como
        // atributos da classe atraves do formulario
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.fomrulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
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
        this.aluno = aluno;
    }
}