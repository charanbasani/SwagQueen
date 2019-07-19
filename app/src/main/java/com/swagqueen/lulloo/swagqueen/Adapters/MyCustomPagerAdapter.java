package com.swagqueen.lulloo.swagqueen.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.swagqueen.lulloo.swagqueen.Model.PagerModel;
import com.swagqueen.lulloo.swagqueen.R;

import java.util.List;


/**
 * Created by AnuReddy on 5/3/2018.
 */

public class MyCustomPagerAdapter extends PagerAdapter {

    Context context;

    LayoutInflater layoutInflater;
    private List<PagerModel> pagerModels;

    public MyCustomPagerAdapter(Context context, List<PagerModel> pagerModels1) {
        this.context = context;
       this.pagerModels = pagerModels1;

       try
       {

           layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

    }

    @Override
    public int getCount() {
        return pagerModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = layoutInflater.inflate(
                R.layout.slidingimages_layout, container, false);



        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);



        if (pagerModels.get(position).getPagerimage().equals("")) {
            Picasso.with(context)
                    .load(R.drawable.d)
                    .placeholder(R.drawable.d)
                    .fit()

                    //    .resize(width, width)
                    .into(imageView);

        } else {
            Picasso.with(context)
                    .load(pagerModels.get(position).getPagerimage() )
                    .placeholder(R.drawable.d)
                    .fit()
                    //  .resize(width, width)
                    .into(imageView);
        }


        container.addView(itemView);



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}