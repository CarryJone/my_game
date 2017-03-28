package com.example.app_game.math;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_game.R;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Random;


public class Mathgame1 extends AppCompatActivity {
    private TextView textView,textView2,textView3,textView4;
    private Context context;
    private CountDownTimer timer;
    private Handler handler;
    private int fraction,num;
    private MediaPlayer player;

    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathgame1);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        context = this;
        player = MediaPlayer.create(context,R.raw.music);
        handler = new Handler();

        //計時器
        timer = new CountDownTimer(60_000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView3.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(context,Mathgame2.class);
                intent.putExtra("fraction",fraction);
                startActivityForResult(intent,101);
                Object[] arge = {fraction};
                db.execSQL("insert into game(number) values (?)",arge);
                player.pause();
                player.stop();

            }
        };

        //創建資料庫
        MathDB helper = new MathDB(context,"game.db",null,1);
        //讀取資料庫
        db = helper.getWritableDatabase();
        select();

    }

    //取得資料
    private void select(){
        //  cursor = db.rawQuery("select _id,number from game ",null);
        cursor =db.rawQuery( "select * from game order by number desc LIMIT 10 ",null); //number 以大到小排序
        //Toast.makeText(context,cursor.getCount()+"筆",Toast.LENGTH_SHORT).show();
    }
    public void onClick2(View view){
        select();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("分數排行榜");
        adapter = new SimpleCursorAdapter(context,R.layout.row,cursor,new String[] {"_id","number"},new int[] {R.id.textView9,R.id.textView10}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        ListView listview = new ListView(context);
        listview.setAdapter(adapter);
        builder.setView(listview);
        builder.setPositiveButton("確定",null);
        builder.show();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        num = getIntent().getIntExtra("num",14);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem m1 = menu.add(1,1,1,"重新開始");
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:

                player.start();



                timer.cancel();
                timer.start();
                handler.postDelayed(r,0);
                fraction=0;
                textView.setText("目前分數:"+fraction+"分");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.point:
                textView4.setText("");
                break;
            case R.id.enter:
                //使用計算API
                double answer = new ExpressionBuilder(textView2.getText().toString()).build().evaluate();
                if (textView4.getText().toString().equals("")){
                    return;
                }
                int num = Integer.parseInt(textView4.getText().toString());
                if(answer ==num){
                    handler.postDelayed(r,0);
                    textView4.setText("");
                    fraction++;
                    textView.setText("目前分數:"+fraction+"分");
                }else {
                    textView4.setText("");
                    Toast.makeText(context, "答錯囉", Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.button4:
//            case R.id.button3:
//                player.start();
//                timer.start();
//                handler.postDelayed(r,0);
//                fraction=0;
//                textView.setText("目前分數:"+fraction+"分");
//                break;
            default:

                //點選數字出現在textview上
                textView4.append(((Button)view).getText().toString());
        }
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            int a = new Random().nextInt(10)+1;
            int b = new Random().nextInt(10)+1;
            textView2.setText(a+"+"+b);
            player.start();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player = MediaPlayer.create(context,R.raw.music);
        player.start();

        timer.cancel();
        timer.start();
        textView4.setText("");
        handler.postDelayed(r,0);
        fraction=0;
        textView.setText("目前分數:"+fraction+"分");
    }
}
