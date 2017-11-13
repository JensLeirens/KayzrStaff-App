package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private Context c;
    public AvailabilitiesAdapter(List<Tournament> tournaments, Context c) {
        this.tournaments = tournaments;
        this.itemCount = this.tournaments.size() ;
        this.c = c;
    }

    @Override
    public AvailabilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_availability, parent, false);
        return new AvailabilitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AvailabilitiesViewHolder holder, final int position) {
        TextView avCardHour = holder.avCardHour;
        TextView avCardtournament = holder.avCardtournament;
        CheckBox avBeschikbaar = holder.avBeschikbaar;
        CardView cardView = holder.cardView;
        final int pos = holder.getAdapterPosition();
        avCardHour.setText(tournaments.get(position).getUur());


        for(Availability av : MainActivity.app.getAvailabilities()){
            if(tournaments.get(position).getId() == av.getTournament().getId()){
                av.setChecked(true);
                avBeschikbaar.setChecked(av.isChecked());
            }
        }

        String tournamentName = tournaments.get(position).getNaamkort();
        if(tournamentName.contains("PS:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorPS));
            tournamentName = tournamentName.replace("PS:", "" );
        }else if(tournamentName.contains("Fun:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorFun));
            tournamentName = tournamentName.replace("Fun:", "" );
        }

        avCardtournament.setText(tournamentName);

        avBeschikbaar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b ) {
                Availability av = new Availability(MainActivity.app.getCurrentUser().getUsername(), tournaments.get(pos).getId());
                if(b) {
                    if (!MainActivity.app.getAvailabilities().contains(av)) {
                        MainActivity.app.getAvailabilities().add(av);
                    }
                } else {

                   for( Availability todelete: MainActivity.app.getAvailabilities()){
                       if( todelete.getTournamentId() == av.getTournamentId()){
                           av = todelete;
                       }
                   }
                   MainActivity.app.getAvailabilities().remove(av);

                }
            }
        });

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

        @BindView(R.id.card_viewCH)
        public CardView cardView;

        public AvailabilitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}