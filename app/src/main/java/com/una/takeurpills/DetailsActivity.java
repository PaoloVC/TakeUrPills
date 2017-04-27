package com.una.takeurpills;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.una.takeurpills.R.id.domingo;
import static com.una.takeurpills.R.id.jueves;
import static com.una.takeurpills.R.id.lunes;
import static com.una.takeurpills.R.id.martes;
import static com.una.takeurpills.R.id.miercoles;
import static com.una.takeurpills.R.id.sabado;
import static com.una.takeurpills.R.id.viernes;

public class DetailsActivity extends ParentClass {
    private String unidadMedida = "";
    private int posicion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pill_logo);
        getSupportActionBar().setTitle("Details");
        getData();
        Button cancelar = (Button) findViewById(R.id.bt_detailsPill_delete);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertBuilder(v);
            }
        });

        OnclickDelButton(R.id.bt_detailsPill_edit);
    } // Fin del Oncreate


    //Metodos para iconos en action Bar, los siguientes dos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.iconsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intento = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intento);
                break;

            case R.id.back:
                this.onBackPressed();
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void Mensaje(String msg) {
        getSupportActionBar().setTitle(msg);
    }

    public void getData(){
        Intent callingIntent = getIntent();
        posicion = callingIntent.getIntExtra("posicion",0);
        JSONObject objjson = testjarray.optJSONObject(posicion);
        try{
            String titulo = String.valueOf(objjson.get("titulo"));
            int dosis = Integer.parseInt(String.valueOf(objjson.get("dosis")));
            String unidad = String.valueOf(objjson.get("Unidad"));
            unidadMedida = unidad;
            int cantidadRestante = Integer.parseInt(String.valueOf(objjson.get("cantidadRestante")));
            int reminder = Integer.parseInt(String.valueOf(objjson.get("Reminder")));
            String lunes = objjson.has("Dia_1")
                    ? String.valueOf(objjson.get("Dia_1"))
                    :"";
            String martes = objjson.has("Dia_2")
                    ? String.valueOf(objjson.get("Dia_2"))
                    :"";
            String miercoles = objjson.has("Dia_3")
                    ? String.valueOf(objjson.get("Dia_3"))
                    :"";
            String jueves = objjson.has("Dia_4")
                    ? String.valueOf(objjson.get("Dia_4"))
                    :"";
            String viernes = objjson.has("Dia_5")
                    ? String.valueOf(objjson.get("Dia_5"))
                    :"";
            String sabado = objjson.has("Dia_6")
                    ? String.valueOf(objjson.get("Dia_6"))
                    :"";
            String domingo = objjson.has("Dia_7")
                    ? String.valueOf(objjson.get("Dia_7"))
                    :"";

        TextView Mi_textview = (TextView) findViewById(R.id.tv_detailsPill_nombreTratamiento);
        TextView Mi_textview2 = (TextView) findViewById(R.id.tv_detailsPill_dosis2);
        TextView Mi_textview3 = (TextView) findViewById(R.id.tv_detailsPill_cantidadRestante2);
        TextView Mi_textview4 = (TextView) findViewById(R.id.tv_detailsPill_reminder2);
        TextView Mi_textview5 = (TextView) findViewById(R.id.tv_detailsPill_frecuencia2);

        Mi_textview.setText(titulo);
        Mi_textview2.setText((dosis == 1) ? String.valueOf(dosis)+ (unidad.equals("unidades")
                ? " unidad": " mililitro") :String.valueOf(dosis)+ " "+ unidad);
        Mi_textview3.setText((cantidadRestante == 1) ? String.valueOf(cantidadRestante)+ (unidad.equals("unidades")
                ? " unidad": " mililitro") :String.valueOf(cantidadRestante)+ " "+ unidad);
        Mi_textview4.setText((reminder == 1) ? String.valueOf(reminder)+ (unidad.equals("unidades")
                ? " unidad": " mililitro") :String.valueOf(reminder)+ " "+ unidad);
        Mi_textview5.setText("");
        Mi_textview5.append((!lunes.equals("")) ? String.valueOf(lunes) + "/":String.valueOf("")+"");
        Mi_textview5.append((!martes.equals("")) ? String.valueOf(martes) + "/":String.valueOf("")+"");
        Mi_textview5.append((!miercoles.equals("")) ? String.valueOf(miercoles) + "/":String.valueOf("")+"");
        Mi_textview5.append((!jueves.equals("")) ? String.valueOf(jueves) + "/":String.valueOf("")+"");
        Mi_textview5.append((!viernes.equals("")) ? String.valueOf(viernes) + "/":String.valueOf("")+"");
        Mi_textview5.append((!sabado.equals("")) ? String.valueOf(sabado) + "/":String.valueOf("")+"");
        Mi_textview5.append((!domingo.equals("")) ? String.valueOf(domingo) + ".":String.valueOf("")+"");
        }
        catch (Exception exc){
        }
    }

    public void Mensaje2(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    ;

    public void AlertBuilder(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("¿Seguro que desea borrar el tratamiento?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Mensaje2("Tratamiento Eliminado");
                        RemoveObj();
                        //Aca agregamos el metodo para eliminar el tratamiento de la lista.
                        Intent intento = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intento);
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Nada
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    ;

    public void OnclickDelButton(int ref) {
        // Ejemplo  OnclickDelButton(R.id.MiButton);
        // 1 Doy referencia al Button
        View view = findViewById(ref);
        Button miButton = (Button) view;
        //  final String msg = miButton.getText().toString();
        // 2.  Programar el evento onclick
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if(msg.equals("Texto")){Mensaje("Texto en el botón ");};
                switch (v.getId()) {
                    case R.id.bt_detailsPill_edit:
                        //Aca se implementa los PutExtra para enviar a la pantalla de AddPills en modo edicion
                        /*TextView Mi_textview = (TextView) findViewById(R.id.tv_detailsPill_nombreTratamiento);
                        TextView Mi_textview2 = (TextView) findViewById(R.id.tv_detailsPill_dosis2);
                        TextView Mi_textview3 = (TextView) findViewById(R.id.tv_detailsPill_cantidadRestante2);
                        TextView Mi_textview4 = (TextView) findViewById(R.id.tv_detailsPill_reminder2);
                        TextView Mi_textview5 = (TextView) findViewById(R.id.tv_detailsPill_frecuencia2);

                        String dosis = splitNumbers(Mi_textview2.getText().toString());
                        int dosisN = Integer.parseInt(dosis);
                        String cantRestante = splitNumbers(Mi_textview3.getText().toString());
                        int cantRestanteN = Integer.parseInt(cantRestante.replace("es",""));
                        String reminder = splitNumbers(Mi_textview4.getText().toString());
                        int reminderN = Integer.parseInt(reminder);
                        String lunes = Mi_textview5.getText().toString().contains("lunes") ?
                                Mi_textview5.getText().toString().replace("lunes", "1"):
                                Mi_textview5.getText().toString().replace("lunes", "");
                        String martes = Mi_textview5.getText().toString().contains("martes") ?
                                Mi_textview5.getText().toString().replace("martes", "2"):
                                Mi_textview5.getText().toString().replace("martes", "");
                        String miercoles = Mi_textview5.getText().toString().contains("miercoles") ?
                                Mi_textview5.getText().toString().replace("miercoles", "3"):
                                Mi_textview5.getText().toString().replace("miercoles", "");
                        String jueves = Mi_textview5.getText().toString().contains("jueves") ?
                                Mi_textview5.getText().toString().replace("jueves", "4"):
                                Mi_textview5.getText().toString().replace("jueves", "");
                        String viernes = Mi_textview5.getText().toString().contains("viernes") ?
                                Mi_textview5.getText().toString().replace("viernes", "5"):
                                Mi_textview5.getText().toString().replace("viernes", "");
                        String sabado = Mi_textview5.getText().toString().contains("sabado") ?
                                Mi_textview5.getText().toString().replace("sabado", "6"):
                                Mi_textview5.getText().toString().replace("sabado", "");
                        String domingo = Mi_textview5.getText().toString().contains("domingo") ?
                                Mi_textview5.getText().toString().replace("domingo", "7"):
                                Mi_textview5.getText().toString().replace("domingo", "");*/


                        //Transicion de datos
                        modo = 1;
                        Intent intento = new Intent(getApplicationContext(), AddPillActivity.class);
                        intento.putExtra("posicion",posicion);
                        /*intento.putExtra("edicion", 1);
                        intento.putExtra("titulo", Mi_textview.getText());
                        intento.putExtra("dosis", dosisN);
                        intento.putExtra("Unidad", unidadMedida);
                        intento.putExtra("cantidadRestante", cantRestanteN);
                        intento.putExtra("Reminder", reminderN);
                        intento.putExtra("Dia_1", lunes);
                        intento.putExtra("Dia_2", martes);
                        intento.putExtra("Dia_3", miercoles);
                        intento.putExtra("Dia_4", jueves);
                        intento.putExtra("Dia_5", viernes);
                        intento.putExtra("Dia_6", sabado);
                        intento.putExtra("Dia_7", domingo);*/
                        startActivity(intento);
                        break;
                    default:
                        break;
                }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

    private void RemoveObj(){
        final int len = testjarray.length();
        JSONArray list = new JSONArray();
        if (testjarray != null)
            for (int i=0;i<len;i++)
                if (i != posicion)
                    list.put(testjarray.optJSONObject(i));
        writeToFile(list.toString());
    }
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public String splitNumbers(String cadena){
        return cadena.replaceAll("[^0-9]","");
    }
}