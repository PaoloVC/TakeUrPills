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
        int cantidadRestante = callingIntent.getIntExtra("cantidadRestante",0);

        TextView Mi_textview = (TextView) findViewById(R.id.tv_detailsPill_nombreTratamiento);
        TextView Mi_textview2 = (TextView) findViewById(R.id.tv_detailsPill_dosis2);
        TextView Mi_textview3 = (TextView) findViewById(R.id.tv_detailsPill_cantidadRestante2);

        Mi_textview.setText(titulo);
        Mi_textview2.setText((dosis == 1) ? String.valueOf(dosis)+" unidad":String.valueOf(dosis)+" unidades");
        Mi_textview3.setText((cantidadRestante == 1)? String.valueOf(cantidadRestante)+" unidad"
                :String.valueOf(cantidadRestante)+" unidades");
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
                        String dosis = Mi_textview2.getText().toString().contains("unidades") ?
                                Mi_textview2.getText().toString().replace(" unidades","") :
                                Mi_textview2.getText().toString().replace(" unidad","");
                        int dosisN = Integer.parseInt(dosis);
                        String cantRestante = Mi_textview3.getText().toString().contains("unidades") ?
                                Mi_textview3.getText().toString().replace(" unidades","") :
                                Mi_textview3.getText().toString().replace(" unidad","");
                        int cantRestanteN = Integer.parseInt(cantRestante.replace("es",""));
                        Intent intento = new Intent(getApplicationContext(), AddPillActivity.class);
                        intento.putExtra("titulo", Mi_textview.getText());
                        intento.putExtra("dosis", dosisN);
                        intento.putExtra("cantidadRestante", cantRestanteN);
                        startActivity(intento);
                        break;
                    default:
                        break;
                }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

}