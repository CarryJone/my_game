package com.example.app_game;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.b01:
//                String num = "456123";
//                Intent intent = new Intent(context , Main2Activity.class);
//                intent.putExtra("num",num);
//                startActivityForResult(intent,101);
                break;
            case R.id.b02:
                break;
            case R.id.b03:
                break;
            case R.id.b04:
                break;

        }
    }
}
