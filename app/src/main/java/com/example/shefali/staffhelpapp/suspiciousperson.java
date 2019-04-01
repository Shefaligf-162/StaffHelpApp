package com.example.shefali.staffhelpapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class suspiciousperson extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    EditText no_per,desc_per;
    Button submit_per,photo_per;
    String contact_tte;
    DatabaseReference db;
    List<String> a=new ArrayList<String>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String email_n = user.getEmail();
    String email=email_n.substring(0, email_n.length()-10);
    Uri uri_global;
    int PICK_IMG=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspiciousperson);
        no_per=findViewById(R.id.number_person);
        desc_per=findViewById(R.id.desc_person);
        db= FirebaseDatabase.getInstance().getReference("Reports").child(email).child("Suspicious Person");
        photo_per=findViewById(R.id.photo_person);
        submit_per=findViewById(R.id.submit_person);
        contact_tte=getIntent().getExtras().getString("contactnumber");
        photo_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(i,PICK_IMG);
            }
        });
        submit_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                    }
        });
    }
public void addData(){
    String x=getPath(uri_global);
    String desc1=desc_per.getText().toString().trim();
    String rn=no_per.getText().toString().trim();
    a.add(desc1);
    a.add(x);
    suspiciousobject_details sp=new suspiciousobject_details(a);
    db.child(rn).setValue(sp);

}
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMG){
            Log.i("iamge",data.getData().toString());
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reports) {
            Intent i=new Intent(suspiciousperson.this,myreports.class);
            i.putExtra("contactnumber",contact_tte);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.profile) {

            Intent i=new Intent(suspiciousperson.this,profile.class);
            startActivity(i);
        } else if (id == R.id.feedback) {

        } else if (id == R.id.impno) {

        } else if (id == R.id.share) {

        }else if (id == R.id.contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
