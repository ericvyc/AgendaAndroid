package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listViewAlunos = (ListView) findViewById(R.id.lista_aluno);

        List<String> alunos = new ArrayList<String>();
        alunos.add("Eric");
        alunos.add("Vilar");
        alunos.add("Yankous");
        alunos.add("Castanheira");
        alunos.add("Eric");
        alunos.add("Vilar");
        alunos.add("Yankous");
        alunos.add("Castanheira");
        alunos.add("Eric");
        alunos.add("Vilar");
        alunos.add("Yankous");
        alunos.add("Castanheira");
        alunos.add("Eric");
        alunos.add("Vilar");
        alunos.add("Yankous");
        alunos.add("Castanheira");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);
        listViewAlunos.setAdapter(adapter);

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCallFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentCallFormulario);
            }
        });

    }
}
