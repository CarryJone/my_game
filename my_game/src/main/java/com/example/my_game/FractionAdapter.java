package com.example.my_game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bryan on 2017/4/11.
 */

public class FractionAdapter extends BaseAdapter {
    private List<UserModel> list ;
    private Context context ;
    private LayoutInflater li;
    public FractionAdapter(Context context, List<UserModel> list){
        this.list = list;
        this.context = context;
        this.li = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = li.inflate(R.layout.row,parent,false);
        TextView t01,t02,t03;
        t01 = (TextView) convertView.findViewById(R.id.textView9);
        t02 = (TextView) convertView.findViewById(R.id.textView10);
        t03 = (TextView) convertView.findViewById(R.id.textView11);
        UserModel model = list.get(position);
        t01.setText(model.getName());
        t02.setText(model.getFraction());
        t03.setText(model.getTime());


        return convertView;
    }
}
