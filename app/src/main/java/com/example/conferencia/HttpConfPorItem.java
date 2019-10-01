package com.example.conferencia;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class HttpConfPorItem extends AsyncTask<Void, Void, Boolean> {

    String oc;
    String prod;

    public HttpConfPorItem(String oc, String prod) {
        this.oc = oc;
        this.prod = prod;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        try {

            URL url = new URL("http://192.168.0.4:8081/andon/confPorItem/" + oc + "%7C" + prod + "/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            StringBuilder resposta = new StringBuilder();

            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }

            if(resposta.charAt(0) == 'S'){
                return true;
            }else{
                return false;
            }



        }catch (ProtocolException e) {
            e.printStackTrace();
            System.out.println("1 - ERRO CONEXAO 02");
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("2 - ERRO CONEXAO 02");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("3 - ERRO CONEXAO 02");
            return false;
        }

    }
}
