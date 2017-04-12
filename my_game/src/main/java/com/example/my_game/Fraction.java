package com.example.my_game;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.my_game.congame.ConDB;
import com.example.my_game.math.MathDB;
import com.example.my_game.photo.PhotoDB;

public class Fraction extends AppCompatActivity {
    private Context context;
    private int _id =-1;
    private Handler handler;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction);
        context = this;
        setTitle("分數查詢");
        handler = new Handler();
        dialog = new ProgressDialog(context);

    }


    public void math(){
        //創建資料庫
        MathDB helper = new MathDB(context,"game.db",null,1);
        //讀取資料庫
        final SQLiteDatabase db = helper.getWritableDatabase();
        final Cursor cursor =db.rawQuery( "select * from game order by number desc LIMIT 10 ",null); //number 以大到小排序
         SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                R.layout.row,
                cursor,
                new String[] {"name","number","time"},
                new int[] {R.id.textView9,R.id.textView10,R.id.textView11},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
         ListView listview = (ListView) findViewById(R.id.math);
         listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("刪除資料");
                builder.setMessage("是否要刪除"+cursor.getString(1)+"資料");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _id = cursor.getInt(0);
                        Object[] a3 = {_id};
                        db.execSQL("delete from game where _id= ?",a3);
                        math();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return false;
            }
        });





    }
    public void photo(){
        //創建資料庫
        PhotoDB helper2 = new PhotoDB(context,"photo.db",null,1);
        //讀取資料庫
        final SQLiteDatabase db2 = helper2.getWritableDatabase();
        final Cursor cursor2 =db2.rawQuery( "select * from photo order by number asc LIMIT 10 ",null); //number 以大到小排序
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
                R.layout.row,
                cursor2,
                new String[] {"name","number","time"},
                new int[] {R.id.textView9,R.id.textView10,R.id.textView11},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView listview2 = (ListView) findViewById(R.id.photo);
        listview2.setAdapter(adapter);
        listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("刪除資料");
                builder.setMessage("是否要刪除"+cursor2.getString(1)+"資料");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _id = cursor2.getInt(0);
                        Object[] a3 = {_id};
                        db2.execSQL("delete from photo where _id= ?",a3);
                        photo();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return false;
            }
        });


    }
    public void continu(){
        //創建資料庫
        ConDB helper3 = new ConDB(context,"continu.db",null,1);
        //讀取資料庫
        final SQLiteDatabase db3 = helper3.getWritableDatabase();
        final Cursor cursor3 =db3.rawQuery( "select * from continu order by number asc LIMIT 10 ",null); //number 以小到大排序
        final SimpleCursorAdapter adapter3 = new SimpleCursorAdapter(context,
                R.layout.row,
                cursor3,
                new String[] {"name","number","time"},
                new int[] {R.id.textView9,R.id.textView10,R.id.textView11},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        final ListView listview3 = (ListView) findViewById(R.id.continuous1);
        listview3.setAdapter(adapter3);
        listview3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("刪除資料");
                builder.setMessage("是否要刪除"+cursor3.getString(1)+"資料");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _id = cursor3.getInt(0);
                        Object[] a3 = {_id};
                        db3.execSQL("delete from continu where _id= ?",a3);
                        continu();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return false;
            }
        });

    }

    public void mathgame(){
        Log.d("MyDebug","Fraction mathgame");
        final FractionAdapter adapter = new FractionAdapter(context,FirebaseModel.mathgamelist);
        ListView listView = (ListView) findViewById(R.id.math);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("刪除紀錄");
                builder.setMessage("是否刪除紀錄");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseModel.mathgamelist.remove(position);
                        FirebaseModel.upmathgame();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("取消",null);
                builder.show();
            }
        });


    }
    public void photogame(){
        final FractionAdapter adapter = new FractionAdapter(context,FirebaseModel.phpotogamelist);
        ListView listView = (ListView) findViewById(R.id.photo);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("刪除紀錄");
                builder.setMessage("是否刪除紀錄");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseModel.phpotogamelist.remove(position);
                        FirebaseModel.upphpotogame();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("取消",null);
                builder.show();
            }
        });
        adapter.notifyDataSetChanged();
    }
    public void congame(){
        final FractionAdapter adapter = new FractionAdapter(context,FirebaseModel.congamelist);
        ListView listView = (ListView) findViewById(R.id.continuous1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("刪除紀錄");
                builder.setMessage("是否刪除紀錄");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseModel.congamelist.remove(position);
                        FirebaseModel.upcongame();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("取消",null);
                builder.show();
            }
        });
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(isdata,0);
        dialog.setTitle("請稍後");
        dialog.setMessage("資料讀取中");
        dialog.show();
    }

    private Runnable isdata = new Runnable() {
        @Override
        public void run() {
            if(!FirebaseModel.issetdata()){
                handler.postDelayed(isdata,1000);
            }else{
                handler.removeCallbacks(isdata);
                dialog.dismiss();
                mathgame();
                photogame();
                congame();
            }
        }
    };
}
