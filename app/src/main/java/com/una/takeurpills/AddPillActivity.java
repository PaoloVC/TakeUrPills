package com.una.takeurpills;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class AddPillActivity extends ParentClass implements
        View.OnClickListener {

    Button button;
    private int mHour, mMinute;
    JSONObject jobject;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);
        jobject = new JSONObject();
        CargarSpinner();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.pill_logo);
        getSupportActionBar().setTitle("Add");
        getData();
        OnclickDelButton(R.id.btAddPillsCancelar);
        OnclickDelButton(R.id.btAddPillsSave);
    }

    public void OnclickDelButton(int ref) {
        View view = findViewById(ref);
        Button miButton = (Button) view;


        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btAddPillsSave:
                        Button save = (Button) findViewById(R.id.btAddPillsSave);
                        if (save.getText().toString().equals("Guardar")) {
                            String mili, uni;
                            EditText tituloPastilla = (EditText) findViewById(R.id.et_addPill_titulo);
                            String tituloPastilla1 = tituloPastilla.getText().toString();
                            EditText dosis = (EditText) findViewById(R.id.et_addPill_dosis);
                            String dosis1 = dosis.getText().toString();
                            RadioButton mililitros = (RadioButton) findViewById(R.id.mililitros);

                            RadioButton unidades = (RadioButton) findViewById(R.id.unidades);
                            Spinner spinner = (Spinner) findViewById(R.id.VecesDiarias);

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
                            String vecesDiarias = spinner.getSelectedItem().toString();
                            try {
                                jobject.put("titulo", tituloPastilla1);
                                jobject.put("dosis", dosis1);
                                jobject.put("cantidadRestante", cantidadRestante1);
                                jobject.put("vecesDiarias", vecesDiarias);
                                if (mililitros.isChecked())
                                    jobject.put("Unidad", "Mililitros");
                                if (unidades.isChecked())
                                    jobject.put("Unidad", "Unidades");
                                if (lunes.isChecked()) {
                                    jobject.put("Dia_1", "lunes");
                                }
                                if (martes.isChecked()) {
                                    jobject.put("Dia_2", "martes");
                                }
                                if (miercoles.isChecked()) {
                                    jobject.put("Dia_3", "miercoles");
                                }
                                if (jueves.isChecked()) {
                                    jobject.put("Dia_4", "jueves");
                                }
                                if (viernes.isChecked()) {
                                    jobject.put("Dia_5", "viernes");
                                }
                                if (sabado.isChecked()) {
                                    jobject.put("Dia_6", "sabado");
                                }
                                if (domingo.isChecked()) {
                                    jobject.put("Dia_7", "doming");
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
                        } else {
                            Editar();
                            Intent intento = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intento);
                        }

                        break;

                    case R.id.btAddPillsCancelar:
                        AlertBuilder(v);
                        break;
                    default:
                        break;
                }
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
                LinearLayout ll1 = (LinearLayout) findViewById(R.id.horas_list);
                ll1.removeAllViews();
                for (int i = 0; i < position; i++) {
                    Button myButton = new Button(getApplicationContext());
                    myButton.setText("(+) Agregar Hora");
                    myButton.setId(i);
                    myButton.setOnClickListener(AddPillActivity.this);
                    LinearLayout ll = (LinearLayout) findViewById(R.id.horas_list);
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
        i = v.getId();
        button = (Button) findViewById(v.getId());
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        button.setText(hourOfDay + ":" + minute);
                        try {

                            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            c.set(Calendar.MINUTE,minute);
                            c.set(Calendar.SECOND,0);
                            String text = button.getText().toString();
                            if(jobject == null) jobject = new JSONObject();
                            jobject.put("hora" + String.valueOf(i), text);
                            Intent intent = new Intent(getApplicationContext(),AlarmReceiver.class);
                            final int _id = (int) System.currentTimeMillis();
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                    _id, intent,PendingIntent.FLAG_ONE_SHOT);
                            AlarmManager am =
                                    (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                            am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                                    pendingIntent);
                        } catch (JSONException e) {
                            Log.e("Exception", "Unable to create JSONArray: " + e.toString());
                        }
                        catch (Exception exc){
                            Log.e("Exception", "Unable to create JSONArray: " + exc.toString());
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void Editar() {
        final int len = testjarray.length();
        for (int i = 0; i < len; i++) {
            final JSONObject obj = testjarray.optJSONObject(i);
            try {
                String nombre = String.valueOf(obj.get("titulo"));
                if (String.valueOf(jobject.get("titulo")).toString().equals(nombre)) {
                    EditText tituloPastilla = (EditText) findViewById(R.id.et_addPill_titulo);
                    String tituloPastilla1 = tituloPastilla.getText().toString();

                    EditText dosis = (EditText) findViewById(R.id.et_addPill_dosis);
                    String dosis1 = dosis.getText().toString();
                    RadioButton mililitros = (RadioButton) findViewById(R.id.mililitros);
                    RadioButton unidades = (RadioButton) findViewById(R.id.unidades);
                    Spinner spinner = (Spinner) findViewById(R.id.VecesDiarias);
                    String vecesDiarias = spinner.getSelectedItem().toString();
                    int vecesDiarias2 = Integer.parseInt(vecesDiarias);
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


                    obj.put("titulo", tituloPastilla1);
                    obj.put("dosis", dosis1);
                    obj.put("cantidadRestante", cantidadRestante1);
                    obj.put("vecesDiarias", vecesDiarias);
                    obj.put("Reminder", reminder1);
                    if (mililitros.isChecked())
                        obj.put("Unidad", "Mililitros");
                    if (unidades.isChecked())
                        obj.put("Unidad", "Unidades");
                    if (lunes.isChecked()) {
                        obj.put("Dia_1", "lunes");
                    }
                    else{
                        obj.remove("Dia_1");
                    }
                    if (martes.isChecked()) {
                        obj.put("Dia_2", "martes");
                    }
                    else{
                        obj.remove("Dia_2");
                    }
                    if (miercoles.isChecked()) {
                        obj.put("Dia_3", "miercoles");
                    }
                    else{
                        obj.remove("Dia_3");
                    }
                    if (jueves.isChecked()) {
                        obj.put("Dia_4", "jueves");
                    }
                    else{
                        obj.remove("Dia_4");
                    }
                    if (viernes.isChecked()) {
                        obj.put("Dia_5", "viernes");
                    }
                    else{
                        obj.remove("Dia_5");
                    }
                    if (sabado.isChecked()) {
                        obj.put("Dia_6", "sabado");
                    }
                    else{
                        obj.remove("Dia_6");
                    }
                    if (domingo.isChecked()) {
                        obj.put("Dia_7", "doming");
                    }
                    else{
                        obj.remove("Dia_7");
                    }

                    //Para agregar hora editada
                    /*for (int y = 0; y < vecesDiarias2; y++) {
                        String horas = String.valueOf(obj.get("hora" + i));
                        obj.put("hora" + String.valueOf(y), horas);

                    }*/
                    writeToFile(testjarray.toString());
                    Mensaje("Objeto Modificado con Éxito!");
                }
            } catch (JSONException exc) {
            }
        }
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
        return jarray;
    }

    public void AlertBuilder(View view) {
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
    }

    ;

    public void getData() {
        Intent callingIntent = getIntent();
        int edicion = callingIntent.getIntExtra("edicion", 0);
        int posicion = callingIntent.getIntExtra("posicion", 0);
        JSONObject objjson = testjarray.optJSONObject(posicion);
        jobject = objjson;
        Button save = (Button) findViewById(R.id.btAddPillsSave);
        TextView title = (TextView) findViewById(R.id.tv_addPill_title);
        if (modo == 1) {
            save.setText("Actualizar");
            title.setText("Edite el tratamiento");
            try {
                String titulo = String.valueOf(objjson.get("titulo"));
                int dosis = Integer.parseInt(String.valueOf(objjson.get("dosis")));
                String unidad = String.valueOf(objjson.get("Unidad"));
                int cantidadRestante = Integer.parseInt(String.valueOf(objjson.get("cantidadRestante")));
                int reminder = Integer.parseInt(String.valueOf(objjson.get("Reminder")));
                int vecesDiarias = Integer.parseInt(String.valueOf(objjson.get("vecesDiarias")));
                String lunes = objjson.has("Dia_1")
                        ? String.valueOf(objjson.get("Dia_1"))
                        : "";
                String martes = objjson.has("Dia_2")
                        ? String.valueOf(objjson.get("Dia_2"))
                        : "";
                String miercoles = objjson.has("Dia_3")
                        ? String.valueOf(objjson.get("Dia_3"))
                        : "";
                String jueves = objjson.has("Dia_4")
                        ? String.valueOf(objjson.get("Dia_4"))
                        : "";
                String viernes = objjson.has("Dia_5")
                        ? String.valueOf(objjson.get("Dia_5"))
                        : "";
                String sabado = objjson.has("Dia_6")
                        ? String.valueOf(objjson.get("Dia_6"))
                        : "";
                String domingo = objjson.has("Dia_7")
                        ? String.valueOf(objjson.get("Dia_7"))
                        : "";

                TextView Mi_textview = (TextView) findViewById(R.id.et_addPill_titulo);
                TextView Mi_textview2 = (TextView) findViewById(R.id.et_addPill_dosis);
                TextView Mi_textview3 = (TextView) findViewById(R.id.et_addPill_cantidadRestante);
                TextView Mi_textview4 = (TextView) findViewById(R.id.et_addPill_reminder);
                Spinner spinner = (Spinner) findViewById(R.id.VecesDiarias);

                RadioButton Mi_radiobutton = (RadioButton) findViewById((unidad.equals("Mililitros")
                        ? R.id.mililitros : R.id.unidades));

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
                if (vecesDiarias != 0)
                    spinner.setSelection(vecesDiarias);

                Mi_radiobutton.setChecked(true);
                if (lunes.contains("lunes"))
                    checkBox.setChecked(true);
                if (martes.contains("martes"))
                    checkBox2.setChecked(true);
                if (miercoles.contains("miercoles"))
                    checkBox3.setChecked(true);
                if (jueves.contains("jueves"))
                    checkBox4.setChecked(true);
                if (viernes.contains("viernes"))
                    checkBox5.setChecked(true);
                if (sabado.contains("sabado"))
                    checkBox6.setChecked(true);
                if (domingo.contains("doming"))
                    checkBox7.setChecked(true);
            } catch (JSONException exc) {

            }
        } else {
            save.setText("Guardar");
            title.setText("Ingrese el tratamiento");
        }
    }

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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    ;

}
