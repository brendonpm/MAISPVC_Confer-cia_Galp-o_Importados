package com.example.conferencia;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Httpfim extends AsyncTask<Void, Void, String> {

    public Httpfim(String oc) {
        this.oc = oc;
    }

    String oc;


    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL("http://192.168.0.4:8081/andon/conf/"+this.oc+"/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String saida;

            StringBuilder resposta = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }

            saida = resposta.toString();

            return saida;

        } catch (IOException e) {
            e.printStackTrace();

            return "Erro na conexão com WS";
        }

    }

}


