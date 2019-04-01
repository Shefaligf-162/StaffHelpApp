package com.example.shefali.staffhelpapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
DrawerLayout drawer;
    ViewFlipper v_flipper;
NavigationView navigationView;
ImageView uh,ca,so,mh,wh,sp1;
Toolbar toolbar=null;
String contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v_flipper=findViewById(R.id.v_flipper);
        int images[]={R.drawable.login, R.drawable.logo, R.drawable.login};

        for(int image:images){
            flipperImages(image);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        uh=findViewById(R.id.urgenthelp);
        ca=findViewById(R.id.crimeactivity);
        so=findViewById(R.id.suspiciousobject);
        mh=findViewById(R.id.medicalemergency);
        wh=findViewById(R.id.womenhelp);
        sp1=findViewById(R.id.suspiciousperson);
        contact=getIntent().getExtras().getString("contactnumber");
        uh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Urgent Help");
                builder.setMessage("Notification sent").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,report.class);
                i.putExtra("contactnumber",contact);
                startActivity(i);

            }
        });
        so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(MainActivity.this,suspiciousobject.class);
            i.putExtra("contactnumber",contact);
            startActivity(i);
            }
        });
        mh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Medical Help");
                builder.setMessage("Notification sent").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(MainActivity.this,suspiciousperson.class);
                        i.putExtra("contactnumber",contact);
                        startActivity(i);
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        wh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Women Help");
                builder.setMessage("Notification sent").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        sp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,suspiciousperson.class);
                i.putExtra("contactnumber",contact);
                startActivity(i);
            }
        });
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void flipperImages(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(1000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.reports) {
            Intent i=new Intent(MainActivity.this,myreports.class);
            i.putExtra("contactnumber",contact);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.profile) {
            Intent i=new Intent(MainActivity.this,profile.class);
            i.putExtra("contactnumber",contact);
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
