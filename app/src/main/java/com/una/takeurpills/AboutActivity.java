package com.una.takeurpills;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Mensaje("About us");

        LlenarListaObjetos();
        LlenarListView();
    }

    public void Mensaje(String msg){getSupportActionBar().setTitle(msg);};

    private List<ObjetosXDesplegar> misObjetos = new ArrayList<ObjetosXDesplegar>();
    private void LlenarListaObjetos() {
        misObjetos.add(new ObjetosXDesplegar("Bryan Murillo Rodriguez", "402240326", R.drawable.bryan));
        misObjetos.add(new ObjetosXDesplegar("Katherine Solorzano Quintanilla", "115910112", R.drawable.kathy));
        misObjetos.add(new ObjetosXDesplegar("Paolo Vargas Campos", "112040946", R.drawable.paolo));
       misObjetos.add(new ObjetosXDesplegar("Diego Vargas Medrano", "115880814", R.drawable.diego));


    }
    private void LlenarListView() {
        ArrayAdapter<ObjetosXDesplegar> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<ObjetosXDesplegar> {
        public MyListAdapter() {
            super(AboutActivity.this, R.layout.listview, misObjetos);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listview, parent, false);
            }
            ObjetosXDesplegar ObjetoActual = misObjetos.get(position);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.ivdibujo);
            imageView.setImageResource(ObjetoActual.getNumDibujo());
            TextView elatributo01 = (TextView) itemView.findViewById(R.id.paraelatributo01);
            elatributo01.setText(ObjetoActual.getAtributo01());
            TextView elatributo02 = (TextView) itemView.findViewById(R.id.paraelatributo02);
            elatributo02.setText("" + ObjetoActual.getAtributo02());
            return itemView;
        }
    }
}