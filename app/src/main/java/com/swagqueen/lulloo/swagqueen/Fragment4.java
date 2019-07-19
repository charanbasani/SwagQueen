package com.swagqueen.lulloo.swagqueen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.swagqueen.lulloo.swagqueen.Activities.Gallery_Post;
import com.swagqueen.lulloo.swagqueen.Adapters.FacebookRecyclerviewAdapter;
import com.swagqueen.lulloo.swagqueen.Model.Facebookmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment4 extends Fragment {
RecyclerView recyclerview;
    private FirebaseStorage mStorage;
    ProgressDialog progress;
    Button GAllery;
    private DatabaseReference mDatabaseRef5;
    String  url;
    List<Facebookmodel> facebookmodelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, container, false);



        progress = new ProgressDialog(getActivity());
        progress.setTitle("please wait");
        progress.show();


        MobileAds.initialize(getActivity(), String.valueOf(R.string.ad_app_id));
        final InterstitialAd interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });


        facebookmodelList=new ArrayList<>();
        GAllery=view.findViewById(R.id.GAllery);
        recyclerview=view.findViewById(R.id.recyclerview);
        GAllery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Gallery_Post.class);
                startActivity(intent);
                //https://graph.facebook.com/932810943736223/feed?fields=message,access_token=EAADm0nYY59ABAAKSRINf8aSX5IkCEvFvgZCiltOLWdeUlDfZCX9CLO3agPg0nYbxgPjghWI90iCZC0yDnXXBkvf8zCCC2XZCG38R63qyMqZCsLxax9x3N4HspzxqQUAVTyoxrf5WcpkFk63M54QhaMPKmyHxraLuK5MZBAbz6tJh1eLgvfCh56EanuYyYlNy8ZD
            }
        });

       //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef5 = FirebaseDatabase.getInstance().getReference("general/tokenfb/token");
        mDatabaseRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                // more.setText(value);
                url=value;
                getData();
              //  Toast.makeText(getActivity(), ""+url, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void getData() {

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println("Data:"+response);

                            JSONObject jsonObject= new JSONObject(response);
                            progress.dismiss();

                            JSONArray jsonArray1=jsonObject.getJSONArray("data");



                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
                                String id     = jsonObj1.optString("id");
                                String message     = jsonObj1.optString("message");
                                String fullimage     = jsonObj1.optString("full_picture");
                                String stort     = jsonObj1.optString("story");
                                JSONObject jsonObject1=jsonObj1.getJSONObject("from");
                                String name     = jsonObject1.optString("name");

                                facebookmodelList.add( new Facebookmodel(name,id,fullimage,message,stort));
                            }
                            FacebookRecyclerviewAdapter myCustomPagerAdapter = new FacebookRecyclerviewAdapter(getActivity(),facebookmodelList);
                            recyclerview.setAdapter(myCustomPagerAdapter);

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
            };
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("d","25");
                return params;
            }
        });
        rq.add(stringRequest);

    }

}
