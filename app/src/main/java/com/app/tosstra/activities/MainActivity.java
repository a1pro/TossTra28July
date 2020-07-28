package com.app.tosstra.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tosstra.R;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavController navController;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ProgressBar progressBar1;
    private ImageView imageOpen, userPic;
    private TextView title_tv, nameUser;
    public static String userType;
    private MenuItem activeTruckOrAllJobs, myJobOrAddNewJob, activeDriverDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        initUI();
        setDrawer();
        
        Menu menu = navigationView.getMenu();
        activeTruckOrAllJobs = menu.findItem(R.id.activetrucks);
        myJobOrAddNewJob = menu.findItem(R.id.addjob);
        activeDriverDis = menu.findItem(R.id.activedrivers);
        View headerView = navigationView.getHeaderView(0);
        nameUser = (TextView) headerView.findViewById(R.id.nameUser);
        userType = getIntent().getStringExtra("user_type");
        if (userType.equalsIgnoreCase("driver")) {
            activeTruckOrAllJobs.setTitle("All Jobs");
            myJobOrAddNewJob.setTitle("My Jobs");
            activeDriverDis.setVisible(false);
            title_tv.setText("All Jobs");
            nameUser.setText("Driver");
            Log.e("User_id", PreferenceHandler.readString(MainActivity.this, PreferenceHandler.USER_ID, ""));
            navController.navigate(R.id.allJobsFragments);
        } else if (userType.equalsIgnoreCase("dispatcher")) {
            activeTruckOrAllJobs.setTitle("Available Trucks");
            myJobOrAddNewJob.setVisible(false);
            activeDriverDis.setTitle("Active Driver");
            title_tv.setText("All available trucks");
            nameUser.setText("Dispatcher");
            userPic.setVisibility(View.GONE);
            navController.navigate(R.id.activeTrucksFragments);
            Log.e("User_id", PreferenceHandler.readString(MainActivity.this, PreferenceHandler.USER_ID, ""));
        }

        imageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }

    NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

        }
    };

    private void initUI() {

        navController = Navigation.findNavController(MainActivity.this, R.id.mainNavFragment2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
//        progressBar1 = findViewById(R.id.pro1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_tv = findViewById(R.id.title_tv);
        imageOpen = findViewById(R.id.imageOpen);
        userPic = findViewById(R.id.userPic);
        userPic.setOnClickListener(this);
    }

    private void setDrawer() {
        navigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navController.getCurrentDestination().getId();
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.activetrucks:
                if (userType.equalsIgnoreCase("driver")) {
                    title_tv.setText("All Jobs");
                    navController.navigate(R.id.allJobsFragments);
                } else {
                    navController.navigate(R.id.activeTrucksFragments);
                    activeTruckOrAllJobs.setTitle("Available Trucks");
                    title_tv.setText("All available trucks");
                }
                break;
            case R.id.addjob:
                if (userType.equalsIgnoreCase("driver")) {
                    navController.navigate(R.id.myJobFragments);
                    title_tv.setText("My Jobs");
                } else {
                    navController.navigate(R.id.addAnewJobFragments);
                    title_tv.setText("Add a new Job");
                }
                break;

            case R.id.activedrivers:
                title_tv.setText("Active Drivers on job");
                navController.navigate(R.id.activerDriverFragments);
                break;

            case R.id.profile:
                title_tv.setText("Profile");
                navController.navigate(R.id.profileFragment);

                break;

            case R.id.notification:
                title_tv.setText("Notifications");
                navController.navigate(R.id.notificationFragment);
                break;


            case R.id.settings:
                title_tv.setText("Settings");
                navController.navigate(R.id.settingsFragment);
                break;


        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(MainActivity.this, R.id.mainNavFragment2), drawerLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userPic:
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
                break;
        }
    }
}