package com.example.my_game.photo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.my_game.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Photo extends AppCompatActivity {
    private Context context;
    private List<Map<String, Object>> list;
    private SimpleAdapter adapter;
    private SimpleCursorAdapter adapter1;
    private int nextInt = 0, id1 = 100;
    private String data;
    private GridView gv01;
    private Chronometer chronometer;
    private ImageView t01, t02;
    private SQLiteDatabase db;
    private int num = 3;
    private MediaPlayer mp;
    final int[] ar = {R.drawable.cat, R.drawable.dog, R.drawable.images, R.drawable.t01, R.drawable.t02, R.drawable.t03
            , R.drawable.t04, R.drawable.t05, R.drawable.t06, R.drawable.t07}; //圖片
    final int[] ar1 = {R.drawable.b01,R.drawable.b02,R.drawable.b03,R.drawable.b04,R.drawable.b05,R.drawable.b06,R.drawable.b07,R.drawable.b08,R.drawable.b09,R.drawable.b10};
    final String[] str = {"cat", "dog", "t08", "t01", "t02", "t03", "t04", "t05", "t06", "t07"}; //名稱 再點的時候可以比對
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        context = this;
        setTitle("找相同圖");
        chronometer = (Chronometer) findViewById(R.id.chronometer2);
        list = new ArrayList<>();

        gv01 = (GridView) findViewById(R.id.gv01);

        PhotoDB gameDB = new PhotoDB(context,"photo.db",null,1);
        db = gameDB.getWritableDatabase();
        for (int i = 0; i < ar1.length; i++) {
            Map<String, Object> imet = new HashMap<String, Object>();
            imet.put("image",ar1[i] );
            imet.put("image2",ar[i] );
            imet.put("str", str[i]);
            list.add(imet);
            list.add(imet);
        }//將資料轉成Map放入list
//

        //context, 要放的東西 , 放東西的容器 , 要放的東西名子  , 要放的容器 名子順序與容器照排會對應到相對得位子


        play();





        gv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = list.get(position);
                String data2 = map.get("str").toString();
                t01 = (ImageView) view;
                if (data2.equals(data) && id != id1) {
                    nextInt++;
                    t01.setImageResource(R.drawable.ok);
                    t02.setImageResource(R.drawable.ok);
                    mp =  MediaPlayer.create(context,R.raw.striking);
                    mp.start();
                    if (nextInt == 10) {
                        String temp = chronometer.getText().toString();
                        chronometer.stop();
                        Toast.makeText(context, temp + "恭喜完成遊戲", Toast.LENGTH_LONG).show();
                        store(temp);

                    }
                }
                t02 = (ImageView) view;
                data = map.get("str").toString();
                id1 = (int) id;
            }
        });

    }

    public void play() {
        num = new Random().nextInt(10)+1;
        adapter = new SimpleAdapter(context, list, R.layout.imrow, new String[]{num>=5?"image":"image2"}, new int[]{R.id.imageView});
        adapter.notifyDataSetChanged();
        gv01.setAdapter(adapter);
        Collections.shuffle(list);//洗牌
        adapter.notifyDataSetChanged();//更新排序畫面
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        nextInt = 0;
        data = "";

    }

    public void onClick(View view) {
        play();
    }
    public void store(final String temp){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText editText = new EditText(context);
        editText.setHint("請輸入暱稱");
        builder.setView(editText);
        builder.setTitle("恭喜過關");
        builder.setMessage("所花時間 "+temp);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                if(name != null || name.equals("")) {
                    Object[] arge = {name,temp};
                    db.execSQL("insert into photo(name,number) Values(?,?)",arge);
                }
            }
        });
        builder.setNegativeButton("取消",null);

        builder.show();

    }
    public void onClick2(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("分數查詢");
        Cursor cursor = db.rawQuery("SELECT _id, name, number, strftime('%Y/%m/%d %H:%M:%S', time) as 'time' FROM photo", null);
        Log.d("curry",cursor.getColumnCount()+"");

        adapter1 = new SimpleCursorAdapter(context,
                R.layout.row,
                cursor,
                new String[] {"name","number","time"},
                new int[] {R.id.textView9,R.id.textView10,R.id.textView11},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        ListView listView = new ListView(context);
        builder.setView(listView);
        listView.setAdapter(adapter1);
        builder.setNegativeButton("取消",null);
        builder.show();




    }
}
