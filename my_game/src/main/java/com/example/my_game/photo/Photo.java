package com.example.my_game.photo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_game.R;
import com.example.my_game.UserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.my_game.R.drawable.t03;

public class Photo extends AppCompatActivity {
    private Context context;
    private List<Map<String, Object>> list;
    private PhotoAdapter adapter;
    private SimpleCursorAdapter adapter1;
    private int nextInt = 0, id1 = 100;
    private String data;
    private GridView gv01;
    private Chronometer chronometer;
    private ImageView t01, t02;
    private SQLiteDatabase db;
    private int num = 3;
    private MediaPlayer mp;
    private UserModel model;
    final int[] ar = {R.drawable.cat, R.drawable.dog, R.drawable.images, R.drawable.t01, R.drawable.t02, t03
            , R.drawable.t04, R.drawable.t05, R.drawable.t06, R.drawable.t07}; //圖片
    final int[] ar1 = {R.drawable.b01,R.drawable.b02,R.drawable.b03,R.drawable.b04,R.drawable.b05,R.drawable.b06,R.drawable.b07,R.drawable.b08,R.drawable.b09,R.drawable.b10};
    final String[] str = {"cat", "dog", "t08", "t01", "t02", "t03", "t04", "t05", "t06", "t07"}; //名稱 再點的時候可以比對
    private Handler handler;
    private int count = 0;
    private TextView textView;
    public CountDownTimer ctime;
    private Map<String,Object> map2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        context = this;
        model = UserModel.getInstence();
        setTitle("找相同圖");
        chronometer = (Chronometer) findViewById(R.id.chronometer2);
        list = new ArrayList<>();
        handler = new Handler();
        gv01 = (GridView) findViewById(R.id.gv01);
        textView = (TextView) findViewById(R.id.textView20);
        ctime = new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count = -1;
                textView.setVisibility(View.VISIBLE);
                int n = ((int)millisUntilFinished/1000);
                if(n != 1) {
                    textView.setText(millisUntilFinished / 1000 + "");
                }else{
                    textView.setText("1");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           textView.setText("Go");
                        }
                    },500);
                }
            }

            @Override
            public void onFinish() {
                count =0;
                textView.setText(0+"");
                textView.setVisibility(View.GONE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        };



        PhotoDB gameDB = new PhotoDB(context,"photo.db",null,1);
        db = gameDB.getWritableDatabase();
        play();

        gv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, Object> map = list.get(position);

                String data2 = map.get("str").toString();
                String data3 = map.get("str").toString();


                if (count !=-1 &&!data2.equals("123")&&!data3.equals("123")) {
                    count++;
                    handler = new Handler();


                    int photo = (Integer) map.get("image");
                    t01 = (ImageView) view;
                    t01.setImageResource(photo);


                    if (data2.equals(data) && id != id1 && !data2.equals("123")) {
                        nextInt++;
                        map.put("str","123");
                        if(map2 !=null){
                            map2.put("str","123");
                        }

                        t01.setImageResource(photo);
                        t02.setImageResource((Integer) map2.get("image"));
                        mp =  MediaPlayer.create(context,R.raw.striking);
                        count = 0;
                        mp.start();
                        if (nextInt == 10) {
                            String temp = chronometer.getText().toString();
                            chronometer.stop();
                            Toast.makeText(context, temp + "恭喜完成遊戲", Toast.LENGTH_LONG).show();
                            store(temp);

                        }
                    }

                    data = map.get("str").toString();
                    id1 = (int) id;

                    if(count == 2 ){

                        seephoto(t01,t02);
                        t02 =null;
                        data = "";
                        id1 = -1;
                        count = 0;
                    }
                    t02 = (ImageView) view;
                    map2 = map;
                }

            }
        });
    }
    public void seephoto(final ImageView t01,final ImageView t02){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t01.setImageResource(R.drawable.ipod);
                t02.setImageResource(R.drawable.ipod);

            }
        },1000);
    }


    public void play(){
        ctime.start();
        list.clear();
        num = new Random().nextInt(10)+1;
        for (int i = 0; i < ar1.length; i++) {
            Map<String, Object> imet = new HashMap<String, Object>();
            if(num%2 == 0) {
                imet.put("image", ar1[i]);
            }else {
                imet.put("image", ar[i]);
            }
            imet.put("str", str[i]);
            list.add(imet);
            list.add(imet);
        }//將資料轉成Map放入list



        //context, 要放的東西 , 放東西的容器 , 要放的東西名子  , 要放的容器 名子順序與容器照排會對應到相對得位子
        adapter = new PhotoAdapter(list,context);
//        adapter = new SimpleAdapter(context, list, R.layout.imrow, new String[]{num>=5?"image":"image2"}, new int[]{R.id.imageView});
////        adapter.notifyDataSetChanged();
        gv01.setAdapter(adapter);
        Collections.shuffle(list);//洗牌
        adapter.notifyDataSetChanged();//更新排序畫面
        chronometer.stop();
        ctime.cancel();
        ctime.start();

        nextInt = 0;
        data = "";

    }

    public void onClick(View view) {
        play();
    }
    public void store(final String temp){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
//        final EditText editText = new EditText(context);
//        editText.setHint("請輸入暱稱");
//        builder.setView(editText);
        builder.setTitle("恭喜過關");
        builder.setMessage("所花時間 "+temp);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = model.getName();
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
