package com.example.conferencia;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class HttpConfItem extends AsyncTask<Void, Void, String> {

    String oc;
    String prod;

    public HttpConfItem(String oc, String prod) {
        this.oc = oc;
        this.prod = prod;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL("http://192.168.0.4:8081/andon/confItem/"+oc+"%7C"+prod+"/");
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

        } catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("1 - ERRO CONEXAO");
            return "erro";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("2 - ERRO CONEXAO");
            return "erro";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("3 - ERRO CONEXAO");
            return "erro";
        }

    }

}