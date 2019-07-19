package com.swagqueen.lulloo.swagqueen.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.swagqueen.lulloo.swagqueen.Fragment1;
import com.swagqueen.lulloo.swagqueen.Fragment2;
import com.swagqueen.lulloo.swagqueen.Fragment3;
import com.swagqueen.lulloo.swagqueen.Fragment4;
import com.swagqueen.lulloo.swagqueen.Fragment5;
import com.swagqueen.lulloo.swagqueen.R;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.dashboard);
        toolbar = getSupportActionBar();
        text=findViewById(R.id.text);
        // load the store fragment by default
        toolbar = getSupportActionBar();
        MobileAds.initialize(this, String.valueOf(R.string.ad_app_id));
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       // toolbar.setTitle("Shop");
        loadFragment(new Fragment1());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()) {
                case R.id.home:
                    //toolbar.setTitle("Shop");

                     fragment=new Fragment1();
                     loadFragment(fragment);
                    return true;
                case R.id.rss:

                  //  toolbar.setTitle("My Gifts");
                    fragment=new Fragment2();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                   // toolbar.setTitle("Cart");

                    fragment=new Fragment3();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:

                  //  toolbar.setTitle("Profile");
                    fragment=new Fragment4();
                    loadFragment(fragment);
                    return true;

                    case R.id.videos:

                  //  toolbar.setTitle("Profile");
                    fragment=new Fragment5();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}