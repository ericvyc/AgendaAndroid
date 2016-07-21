package br.com.alura.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
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

        //Pega aluno na posição passada como parâmetro da lista
        Aluno aluno = alunos.get(position);

        //Pega o layout criado e infla ele
        LayoutInflater inflater = LayoutInflater.from(context);

        //Seta a convertView em uma outra variavel view
        View view = convertView;

        //Testa se a convertView está nula (elementos prontos da lista acima e abaixo)
        // para evitar inflar sempre o layout xml criado e com isso ganhar performance
        if(view == null) {
            //Caso a convertView esteja nula, infla o layout xml criado e instancia ele na view
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        //Pega campo nome do layout criado e seta o nome do aluno
        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        //Pega campo telefone do layout criado e seta o telefone do aluno
        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        //Pega campo endereço do layout criado e seta o endereço do aluno apenas no
        //layout de paisagem
        TextView campoEndereco = (TextView) view.findViewById(R.id.item_endereco);

        //Testa se o campoEndereco existe na orientação atual do layout
        if(campoEndereco != null) {

            //Se a orientação do layout atual for paisagem, seta o endereço
            //do aluno no campoEndereco
            campoEndereco.setText(aluno.getEndereco());
        }

        //Pega campo site do layout criado e seta o site do aluno apenas no
        //layout de paisagem
        TextView campoSite = (TextView) view.findViewById(R.id.item_site);

        //Testa se o campoSite existe na orientação atual do layout
        if(campoSite != null) {

            //Se a orientação do layout atual for paisagem, seta o site
            //do aluno no campoSite
            campoSite.setText(aluno.getSite());
        }

        //Pega ImageView da foto do layout criado
        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);

        //Testa se o caminhoFoto é diferente de nulo, ou seja, caso haja
        // uma imagem para esse Aluno
        if(aluno.getCaminhoFoto() != null) {

            //Cria bitmap atraves do caminho da imagem
            Bitmap bitmap = BitmapFactory.decodeFile(aluno.getCaminhoFoto());

            //Reduz imagem para caber no ImageView
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

            //Seta foto reduziada no ImageView
            campoFoto.setImageBitmap(bitmapReduzido);

            //Encaixar imagem na altura e largura disponível no ImageView
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        return view;
    }
}
