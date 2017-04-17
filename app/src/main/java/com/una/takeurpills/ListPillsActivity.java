package com.una.takeurpills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListPillsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pills);

        FillListView();
        OnClickListItems();

        Mensaje("Listado de Tratamientos");


    } // Fin del Oncreate de la Actividad 01

    //Aca se pueden cargar los tratamientos desde el Json  del app, pero solo los nombres (posible a cambios)
    private void FillListView() {
        String[] pills = {
                "Acetaminofen",
                "Paracetamol",
                "Ibuprofeno",
                "Flumocil",
                "Acetaminofen",
                "Paracetamol",
                "Ibuprofeno",
                "Flumocil",
                "Acetaminofen",
                "Paracetamol",
                "Ibuprofeno",
                "Flumocil",
                "Acetaminofen",
                "Paracetamol",
                "Ibuprofeno",
                "Flumocil"
        };

        ArrayAdapter<String> adaptador = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, pills);
        ListView milistview = (ListView) findViewById(R.id.listPills);
        milistview.setAdapter(adaptador);
    }

    //Se cambia este metodo para enviar a pantalla de Detalles del tratamiento
    public void OnClickListItems() {
        ListView list = (ListView) findViewById(R.id.listPills);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked,
                                    int position, long id) {
                Intent intento = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intento);
                TextView textView = (TextView) viewClicked;
                String message = "Tratamiento # " + (1 + position) + ", corresponde a: " + textView.getText().toString();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Mensaje(String msg) {
        getSupportActionBar().setTitle(msg);
    };

}
