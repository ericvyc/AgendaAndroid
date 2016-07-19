package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by eric.castanheira on 19/07/2016.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Cria um Toast quando recebe um SMS
        Toast.makeText(context, "Chegou um SMS! - Agenda", Toast.LENGTH_SHORT).show();

    }
    
}
