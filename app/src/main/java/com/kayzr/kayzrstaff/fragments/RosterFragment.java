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

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.RosterAdapter;
import com.kayzr.kayzrstaff.domain.KayzrApp;
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
    private int tabIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_roster, container, false);
        ButterKnife.bind(this, v);

        initdata();

        KayzrApp app = (KayzrApp) getActivity().getApplicationContext();

        setCurrentDayAsDefault();

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabIndex = tab.getPosition(); // 0 = monday, 1 = dinsdag, 2 = woendag ...
                //get String from tabindex
                initializeAdapter(MainActivity.app.dayOfWeek(tabIndex));//insertstring
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

    public void setCurrentDayAsDefault(){

        int tab = MainActivity.app.currentDayOfWeek();
        initializeAdapter(MainActivity.app.dayOfWeek(tab));
        mTablayout.getTabAt(tab).select();

    }

    private void initializeAdapter(String dayOfWeek){
        List<Tournament> tournamentsOfThatDay = new ArrayList<>();


        for(Tournament t : tournaments){
            if(t.getDag().equals(dayOfWeek)){
                tournamentsOfThatDay.add(t);
            }
        }

        RosterAdapter adapter = new RosterAdapter(tournamentsOfThatDay,true,getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initdata(){
       tournaments = MainActivity.app.getThisWeek();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
