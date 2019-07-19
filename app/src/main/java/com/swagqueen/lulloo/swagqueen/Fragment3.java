package com.swagqueen.lulloo.swagqueen;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.swagqueen.lulloo.swagqueen.Activities.DetailsActivity;
import com.swagqueen.lulloo.swagqueen.Adapters.GridViewAdapter;
import com.swagqueen.lulloo.swagqueen.Model.Gallerymodel;

import java.util.ArrayList;

public class Fragment3 extends android.support.v4.app.Fragment {


    private DatabaseReference mDatabaseRef6;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
private static final String TAG = Fragment3.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<Gallerymodel> mGridData;
    private String FEED_URL = "http://javatechig.com/?json=get_recent_posts&count=45";

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_gridview, container, false);




       mGridView = (GridView) view.findViewById(R.id.gridView);
       mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

       //Initialize with empty data
       mGridData = new ArrayList<>();
       mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
       mGridView.setAdapter(mGridAdapter);
       Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animma);
       GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
       mGridView.setLayoutAnimation(controller);
       mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               //Get item at position
               Gallerymodel item = (Gallerymodel) parent.getItemAtPosition(position);

               Intent intent = new Intent(getActivity(), DetailsActivity.class);
               ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

               // Interesting data to pass across are the thumbnail size/location, the
               // resourceId of the source bitmap, the picture description, and the
               // orientation (to avoid returning back to an obsolete configuration if
               // the device rotates again in the meantime)

               int[] screenLocation = new int[2];
               imageView.getLocationOnScreen(screenLocation);

               //Pass the image title and url to DetailsActivity
               intent.putExtra("left", screenLocation[0]).
                       putExtra("top", screenLocation[1]).
                       putExtra("width", imageView.getWidth()).
                       putExtra("height", imageView.getHeight()).
//                       putExtra("title", item.getTitle()).
                       putExtra("image", item.getImage());

               //Start details activity
               startActivity(intent);
           }
       });
     // new AsyncHttpTask().execute(FEED_URL);
       mProgressBar.setVisibility(View.VISIBLE);
       init();













        return view;
    }

    private void init() {

        mDatabaseRef6 = FirebaseDatabase.getInstance().getReference("general/Movie_gallery");
        mDBListener = mDatabaseRef6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mGridData.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Gallerymodel upload = postSnapshot.getValue(Gallerymodel.class);
                    upload.setKey(postSnapshot.getKey());
                    mGridData.add(upload);
                    mGridAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }


}

