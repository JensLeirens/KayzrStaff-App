package com.kayzr.kayzrstaff;

import android.content.Intent;
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
import android.widget.Toast;

import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.EndWeekResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.TournamentResponse;
import com.kayzr.kayzrstaff.domain.NetworkClasses.UserResponse;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;
import com.kayzr.kayzrstaff.fragments.AboutFragment;
import com.kayzr.kayzrstaff.fragments.AvailibilitiesFragment;
import com.kayzr.kayzrstaff.fragments.HomeFragment;
import com.kayzr.kayzrstaff.fragments.RosterFragment;
import com.kayzr.kayzrstaff.fragments.SettingsFragment;
import com.kayzr.kayzrstaff.fragments.TeamInfoFragment;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.util.ArrayList;
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

    private static int currentPage = R.id.nav_home;
    private KayzrApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        app = (KayzrApp) getApplicationContext();
        // als er NOG GEEN current user is dan moeten we deze gaan ophalen
        if (app.getCurrentUser() == null) {
            // haal de user uit de database
            app.getData();
        }
        //kijken of hij all ingelogged is en zo nodig dan laten inloggen
        //checkLoggedIn();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStop(){
        app.getCurrentUser().setLoggedOn(false);
        app.saveData();
        super.onStop();

    }

    @Override
    public void onDestroy(){
        app.getCurrentUser().setLoggedOn(false);
        app.saveData();
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        //maak de api calls
        getThisWeekTournaments();
        getAvailabilities();
        getEndOfWeek();
        getTeamInfo();
        // als er NOG GEEN current user is dan moeten we deze gaan ophalen
        if (app.getCurrentUser() == null) {
            // haal de user asynchroon uit de database
            app.getData();
        } else {
            // asynchroon opslaan!
            app.saveData();

        }
        //kijken of hij all ingelogged is en zo nodig dan laten inloggen
        checkLoggedIn();

    }
    public void getThisWeekTournaments() {
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<TournamentResponse> call = caller.getThisWeekTournaments();
        call.enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(Call<TournamentResponse> call, Response<TournamentResponse> response) {
                if(response.body().isSuccess()) {
                    app.setThisWeek(response.body().getData());
                    Log.d("Backend Call", " call successful (ThisWeekTournaments)");
                } else {
                    Log.e("Backend Call", "Call UnSuccessful (ThisWeekTournaments) " + response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong: " + response.body().getMessage() + " Report this to Mafken" ,
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TournamentResponse> call, Throwable t) {
                Log.e("Backend Call", "call failed (ThisWeekTournaments) " + t.getMessage());
            }
        });

    }

    public void getNextWeekTournaments() {
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<TournamentResponse> call = caller.getNextWeekTournaments();
        call.enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(Call<TournamentResponse> call, Response<TournamentResponse> response) {
                if(response.body().isSuccess()){
                    app.setNextWeek(response.body().getData());
                    Log.d("Backend Call", " call successful (NextWeekTournaments)");
                } else {
                    Log.e("Backend Call", "Call UnSuccessful (NextWeekTournaments) " + response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong: " + response.body().getMessage() + " Report this to Mafken" ,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TournamentResponse> call, Throwable t) {
                Log.e("Backend Call", "call failed (NextWeekTournaments) " + t.getMessage());
            }
        });

    }

    public void getAvailabilities() {
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<AvResponse> call = caller.getAvailabilities();
        call.enqueue(new Callback<AvResponse>() {
            @Override
            public void onResponse(Call<AvResponse> call, Response<AvResponse> response) {
                if(response.body().isSuccess()){
                    List<AvResponse.NetworkAvailability> avResponses = response.body().getData();
                    List<Availability> availabilities = new ArrayList<>();

                    for (AvResponse.NetworkAvailability na: avResponses) {
                        User user = new User();
                        Tournament t;
                        for(User tempUser : app.getKayzrTeam()){
                            if(tempUser.getUsername().equals(na.getUsername())){
                                user = tempUser ;
                            }
                        }
                        //maak voor elk tournamentId een availability object
                        for(String tournamentId: na.getTournaments()){
                            Availability av = new Availability(user, new Tournament());
                            av.getTournament().setId(tournamentId);
                            availabilities.add(av);
                        }

                    }
                    app.setAvailabilities(availabilities);

                    Log.d("Backend Call", " call successful (Availabilities)");
                } else {
                    Log.e("Backend Call", "Call UnSuccessful (NextWeekTournaments) " + response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong: " + response.body().getMessage() + " Report this to Mafken" ,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AvResponse> call, Throwable t) {
                Log.e("Backend Call", "call failed (Availabilities) " + t.getMessage());
            }
        });

    }

    public void getEndOfWeek() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<EndWeekResponse> call = caller.getEndweek();
        call.enqueue(new Callback<EndWeekResponse>() {
            @Override
            public void onResponse(Call<EndWeekResponse> call, Response<EndWeekResponse> response) {
                app.setEndOfWeek(response.body().getEndWeek());
                Log.d("Backend Call", " call successful (getEndweek)");
                if (app.getEndOfWeek().getEndWeek()) {
                    getNextWeekTournaments();
                }
            }


            @Override
            public void onFailure(Call<EndWeekResponse> call, Throwable t) {
                Log.e("Backend Call", "call failed (getEndweek) " + t.getMessage());
            }
        });
    }

    public void getTeamInfo() {
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<UserResponse> call = caller.getUsers("8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().isSuccess()){
                    app.setKayzrTeam(response.body().getData());
                    Log.d("Backend Call", " call successful (getTeam)");
                }else {
                    Log.e("Backend Call", "Call UnSuccessful (GetTeam) " + response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Something went wrong: " + response.body().getMessage() + " Report this to Mafken" ,
                            Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Backend CAll", "call failed (getTeam) " + t.getMessage());
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
            currentPage = R.id.nav_home ;
        } else if (itemId == R.id.nav_this_week) {
            fragment = new RosterFragment();
            toolbar.setTitle("This week");
            navigationView.getMenu().getItem(1).setChecked(true);
            currentPage = R.id.nav_this_week ;
        } else if (itemId == R.id.nav_availabilities) {
            fragment = new AvailibilitiesFragment();
            toolbar.setTitle("availabilities");
            navigationView.getMenu().getItem(2).setChecked(true);
            currentPage = R.id.nav_availabilities ;
        } else if (itemId == R.id.nav_team_info) {
            fragment = new TeamInfoFragment();
            toolbar.setTitle("Team Info");
            navigationView.getMenu().getItem(3).setChecked(true);
            currentPage = R.id.nav_team_info ;
        } else if (itemId == R.id.nav_about) {
            fragment = new AboutFragment();
            toolbar.setTitle("About");
            navigationView.getMenu().getItem(4).setChecked(true);
            currentPage = R.id.nav_about ;
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

    public void checkLoggedIn() {
        // check if the user is logged in
        if (app.getCurrentUser().isLoggedOn()) {

            //zet de username in de sidebar
            View navView = navigationView.getHeaderView(0);
            TextView username = navView.findViewById(R.id.userName);
            username.setText(app.getCurrentUser().getUsername());

            //ga naar zijn laatste scherm
            displaySelectedScreen(currentPage);
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
}
