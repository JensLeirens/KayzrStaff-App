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
import com.kayzr.kayzrstaff.domain.Rank;
import com.kayzr.kayzrstaff.domain.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamInfoFragment extends Fragment {

    @BindView(R.id.teamInfoRecycler) RecyclerView mRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<User> users = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team_info, container, false);
        ButterKnife.bind(this, v);
        initdata();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        UserAdapter adapter = new UserAdapter(users,getContext());
        mRecycler.setAdapter(adapter);


        return v;
    }

    private void initdata(){
        users.add(new User( 1,false,"Pollie","Role", Rank.MOD,"0495/56.77.62","Pieter Jan Pollie"));
        users.add(new User( 2,false,"Mafken","Role", Rank.MOD,"0471626404","Jens Leirens"));
        users.add(new User( 1,false,"Pollie","Role", Rank.MOD,"0495/56.77.62","Pieter Jan Pollie"));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
