package com.example.login_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String namess;
    private TextView username,useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        this.namess = getIntent().getStringExtra("name");
        namess = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsername();
//        Toast.makeText(this,namess,Toast.LENGTH_SHORT).show();
       drawer = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar , R.string.open, R.string.close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navmenu);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        username = headerview.findViewById(R.id.username);
        useremail = headerview.findViewById(R.id.useremail);
        username.setText(namess);
        useremail.setText(namess+"@gmail.com");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new list_details()).commit();
            navigationView.setCheckedItem((int) R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_dashboard :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new list_details()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout:
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    public Bundle getMyData() {
        Bundle hm = new Bundle();
        hm.putString("name", this.namess);
        return hm;
    }
}