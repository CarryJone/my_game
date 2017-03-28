package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);

    }
    public void onClick(View view){
        String num = editText.getText().toString();
        Intent intent = new Intent(this , Main2Activity.class);
        intent.putExtra("num",num);
        startActivityForResult(intent,101);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {





        return super.onCreateOptionsMenu(menu);
    }
}
