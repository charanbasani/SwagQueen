package com.swagqueen.lulloo.swagqueen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.swagqueen.lulloo.swagqueen.Activities.ProfileActivity;
import com.swagqueen.lulloo.swagqueen.Adapters.AwardAdapter;
import com.swagqueen.lulloo.swagqueen.Adapters.FamilyAdapter;
import com.swagqueen.lulloo.swagqueen.Adapters.MovieslistAdapter;
import com.swagqueen.lulloo.swagqueen.Adapters.MyCustomPagerAdapter;
import com.swagqueen.lulloo.swagqueen.Helper.User;
import com.swagqueen.lulloo.swagqueen.Model.Awardsmodel;
import com.swagqueen.lulloo.swagqueen.Model.Familymodel;
import com.swagqueen.lulloo.swagqueen.Model.MoviesListmodel;
import com.swagqueen.lulloo.swagqueen.Model.PagerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment1 extends Fragment {

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private DatabaseReference mDatabaseRef2;
    private DatabaseReference mDatabaseRef3;
    private DatabaseReference mDatabaseRef4;
    private DatabaseReference mDatabaseRef5;
    private DatabaseReference mDatabaseRef6;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private ValueEventListener mDBListener1;
    private ValueEventListener mDBListener2;
    private ValueEventListener mDBListener4;
    FamilyAdapter familyAdapter;
    MovieslistAdapter movieslistAdapter;
    List<MoviesListmodel> moviesListmodels;
    List<Familymodel> familymodels;
    List<Awardsmodel> awardsmodels;
    AwardAdapter awardAdapter;
    ProgressBar mProgressCircle;
    ProgressBar mProgressCircle1;
    ProgressBar mProgressCircle2;
    RecyclerView family_recycler;
    TextView familydetails, profileld, more;
    Timer timer;
    AdView mAdView;
    ImageView imageprofile;
    MyCustomPagerAdapter myCustomPagerAdapter;
    private ViewPager pager;
    RecyclerView listVieawards;
    FirebaseUser firebaseUser;
    String profileid;
    RecyclerView listviewactor;
    private ArrayList<PagerModel> pagerModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment1, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profileid = firebaseUser.getUid();
//        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
//        profileid = prefs.getString("profileid", "none");
        familymodels = new ArrayList<>();
        awardsmodels = new ArrayList<>();
        moviesListmodels = new ArrayList<>();


        Toast.makeText(getActivity(), ""+firebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        FirebaseApp.initializeApp(getActivity());

        mProgressCircle=view.findViewById(R.id.pp);
        mProgressCircle1=view.findViewById(R.id.pp1);
        mProgressCircle2=view.findViewById(R.id.pp2);
        pager = (ViewPager)view. findViewById(R.id.pager);
        listVieawards = view. findViewById(R.id.listVieawards);
        more = view. findViewById(R.id.more);
        imageprofile = view. findViewById(R.id.imageprofile);
        familydetails = view. findViewById(R.id.familydetails);
        profileld = view. findViewById(R.id.profileld);
        family_recycler = view. findViewById(R.id.family_recycler);
        listviewactor = view. findViewById(R.id.listviewactor);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listVieawards.setLayoutManager(layoutManager);


        final LinearLayoutManager layoutManagers = new LinearLayoutManager(getActivity());
        layoutManagers.setOrientation(LinearLayoutManager.HORIZONTAL);
        family_recycler.setLayoutManager(layoutManagers);

   final LinearLayoutManager layoutManagers1 = new LinearLayoutManager(getActivity());
        layoutManagers1.setOrientation(LinearLayoutManager.HORIZONTAL);
        listviewactor.setLayoutManager(layoutManagers1);

        pagerModelArrayList=new ArrayList<>();
        pagerReq();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                pager.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            pager.setCurrentItem((pager.getCurrentItem() + 1) % pagerModelArrayList.size());
                        }
                        catch (Exception e){

                        }

                    }
                });

            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

       Familydetails();
        FamilydetailsPhotos();
        profilelds();
        awards();
        More();

movieslist();

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);

            }
        });





        userInfo();

        return view;
}

    private void movieslist() {
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef2= FirebaseDatabase.getInstance().getReference("general/Movie_DETAILS");
        mDBListener2= mDatabaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moviesListmodels.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MoviesListmodel upload = postSnapshot.getValue(MoviesListmodel.class);
                    upload.setKey(postSnapshot.getKey());
                    moviesListmodels.add(upload);
                    movieslistAdapter=new MovieslistAdapter(getActivity(),moviesListmodels);
                    listviewactor.setAdapter(movieslistAdapter);
                    movieslistAdapter.notifyDataSetChanged();
                    mProgressCircle.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void awards() {
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef1= FirebaseDatabase.getInstance().getReference("general/AWARDS");
        mDBListener1= mDatabaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                awardsmodels.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Awardsmodel upload = postSnapshot.getValue(Awardsmodel.class);
                    upload.setKey(postSnapshot.getKey());
                    awardsmodels.add(upload);
                    awardAdapter=new AwardAdapter(getActivity(),awardsmodels);
                    listVieawards.setAdapter(awardAdapter);
                    awardAdapter.notifyDataSetChanged();
                    mProgressCircle2.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void FamilydetailsPhotos() {

            mStorage = FirebaseStorage.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("general/FAMILY_DETAILS");
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    familymodels.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Familymodel upload = postSnapshot.getValue(Familymodel.class);
                        upload.setKey(postSnapshot.getKey());
                        familymodels.add(upload);
                        familyAdapter=new FamilyAdapter(getActivity(),familymodels);
                        family_recycler.setAdapter(familyAdapter);
                        familyAdapter.notifyDataSetChanged();
                        mProgressCircle1.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getContext()).load(user.getImageurl()).into(imageprofile);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void More() {


            mStorage = FirebaseStorage.getInstance();
            mDatabaseRef5 = FirebaseDatabase.getInstance().getReference("general/More/Mor");
            mDatabaseRef5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    more.setText(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private void profilelds() {
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef4 = FirebaseDatabase.getInstance().getReference("general/Profile/Profle");
        mDatabaseRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                profileld.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Familydetails() {
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef3 = FirebaseDatabase.getInstance().getReference("general/FAMYLY/FAMLY");
       mDatabaseRef3.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String value = dataSnapshot.getValue(String.class);
               familydetails.setText(value);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }





    private void pagerReq() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://godigitalpath.com/app/knn/categorySearch.php?category=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println("Data:"+response);

                            JSONObject jsonObject= new JSONObject(response);
                            // System.out.println("Result1 :" + jsonArray);
                            // JSONObject jsonObject=jsonArray.getJSONObject(0);

                            JSONArray jsonArray1=new JSONArray(response);

                            Toast.makeText(getActivity(), ""+jsonArray1, Toast.LENGTH_SHORT).show();
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                JSONObject jsonObj1 = jsonArray1.getJSONObject(i);

                                String image     = jsonObj1.getString("thumbnail");

                                pagerModelArrayList.add( new PagerModel(image) );
                            }

                            myCustomPagerAdapter = new MyCustomPagerAdapter(getActivity(),pagerModelArrayList);
                            pager.setAdapter(myCustomPagerAdapter);
                            //dotsIndicator.setViewPager(pager);
                        }catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
            }
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("d","25");
                return params;
            }
        });
        rq.add(stringRequest);

    }
}
