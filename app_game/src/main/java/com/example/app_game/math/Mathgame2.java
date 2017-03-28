package com.example.app_game.math;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.app_game.R;

public class Mathgame2 extends AppCompatActivity {
    private TextView textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathgame2);
        int fraction = getIntent().getIntExtra("fraction",0);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText("共答對"+fraction+"題");

    }

    public void onClick(View view){
        Intent intent = new Intent();
        intent.putExtra("num",100);
        setResult(100,intent);

        finish();
    }
}
