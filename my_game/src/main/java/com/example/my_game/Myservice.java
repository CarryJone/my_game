package com.example.my_game;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by bryan on 2017/4/11.
 */

public class Myservice  {
  public Myservice(Context context){
      Log.d("MyDebug","Mysevice");
      getConlist();
      getMathlist();
      getPhotolist();
    }
    private void getConlist(){
        FirebaseModel.congame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseModel.congamelist.clear();
               for(DataSnapshot da : dataSnapshot.getChildren()){
                   UserModel model = da.getValue(UserModel.class);
                   FirebaseModel.congamelist.add(model);
               }
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
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
