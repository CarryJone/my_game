package com.example.my_game.math;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_game.R;

public class MathGame2 extends AppCompatActivity {
    private TextView textView6;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game2);
        editText = (EditText) findViewById(R.id.editText);
        int fraction = getIntent().getIntExtra("fraction",0);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText("共答對"+fraction+"題");

    }
    public void onClick(View view){
        Intent intent = new Intent();
        String name = editText.getText().toString();
        if(name.equals("")|| name == null){
            Toast.makeText(this,"請輸入暱稱" ,Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("name",name);
        setResult(100,intent);

        finish();
    }
}
