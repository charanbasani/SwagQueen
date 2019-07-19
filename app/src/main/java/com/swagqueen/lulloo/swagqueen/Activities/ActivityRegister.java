package com.swagqueen.lulloo.swagqueen.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.swagqueen.lulloo.swagqueen.Helper.IConstant;
import com.swagqueen.lulloo.swagqueen.Helper.SharedPreferenceManager1;
import com.swagqueen.lulloo.swagqueen.R;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ActivityRegister extends AppCompatActivity {
    EditText username, fullname, email, password;
    Button register;
    TextView txt_login,mobilenumber;
    ImageView close, image_profile;
    TextView sendotp, verifive;
    EditText phonenumberr, otpente;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    String phoneNumber;

    private static final String TAG = "PhoneAuth";

    MaterialDialog process_dialog;

    private EditText phoneText;
    private EditText codeText;
    private TextView verifyButton;
    private TextView sendButton;
    private TextView resendButton;

    private String uid;
    private String country_code;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;


    private FirebaseAuth fbAuth;

    AlertDialog.Builder builder;

    AlertDialog alertDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        mobilenumber = findViewById(R.id.phonenumber);
        txt_login = findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();
        fbAuth = FirebaseAuth.getInstance();






        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(ActivityRegister.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_phone = mobilenumber.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(ActivityRegister.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if(str_password.length() < 6){
                    Toast.makeText(ActivityRegister.this, "Password must have 6 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    register(str_username, str_fullname, str_email, str_password,str_phone);
                }
            }
        });


       // phoneenumbber();



    }

    private void phoneenumbber() {




        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.phoneverification_dialoge, viewGroup, false);




        phoneText = dialogView.findViewById(R.id.phoneText);
        codeText = dialogView.findViewById(R.id.codeText);




        verifyButton = dialogView.findViewById(R.id.verifyButton);
        sendButton = dialogView.findViewById(R.id.sendButton);
        resendButton = dialogView.findViewById(R.id.resendButton);
        country_code = "+263";
        FirebaseAuth.AuthStateListener mAuthListener;

        //Now we need an AlertDialog.Builder object
         builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
         alertDialog = builder.create();
         alertDialog.setCancelable(false);
        alertDialog.show();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!isConnected()) {
                        //  Toast.makeText(LoginActivity.this, "Network problem", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Sorry there was a problem with your network", Snackbar.LENGTH_LONG)
                                .setAction("Check Internet", null).show();
                    }
                    else {

                        sendCode(view);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
 resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!isConnected()) {
                        //  Toast.makeText(LoginActivity.this, "Network problem", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Sorry there was a problem with your network", Snackbar.LENGTH_LONG)
                                .setAction("Check Internet", null).show();
                    }
                    else {

                        sendCode(view);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        final EditText codeText = dialogView.findViewById(R.id.text_countrycode);

        codeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        // Implement your code here
                        codeText.setText(dialCode);
                        country_code = dialCode;
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });

    }
    private void goToHome() {

        phoneNumber = country_code + phoneText.getText().toString();

        mobilenumber.setText(phoneNumber);
        alertDialog.dismiss();
    }


    public void sendCode(View view) {

         phoneNumber = country_code + phoneText.getText().toString();
        if (phoneNumber.length() > 7) {

            setUpVerificatonCallbacks();


            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    verificationCallbacks);

            //Set processing indicators
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title("Sending code")
                    .content("Please wait")
                    .progress(true, 0);

            process_dialog = builder.build();
            process_dialog.show();
        }else {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        //   signoutButton.setEnabled(true);
                        //   statusText.setText("Signed In");

                        codeText.setText("");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Toast.makeText(ActivityRegister.this, "Invalid credential", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Toast.makeText(ActivityRegister.this, "Limit Reached Try Again In a few Hours", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        verifyButton.setVisibility(View.VISIBLE);
                        codeText.setVisibility(View.VISIBLE);
                        //sendButton.setEnabled(false);
                        sendButton.setText("RESEND OTP");
                        resendButton.setVisibility(View.GONE);
                        process_dialog.dismiss();
                    }
                };
    }

    public void verifyCode(View view) {

        String code = codeText.getText().toString();
        if(TextUtils.isEmpty(codeText.getText().toString())) {
            codeText.setError("Your message");
            return;
        }
        else {
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);

            //Set processing indicators
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title("Verifying code")
                    .content("Please wait")
                    .progress(true, 0);

            process_dialog = builder.build();
            process_dialog.show();
        }


    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            codeText.setText("");

                           // goToHome();
                            FirebaseUser user = task.getResult().getUser();

                            uid = user.getUid();

                            process_dialog.dismiss();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = phoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Sending code")
                .content("Please wait")
                .progress(true, 0);

        process_dialog = builder.build();
        process_dialog.show();
    }
    public boolean isConnected() throws InterruptedException, IOException
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }





    private void register(final String username, final String fullname, final String email, String password, final String str_phone) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ActivityRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userID = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", userID);
                            map.put("username", username.toLowerCase());
                            map.put("phone_number", str_phone);
                            map.put("fullname", fullname);
                            map.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/instagramtest-fcbef.appspot.com/o/placeholder.png?alt=media&token=b09b809d-a5f8-499b-9563-5252262e9a49");
                            map.put("bio", "");

                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        String name1=email;

                                        SharedPreferenceManager1 sharedPreferenceManager = new SharedPreferenceManager1(ActivityRegister.this);

                                        sharedPreferenceManager.connectDB();

                                        sharedPreferenceManager.setString(IConstant.Name, name1);

                                        sharedPreferenceManager.closeDB();

                                        Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(ActivityRegister.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(ActivityRegister.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
