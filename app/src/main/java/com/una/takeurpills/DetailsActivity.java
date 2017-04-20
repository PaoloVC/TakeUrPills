package com.una.takeurpills;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    private String unidadMedida = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getData();
        Mensaje("Detalles del Tratamiento");
        Button cancelar = (Button) findViewById(R.id.bt_detailsPill_delete);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertBuilder(v);
            }
        });

        OnclickDelButton(R.id.bt_detailsPill_edit);
    } // Fin del Oncreate

    public void Mensaje(String msg) {
        getSupportActionBar().setTitle(msg);
    }

    public void getData(){
        Intent callingIntent = getIntent();
        String titulo = callingIntent.getStringExtra("titulo");
        int dosis = callingIntent.getIntExtra("dosis",0);
        String unidad = callingIntent.getStringExtra("Unidad");
        unidadMedida = unidad;
        int cantidadRestante = callingIntent.getIntExtra("cantidadRestante",0);
        int reminder = callingIntent.getIntExtra("Reminder", 0);
        String lunes = callingIntent.getStringExtra("Dia_1");
        String martes = callingIntent.getStringExtra("Dia_2");
        String miercoles = callingIntent.getStringExtra("Dia_3");
        String jueves = callingIntent.getStringExtra("Dia_4");
        String viernes = callingIntent.getStringExtra("Dia_5");
        String sabado = callingIntent.getStringExtra("Dia_6");
        String domingo = callingIntent.getStringExtra("Dia_7");

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
        Mi_textview5.append((lunes != null) ? String.valueOf(lunes) + "/":String.valueOf("")+"");
        Mi_textview5.append((martes != null) ? String.valueOf(martes) + "/":String.valueOf("")+"");
        Mi_textview5.append((miercoles != null) ? String.valueOf(miercoles) + "/":String.valueOf("")+"");
        Mi_textview5.append((jueves != null) ? String.valueOf(jueves) + "/":String.valueOf("")+"");
        Mi_textview5.append((viernes != null) ? String.valueOf(viernes) + "/":String.valueOf("")+"");
        Mi_textview5.append((sabado != null) ? String.valueOf(sabado) + "/":String.valueOf("")+"");
        Mi_textview5.append((domingo != null) ? String.valueOf(domingo) + ".":String.valueOf("")+"");
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
                        TextView Mi_textview = (TextView) findViewById(R.id.tv_detailsPill_nombreTratamiento);
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
                                Mi_textview5.getText().toString().replace("domingo", "");


                        //Transicion de datos
                        Intent intento = new Intent(getApplicationContext(), AddPillActivity.class);
                        intento.putExtra("edicion", 1);
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
                        intento.putExtra("Dia_7", domingo);
                        startActivity(intento);
                        break;
                    default:
                        break;
                }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton
    public String splitNumbers(String cadena){
        return cadena.replaceAll("[^0-9]","");
    }
}