package com.example.my_game.congame;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import com.example.my_game.FirebaseModel;
import com.example.my_game.Myapplication;
import com.example.my_game.R;
import com.example.my_game.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Congame extends AppCompatActivity {
    private Context context;
    private GridView gridView;
    private List<Integer> list;
    private UserModel model;
    private ArrayAdapter<Integer> adapter;
    private Chronometer chronometercon;
    private int num = 1;
    private SoundPool soundPool;
    private int select09,jump01;
    private SQLiteDatabase db;
    private UserModel muserdate;
    private int data2 = 1;
    private Myapplication myapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congame);
        context = this;


        myapplication = Myapplication.getInstence();
        muserdate = UserModel.getInstence();



        setTitle("照順序點");
        gridView = (GridView) findViewById(R.id.gridviewcon1);
        chronometercon = (Chronometer) findViewById(R.id.chronometercon1);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,10);
        select09 = soundPool.load(context,R.raw.select09,1);
        jump01 = soundPool.load(context,R.raw.jump01,1);
        ConDB helper = new ConDB(context, "continu.db", null, 1);
        //讀取資料庫
        db = helper.getWritableDatabase();
        list = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            list.add(i);
        }
        play();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int data = (Integer) parent.getItemAtPosition(position);
                TextView textView = (TextView) view;
                Log.d("mycongame","data:"+data+
                        "\ndata2:"+data2+
                        "\ntextview:"+Integer.parseInt(textView.getText().toString())+
                        "\nnum:"+num);
                if (data == num ||data+25 ==num ) {
//                    if(data2 == 5){
//                        gameover();
//                    }

                    soundPool.play(select09,1,1,0,0,1);
                    num++;
                    if(data2 <=25 ){
                        textView.setText(data+25+"");
                        textView.setTextColor(Color.BLUE);
                    }else if(data2 >25 && data2 < 50){
                        textView.setTextColor(Color.RED);
                    }else if(data2 == 50){
                        textView.setTextColor(Color.RED);
                        gameover();
                    }
                    data2++;
                }else{
                    soundPool.play(jump01,1,1,0,0,1);
                }
            }
        });
    }
    public void gameover(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("恭喜過關");
        builder.setIcon(R.drawable.drum);
        builder.setMessage("共花了"+chronometercon.getText().toString()+"秒完成");
//                        final EditText editText = new EditText(context);
//                        editText.setHint("請輸入暱稱");
//                        builder.setView(editText);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = muserdate.getName();
                if(name != null || name.equals("")) {
//                    Object[] arge = {name,chronometercon.getText().toString()};
//                    db.execSQL("insert into continu(name,number) Values(?,?)",arge);
                    model = UserModel.getInstence();
                    model.setFraction(chronometercon.getText().toString());
                    model.setTime(new Date().toString());
                    FirebaseModel.congamelist.add(model);
                    FirebaseModel.upcongame();
                }
            }
        });
        builder.show();
        chronometercon.stop();
        num = 1;
    }

    public void play() {
        adapter = new ArrayAdapter<Integer>(context,R.layout.textrow, list);
        //android.R.layout.simple_dropdown_item_1line中間可以放這個R.layout.row
        gridView.setAdapter(adapter);
        Collections.shuffle(list);
        adapter.notifyDataSetChanged();
        chronometercon.setBase(SystemClock.elapsedRealtime());
        chronometercon.start();
        num = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu m1 = menu.addSubMenu(1,1,1,"遊戲選單");
        m1.add(1,101,1,"在挑戰一次");
        m1.add(1,102,2,"遊戲說明");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case 101:
                play();
                break;
            case 102:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("遊戲說明");
                TextView textView = new TextView(context);
                builder.setView(textView);
                textView.setText("照順序由1點到25");

                builder.show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}

