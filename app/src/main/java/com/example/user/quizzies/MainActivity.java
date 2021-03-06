package com.example.user.quizzies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.quizzies.Fragment.historyFragment;
import com.example.user.quizzies.Fragment.playFragment;
import com.example.user.quizzies.Fragment.scanFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
playFragment.OnFragmentInteractionListener,
scanFragment.OnFragmentInteractionListener,
historyFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    ImageView navUserPic;
    TextView navUserName;
    TextView navUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intentStartedThisActivity = getIntent();
        String fragmentToLoad = intentStartedThisActivity.getStringExtra("fragmentToLoad");

        if(fragmentToLoad != null) {
            if (fragmentToLoad.equalsIgnoreCase("Play")) {
                Fragment fragment = new playFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, "play").commit();
            } else if (fragmentToLoad.equalsIgnoreCase("Scan")) {
                Fragment fragment = new scanFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, "scan").commit();
            }
        }else{
            Fragment fragment = new playFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, "play").commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        try {
            if (currentUser.getPhotoUrl() != null){
                String profilePicURL = currentUser.getPhotoUrl().toString();
            navUserPic = (ImageView) findViewById(R.id.navUserPic);
            Picasso.with(getApplicationContext()).load(profilePicURL).into(navUserPic);
            }

            navUserName = (TextView) findViewById(R.id.navUserName);
            navUserName.setText(currentUser.getDisplayName());
            navUserEmail = (TextView) findViewById(R.id.navUserEmail);
            navUserEmail.setText(currentUser.getEmail());
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (id == R.id.nav_gallery) {
            Fragment fragment = new playFragment();
            ft.replace(R.id.frame_container, fragment, "play").commit();
        } else if (id == R.id.nav_camera) {
            Fragment fragment = new scanFragment();
            ft.replace(R.id.frame_container, fragment, "scan").commit();
        } else if (id == R.id.nav_score) {
            Intent intent = new Intent(getApplicationContext(), MyScoreActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Fragment fragment = new historyFragment();
            ft.replace(R.id.frame_container, fragment, "history").commit();
        }else if (id == R.id.nav_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                //showSnackbar(R.string.sign_out_failed);
                            }
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
