package com.kayzr.kayzrstaff.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.AvailabilitiesAdapter;
import com.kayzr.kayzrstaff.adapters.RosterAdapter;
import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.JsonResponse;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailibilitiesFragment extends Fragment {


    protected RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.avTablayout) TabLayout mTablayout;
    @BindView(R.id.sendAvailabilities) Button sendAvailabilities;
    @BindView(R.id.avRecycler) RecyclerView mRecycler;
    @BindView(R.id.avNextWeekInfoText)TextView infoTextNextWeek;
    @BindView(R.id.avDate) TextView avDate;

    private int tabIndex = 0 ;
    private int amountOfAvailabiltiesSend = 0 ;
    private int totalAmountOfAVSend = 0 ;
    private List<Tournament> tournamentsOfThatDay = new ArrayList<>();
    private List<String> days = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availabilities, container, false);
        ButterKnife.bind(this, v);
        setCurrentDayAsDefault();
        calculateWeekDates();
        checkWeek();

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition(); // 0 = monday, 1 = dinsdag, 2 = woendag ...
                checkWeek();
                avDate.setText(days.get(tabIndex));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        return v;
    }

    private void checkWeek(){
        initializeAdapterData(MainActivity.app.dayOfWeek(tabIndex));
        //End Week false: Niet einde van de week. Dus availabilities zijn nog open.
        //End Week true: Einde van de Week. Roster Next Week is gemaakt en mensen kunnen geen availabilities meer invullen

        if(!MainActivity.app.getEndOfWeek().getEndWeek()){
            //data bereken voor availabilty en juiste adapter koppelen
            calculateData();
            initializeAvAdapter();
            infoTextNextWeek.setVisibility(View.GONE);
            sendAvailabilities.setVisibility(View.VISIBLE);

        } else {
            //adapter koppelen van roster
            calculateNextWeekData();
            initializeNextWeekAdapter();
            infoTextNextWeek.setVisibility(View.VISIBLE);
            sendAvailabilities.setVisibility(View.GONE);
        }
    }

    private void calculateData(){
        List<Availability> avs = new ArrayList<>();
        //Selecting the av for the current user
        // app crash when availabilities is 0
        if (MainActivity.app.getAvailabilities() != null) {
        for(Availability av : MainActivity.app.getAvailabilities()){
            if(av.getUser().equals(MainActivity.app.getCurrentUser().getUsername())){
                avs.add(av);
            }
        }
        //set the avs
        MainActivity.app.setAvailabilities(avs);

        //get the tournament details of the avs
        for(Tournament t : MainActivity.app.getThisWeek()){
            for(Availability av : MainActivity.app.getAvailabilities()){
                if(t.getId() == av.getTournamentId()){
                    av.setTournament(t);
                }
            }
        }
        } else {
            MainActivity.app.setAvailabilities(new ArrayList<Availability>());
        }

    }

    private void calculateNextWeekData(){

        tournamentsOfThatDay.clear();

        String selectedDay = MainActivity.app.dayOfWeek(tabIndex);

            for(Tournament t : MainActivity.app.getNextWeek()){
                if(t.getDag().equals(selectedDay)){
                    tournamentsOfThatDay.add(t);
                }
            }


    }

    private void initializeNextWeekAdapter(){

        RosterAdapter adapter = new RosterAdapter(tournamentsOfThatDay,true,getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initializeAvAdapter(){

        AvailabilitiesAdapter adapter = new AvailabilitiesAdapter(tournamentsOfThatDay, getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initializeAdapterData(String dayOfWeek){
        tournamentsOfThatDay.clear();

        for(Tournament t : MainActivity.app.getThisWeek()){
            if(t.getDag().equals(dayOfWeek)){
                tournamentsOfThatDay.add(t);
            }
        }
    }

    public void setCurrentDayAsDefault(){
        tabIndex = MainActivity.app.currentDayOfWeek();
        initializeAdapterData(MainActivity.app.dayOfWeek(tabIndex));
        mTablayout.getTabAt(tabIndex).select();
    }

    @OnClick(R.id.sendAvailabilities)
    public void clearAvailabilities(){
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call call = caller.clearAV("8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C", MainActivity.app.getCurrentUser().getUsername());
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                Log.d("Backend CAll", "call succes (clear Availabilities) ");
                Log.d("Backend CAll", "call RESPONSE " + jsonResponse.isError() + " message: " + jsonResponse.getMessage());

                totalAmountOfAVSend = MainActivity.app.getAvailabilities().size();

                if(!jsonResponse.isError()) {
                    if (totalAmountOfAVSend > 0) {
                        for (Availability av : MainActivity.app.getAvailabilities()) {
                            sendAvailability(av);
                        }
                    } else {
                        Toast.makeText(getContext(), "No availabilities send! ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "There was a disturbance in the force", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.e("Backend CAll", "call failed (clear Availabilities) " + t.getMessage());
                Toast.makeText(getContext(), "Failed to send availabilities!", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void sendAvailability(final Availability av){
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call call = caller.sendAV("8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C", MainActivity.app.getCurrentUser().getUsername(), String.valueOf(av.getTournamentId()) );
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                JsonResponse jsonResponse = response.body();
                Log.d("Backend CAll", "call succes (send Availabilities) ");
                Log.d("Backend CAll", "call RESPONSE " + jsonResponse.isError() + "message: " + jsonResponse.getMessage());
                if(!jsonResponse.isError()) {
                    amountOfAvailabiltiesSend++;
                }

                if(amountOfAvailabiltiesSend == totalAmountOfAVSend) {
                    Toast.makeText(getContext(), "Succesfully send " + amountOfAvailabiltiesSend + " availabilities", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.e("Backend CAll", "call failed (send Availabilities) " + t.getMessage());
                if(amountOfAvailabiltiesSend == totalAmountOfAVSend) {
                    Toast.makeText(getContext(), "Succesfully send " + amountOfAvailabiltiesSend + " availabilities", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void calculateWeekDates(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd.MM.yyyy", Locale.US);

        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }

        for (int i = 0; i < 7; i++) {
            days.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }

        avDate.setText(days.get(tabIndex));
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
