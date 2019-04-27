package com.hackjam.sevenshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackjam.sevenshop.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater Inflater;

    public String [] titleArray = {"Facebook","Twitter","Instagram"};

    public  SliderAdapter(Context context){
        this.context=context;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==o);
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = Inflater.inflate(R.layout.slide,container,false);

        ConstraintLayout  constraintLayout = view.findViewById(R.id.csly_slide);
        TextView textView = view.findViewById(R.id.tv_slide_slide);
        textView.setText(titleArray[position]);
        container.addView(view);
        return view;
    }


}
