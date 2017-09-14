package com.kayzr.kayzrstaff.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvailabilitiesAdapter extends RecyclerView.Adapter<AvailabilitiesAdapter.AvailabilitiesViewHolder> {

    private int itemCount;
    private List<Tournament> tournaments;

    public AvailabilitiesAdapter(List<Tournament> tournaments) {
        this.tournaments = tournaments;
        this.itemCount = tournaments.size();
    }

    @Override
    public AvailabilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_availability, parent, false);
        return new AvailabilitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AvailabilitiesViewHolder holder, int position) {
        TextView avCardHour = holder.avCardHour;
        TextView avCardtournament = holder.avCardtournament;
        CheckBox avBeschikbaar = holder.avBeschikbaar;

        avCardHour.setText(tournaments.get(position).getUur());
        avCardtournament.setText(tournaments.get(position).getNaamkort());

        for(Availability av : MainActivity.app.getAvailabilities()){
            if(tournaments.get(position).getId() == av.getTournament().getId()){
                av.setChecked(true);
                avBeschikbaar.setChecked(av.isChecked());
            }
        }

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class AvailabilitiesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avCardHour)
        public TextView avCardHour;

        @BindView(R.id.avCardtournament)
        public TextView avCardtournament;

        @BindView(R.id.avBeschikbaar)
        public CheckBox avBeschikbaar;

        public AvailabilitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}