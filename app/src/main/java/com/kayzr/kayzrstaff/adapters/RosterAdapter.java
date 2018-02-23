package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.domain.Role;
import com.kayzr.kayzrstaff.domain.Tournament;
import com.kayzr.kayzrstaff.domain.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RosterAdapter extends RecyclerView.Adapter<RosterAdapter.RosterViewHolder> {

    private int itemCount;
    private Context c ;
    private List<Tournament> tournaments;

    public RosterAdapter(List<Tournament> tournaments, Context c) {
        this.tournaments = tournaments;
        this.itemCount = tournaments.size();
        this.c = c ;
    }

    @Override
    public RosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_roster, parent, false);
        return new RosterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RosterViewHolder holder, int position) {
        TextView tournyStartHour = holder.tournyStartHour;
        TextView tournyMod = holder.tournyMod;
        TextView tournyName = holder.tournyName;
        CardView cardView = holder.cardView;
        String mod;

        if(tournaments.get(position).getModerator().contains(";")){
            mod = tournaments.get(position).getModerator().replace(";"," & ");
        } else
        {
            mod = tournaments.get(position).getModerator() ;
        }

        for(User u : MainActivity.app.getKayzrTeam()){
            if(tournaments.get(position).getModerator().equals(u.getUsername())){
                if(u.getRole() == Role.Mod){
                    tournyMod.setTextColor(ContextCompat.getColor(c,R.color.colorMOD));
                } else {
                    tournyMod.setTextColor(ContextCompat.getColor(c, R.color.colorCM));
                }
            }
        }

        if(mod.equals("Cancelled")){
            tournyMod.setTextColor(ContextCompat.getColor(c,R.color.colorCancelled));
        }

        String tournamentName = tournaments.get(position).getNaamkort();
        if(tournamentName.contains("PS:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorPS));
            tournamentName = tournamentName.replace("PS:", "" );
        }else if(tournamentName.contains("Fun:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorFun));
            tournamentName = tournamentName.replace("Fun:", "" );
        }


        tournyMod.setText(mod);
        tournyStartHour.setText(tournaments.get(position).getUur());
        tournyName.setText(tournamentName);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class RosterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardTournyStartHour)
        public TextView tournyStartHour;

        @BindView(R.id.cardTournyMod)
        public TextView tournyMod;

        @BindView(R.id.cardTournyDate)
        public TextView tournyDate;

        @BindView(R.id.cardTournyName)
        public TextView tournyName;

        @BindView(R.id.cardTournyImage)
        public ImageView tournyImage;

        @BindView(R.id.card_viewCH)
        public CardView cardView;

        public RosterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}