package com.example.my_game.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.my_game.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 2017/4/3.
 */

public class PhotoAdapter extends BaseAdapter {
    private List<Map<String, Object>> list;
    private Context mcontext;
    private android.os.Handler handler;

    public PhotoAdapter(List<Map<String, Object>> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
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
        handler = new android.os.Handler();
        View v = LayoutInflater.from(mcontext).inflate(R.layout.imrow, null);

        final ImageView image = (ImageView) v.findViewById(R.id.imageView);

        Map<String, Object> map = new HashMap<>();
        map = list.get(position);
        final int photo = (Integer) map.get("image");
        image.setImageResource(photo);
        image.setScaleType(ImageView.ScaleType.CENTER);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setImageResource(R.drawable.ipod);
            }
        },10000);

//            image.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        image.setImageResource(photo);
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                image.setImageResource(R.drawable.ipod);
//                            }
//                        }, 1000);
//                    }
//
//
//                    return false;
//                }
//
//            });

        return v;
    }


}
