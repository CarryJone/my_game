package com.example.my_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.my_game.congame.Congame;
import com.example.my_game.math.MathGame;
import com.example.my_game.photo.Photo;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView textView;
    private UserModel muserModel;
    private Myapplication myapplication;
    private long time = 0;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //設定隱藏標題
        getSupportActionBar().hide();
        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        context = this;
        textView = (TextView) findViewById(R.id.tv_title);
        muserModel = UserModel.getInstence();
        myapplication = Myapplication.getInstence();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.b01:
                Intent intent = new Intent(context, MathGame.class);
                startActivity(intent);

                break;
            case R.id.b02:
                Intent intent2 = new Intent(context, Photo.class);
                startActivity(intent2);

                break;
            case R.id.b03:
                Intent intent3 = new Intent(context, Congame.class);
                startActivity(intent3);
                break;
            case R.id.b04:
                Intent intent4 = new Intent(context, Fraction.class);
                startActivity(intent4);
//
                break;
            case R.id.tv_title:
                if((System.currentTimeMillis()-time) < 250) {
                    count++;
                    time = System.currentTimeMillis();
                    Log.d("MyDebug", System.currentTimeMillis() + "");
                    //因為第一下是更新
                     if(count >= 4){
                        Intent i = new Intent(this,UserName.class);
                        startActivity(i);
                        count = 0;
                     }
                }else {
                    time = System.currentTimeMillis();
                    count = 0;
                }


                break;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MyDebug :","mainonpause"+muserModel.getisfirst());
        if(myapplication.islogin()){
            Intent intent = new Intent(context,UserName.class);
            startActivityForResult(intent,101);
        }
        textView.setText("Hi "+muserModel.getName());
    }
}


