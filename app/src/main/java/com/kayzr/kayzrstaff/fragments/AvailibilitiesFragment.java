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

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.clearAvailabilities) Button clearAvailabilities;
    @BindView(R.id.avRecycler) RecyclerView mRecycler;
    @BindView(R.id.avNextWeekInfoText)TextView infoTextNextWeek;


    private int tabIndex;
    private int amountOfAvailabiltiesSend = 0 ;
    private int totalAmountOfAVSend = 0 ;
    private List<Tournament> tournamentsOfThatDay = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availabilities, container, false);
        ButterKnife.bind(this, v);
        sendAvailabilities.setEnabled(false);

        //lijst van tournamenten voor die dag maken
        setCurrentDayAsDefault();
        //End Week 0 = Niet einde van de week. Dus availabilities zijn nog open.
        //End Week 1 = Einde van de Week. Roster Next Week is gemaakt en mensen kunnen geen availabilities meer invullen



        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition(); // 0 = monday, 1 = dinsdag, 2 = woendag ...
                initializeAdapterData(MainActivity.app.dayOfWeek(tabIndex));
                checkWeek();
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
        if(MainActivity.app.getEndOfWeek().getEndWeek() == 0 ){
            //data bereken voor availabilty en juiste adapter koppelen
            calculateData();
            initializeAvAdapter();
            infoTextNextWeek.setVisibility(View.GONE);
            sendAvailabilities.setVisibility(View.VISIBLE);
            clearAvailabilities.setVisibility(View.VISIBLE);
        } else {
            //adapter koppelen van roster
            calculateNextWeekData();
            initializeNextWeekAdapter();
            infoTextNextWeek.setVisibility(View.VISIBLE);
            sendAvailabilities.setVisibility(View.GONE);
            clearAvailabilities.setVisibility(View.GONE);

        }
    }

    private void calculateData(){
        List<Availability> avs = new ArrayList<>();
        //Selecting the av for the current user
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

        RosterAdapter adapter = new RosterAdapter(tournamentsOfThatDay,getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initializeAvAdapter(){

        AvailabilitiesAdapter adapter = new AvailabilitiesAdapter(tournamentsOfThatDay);
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
        int tab = MainActivity.app.currentDayOfWeek();
        initializeAdapterData(MainActivity.app.dayOfWeek(tab));
        mTablayout.getTabAt(tab).select();
    }

    @OnClick(R.id.sendAvailabilities)
    public void sendAvailabilities(){

        totalAmountOfAVSend = MainActivity.app.getAvailabilities().size();

        for (Availability av : MainActivity.app.getAvailabilities()){
            sendCallAvailabilities(av);
        }


    }

    @OnClick(R.id.clearAvailabilities)
    public void clearAvailabilities(){
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call call = caller.clearAV(MainActivity.app.getCurrentUser().getUsername(),"8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C");
        call.enqueue(new Callback<List<JsonResponse>>() {
            @Override
            public void onResponse(Call<List<JsonResponse>> call, Response<List<JsonResponse>> response) {
                List<JsonResponse> responses = response.body();
                Log.d("Backend CAll", "call succes (clear Availabilities) ");
                Log.d("Backend CAll", "call RESPONSE " + responses.get(0).getError());

            }

            @Override
            public void onFailure(Call<List<JsonResponse>> call, Throwable t) {
                Log.e("Backend CAll", "call failed (clear Availabilities) " + t.getMessage());
            }
        });

        Toast.makeText(getContext(),"Succesfully cleared availabilities, you are able to send now",Toast.LENGTH_LONG).show();
        sendAvailabilities.setEnabled(true);
    }

    public void sendCallAvailabilities(final Availability av){
        Calls caller = Config.getRetrofit().create(Calls.class);
        Call call = caller.sendAV(MainActivity.app.getCurrentUser().getUsername(), String.valueOf(av.getTournamentId()), "8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C");
        call.enqueue(new Callback<List<JsonResponse>>() {
            @Override
            public void onResponse(Call<List<JsonResponse>> call, Response<List<JsonResponse>> response) {
                List<JsonResponse> responses = response.body();
                Log.d("Backend CAll", "call succes (send Availabilities) ");
                Log.d("Backend CAll", "call RESPONSE " + responses.get(0).getError());
                amountOfAvailabiltiesSend ++;

                if(amountOfAvailabiltiesSend == totalAmountOfAVSend) {
                    Toast.makeText(getContext(), "Succesfully send " + amountOfAvailabiltiesSend + " availabilities", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.app.getAvailabilities().clear();
                    checkWeek();
                    Toast.makeText(getContext(),"Please recheck your availabilities and clear them before sending. TRY another time! Please contact Mafken!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<JsonResponse>> call, Throwable t) {
                Log.e("Backend CAll", "call failed (send Availabilities) " + t.getMessage());
                if(amountOfAvailabiltiesSend == totalAmountOfAVSend) {
                    Toast.makeText(getContext(), "Succesfully send " + amountOfAvailabiltiesSend + " availabilities", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.app.getAvailabilities().clear();
                    checkWeek();
                    Toast.makeText(getContext(),"Please recheck your availabilities and clear them before sending. TRY another time! Please contact Mafken!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
