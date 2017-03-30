package com.example.my_game.math;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.my_game.R;
import com.example.my_game.UserModel;

public class MathGame2 extends AppCompatActivity {
    private TextView textView6;
    private UserModel muserdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game2);
        int fraction = getIntent().getIntExtra("fraction",0);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText("共答對"+fraction+"題");
        muserdate = UserModel.getInstence();

    }
    public void onClick(View view){
        Intent intent = new Intent();
        String name = muserdate.getName();

        intent.putExtra("name",name);
        setResult(100,intent);

        finish();
    }
}
