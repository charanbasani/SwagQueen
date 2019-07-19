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
import com.swagqueen.lulloo.swagqueen.Model.Facebookmodel;
import com.swagqueen.lulloo.swagqueen.R;

import java.util.List;

public class FacebookRecyclerviewAdapter  extends RecyclerView.Adapter<FacebookRecyclerviewAdapter.viewHolder> {
    Context context;
    List<Facebookmodel> facebookmodels;

    public FacebookRecyclerviewAdapter(Context context, List<Facebookmodel> facebookmodels) {
        this.context = context;
        this.facebookmodels = facebookmodels;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,message,id,stort;
        ImageView full_picture;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.username);
            full_picture=itemView.findViewById(R.id.post_image);
            message=itemView.findViewById(R.id.likes);
            stort=itemView.findViewById(R.id.stort);
        }
    }

    @NonNull
    @Override
    public FacebookRecyclerviewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view= LayoutInflater.from(context).inflate(R.layout.facebookpage,null);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacebookRecyclerviewAdapter.viewHolder viewHolder, int i) {

        Facebookmodel  facebookmodel=facebookmodels.get(i);


        viewHolder.name.setText(facebookmodel.getName());

        viewHolder.message.setText(facebookmodel.getMessage());
        viewHolder.stort.setText(facebookmodel.getStory());



        if (facebookmodel.getFull_picture().equals("")) {
            Picasso.with(context)
                    .load(R.drawable.d)
                    .placeholder(R.drawable.d)
                    .fit()

                    //    .resize(width, width)
                    .into(viewHolder.full_picture);

        } else {
            Picasso.with(context)
                    .load(facebookmodel.getFull_picture() )
                    .placeholder(R.drawable.d)
                    .fit()
                    //  .resize(width, width)
                    .into(viewHolder.full_picture);
        }

    }

    @Override
    public int getItemCount() {
        return facebookmodels.size();
    }
}
