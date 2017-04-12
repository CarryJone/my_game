package com.example.my_game;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by bryan on 2017/4/11.
 */

public class Myservice extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        getConlist();
        getMathlist();
        getPhotolist();
        Log.d("MyDebug","確認是否得到資料"+FirebaseModel.congamelist.size()+"");

    }
    private void getConlist(){
        Log.d("MyDebug","Myservice getConlist");
        FirebaseModel.congame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseModel.congamelist.clear();
               for(DataSnapshot da : dataSnapshot.getChildren()){
                   UserModel model = da.getValue(UserModel.class);
                   FirebaseModel.congamelist.add(model);
               }
                Collections.sort(FirebaseModel.congamelist, new Comparator<UserModel>() {
                    @Override
                    public int compare(UserModel o1, UserModel o2) {
                        return o1.getFraction().compareTo(o2.getFraction()) ;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void getMathlist(){
        FirebaseModel.mathgame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseModel.mathgamelist.clear();
                for(DataSnapshot da : dataSnapshot.getChildren()){
                    UserModel model = da.getValue(UserModel.class);
                    FirebaseModel.mathgamelist.add(model);
                }
                Collections.sort(FirebaseModel.mathgamelist, new Comparator<UserModel>() {
                    @Override
                    public int compare(UserModel o1, UserModel o2) {
                        return o1.getFraction().compareTo(o2.getFraction()) ;
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void getPhotolist(){
        FirebaseModel.photogame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseModel.phpotogamelist.clear();
                for(DataSnapshot da: dataSnapshot.getChildren()){
                    UserModel model = da.getValue(UserModel.class);
                    FirebaseModel.phpotogamelist.add(model);
                }
                Collections.sort(FirebaseModel.phpotogamelist, new Comparator<UserModel>() {
                    @Override
                    public int compare(UserModel o1, UserModel o2) {
                        return o1.getFraction().compareTo(o2.getFraction()) ;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
