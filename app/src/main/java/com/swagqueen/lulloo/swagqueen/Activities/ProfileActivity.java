package com.swagqueen.lulloo.swagqueen.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.swagqueen.lulloo.swagqueen.Helper.User;
import com.swagqueen.lulloo.swagqueen.R;

import static java.security.AccessController.getContext;

public class ProfileActivity extends AppCompatActivity {
ImageView imageView;
TextView fullname,username,phonenumber;
    FirebaseUser firebaseUser;
    String profileid;
    Button edit_profile,logout;
    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;

    ImageView close, image_profile,about;
    TextView save, tv_change;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        imageView=findViewById(R.id.image_profile);
        about=findViewById(R.id.about);
        fullname=findViewById(R.id.fullname);
        username=findViewById(R.id.username);
        phonenumber=findViewById(R.id.phonenumber);
        edit_profile=findViewById(R.id.edit_profile);
        logout=findViewById(R.id.logout);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profileid=firebaseUser.getUid();

        MobileAds.initialize(this, String.valueOf(R.string.ad_app_id));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(ProfileActivity.this, EditProfile.class);
               startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Dieforfan", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.clear().commit();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileActivity.this,SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
                finish();

            }
        });
        userInfo();
    }



    private void showCustomDialog() {


        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.about, viewGroup, false);

        builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
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

                Glide.with(getApplicationContext()).load(user.getImageurl()).into(imageView);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                phonenumber.setText(user.getPhone_number());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    }








