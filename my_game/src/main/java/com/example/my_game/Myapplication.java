package com.example.my_game;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by bryan on 2017/3/30.
 */

public class Myapplication extends Application {
    private static Myapplication myapplication;
    private UserModel userModel;
    private FirebaseModel firebaseModel;
    public static Myapplication getInstence(){
        return myapplication;
    }
    private SharedPreferences sp;




    @Override
    public void onCreate() {
        super.onCreate();
        myapplication = this;


        userModel = UserModel.getInstence();
        sp = getSharedPreferences("myname",Context.MODE_PRIVATE);
        islogin();
    }
    public boolean getInternetStatus(){
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        //網路是否可用
        networkInfo.isAvailable();
        // 網路是否已連線(true or false)
        networkInfo.isConnected();
        // 網路連線方式名稱(WIFI or mobile)
        networkInfo.getTypeName();
        // 網路連線狀態(CONNECTED or DISCONNECTED)
        networkInfo.getState();
        return  networkInfo.isConnected();
    }
    public void saveUserName(String name){
        //紀錄name
        userModel.setName(name);
        //將name 記錄到暫存資料
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.commit();
    }
    public boolean islogin(){

        String name = sp.getString("name","");
        Log.d("MyDebug :","islogin"+name);
        if(name.equals("")||name.equals(null)){
            return true;
        }else{
            userModel.setName(name);
            return false;
        }
    }


}
