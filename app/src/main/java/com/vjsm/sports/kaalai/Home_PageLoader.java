package com.vjsm.sports.kaalai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Home_PageLoader extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView SET, Name, PhoneNumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page_loader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("JallikattuHUB");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //headerView

        View headerView = navigationView.getHeaderView(0);
        Name = (TextView) headerView.findViewById(R.id.Names);
        PhoneNumber = (TextView) headerView.findViewById(R.id.PhoneNumber);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = getApplicationContext().getSharedPreferences("com.vjsm.sports.kaalai", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String names = sharedPreferences.getString(getString(R.string.LoginUserName), "");
        String phone = sharedPreferences.getString(getString(R.string.LoginUser_PhoneNumber), "");
        Name.setText(names);
        PhoneNumber.setText(phone);

        Fragment fragment = new Home();
        loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containers, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_PageLoader.this);
        builder.setTitle("JallikattuHUB");

        builder.setIcon(R.drawable.playstore_icon);
        builder.setMessage("Do you Want  Exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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

        if (id == R.id.Posts) {
            Fragment fragment=new Deletion_Activity();
            loadFragment(fragment);
        } else if (id == R.id.logout) {
            Fragment fragment=new Home();
            Bundle b= new Bundle();
            b.putString("logout","logout");
            fragment.setArguments(b);
            loadFragment(fragment);
        }
        else if (id==R.id.Home){
            Fragment fragment=new Home();
            loadFragment(fragment);
        }
        else if(id==R.id.about){
            Fragment fragment=new About_Our_Comapany();
            loadFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
