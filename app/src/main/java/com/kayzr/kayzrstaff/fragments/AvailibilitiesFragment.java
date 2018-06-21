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

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.adapters.AvailabilitiesAdapter;
import com.kayzr.kayzrstaff.adapters.RosterAdapter;
import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvRequest;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AvSendResponse;
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
    @BindView(R.id.avTablayout)
    TabLayout mTablayout;
    @BindView(R.id.sendAvailabilities)
    Button sendAvailabilities;
    @BindView(R.id.avRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.avNextWeekInfoText)
    TextView infoTextNextWeek;
    @BindView(R.id.avDate)
    TextView avDate;

    private int tabIndex = 0;
    private int amountOfAvailabiltiesSend = 0;
    private int totalAmountOfAVSend = 0;
    private List<Tournament> tournamentsOfThatDay = new ArrayList<>();
    private List<String> days = new ArrayList<>();
    private KayzrApp app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availabilities, container, false);
        ButterKnife.bind(this, v);
        app = (KayzrApp) getActivity().getApplicationContext();
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

    private void checkWeek() {
        initializeAdapterData(app.dayOfWeek(tabIndex));
        //End Week false: Niet einde van de week. Dus availabilities zijn nog open.
        //End Week true: Einde van de Week. Roster Next Week is gemaakt en mensen kunnen geen availabilities meer invullen

        if (!app.getEndOfWeek().getEndWeek()) {
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

    private void calculateData() {
        List<Availability> avs = new ArrayList<>();
        //Selecting the av for the current user
        // app crash when availabilities is 0
        if (app.getAvailabilities() != null) {
            for (Availability av : app.getAvailabilities()) {
                if (av.getUser().getUsername().equals(app.getCurrentUser().getUsername())) {
                    avs.add(av);
                }
            }
            //set the avs
            app.setAvailabilities(avs);

            //get the tournament details of the avs
            for (Tournament t : app.getThisWeek()) {
                for (Availability av : app.getAvailabilities()) {
                    if (t.getId().equals(av.getTournament().getId())) {
                        av.setTournament(t);
                    }
                }
            }
        } else {
            app.setAvailabilities(new ArrayList<Availability>());
        }

    }

    private void calculateNextWeekData() {

        tournamentsOfThatDay.clear();

        String selectedDay = app.dayOfWeek(tabIndex);

        for (Tournament t : app.getNextWeek()) {
            if (t.getDag().equals(selectedDay)) {
                tournamentsOfThatDay.add(t);
            }
        }


    }

    private void initializeNextWeekAdapter() {

        RosterAdapter adapter = new RosterAdapter(tournamentsOfThatDay, true, getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initializeAvAdapter() {

        AvailabilitiesAdapter adapter = new AvailabilitiesAdapter(tournamentsOfThatDay, getContext());
        mRecycler.setAdapter(adapter);
    }

    private void initializeAdapterData(String dayOfWeek) {
        tournamentsOfThatDay.clear();

        for (Tournament t : app.getThisWeek()) {
            if (t.getDag().equals(dayOfWeek)) {
                tournamentsOfThatDay.add(t);
            }
        }
    }
    @OnClick(R.id.sendAvailabilities)
    public void sendAvailabilities() {
        // lijst met tounament ids opvullen
        List<String> tournamentIds = new ArrayList<>();
        for (Availability av : app.getAvailabilities()) {
            tournamentIds.add(av.getTournament().getId());
        }

        // maak AvRequest object
        // this is nice :P
        AvRequest avRequest = new AvRequest()
                .setKey("8w03QQ2ByD6vxZEPSBjPJR89SeQhoR8C")
                .setUser(app.getCurrentUser().getUsername())
                .setTournaments(tournamentIds);

        Calls caller = Config.getRetrofit().create(Calls.class);
        Call call = caller.sendAVs(avRequest);
        call.enqueue(new Callback<AvSendResponse>() {
            @Override
            public void onResponse(Call<AvSendResponse> call, Response<AvSendResponse> response) {

                Log.d("Backend CAll", "call succes (send Availabilities) ");
                Log.d("Backend CAll", "call RESPONSE " + response.body().isSuccess() + " message: " + response.body().getMessage());

                totalAmountOfAVSend = app.getAvailabilities().size();

                if (response.body().isSuccess()) {
                    Toast.makeText(getContext(), response.body().getData().getSuccess() + " availabilities send, "
                            + response.body().getData().getFailed() + " availabilities failed, ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "There was a disturbance in the force, no availabilities send", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AvSendResponse> call, Throwable t) {
                Log.e("Backend CAll", "call failed (send Availabilities) " + t.getMessage());
                Toast.makeText(getContext(), "Failed to send availabilities!", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void calculateWeekDates() {
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
