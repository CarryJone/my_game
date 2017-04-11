package com.example.my_game.math;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_game.FirebaseModel;
import com.example.my_game.R;
import com.example.my_game.UserModel;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Date;
import java.util.Random;

public class MathGame extends AppCompatActivity {
    private TextView textView, textView2, textView3, textView4;
    private Context context;
    private CountDownTimer timer;
    private Handler handler;
    private int fraction, num, num2;
    private MediaPlayer player;
    private  String name = "tim";
    private SQLiteDatabase db;
    public UserModel muserdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game);
        muserdate = UserModel.getInstence();
        Log.d("MyDebug",muserdate.getName());
        setTitle("算數遊戲");
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        context = this;
        player = MediaPlayer.create(context, R.raw.music);
        handler = new Handler();
        MathDB helper = new MathDB(context, "game.db", null, 1);
        //讀取資料庫
        db = helper.getWritableDatabase();
        //select();


        num = 60000;
        //計時器
        timer = new CountDownTimer(num, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView3.setText(millisUntilFinished / 1000 + "秒");
                if(!player.isPlaying()){
                    player = MediaPlayer.create(context, R.raw.music);
                    player.start();
                }
            }

            @Override
            public void onFinish() {
                if (num2 >= 15) {
                    num2 = 0;
                    Toast.makeText(context, "恭喜超過15題在加60秒", Toast.LENGTH_SHORT).show();
                    textView3.setText(60 + "秒");
                    timer.start();
                } else {
                    Intent intent = new Intent(context, MathGame2.class);
                    intent.putExtra("fraction", fraction);
                    startActivityForResult(intent, 101);

                    player.pause();
                    player.stop();
                }
            }
        };

        //創建資料庫


    }

//    //取得資料
//    private void select(){
//        //  cursor = db.rawQuery("select _id,number from game ",null);
//        cursor =db.rawQuery( "select * from game order by number desc LIMIT 10 ",null); //number 以大到小排序
//        //Toast.makeText(context,cursor.getCount()+"筆",Toast.LENGTH_SHORT).show();
//    }
//    public void onClick2(View view){
//        select();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("分數排行榜");
//        adapter = new SimpleCursorAdapter(context,R.layout.row,cursor,new String[] {"_id","number"},new int[] {R.id.textView9,R.id.textView10}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//
//        ListView listview = new ListView(context);
//        listview.setAdapter(adapter);
//        builder.setView(listview);
//        builder.setPositiveButton("確定",null);
//        builder.show();
//
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        name = data.getStringExtra("name").toString();
//        Object[] arge = {name,fraction};
//        db.execSQL("insert into game(name,number) values (?,?)", arge);
        UserModel model = UserModel.getInstence();
        model.setFraction(fraction+"");
        model.setTime(new Date().toString());
        FirebaseModel.mathgamelist.add(model);
        FirebaseModel.mathgame.setValue(FirebaseModel.mathgamelist);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem m1 = menu.add(1, 1, 1, "開始遊戲");
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItem m2 = menu.add(2, 2, 2, "遊戲說明");
        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                player.start();
                timer.cancel();
                timer.start();
                handler.postDelayed(r, 0);
                fraction = 0;
                textView.setText("目前分數:" + fraction + "分");
                break;
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("遊戲說明");
                builder.setMessage("遊戲時間60秒\n60秒內答題超過15題\n時間在加60秒\n時間到達題數不超過15題\n遊戲結束!!!");
                builder.setNegativeButton("確定", null);
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.point:
                textView4.setText("");
                break;
            case R.id.enter:

                double answer = new ExpressionBuilder(textView2.getText().toString()).build().evaluate();
                if (textView4.getText().toString().equals("")) {
                    return;
                }
                int contanswer = Integer.parseInt(textView4.getText().toString());

                if (answer == contanswer) {
                    handler.postDelayed(r, 0);
                    textView4.setText("");
                    fraction++;
                    num2++;
                    textView.setText("目前分數:" + fraction + "分");
                } else {
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
                textView4.append(((Button) view).getText().toString());
        }
    }






    Runnable r = new Runnable() {
        @Override
        public void run() {
            int num = new Random().nextInt(4)+1;
            int a = 0;
            int b = 0;
            switch (num){
                case 1:
                    a = new Random().nextInt(50) + 1;
                    b = new Random().nextInt(50) + 1;
                    textView2.setText(a + "+" + b);

                    break;
                case 2:
                    do{
                        a = new Random().nextInt(50) + 1;
                        b = new Random().nextInt(50) + 1;
                    }while ((a-b)<=0);
                    textView2.setText(a + "-" + b);

                    break;
                case 3:
                    a = new Random().nextInt(9)+1;
                    b = new Random().nextInt(20) + 1;
                    textView2.setText(a + "*" + b);
                    break;
                case 4:
                    do{
                        a = new Random().nextInt(100) + 1;
                        b = new Random().nextInt(100) + 2;
                    }while((a%b)!=0);
                    textView2.setText(a + "/" + b);
                    break;
            }

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        player.stop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player = MediaPlayer.create(context, R.raw.music);
//        player.start();

        timer.cancel();

//        timer.start();
        textView4.setText("");
//        handler.postDelayed(r,0);
        fraction = 0;
        textView3.setText("0秒");
        textView2.setText("");
        textView.setText("目前分數:" + fraction + "分");
    }
}
