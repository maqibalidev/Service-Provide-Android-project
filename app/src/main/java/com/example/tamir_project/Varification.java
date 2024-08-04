package com.example.tamir_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Varification extends AppCompatActivity {

    PinView pinView;
TextView otp_resend_time,otp_resend_option;
FirebaseAuth auth;
CustomProgressDialog dialog;

private static final long STATIC_TIME_IN_MILIS =60000;
private CountDownTimer mcountdownTimer;
private boolean timmerRunning;
private long timeLeftInMilis=STATIC_TIME_IN_MILIS;
private String NUMBER;
int check;
String otp_id;
int Check;
    Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

getSupportActionBar().hide();
dialog=new CustomProgressDialog(Varification.this);

        dialog.showDialog("Sending OTP","Please wait..");
check=getIntent().getIntExtra("check",0);

startTimmer();
pinView=findViewById(R.id.otp_view);
btn=findViewById(R.id.otp_verify_btn);
        otp_resend_time=findViewById(R.id.otp_resend_time);
        otp_resend_option=findViewById(R.id.otp_resend_option);
auth=FirebaseAuth.getInstance();
//auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
       NUMBER= getIntent().getStringExtra("number");
        Check= getIntent().getIntExtra("check",0);
otp_resend_option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog.showDialog("Sending OTP","Please wait..");
        sendOTp();
    }
});
        sendOTp();
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if (pinView.getText().toString().isEmpty()){
            Toast.makeText(Varification.this, "please enter Otp", Toast.LENGTH_SHORT).show();
        } else if (pinView.getText().toString().length()!=6) {
            Toast.makeText(Varification.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
        else {
            PhoneAuthCredential credential =PhoneAuthProvider.getCredential(otp_id,pinView.getText().toString());
dialog.showDialog("Varifying number","Please wait");
            signInWithPhoneAuthCredential(credential);

        }


    }
});



pinView.requestFocus();



      pinView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
              inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_IMPLICIT_ONLY);


          }
      });



    }

    private void startTimmer() {

        mcountdownTimer =new CountDownTimer(timeLeftInMilis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilis= l;
                updateCoundownText();
            }

            @Override
            public void onFinish() {
timmerRunning=false;
otp_resend_option.setVisibility(View.VISIBLE);
            }
        }.start();
        timmerRunning=true;

    }

    private void updateCoundownText() {

    int minutes=(int) (timeLeftInMilis / 1000)/60;
    int sec =(int) (timeLeftInMilis / 1000) % 60;

    String timeLeftFormatted =String.format(Locale.getDefault(),"%02d:%02d",minutes,sec);
        otp_resend_time.setText(timeLeftFormatted);
    }

    private void sendOTp() {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(NUMBER)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Toast.makeText(Varification.this, "OTP sended", Toast.LENGTH_SHORT).show();


                                dialog.dismissDialog();
                                otp_id=s;
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                                SharedPreferences.Editor editor =preferences.edit();
                                editor.putBoolean("islogin",false);

                                editor.commit();
                                Toast.makeText(Varification.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismissDialog();
                            }
                        })// OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);



    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() ){
                            dialog.dismissDialog();
if (check==1) {
    if (getIntent().getBooleanExtra("checked", false)) {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("islogin", true);

        editor.commit();

        startActivity(new Intent(Varification.this, Navigation_bottom.class));
        finish();
    }
else{

    startActivity(new Intent(Varification.this, Navigation_bottom.class));
        finish();
}

} else {

        Intent intent = new Intent(Varification.this, Register.class);
        intent.putExtra("number", NUMBER);
        startActivity(intent);
        finish();
    }






                            // Update UI
                        } else {
                            SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                            SharedPreferences.Editor editor =preferences.edit();
                            editor.putBoolean("islogin",false);

                            editor.commit();


                            Toast.makeText(Varification.this, "Failed to varify please try again!", Toast.LENGTH_SHORT).show();
                            dialog.dismissDialog();
                        }
                    }
                });
    }

}