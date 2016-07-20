package br.com.alura.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by eric.castanheira on 20/07/2016.
 */
public class WebClient {

    public String post(String json) {
        try {

            //Cria URL do servidor
            URL url = new URL("https://www.caelum.com.br/mobile");

            //Seta a conexão da url fazendo cast para HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Informa na requisição que o formato dos dados enviados é json
            connection.setRequestProperty("Content-type", "application/json");

            //Informa na requisição que a resposta tem que ser em formato json
            connection.setRequestProperty("Accept", "application/json");

            //Seta na conexão a inteção de ler a resposta (POST)
            connection.setDoOutput(true);

            //Seta na requisição a string json
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            //Abre a conexão
            connection.connect();

            //Cria um scanner e retorna a resposta da requisição
            Scanner scanner = new Scanner(connection.getInputStream());
            return scanner.next();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
