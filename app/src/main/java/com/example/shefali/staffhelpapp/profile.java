package com.example.shefali.staffhelpapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Button editProfile;
int j=1;
DatabaseReference db;
String contact;
List<String> ls=new ArrayList<String>();
FirebaseAuth auth = FirebaseAuth.getInstance();
FirebaseUser user = auth.getCurrentUser();
String email_n = user.getEmail();
String email=email_n.substring(0, email_n.length()-10);
TextView headquarters,name;
ImageView profileimage;
RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.i("activity","profile");
        rl=(RelativeLayout)findViewById(R.id.rel1);
        db= FirebaseDatabase.getInstance().getReference("Staff").child(email);
        profileimage=findViewById(R.id.profileimage);
        contact=getIntent().getExtras().getString("contactnumber");
        Log.i("conta",contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ls.add("Name");
        ls.add("Designation");
        ls.add("Mob.No");
        ls.add("Head Quarter");
        ls.add("Email");
        editProfile = (Button) findViewById(R.id.edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile.this,update.class);
                i.putExtra("contactnumber",contact);
                startActivity(i);
            }
        });
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot details:dataSnapshot.getChildren()){
                        TextView name=new TextView(profile.this);
                        RelativeLayout.LayoutParams ansprms1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ansprms1.setMargins(200,250+150*j,100,20);
                        name.setLayoutParams(ansprms1);
                        name.setText(ls.get(j-1)+" : "+details.getValue().toString());
                        name.setTextSize(20);
                        rl.addView(name);
                        j=j+1;
                        if(j==6)
                            break; }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* TextView name=new TextView(profile.this);
        RelativeLayout.LayoutParams ansprms1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ansprms1.setMargins(250,250+150*1,100,20);
        name.setLayoutParams(ansprms1);
        name.setText(s+" : "+nk_kh);
        name.setTextSize(20);
        TextView name=new TextView(profile.this);
        RelativeLayout.LayoutParams ansprms1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ansprms1.setMargins(250,250+150*j,100,20);
        ed1.setLayoutParams(ansprms1);
        ed1.setText(s+" : "+nk_kh);
        ed1.setTextSize(20);
        ed1.setId(j);
        TextView name=new TextView(profile.this);
        RelativeLayout.LayoutParams ansprms1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ansprms1.setMargins(250,250+150*j,100,20);
        ed1.setLayoutParams(ansprms1);
        ed1.setText(s+" : "+nk_kh);
        ed1.setTextSize(20);
        ed1.setId(j);

        j=j+1;
        rl.addView(ed1);
*/
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
            Intent i=new Intent(profile.this,myreports.class);
            i.putExtra("contactnumber",contact);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.profile) {

            Intent i=new Intent(profile.this,profile.class);
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
