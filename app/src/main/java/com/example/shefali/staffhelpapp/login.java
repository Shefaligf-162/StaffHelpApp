package com.example.shefali.staffhelpapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class login extends AppCompatActivity {
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE=111;
    EditText username,enterotpEditText,pass;
    EditText contactno;
    Button signIn,sendotpButton;
    TextView signup;
    FirebaseAuth mAuth;
    DatabaseReference db;
    String num,id;
    Boolean otp=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= FirebaseDatabase.getInstance().getReference("Staff");
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        signup=findViewById(R.id.signup);
        pass=findViewById(R.id.password_login);
        contactno = (EditText) findViewById(R.id.contactnumber);
        enterotpEditText = (EditText) findViewById(R.id.enterotpEditText);
        signIn = (Button)findViewById(R.id.signin);
        sendotpButton = (Button)findViewById(R.id.sendotpButton);
        sendotpButton.setEnabled(false);
        mAuth=FirebaseAuth.getInstance();
        if(checkPermission(Manifest.permission.SEND_SMS))
        { sendotpButton.setEnabled(true);
        Toast.makeText(this,"mil gyii permission",Toast.LENGTH_SHORT).show();


        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
            Toast.makeText(login.this, "pura bhar", Toast.LENGTH_SHORT).show();

        }



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(v.getContext(),register.class);
                startActivity(i);
            }
        });

        sendotpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                id = String.format("%04d", rand.nextInt(10000));

                num = contactno.getText().toString();
                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(num) ){
                    if(checkPermission(Manifest.permission.SEND_SMS)){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(num,null,id,null,null);
                    }
                    else{
                        Toast.makeText(login.this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(login.this, "fill all enteries", Toast.LENGTH_SHORT).show();
                }



            }

        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();
            }
        });

    }

    private void userlogin(){
        String loginemail1=username.getText().toString().trim();
        String loginpassword1=pass.getText().toString().trim();
        if(!(TextUtils.isEmpty(loginemail1))){

        }else{
            Toast.makeText(login.this,"Enter your email",Toast.LENGTH_LONG).show();}

        if(!(TextUtils.isEmpty(loginpassword1))){

        }else{
            Toast.makeText(login.this,"Enter password",Toast.LENGTH_LONG).show();}
        if(Patterns.EMAIL_ADDRESS.matcher(loginemail1).matches()){

        }else{
            Toast.makeText(login.this,"Enter valid email",Toast.LENGTH_LONG).show();}

        mAuth.signInWithEmailAndPassword(loginemail1,loginpassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String enteredotp = enterotpEditText.getText().toString().trim();
                if(enteredotp.equals(id))
                    otp = true;
                else
                    Toast.makeText(login.this, "wrong otp",Toast.LENGTH_SHORT).show();
                String usern = username.getText().toString().trim();

                if(/*task.isSuccessful() && otp*/ true){
                    String contact = contactno.getText().toString().trim();
                    Intent i=new Intent(login.this,MainActivity.class);
                    i.putExtra("contactnumber",contact);
                    startActivity(i);
                }
                else {
                    Toast.makeText(login.this,"Enter valid credentials"+otp,Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public boolean checkPermission(String permission){

        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        switch(requestCode){
            case SEND_SMS_PERMISSION_REQUEST_CODE :
                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    sendotpButton.setEnabled(true);
                break;

        }
    }
}
