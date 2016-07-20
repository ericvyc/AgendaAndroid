package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by eric.castanheira on 19/07/2016.
 */
public class SMSReceiver extends BroadcastReceiver {

    //Método chamado quando um SMS é recebido -> Configurado no Manifest
    @Override
    public void onReceive(Context context, Intent intent) {

        //Pega pdus da intent
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        //Pega primeira pdu e seta numa variavel de byte[]
        byte[] pdu = (byte[]) pdus[0];

        //Pega o formato do SMS da intent e seta numa variavel
        String formato = (String) intent.getSerializableExtra("format");

        //Cria SmsMessage a partir das variaveis pdu e formato obtidas
        // na intent
        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);
        String telefone = sms.getDisplayOriginatingAddress();

        //Instancia DAO
        AlunoDAO dao = new AlunoDAO(context);

        //Verifica se o remetente do SMS é um aluno
        if(dao.ehAluno(telefone)){

            //Se o remetente for um aluno, cria um Toast
            Toast.makeText(context, "Chegou um SMS de Aluno", Toast.LENGTH_SHORT).show();

            //Cria MediaPlayer com som da pasta res
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);

            //Chama o som passado na criação do MediaPlayer
            mp.start();
        }

        //Fecha o DAO para liberar memória
        dao.close();

    }

}