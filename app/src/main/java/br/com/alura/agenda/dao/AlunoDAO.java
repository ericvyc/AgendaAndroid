package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by eric.castanheira on 14/07/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    //Construtor padrão passando como parâmetros o context, nome do banco
    // factory e versão do banco
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criando tabela de Alunos no banco
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL," +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    //Atualizando banco, passando como parâmetros o banco, a versão antiga
    // e a versão atual
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "";

        //Testa a versão local do banco, e inicia a execução do codigo de atualização a partir
        // desta versão, atualizando para cada versão seguinte (case 1 -> case 2 -> case 3...)
        // devido a ausência de break
        switch (i) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMUN caminhoFoto TEXT";
                db.execSQL(sql);
        }

    }

    public void insere(Aluno aluno) {
        //Pega banco com permissão de escrita
        SQLiteDatabase db = getWritableDatabase();

        //Cria um ContentValues para usar os dados do aluno na query
        // de inserção
        ContentValues dados = pegaDadosAluno(aluno);

        //Inserse o Aluno no banco usando o ContentValues como valores
        // nas colunas
        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosAluno(Aluno aluno) {

        //Cria ContentValues e seta os atributos do aluno como Key/Value
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {

        //Cria query para recuperar Alunos
        String sql = "SELECT * FROM Alunos;";

        //Pega banco com permissão de leitura
        SQLiteDatabase db = getReadableDatabase();

        //Seta o resultado da query no cursor
        Cursor c = db.rawQuery(sql, null);

        //Lista de Alunos para ser iterada e adicionar os objetos Aluno
        List<Aluno> alunos = new ArrayList<Aluno>();

        //Itera a os resultados no cursos enquanto houver resultados
        while(c.moveToNext()){

            //Instancia novo aluno e seta os valores da linha do resultado no objeto
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            //Adiciona o Aluno na lista de Alunos
            alunos.add(aluno);

        }

        //Fecha o cursor
        c.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {

        //Pega banco com permissão de escrita
        SQLiteDatabase db = getWritableDatabase();

        //Seta o id do aluno como parâmetro para query de exclusão
        String[] params = {aluno.getId().toString()};

        //Deleta o aluno pelo id passado como parâmetro no Array de String
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {

        //Pega banco com permissão de escrita
        SQLiteDatabase db = getWritableDatabase();

        //Chama método que retorna os dados do Aluno num ContentValues
        // no formato Key/Value
        ContentValues dados = pegaDadosAluno(aluno);

        //Seta o id do aluno como parâmetro para query de alteração
        String[] params = {aluno.getId().toString()};

        //Altera o aluno pelo id passado como parâmetro no Array de String
        // e coloca os valores do ContentValues nas colunas
        db.update("Alunos", dados, "id = ?", params);
    }
}
