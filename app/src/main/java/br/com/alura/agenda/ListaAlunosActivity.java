package br.com.alura.agenda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listViewAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewAlunos = (ListView) findViewById(R.id.lista_aluno);

        //Listener colocando aluno na intent ao chamar FormularioActivity para edição
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(position);
                Intent intentCallFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentCallFormulario.putExtra("aluno", aluno);
                startActivity(intentCallFormulario);
            }
        });

//        listViewAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(ListaAlunosActivity.this, "Clique longo!", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        //Botão para chamar formuçário sem aluno para a inclusão
        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCallFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentCallFormulario);
            }
        });

        registerForContextMenu(listViewAlunos);

    }

    //Método para carregar lista de alunos do banco de dados
    private void carregaListaAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //Usando Adapter criado para colocar Alunos no layout criado para a lista de Alunos
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listViewAlunos.setAdapter(adapter);
    }

    //Carregar lista de alunos após Activity ser interrompida
    @Override
    protected void onResume() {
        super.onResume();
        carregaListaAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        //Pega aluno clicado da ListView para o context menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listViewAlunos.getItemAtPosition(info.position);

        //Botão de ligar e intent de ligação
        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //Testa se a permissão de ligação está concedida à aplicação pelo usuário - necessário para Android 6.0+
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //Se não estiver concedida, pede a permissão - (123 - request code - necessario para identificar no
                    // onRequestPermissionsResult)
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                } else {
                    //Se está concedida, realiza a ação de ligar
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }


                return false;
            }
        });

        //Botão de SMS e intent de SMS
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        //Botão de mapa e intent de mapa
        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //Botão de visitar site e intent de visitar site
        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        //If para tratar o protocolo para abrir o browser
        String site = aluno.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }
        //Setando o URI com a URL do site do aluno
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        //Botão de deletar usando listener
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //Deletando aluno do banco
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                //Chamando toast com o nome do aluno deletado
                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " deletado",
                        Toast.LENGTH_SHORT).show();

                //Atualizando lista de alunos
                carregaListaAlunos();
                return false;
            }
        });
    }
}
