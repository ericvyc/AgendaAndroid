package br.com.alura.agenda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by eric.castanheira on 19/07/2016.
 */
public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        //Retorna o tamanho da lista
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        //Retorna o Aluno na posição passada como parâmetro
        return alunos.get(position);
    }

    @Override
    public long getItemId(int i) {
        //Retorna o id do Aluno na posição passada como parâmetro
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Retorna uma View para o Adapter, aonde será construida de acordo com o
        // layout definido
        TextView view = new TextView(context);

        Aluno aluno = alunos.get(position);

        view.setText(aluno.toString());

        return view;
    }
}
