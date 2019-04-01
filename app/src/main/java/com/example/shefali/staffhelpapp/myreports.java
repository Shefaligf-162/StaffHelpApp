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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myreports extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String email_n = user.getEmail();
    String email=email_n.substring(0, email_n.length()-10);
    ListView listView;
    String contact;
    int j=1;
    RelativeLayout rl;
    ListAdapter la;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreports);
        db= FirebaseDatabase.getInstance().getReference("Reports").child(email);
        contact=getIntent().getExtras().getString("contactnumber");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(final DataSnapshot reports:dataSnapshot.getChildren()){
                    String type=reports.getKey().toString();
                    final DatabaseReference db1=db.child(type);
                    db1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(final DataSnapshot nu:dataSnapshot.getChildren()){
                                String num=nu.getKey().toString();
                                final DatabaseReference db2=db1.child(num).child("a");
                                db2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String b=dataSnapshot.getValue().toString();
                                        String b1[]=b.split(",");
                                        String c=b1[0].substring(1);
                                        /*TextView name=new TextView(myreports.this);
                                        RelativeLayout.LayoutParams ansprms1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                        ansprms1.setMargins(50,150*j,20,20);
                                        name.setLayoutParams(ansprms1);
                                        name.setText(nu.getKey()+" | "+reports.getKey()+" | "+c);
                                        name.setTextSize(20);
                                        rl.addView(name);
                                        j=j+1;*/
                                        TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);

                                            TableRow row= new TableRow(myreports.this);
                                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                            TextView sno=new TextView(myreports.this);
                                            TextView type=new TextView(myreports.this);
                                            TextView desc=new TextView(myreports.this);
                                            sno.setText("  "+nu.getKey()+"    ");
                                            type.setText(reports.getKey()+"     ");
                                            desc.setText(c+"   ");
                                            row.setLayoutParams(lp);
                                            row.addView(sno);
                                            row.addView(type);
                                            row.addView(desc);
                                            ll.addView(row);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                               }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            Intent i=new Intent(myreports.this,myreports.class);
            i.putExtra("contactnumber",contact);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.profile) {

            Intent i=new Intent(myreports.this,profile.class);
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
