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
import com.kayzr.kayzrstaff.adapters.UserAdapter;
import com.kayzr.kayzrstaff.domain.KayzrApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamInfoFragment extends Fragment {

    @BindView(R.id.teamInfoRecycler) RecyclerView mRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    private KayzrApp app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team_info, container, false);
        ButterKnife.bind(this, v);
        app = (KayzrApp) getActivity().getApplicationContext();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        UserAdapter adapter = new UserAdapter(app.getKayzrTeam(),getContext());
        mRecycler.setAdapter(adapter);


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
