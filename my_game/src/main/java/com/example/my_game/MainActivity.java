package com.example.my_game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.my_game.congame.Congame;
import com.example.my_game.math.MathGame;
import com.example.my_game.photo.Photo;

public class MainActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        setTitle("小遊戲選單");
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

        }
    }
}


