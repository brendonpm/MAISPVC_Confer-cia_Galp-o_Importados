package com.example.conferencia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class finalizar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);


        Button b = findViewById(R.id.button4);
        TextView tv = findViewById(R.id.textView12);
        b.setEnabled(false);
        tv.setText("");

    }
    public void finalizar(View view){

        EditText txt = findViewById(R.id.editText2);
        String oc = txt.getText().toString();

        String str = null;

        if(oc != "  "){
            try {
                str = new HttpItemNConf(oc).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(str.charAt(0) != 'v'){

                Toast.makeText(finalizar.this,"Os seguintes produtos não foram marcados como carregados: "+str+" !",Toast.LENGTH_LONG).show();

                Button b = findViewById(R.id.button4);
                TextView tv = findViewById(R.id.textView12);
                b.setEnabled(true);
                tv.setText("Existem Itens não conferidos! \nDeseja Finalizar mesmo assim?");


            }else {

                try {
                    str = new Httpfim(oc).execute().get();
                    Toast.makeText(finalizar.this, "Finalizada!", Toast.LENGTH_LONG).show();
                    txt.setText("");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }else{
            Toast.makeText(finalizar.this,"Você deve informar a ordem de carga!",Toast.LENGTH_LONG).show();
        }

    }

    public void finalizarIncom(View view){

        EditText txt = findViewById(R.id.editText2);
        String oc = txt.getText().toString();
        String str = null;

        try {
            str = new Httpfim(oc).execute().get();
            str = new HttpFimIncompleto(oc).execute().get();
            Toast.makeText(finalizar.this, "Finalizada!", Toast.LENGTH_LONG).show();
            txt.setText("");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
