package com.codecamp.codecamp06.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.ui.fragment.AsyncTaskBooksListFragment;
import com.codecamp.codecamp06.ui.fragment.MainThreadBooksListFragment;
import com.codecamp.codecamp06.ui.fragment.RetrofitBooksListFragment;
import com.codecamp.codecamp06.ui.fragment.ThreadBooksListFragment;

/**
 * Activity with the list of books.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/2/2016.
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final String fragmentName;
                Log.d(TAG, "onNavigationItemSelected: " + item.getTitle());
                switch (item.getItemId()) {
                    case R.id.nav_main_thread:
                        fragmentName = MainThreadBooksListFragment.class.getName();
                        break;
                    case R.id.nav_thread_handler:
                        fragmentName = ThreadBooksListFragment.class.getName();
                        break;
                    case R.id.nav_async_task:
                        fragmentName = AsyncTaskBooksListFragment.class.getName();
                        break;
                    case R.id.nav_retrofit:
                        fragmentName = RetrofitBooksListFragment.class.getName();
                        break;

                    default:
                        fragmentName = null;
                }

                drawerLayout.closeDrawers();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        replaceFragment(fragmentName);
                    }
                }, 250);

                return true;
            }
        });

        if (savedInstanceState == null) {
            replaceFragment(MainThreadBooksListFragment.class.getName());
            navigationView.setCheckedItem(R.id.nav_main_thread);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    private void replaceFragment(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(fragmentName);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentName);
        }
        fm.beginTransaction().replace(R.id.fragment_container, fragment, fragmentName).commit();
    }
}
