package com.example.my_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class UserName extends AppCompatActivity {
    private EditText editText;
    private Myapplication myapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        myapplication = Myapplication.getInstence();
        Log.d("MyDebug :","nameActivityonCreate");
    }

    public void onClick(View view){
        editText = (EditText) findViewById(R.id.username);
        myapplication.saveUserName(editText.getText().toString());
        Intent intent = new Intent();
        setResult(101,intent);
        finish();
    }
}
