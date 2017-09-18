package com.kayzr.kayzrstaff.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvailibilitiesFragment extends Fragment {


    protected RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.avTablayout) TabLayout mTablayout;
    @BindView(R.id.sendAvailabilities) Button sendAvailabilities;
    @BindView(R.id.avRecycler) RecyclerView mRecycler;
    @BindView(R.id.avNextWeekInfoText)TextView infoTextNextWeek;
    //todo make the first index go to the current day of the week
    private int tabIndex = 0 ;
    private List<Tournament> tournamentsOfThatDay = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availabilities, container, false);
        ButterKnife.bind(this, v);

        //lijst van tournamenten voor die dag maken
        initializeAdapterData();
        //End Week 0 = Niet einde van de week. Dus availabilities zijn nog open.
        //End Week 1 = Einde van de Week. Roster Next Week is gemaakt en mensen kunnen geen availabilities meer invullen
        if(MainActivity.app.getEndOfWeek().getEndWeek() == 0 ){
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


        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition(); // 0 = monday, 1 = dinsdag, 2 = woendag ...
                initializeAdapterData();
                if(MainActivity.app.getEndOfWeek().getEndWeek() == 0 ){
                    calculateData();
                    initializeAvAdapter();
                    sendAvailabilities.setVisibility(View.VISIBLE);
                } else {
                    calculateNextWeekData();
                    initializeNextWeekAdapter();
                    sendAvailabilities.setVisibility(View.GONE);
                }
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

        String selectedDay = "";
        if(tabIndex == 0 ){
            selectedDay = "Maandag";
        } else if(tabIndex == 1){
            selectedDay = "Dinsdag";
        }else if(tabIndex == 2){
            selectedDay = "Woensdag";
        }else if(tabIndex == 3){
            selectedDay = "Donderdag";
        }else if(tabIndex == 4){
            selectedDay = "Vrijdag";
        }else if(tabIndex == 5){
            selectedDay = "Zaterdag";
        }else if(tabIndex == 6){
            selectedDay = "Zondag";
        }

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

    private void initializeAdapterData(){
        tournamentsOfThatDay.clear();

        String selectedDay = "";
        if(tabIndex == 0 ){
            selectedDay = "Maandag";
        } else if(tabIndex == 1){
            selectedDay = "Dinsdag";
        }else if(tabIndex == 2){
            selectedDay = "Woensdag";
        }else if(tabIndex == 3){
            selectedDay = "Donderdag";
        }else if(tabIndex == 4){
            selectedDay = "Vrijdag";
        }else if(tabIndex == 5){
            selectedDay = "Zaterdag";
        }else if(tabIndex == 6){
            selectedDay = "Zondag";
        }

        for(Tournament t : MainActivity.app.getThisWeek()){
            if(t.getDag().equals(selectedDay)){
                tournamentsOfThatDay.add(t);
            }
        }
    }

    @OnClick(R.id.sendAvailabilities)
    public void sendAvailabilities(){
        Toast.makeText(getContext(),"Sorry this feature is currently not implemented ",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
