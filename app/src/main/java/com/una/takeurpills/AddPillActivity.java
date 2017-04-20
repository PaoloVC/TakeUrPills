package com.una.takeurpills;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class AddPillActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button button;
    private int mHour, mMinute;
    JSONObject jobject;
    int i;
    static private int edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        jobject= new JSONObject();
        CargarSpinner();
        getData();
        OnclickDelButton(R.id.btAddPillsCancelar);
        OnclickDelButton(R.id.btAddPillsSave);
    }
    public void OnclickDelButton(int ref) {
        View view =findViewById(ref);
        Button miButton = (Button) view;


        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btAddPillsSave:
                        String mili,uni;
                        EditText tituloPastilla = (EditText) findViewById(R.id.et_addPill_titulo);
                        String tituloPastilla1 = tituloPastilla.getText().toString();
                        EditText dosis = (EditText) findViewById(R.id.et_addPill_dosis);
                        String dosis1 = dosis.getText().toString();
                        RadioButton mililitros = (RadioButton) findViewById(R.id.mililitros);

                        RadioButton unidades = (RadioButton) findViewById(R.id.unidades);

                        CheckBox lunes = (CheckBox) findViewById(R.id.lunes);
                        CheckBox martes = (CheckBox) findViewById(R.id.martes);
                        CheckBox miercoles = (CheckBox) findViewById(R.id.miercoles);
                        CheckBox jueves = (CheckBox) findViewById(R.id.jueves);
                        CheckBox viernes = (CheckBox) findViewById(R.id.viernes);
                        CheckBox sabado = (CheckBox) findViewById(R.id.sabado);
                        CheckBox domingo = (CheckBox) findViewById(R.id.domingo);
                        EditText cantidadRestante = (EditText) findViewById(R.id.et_addPill_cantidadRestante);
                        String cantidadRestante1 = cantidadRestante.getText().toString();
                        EditText reminder = (EditText) findViewById(R.id.et_addPill_reminder);
                        String reminder1 = reminder.getText().toString();
                        try {
                            jobject.put("titulo", tituloPastilla1);
                            jobject.put("dosis", dosis1);
                            jobject.put("cantidadRestante", cantidadRestante1);
                            if(mililitros.isChecked())
                                jobject.put("Unidad","mililitros");
                            if(unidades.isChecked())
                                jobject.put("Unidad","Unidades");
                            if (lunes.isChecked()){
                                jobject.put("Dia_1","lunes");
                            }
                            if (martes.isChecked()){
                                jobject.put("Dia_2","martes");
                            }
                            if (miercoles.isChecked()){
                                jobject.put("Dia_3","miercoles");
                            }
                            if (jueves.isChecked()){
                                jobject.put("Dia_4","jueves");
                            }
                            if (viernes.isChecked()){
                                jobject.put("Dia_5","viernes");
                            }
                            if (sabado.isChecked()){
                                jobject.put("Dia_6","sabado");
                            }
                            if (domingo.isChecked()){
                                jobject.put("Dia_7","doming");
                            }
                            jobject.put("Reminder", reminder1);
                            JSONArray jarray = readFromFile();
                            jarray.put(jobject);
                            writeToFile(jarray.toString());
                            Mensaje("Objeto Salvado con Éxito!");
                            Intent intento = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intento);
                        } catch (JSONException e) {
                            Log.e("Exception", "Unable to create JSONArray: " + e.toString());
                        }
                        break;

                    case R.id.btAddPillsCancelar:
                        AlertBuilder(v);
                        break;
                    default:break; }
            }
        });
    }
    private void CargarSpinner() {
        Spinner s1;
        final String[] vecesDiarias = {
                "Veces Diarias",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24"

        };
        //---Spinner View---
        s1 = (Spinner) findViewById(R.id.VecesDiarias);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, vecesDiarias);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                for(int i=0;i<position;i++)
                {
                    Button myButton = new Button(getApplicationContext());
                    myButton.setText("(+) Agregar Hora");
                    myButton.setId(i);
                    myButton.setOnClickListener(AddPillActivity.this);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.horas_list);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.addView(myButton, lp);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        s1.setAdapter(adapter);
    }// fin de CargarSpinner
        @Override
    public void onClick(View v) {
            i=v.getId();
            button=(Button)findViewById(v.getId());
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            button.setText(hourOfDay + ":" + minute);
                            try{
                                String text=button.getText().toString();
                                jobject.put("hora"+i,text);
                            } catch (JSONException e) {
                                Log.e("Exception", "Unable to create JSONArray: " + e.toString());
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

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

    private JSONArray readFromFile() throws JSONException {

        String ret = "";
        JSONArray jarray;

        try {
            InputStream inputStream = getApplicationContext().openFileInput("data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        if(ret == null || ret.isEmpty()){
            jarray = new JSONArray();
        }else{
            jarray = new JSONArray(ret);
        }
        return jarray;
    }

    public void AlertBuilder(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("¿Seguro que deseas cancelar los cambios?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intento = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intento);
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    };

    public void getData(){
        Intent callingIntent = getIntent();
        int edicion = callingIntent.getIntExtra("edicion",0);
        if(edicion == 1) {
            String titulo = callingIntent.getStringExtra("titulo");
            int dosis = callingIntent.getIntExtra("dosis", 0);
            int cantidadRestante = callingIntent.getIntExtra("cantidadRestante", 0);
            int reminder = callingIntent.getIntExtra("Reminder", 0);
            String lunes = callingIntent.getStringExtra("Dia_1");
            String martes = callingIntent.getStringExtra("Dia_2");
            String miercoles = callingIntent.getStringExtra("Dia_3");
            String jueves = callingIntent.getStringExtra("Dia_4");
            String viernes = callingIntent.getStringExtra("Dia_5");
            String sabado = callingIntent.getStringExtra("Dia_6");
            String domingo = callingIntent.getStringExtra("Dia_7");

            TextView Mi_textview = (TextView) findViewById(R.id.et_addPill_titulo);
            TextView Mi_textview2 = (TextView) findViewById(R.id.et_addPill_dosis);
            TextView Mi_textview3 = (TextView) findViewById(R.id.et_addPill_cantidadRestante);
            TextView Mi_textview4 = (TextView) findViewById(R.id.et_addPill_reminder);
            CheckBox checkBox = (CheckBox) findViewById(R.id.lunes);
            CheckBox checkBox2 = (CheckBox) findViewById(R.id.martes);
            CheckBox checkBox3 = (CheckBox) findViewById(R.id.miercoles);
            CheckBox checkBox4 = (CheckBox) findViewById(R.id.jueves);
            CheckBox checkBox5 = (CheckBox) findViewById(R.id.viernes);
            CheckBox checkBox6 = (CheckBox) findViewById(R.id.sabado);
            CheckBox checkBox7 = (CheckBox) findViewById(R.id.domingo);

            Mi_textview.setText(titulo);
            if (dosis != 0)
                Mi_textview2.setText(String.valueOf(dosis));
            if (cantidadRestante != 0)
                Mi_textview3.setText(String.valueOf(cantidadRestante));
            if (reminder != 0)
                Mi_textview4.setText(String.valueOf(reminder));
            if (lunes.contains("1"))
                checkBox.setChecked(true);
            if (martes.contains("2"))
                checkBox2.setChecked(true);
            if (miercoles.contains("3"))
                checkBox3.setChecked(true);
            if (jueves.contains("4"))
                checkBox4.setChecked(true);
            if (viernes.contains("5"))
                checkBox5.setChecked(true);
            if (sabado.contains("6"))
                checkBox6.setChecked(true);
            if (domingo.contains("7"))
                checkBox7.setChecked(true);
        }
    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}
