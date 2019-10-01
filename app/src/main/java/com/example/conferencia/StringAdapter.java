package com.example.conferencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

    private final List<String> cod;
    private final List<String> desc;
    private final List<String> quant;
    private final List<String> unid;

    private String oc;

    private Context context;


    public StringAdapter(Context context, List<String> cod, List<String> desc,List<String> quant, List<String> unid, String oc) {
        this.context = context;
        this.cod = cod;
        this.desc = desc;
        this.quant = quant;
        this.unid = unid;
        this.oc = oc;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item_string, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.bind(cod.get(i),desc.get(i),unid.get(i),quant.get(i));

//--------------------------------------------------------------------------- CHECA SE ITEM JA ESTÁ CONFIRMADO
        String prod2 = cod.get(i);
        try {
            Boolean ConfPorItem = new HttpConfPorItem(oc,prod2).execute().get();

            System.out.println("Resposta "+ConfPorItem);

            if(ConfPorItem){
                viewHolder.okButton.setEnabled(false);
                viewHolder.okButton.setText("Sim");
                System.out.println("ENTROU");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("1 - Erro Adapter onBind ConfPorItem");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("2 - Erro Adapter onBind ConfPorItem");
        }

//--------------------------------------------------------------------------- CONFERE ITEM
        viewHolder.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prod = cod.get(i);
                String str="";

                if(oc != "" && prod != ""){

                    try {
                        str = new HttpConfItem(oc,prod).execute().get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        System.out.println("1 - Erro Adapter onBind");
                    } catch (InterruptedException e) {
                        System.out.println("2 - Erro Adapter onBind");
                        e.printStackTrace();
                    }

                }
//---------------------------------------------------------------------------
                viewHolder.okButton.setEnabled(false);
                viewHolder.okButton.setText("Sim");

            }
        });
    }


    @Override
    public int getItemCount() {
        return cod.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button okButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            okButton = (Button) itemView.findViewById(R.id.button7);
        }

        public void bind(String codigo,String descricao,String unidade,String quantidade) {
            TextView title = itemView.findViewById(R.id.tv_title);
            title.setText(codigo);
            TextView title2 = itemView.findViewById(R.id.descricao);
            title2.setText(descricao);
            TextView title3 = itemView.findViewById(R.id.unidade);
            title3.setText(unidade);
            TextView title4 = itemView.findViewById(R.id.quantidade);
            title4.setText(quantidade);

        }
    }
}

