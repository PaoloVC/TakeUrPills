package com.una.takeurpills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.onClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        OnClickButton(R.id.btListPills);
        OnClickButton(R.id.btAddPills);
        OnClickButton(R.id.btFindPills);
    }// fin onCreate
    public void OnClickButton(int ref){
        View view = findViewById(ref);
        Button miButton = (Button) view;
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btListPills:
                        break;
                    case R.id.btAddPills:
                        Intent intento = new Intent(getApplicationContext(), AddPillActivity.class);
                        startActivity(intento);
                        break;
                    case R.id.btFindPills:
                        break;
                    default: break;
                }// fin switch
            }// fin onClick
        });
    }// fin OnClickButton
}// fin Activity
