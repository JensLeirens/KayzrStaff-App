package com.kayzr.kayzrstaff.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.HomeAdapter;
import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {


    @BindView(R.id.modDaysRecycler) RecyclerView mRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<Tournament> tournaments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        initdata();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        HomeAdapter adapter = new HomeAdapter(tournaments);
        mRecycler.setAdapter(adapter);


        return v;
    }

    private void initdata(){
        //TODO: check op current user name then display these tourneys
        tournaments.add(new Tournament(1,"Counter Strike: Global Offense 2v2","CSGO 2v2", "Woensdag","06/09/2017","20h00","Mafken;ADC wildsquirte"));
        tournaments.add(new Tournament(1,"League of Legends 5v5","LOL 5V5", "Zaterdag","09/09/2017","19h30","Mafken"));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
