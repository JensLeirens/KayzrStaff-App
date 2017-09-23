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

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.HomeAdapter;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        System.out.println(MainActivity.app.getCurrentUser().isRememberUsernameAndPass());

/*        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        HomeAdapter adapter = new HomeAdapter(tournaments);
        mRecycler.setAdapter(adapter);*/

        return v;
    }

    private void initdata(){

        if(MainActivity.app.getCurrentUser() != null){
            for(Tournament t : MainActivity.app.getThisWeek()){
                if(t.getModerator().contains(MainActivity.app.getCurrentUser().getUsername())){
                    tournaments.add(t);
                }
            }
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        HomeAdapter adapter = new HomeAdapter(tournaments);
        mRecycler.setAdapter(adapter);

    }

    public void getUsersList() {

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call<List<User>> call = caller.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                MainActivity.app.setKayzrTeam(response.body());
                Log.d("Backend Call", " call successful (getTeam)");
                initdata();
            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Backend CAll", "call failed (getTeam) " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUsersList();
    }
}
