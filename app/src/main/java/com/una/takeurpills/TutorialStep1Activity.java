package com.una.takeurpills;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TutorialStep1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_step1);
        TextView saltar = (TextView) findViewById(R.id.tvStepsSaltar);
        saltar.setPaintFlags(saltar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
