package com.kayzr.kayzrstaff.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.RosterAdapter;
import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    @BindView(R.id.modDaysRecycler) RecyclerView mRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<Tournament> tournaments = new ArrayList<>();
    private KayzrApp app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        app = (KayzrApp) getActivity().getApplicationContext();

        processData();
        getThisWeekTournaments();

        return v;
    }

    private void processData(){
        tournaments.clear();

        if(app.getCurrentUser() != null){
            for(Tournament t : app.getThisWeek()){
                if(t.getModerator().contains(app.getCurrentUser().getUsername())){
                    tournaments.add(t);
                }
            }
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        RosterAdapter adapter = new RosterAdapter(tournaments,false, getContext());
        mRecycler.setAdapter(adapter);

    }

    public void getThisWeekTournaments() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<Tournament>> call = caller.getThisWeekTournaments();
        call.enqueue(new Callback<List<Tournament>>() {
            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                app.setThisWeek(response.body());
                processData();
                Log.d("Backend Call", " call successful (ThisWeekTournaments)");

            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Log.e("Backend Call", "call failed (ThisWeekTournaments) " + t.getMessage());
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
