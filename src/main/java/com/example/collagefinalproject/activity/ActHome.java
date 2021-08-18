package com.example.collagefinalproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.collagefinalproject.R;
import com.example.collagefinalproject.databinding.ActHomeBinding;
import com.example.collagefinalproject.databinding.NavHeaderMainBinding;
import com.example.collagefinalproject.db.DBHelper;
import com.example.collagefinalproject.model.User;
import com.google.android.material.navigation.NavigationView;

public class ActHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    public ActHomeBinding mBinding;
    NavController navController;
    boolean doubleBackToExitPressedOnce = false;
    private DBHelper dbHelper;
    private AppCompatTextView tvCartItemCount;
    private RelativeLayout rlCartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActHomeBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        dbHelper = new DBHelper(this);

        mBinding.include.toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(mBinding.include.toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(mBinding.drawerLayout)
                .build();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.include.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, navController);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        mBinding.include.toolbar.setNavigationOnClickListener(v -> {
            if (navController.getGraph().getStartDestination() != navController.getCurrentDestination().getId()) {
                ActHome.super.onBackPressed();
            } else mBinding.drawerLayout.openDrawer(GravityCompat.START);
        });


        mBinding.navView.setNavigationItemSelectedListener(this);

        View headerView = mBinding.navView.getHeaderView(0);
        NavHeaderMainBinding drawerHeaderBinding = NavHeaderMainBinding.bind(headerView);

        if (getIntent() != null) {
            User user = dbHelper.getUser(getIntent().getStringExtra("EMAIL"));
            drawerHeaderBinding.tvUserName.setText(user.getName());
            drawerHeaderBinding.tvEmail.setText(user.getEmail());
        }
    }

    public void hideToolbar() {
        mBinding.include.toolbar.setVisibility(View.GONE);
    }

    public void visibleToolbar() {
        mBinding.include.toolbar.setVisibility(View.VISIBLE);
    }


    public void navigate(int navId, Bundle arg) {
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(navId, true).build();
        navController.navigate(navId, arg, navOptions);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                navigate(R.id.nav_home, null);
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.menu_logout:
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                        .setMessage(getString(R.string.exit_app))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                            finish();
                            startActivity(new Intent(this, ActLogin.class));
                        })
                        .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.dismiss())
                        .show();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.actionCart);

        View actionView = menuItem.getActionView();
        tvCartItemCount = actionView.findViewById(R.id.tv_cart_badge);
        rlCartBadge = actionView.findViewById(R.id.rlCartBadge);
        setupCartBadge();

        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));

        return true;
    }

    public void setupCartBadge() {
        if (tvCartItemCount != null) {
            if (dbHelper.getAllCartItems().size() == 0) {
                if (rlCartBadge.getVisibility() != View.GONE) {
                    rlCartBadge.setVisibility(View.GONE);
                }
            } else {
                tvCartItemCount.setText(String.valueOf(Math.min(dbHelper.getAllCartItems().size(), 99)));
                if (rlCartBadge.getVisibility() != View.VISIBLE) {
                    rlCartBadge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionCart) {
            navigate(R.id.nav_cart, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (navController.getGraph().getStartDestination() != navController.getCurrentDestination().getId()) {
            ActHome.super.onBackPressed();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.exit_message), Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 1000);
        }
    }

}