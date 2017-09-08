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

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.RosterAdapter;
import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RosterFragment extends Fragment {


    @BindView(R.id.rosterRecycler) RecyclerView mRecycler;
    @BindView(R.id.rosterTablayout) TabLayout mTablayout;

    protected RecyclerView.LayoutManager mLayoutManager;
    private List<Tournament> tournaments = new ArrayList<>();
    private int tabIndex = 0 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_roster, container, false);
        ButterKnife.bind(this, v);

        initdata();

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition(); // 0 = monday, 1 = dinsdag, 2 = woendag ...
                initializeAdapter();
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

    private void initializeAdapter(){
        List<Tournament> tournamentsOfThatDay = new ArrayList<>();
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

        for(Tournament t : tournaments){
            if(t.getDag().equals(selectedDay)){
                tournamentsOfThatDay.add(t);
            }
        }

        RosterAdapter adapter = new RosterAdapter(tournamentsOfThatDay);
        mRecycler.setAdapter(adapter);
    }

    private void initdata(){
        tournaments.add(new Tournament(1,"Counter Strike: Global Offense 2v2","CSGO 2v2", "Woensdag","06/09/2017","20h00","Mafken;ADC wildsquirte"));
        tournaments.add(new Tournament(1,"League of Legends 5v5","LOL 5V5", "Zaterdag","09/09/2017","19h30","Mafken"));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
