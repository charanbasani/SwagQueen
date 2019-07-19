package com.swagqueen.lulloo.swagqueen.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swagqueen.lulloo.swagqueen.Model.Familymodel;
import com.swagqueen.lulloo.swagqueen.R;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder>{
    Context context;
    List<Familymodel> familymodelList;

    public FamilyAdapter(Context context, List<Familymodel> familymodelList) {
        this.context = context;
        this.familymodelList = familymodelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView forr,year;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            forr=itemView.findViewById(R.id.forr);
            year=itemView.findViewById(R.id.year);
            image=itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public FamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FamilyAdapter.ViewHolder viewHolder, int i) {
Familymodel familymode1l=familymodelList.get(i);
viewHolder.forr.setText(familymode1l.getForr());
viewHolder.year.setText(familymode1l.getYear());


        if (familymode1l.getImage().equals("")) {
            Picasso.with(context)
                    .load(R.drawable.d)
                    .placeholder(R.drawable.d)
                    .fit()

                    //    .resize(width, width)
                    .into(viewHolder.image);

        } else {
            Picasso.with(context)
                    .load(familymode1l.getImage() )
                    .placeholder(R.drawable.d)
                    .fit()
                    //  .resize(width, width)
                    .into(viewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return familymodelList.size();
    }
}
