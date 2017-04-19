package com.una.takeurpills;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private JSONArray readFromFile() throws JSONException {

        String ret = "";
        JSONArray jarray;

        try {
            InputStream inputStream = getApplicationContext().openFileInput("data.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        if (ret == null || ret.isEmpty()) {
            jarray = new JSONArray();
        } else {
            jarray = new JSONArray(ret);
        }
        int lenght = jarray.length();
        final List<JSONObject> objs = asList(jarray);
        objs.remove(1);
        return jarray;
    }

    public static List<JSONObject> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<JSONObject> result = new ArrayList<JSONObject>(len);
        for (int i = 0; i < len; i++) {
            final JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    private boolean borrarArchivo() {
        File dir = getFilesDir();
        File file = new File(dir, "data.txt");
        boolean deleted = file.delete();
        return deleted;
    }


    public void Mensaje(String msg) {
        getSupportActionBar().setTitle(msg);
    };

}
