package com.example.conferencia;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"Bem vindo!",Toast.LENGTH_LONG).show();

    }

    public void pesquisar(View view){

        EditText txt = findViewById(R.id.editText);
        String oc = txt.getText().toString();

        String str = null;
        try {
            str = new HttpConn(oc).execute().get();
            Toast.makeText(MainActivity.this,"Conexão realizada com sucesso!",Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String codigo = "";
        String descri= "";
        String quant = "";
        String unidade = "";
        String pesoliq = "";
        String pesobru = "";
        float f;

        List<String> cod = new LinkedList<>();
        List<String> descr = new LinkedList<>();
        List<String> quan = new LinkedList<>();
        List<String> uni = new LinkedList<>();
        List<String> peliq = new LinkedList<>();
        List<String> pebru = new LinkedList<>();


        int tam = str.length();
        int pos = 0;
        int cont = 1;
        for(int i=0 ; i<tam ; i++){
            if((cont == 1) && (str.charAt(i) == ';')){
                codigo = str.substring(pos,i);
                cont = 2;
                pos = i+1;
            }else{
                if((cont == 2) && (str.charAt(i) == ';')){
                    descri = str.substring(pos,i);
                    cont = 3;
                    pos = i+1;
                }else{
                    if((cont == 3) && (str.charAt(i) == ';')){
                        f = Float.valueOf(str.substring(pos,i)).floatValue();
                        quant = Float.toString(Math.round(f));
                        cont = 4;
                        pos = i+1;
                    }else{
                        if((cont == 4) && (str.charAt(i) == ';')){
                            unidade = str.substring(pos,i);
                            cont = 5;
                            pos = i+1;
                        }else{
                            if((cont == 5) && (str.charAt(i) == ';')){
                                pesoliq = str.substring(pos,i);
                                cont = 6;
                                pos = i+1;
                            }else{
                                if((cont == 6) && (str.charAt(i) == ';')){
                                    pesobru = str.substring(pos,i);
                                    cont = 1;
                                    pos = i+1;
                                }
                            }
                        }
                    }
                }
            }

            if(str.charAt(i) == '|'){
                cont = 1;
                pos = i+1;
                cod.add(codigo);
                descr.add(descri);
                quan.add(quant);
                uni.add(unidade);
            }
        }

        Queue<String> out = new LinkedList<>();

        RecyclerView recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new StringAdapter(this, cod,descr,quan,uni,oc));


    }
    public void goSobre(View view){

        Intent intent = new Intent(this, sobre.class);
        startActivity(intent);
    }

    public void goFim(View view){

        Intent intent = new Intent(this, finalizar.class);
        startActivity(intent);
    }



}
