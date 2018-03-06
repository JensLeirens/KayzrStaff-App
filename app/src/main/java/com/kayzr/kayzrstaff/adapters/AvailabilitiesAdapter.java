package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.domain.Availability;
import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvailabilitiesAdapter extends RecyclerView.Adapter<AvailabilitiesAdapter.AvailabilitiesViewHolder> {

    private int itemCount;
    private List<Tournament> tournaments;
    private Context c;
    private KayzrApp app;

    public AvailabilitiesAdapter(List<Tournament> tournaments, Context c) {
        this.tournaments = tournaments;
        this.itemCount = this.tournaments.size() ;
        this.c = c;
        app = (KayzrApp) c.getApplicationContext();
    }

    @Override
    public AvailabilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_availability, parent, false);
        return new AvailabilitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AvailabilitiesViewHolder holder, final int position) {
        TextView avCardHour = holder.avCardHour;
        TextView avTourneyName = holder.avTourneyName;
        CheckBox avBeschikbaar = holder.avBeschikbaar;
        ImageView tournyImage = holder.avImage;
        final int pos = holder.getAdapterPosition();
        avCardHour.setText(c.getString(R.string.tournamentHour, tournaments.get(pos).getUur()));


        for(Availability av : app.getAvailabilities()){
            if(tournaments.get(pos).getId() == av.getTournament().getId()){
                av.setChecked(true);
                avBeschikbaar.setChecked(av.isChecked());
            }
        }

        String tournamentName = tournaments.get(pos).getNaam();
        if(tournamentName.contains("PS:")){
            //cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorPS));
            tournamentName = tournamentName.replace("PS:", "" );
        }else if(tournamentName.contains("Fun:")){
            //cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorFun));
            tournamentName = tournamentName.replace("Fun:", "" );
        }

        avTourneyName.setText(tournamentName);

        String tournamentNameKort = tournaments.get(pos).getNaamkort();
        //adding the correct image
        if (tournamentNameKort.contains("CS:GO")) {
            Picasso.with(c).load(R.drawable.csgo).into(tournyImage);

        } else if (tournamentNameKort.contains("HS")) {
            Picasso.with(c).load(R.drawable.hs).into(tournyImage);

        } else if (tournamentNameKort.contains("LoL")) {
            Picasso.with(c).load(R.drawable.lol).into(tournyImage);

        } else if (tournamentNameKort.contains("RL")) {
            Picasso.with(c).load(R.drawable.rl).into(tournyImage);

        } else if (tournamentNameKort.contains("COD")) {
            Picasso.with(c).load(R.drawable.cod).into(tournyImage);

        } else if (tournamentNameKort.contains("BR")) {
            Picasso.with(c).load(R.drawable.battlerite).into(tournyImage);

        } else if (tournamentNameKort.contains("FIFA")) {
            Picasso.with(c).load(R.drawable.fifa).into(tournyImage);

        } else if (tournamentNameKort.contains("Dota")) {
            Picasso.with(c).load(R.drawable.dota).into(tournyImage);

        } else if (tournamentNameKort.contains("BH")) {
            Picasso.with(c).load(R.drawable.brawlhalla).into(tournyImage);

        } else if (tournamentNameKort.contains("OW")) {
            Picasso.with(c).load(R.drawable.overwatch).into(tournyImage);

        } else if (tournamentNameKort.contains("SC")) {
            Picasso.with(c).load(R.drawable.scii).into(tournyImage);

        } else {
            Picasso.with(c).load(R.drawable.icon).into(tournyImage);
        }

        avBeschikbaar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b ) {
                Availability av = new Availability(app.getCurrentUser().getUsername(), tournaments.get(pos).getId());
                if(b) {
                    if (!app.getAvailabilities().contains(av)) {
                        app.getAvailabilities().add(av);
                    }
                } else {

                   for( Availability todelete: app.getAvailabilities()){
                       if( todelete.getTournamentId() == av.getTournamentId()){
                           av = todelete;
                       }
                   }
                   app.getAvailabilities().remove(av);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class AvailabilitiesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avHour)
        public TextView avCardHour;

        @BindView(R.id.avName)
        public TextView avTourneyName;

        @BindView(R.id.avBeschikbaar)
        public CheckBox avBeschikbaar;

        @BindView(R.id.avImage)
        public ImageView avImage;

        public AvailabilitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}