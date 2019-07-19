package com.swagqueen.lulloo.swagqueen.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.swagqueen.lulloo.swagqueen.Model.Gallerymodel;
import com.swagqueen.lulloo.swagqueen.R;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<Gallerymodel> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Gallerymodel> mGridData = new ArrayList<Gallerymodel>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Gallerymodel> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<Gallerymodel> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Gallerymodel item = mGridData.get(position);


        if (mGridData.get(position).equals("")) {
            Picasso.with(mContext)
                    .load(R.drawable.ic_feeds_fan)
                    .placeholder(R.drawable.d)
                    .fit()

                    //    .resize(width, width)
                    .into(holder.imageView);

        } else {
            Picasso.with(mContext)
                    .load(mGridData.get(position).getImage() )
                    .placeholder(R.drawable.d)
                    .fit()
                    //  .resize(width, width)
                    .into(holder.imageView);
        }

        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}