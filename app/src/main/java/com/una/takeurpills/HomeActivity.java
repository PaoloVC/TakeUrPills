package com.una.takeurpills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        OnClickButton(R.id.btListPills);
        OnClickButton(R.id.btAddPills);
        OnClickButton(R.id.btFindPills);
        OnClickButton(R.id.btAbout);
    }// fin onCreate
    public void OnClickButton(int ref){
        View view = findViewById(ref);
        Button miButton = (Button) view;
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btListPills:
                        Intent intento2 = new Intent(getApplicationContext(), ListPillsActivity.class);
                        startActivity(intento2);
                        break;
                    case R.id.btAddPills:
                        Intent intento = new Intent(getApplicationContext(), AddPillActivity.class);
                        startActivity(intento);
                        break;
                    case R.id.btFindPills:
                        Intent intento3 = new Intent(getApplicationContext(), FindPillsActivity.class);
                        startActivity(intento3);
                        break;
                    case R.id.btAbout:
                        Intent intento4 = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intento4);
                        break;
                    default: break;
                }// fin switch
            }// fin onClick
        });
    }// fin OnClickButton
}// fin Activity
