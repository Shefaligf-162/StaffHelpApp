package com.example.shefali.staffhelpapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class update extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText name,contactno,designation,headquarters;
    Button register,selectimage;
    Databasehandler db;
    String contact_tte;
    Uri uri_global;
    private AwesomeValidation awesomeValidation;
    private static final int PICK_IMG=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        contact_tte=getIntent().getExtras().getString("contactnumber");
        name=findViewById(R.id.name);
        contactno=findViewById(R.id.contactnumber);
        designation=findViewById(R.id.designation);
        headquarters=findViewById(R.id.headquarters);
        register=findViewById(R.id.register);
        selectimage=findViewById(R.id.selectimage);
        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.contactnumber, "^[2-9]{2}[0-9]{8}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.designation, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.headquarters, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        db=new Databasehandler(this);
        Cursor c=db.getdata(contact_tte);
        if (c.getCount() == 0) {
            Toast.makeText(update.this,"No data",Toast.LENGTH_LONG).show();
        }
        else{
            while(c.moveToNext()){
                Bitmap bt=null;
                name.setText(c.getString(0));
                designation.setText(c.getString(2));
                headquarters.setText(c.getString(4));
                contactno.setText(c.getString(1));

            }
        }
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
                String x=getPath(uri_global);
                String name1=name.getText().toString();
                String des=designation.getText().toString();
                String headq=headquarters.getText().toString();
                String num=contactno.getText().toString();
                if(awesomeValidation.validate()) {
                    if(db.update(x,name1,num,des,headq,contact_tte)&&db.update_mr(num,contact_tte)){
                        Toast.makeText(update.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(update.this,profile.class);
                          i.putExtra("contactnumber",num);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(update.this, "Try again 1", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(update.this, "Try Again", Toast.LENGTH_SHORT).show();

                }

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reports) {
            Intent i=new Intent(update.this,report.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.profile) {

            Intent i=new Intent(update.this,profile.class);
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
