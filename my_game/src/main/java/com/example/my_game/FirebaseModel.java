package com.example.my_game;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan on 2017/4/11.
 */

public class FirebaseModel {
    //model 實例
    private static FirebaseModel firebaseModel = new FirebaseModel();
    //取得firebase
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference congame = database.getReference("congame");
    public static DatabaseReference mathgame = database.getReference("mathgame");
    public static DatabaseReference photogame = database.getReference("photogame");

    //建立分數資料容器
    public static List<UserModel> congamelist = new ArrayList<>();
    public static List<UserModel> mathgamelist = new ArrayList<>();
    public static List<UserModel> phpotogamelist = new ArrayList<>();

    public static boolean issetdata(){
        if(congamelist.size()>0||mathgamelist.size()>0||phpotogamelist.size()>0){
            return true;
        }else{
            return false;
        }
    }

    public static FirebaseModel getInstence(){return firebaseModel;}
    public static void upcongame(){congame.setValue(congamelist);}
    public static void upmathgame(){mathgame.setValue(mathgamelist);}
    public static void upphpotogame(){photogame.setValue(phpotogamelist);}
}
