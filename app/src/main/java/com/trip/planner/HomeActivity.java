package com.trip.planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private Button startButton, calcButton;
        private FirebaseAuth auth;
        private FirebaseAuth.AuthStateListener authListener;
        private DatabaseReference myRef;
        private TextView profileName, ageNum, heightNum, weightNum, sumNum, sumNum2;
        private FirebaseDatabase mFirebaseDatabase;
        private String userID;
        Spinner sp;
        double exercise, finalSumMale, finalSumFemale;
        User uInfo = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startButton = (Button) findViewById(R.id.startButton);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sp = (Spinner)findViewById(R.id.spinner);
        calcButton = (Button) findViewById(R.id.calcButton);

        /*DISPLAY DATABASE CONTENTS*/
        profileName = (TextView) findViewById(R.id.profileName);
        //get numbers
        sumNum = (TextView) findViewById(R.id.sumNum);
        sumNum2 = (TextView) findViewById(R.id.sumNum2);
        ageNum = (TextView) findViewById(R.id.ageNum);
        heightNum = (TextView) findViewById(R.id.heightNum);
        weightNum = (TextView) findViewById(R.id.weightNum);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*FINISH*/



        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Coming Soon", Snackbar.LENGTH_LONG)
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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MUST CALCULATE BMR TO ENTER MAP
                if(finalSumFemale == 0 && finalSumMale == 0) {
                    Toast.makeText(HomeActivity.this, "Calculate BMR before continuing!", Toast.LENGTH_SHORT).show();
                }else if(finalSumFemale > 0 && finalSumMale > 0){
                    Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                    startActivity(intent);
                    //startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                    //Send data to Maps Activity
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    i.putExtra("maleBMR", finalSumMale);
                    i.putExtra("femaleBMR", finalSumFemale);
                    startActivity(i);
                }

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            uInfo.setName(ds.child(userID).getValue(User.class).getName());
            uInfo.setAge(ds.child(userID).getValue(User.class).getAge());
            uInfo.setHeight(ds.child(userID).getValue(User.class).getHeight());
            uInfo.setWeight(ds.child(userID).getValue(User.class).getWeight());
            //get firebase data
            profileName.setText(uInfo.getName());
            //get firebase data as int
            ageNum.setText(String.valueOf(uInfo.getAge()));
            heightNum.setText(String.valueOf(uInfo.getHeight()));
            weightNum.setText(String.valueOf(uInfo.getWeight()));


            String names[] = {"inactive and have rather no exercise", "active 1-3 days/week","active 3-5 days/week","active most days","active everyday"};

            ArrayAdapter <String> adapter;

            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names);

            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    switch (position)
                    {
                        case 0:
                            exercise = 1.2;
                            break;
                        case 1:
                            exercise = 1.3;
                            break;
                        case 2:
                            exercise = 1.55;
                            break;
                        case 3:
                            exercise = 1.725;
                            break;
                        case 4:
                            exercise = 1.9;
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            calcButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //CALCULATING BMR FOR MALE
                        double sumMale = (13.75 * uInfo.getWeight()) + (5 * uInfo.getHeight()) + (6.76 * uInfo.getAge()) + 66;
                        finalSumMale = sumMale * exercise;
                        String maleFormatted = String.format("%.0f", finalSumMale);
                        sumNum.setText(maleFormatted);
                        //CALCULATING BMR FOR FEMALE
                        double sumFemale = (9.56 * uInfo.getWeight()) + (1.85 * uInfo.getHeight()) + (4.68 * uInfo.getAge()) + 665;
                        finalSumFemale = sumFemale * exercise;
                        String femaleFormatted = String.format("%.0f", finalSumFemale);
                        sumNum2.setText(femaleFormatted);
                }
            });
        }
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
        getMenuInflater().inflate(R.menu.home, menu);
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
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_account:
                Intent a= new Intent(HomeActivity.this,Profile.class);
                startActivity(a);
                break;
            case R.id.nav_settings:
                Intent b= new Intent(HomeActivity.this,UserActivity.class);
                startActivity(b);
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_share:
                shareIt();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareIt(){
        Intent myIntent  = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "Im using Trip Planner";
        String shareSub = "Try it too!";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));

    }

    public void signOut() {
        auth.signOut();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
