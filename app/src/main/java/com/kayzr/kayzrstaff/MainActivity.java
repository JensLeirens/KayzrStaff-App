package com.kayzr.kayzrstaff;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.DaoSession;
import com.kayzr.kayzrstaff.domain.EndWeek;
import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;
import com.kayzr.kayzrstaff.domain.UserDao;
import com.kayzr.kayzrstaff.fragments.AboutFragment;
import com.kayzr.kayzrstaff.fragments.AvailibilitiesFragment;
import com.kayzr.kayzrstaff.fragments.HomeFragment;
import com.kayzr.kayzrstaff.fragments.RosterFragment;
import com.kayzr.kayzrstaff.fragments.SettingsFragment;
import com.kayzr.kayzrstaff.fragments.TeamInfoFragment;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public static KayzrApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        app = (KayzrApp) getApplicationContext();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getThisWeekTournaments() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<Tournament>> call = caller.getThisWeekTournaments();
        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                app.setThisWeek(response.body());
                Log.d("Backend Call", " call successful (ThisWeekTournaments)");

            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e("Backend Call", "call failed (ThisWeekTournaments) " + t.getMessage());
            }
        });

    }

    public void getNextWeekTournaments() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<Tournament>> call = caller.getNextWeekTournaments();
        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                app.setNextWeek(response.body());
                Log.d("Backend Call", " call successful (NextWeekTournaments)");

            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e("Backend Call", "call failed (NextWeekTournaments) " + t.getMessage());
            }
        });

    }

    public void getAvailabilities() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<Availability>> call = caller.getAvailabilities();
        call.enqueue(new Callback<List<Availability>>() {
            @Override
            public void onResponse(Call<List<Availability>> call, Response<List<Availability>> response) {
                app.setAvailabilities(response.body());
                Log.d("Backend Call", " call successful (Availabilities)");
            }

            @Override
            public void onFailure(Call<List<Availability>> call, Throwable t) {
                Log.e("Backend Call", "call failed (Availabilities) " + t.getMessage());
            }
        });

    }

    public void getEndOfWeek() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<EndWeek>> call = caller.getEndweek();
        call.enqueue(new Callback<List<EndWeek>>() {
            @Override
            public void onResponse(Call<List<EndWeek>> call, Response<List<EndWeek>> response) {
                MainActivity.app.setEndOfWeek(response.body().get(0));
                Log.d("Backend Call", " call successful (getEndweek)");
                if (app.getEndOfWeek().getEndWeek() == 1) {
                    getNextWeekTournaments();
                }
            }


            @Override
            public void onFailure(Call<List<EndWeek>> call, Throwable t) {
                Log.e("Backend Call", "call failed (getEndweek) " + t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
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
        Fragment fragment = null;
        int id = item.getItemId();

        // Noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragment = new SettingsFragment();
            toolbar.setTitle("Settings");
        }

        // Replacing the fragment.
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;

        if (itemId == R.id.nav_home) {
            fragment = new HomeFragment();
            toolbar.setTitle("KayzrStaff");
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (itemId == R.id.nav_this_week) {
            fragment = new RosterFragment();
            toolbar.setTitle("This week");
            navigationView.getMenu().getItem(1).setChecked(true);
        } else if (itemId == R.id.nav_availabilities) {
            fragment = new AvailibilitiesFragment();
            toolbar.setTitle("Availibilities");
            navigationView.getMenu().getItem(2).setChecked(true);
        } else if (itemId == R.id.nav_team_info) {
            fragment = new TeamInfoFragment();
            toolbar.setTitle("Team Info");
            navigationView.getMenu().getItem(3).setChecked(true);
        } else if (itemId == R.id.nav_about) {
            fragment = new AboutFragment();
            toolbar.setTitle("About");
            navigationView.getMenu().getItem(4).setChecked(true);
        }

        // Replace the fragment.
        if (fragment != null) {
            // Make sure options show
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onResume() {
        super.onResume();
        // als er NOG GEEN current user is dan moeten we deze gaan ophalen
        if (app.getCurrentUser() == null) {
            // haal de user asynchroon uit de database
            new GetData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            //we hebben al een user dus we kunnen hem meteen laten inloggen
            checkNoUser();
        }

    }

    public void checkNoUser() {
        // check if the user is logged in
        if (app.getCurrentUser().isLoggedOn()) {
            //maak de api calls
            getThisWeekTournaments();
            getAvailabilities();
            getEndOfWeek();

            //zet de username in de sidebar
            View navView = navigationView.getHeaderView(0);
            TextView username = (TextView) navView.findViewById(R.id.userName);
            username.setText(MainActivity.app.getCurrentUser().getUsername());

            //ga naar het homescherm
            displaySelectedScreen(R.id.nav_home);
        } else {
            //if no user logged in then close the app
            if (!LoginActivity.leave) {
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
            } else {
                finish();
                //killing the application violently but effectively
                System.exit(0);
            }
        }
    }

    @Override
    public void onStop() {
        //als er een user is sla deze op!
        if (app.getCurrentUser() != null) {
            // asynchroon oplsaan!
            new SaveData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        super.onStop();
    }

    private class SaveData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //deze methode slaat de user op in de DB
            DaoSession daosesion = app.getDaoSession();
            UserDao userDao = daosesion.getUserDao();
            // eerste gaan we alle vorige users deleten
            //( want we willen alleen de current user opslaan geen andere)
            userDao.deleteAll();
            //nu gaan we de current user inserten in de database
            userDao.insert(app.getCurrentUser());

            return null;

        }
    }

    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // deze methode haalt de user uit de database

            DaoSession daosesion = app.getDaoSession();
            UserDao userDao = daosesion.getUserDao();
            if (!userDao.loadAll().isEmpty()){
                app.setCurrentUser(userDao.loadAll().get(0));
                app.getCurrentUser().setLoggedOn(false);
            } else {
                app.setCurrentUser(new User());
                app.getCurrentUser().setLoggedOn(false);
            }


            //als de user uit de DB is gaan we de controle starten voor de user in te loggen
            checkNoUser();
            return null;

        }
    }

}
