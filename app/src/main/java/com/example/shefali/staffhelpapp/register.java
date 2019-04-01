package com.example.shefali.staffhelpapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText name,contactno,deisgnation,headquarters,eml,pass;
    Button register,selectimage;
    DatabaseReference db;
    Uri uri_global;
    FirebaseAuth mAuth;
    private AwesomeValidation awesomeValidation;
    private static final int PICK_IMG=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name=findViewById(R.id.name);
        contactno=findViewById(R.id.contactnumber);
        eml=findViewById(R.id.email);
        db= FirebaseDatabase.getInstance().getReference("Staff");
        mAuth=FirebaseAuth.getInstance();
        deisgnation=findViewById(R.id.designation);
        headquarters=findViewById(R.id.headquarters);
        register=findViewById(R.id.register);
        pass=findViewById(R.id.password);
        selectimage=findViewById(R.id.selectimage);
        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.contactnumber, "^[2-9]{2}[0-9]{8}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.designation, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.headquarters, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(i,PICK_IMG);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });
    }
public void AddData(){
    String x=getPath(uri_global);
    String password1=pass.getText().toString().trim();
    String email1=eml.getText().toString().trim();
    String name1=name.getText().toString().trim();
    String des=deisgnation.getText().toString().trim();
    String headq=headquarters.getText().toString().trim();
    String num=contactno.getText().toString().trim();
    String email2=email1.substring(0, email1.length()-10);
    if(!(TextUtils.isEmpty(x))){

    }
    else{
        Toast.makeText(register.this,"Upload your Image",Toast.LENGTH_LONG).show();
    }
    if(!(TextUtils.isEmpty(name1))){

    }
    else{
        Toast.makeText(register.this,"Enter your Name",Toast.LENGTH_LONG).show();
    }
    if(!(TextUtils.isEmpty(des))){

    }
    else{
        Toast.makeText(register.this,"Enter your Designation",Toast.LENGTH_LONG).show();
    }
    if(!(TextUtils.isEmpty(headq))){

    }
    else{
        Toast.makeText(register.this,"Enter your headquater",Toast.LENGTH_LONG).show();
    }
    if(!(TextUtils.isEmpty(email1))){

    }
    else{
        Toast.makeText(register.this,"Enter your email",Toast.LENGTH_LONG).show();
    }

    if(Patterns.EMAIL_ADDRESS.matcher(email1).matches()){

    }else{Toast.makeText(register.this,"Enter valid email",Toast.LENGTH_LONG).show();}

    mAuth.createUserWithEmailAndPassword(email1,password1)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(register.this,"registered successfully",Toast.LENGTH_LONG).show();

                    } else {
                        // If sign in fails, display a message to the user.
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(register.this,"User is already registred",Toast.LENGTH_LONG).show();
                        }
                        else {Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();}

                    }

                    // ...
                }
            });

    Registration_details rg=new Registration_details(x,name1,des,headq,num,email1);
    db.child(email2).setValue(rg);
}
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMG){
            Log.i("image",data.getData().toString());
            uri_global=data.getData();
        }
    }
    public String getPath(Uri uri){
        if(uri==null){
            Log.i("uri","Null");
            return null;}
        String[] projection={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int colIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(colIndex);
        }
        return uri.getPath();
    }
}
