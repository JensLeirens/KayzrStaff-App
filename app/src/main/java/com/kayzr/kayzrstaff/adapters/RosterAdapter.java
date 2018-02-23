package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RosterAdapter extends RecyclerView.Adapter<RosterAdapter.RosterViewHolder> {

    private int itemCount;
    private Context c ;
    private List<Tournament> tournaments;
    private Boolean moderator;

    public RosterAdapter(List<Tournament> tournaments,Boolean moderator, Context c) {
        this.tournaments = tournaments;
        this.itemCount = tournaments.size();
        this.c = c ;
        this.moderator = moderator;
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
        TextView tournyDate = holder.tournyDate;
        ImageView tournyImage = holder.tournyImage;
        //CardView cardView = holder.cardView;
        String tournamentName = tournaments.get(position).getNaam();
        String tournamentNameKort = tournaments.get(position).getNaamkort();

        // checking for multiple moderators
        String mod;
        if(tournaments.get(position).getModerator().contains(";")){
            mod = tournaments.get(position).getModerator().replace(";"," & ");
        } else
        {
            mod = tournaments.get(position).getModerator() ;
        }

        //adding the color for the moderator
        for(User u : MainActivity.app.getKayzrTeam()){
            if(tournaments.get(position).getModerator().contains(u.getUsername())){
                if(u.getRole() == Role.Mod){
                    tournyMod.setTextColor(ContextCompat.getColor(c,R.color.colorMOD));
                } else if(u.getRole() == Role.CM){
                    tournyMod.setTextColor(ContextCompat.getColor(c, R.color.colorCM));
                } else if(u.getRole() == Role.Admin){
                    tournyMod.setTextColor(ContextCompat.getColor(c, R.color.colorAdmin));
                }
            }
        }

        //checking for cancelled tourny
        if(mod.equals("Cancelled")){
            tournyMod.setTextColor(ContextCompat.getColor(c,R.color.colorCancelled));
        }

        //adding the fun or PS colors

        if(tournamentName.contains("PS:")){
            //cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorPS));
            tournamentName = tournamentName.replace("PS: ", "" );
        }else if(tournamentName.contains("Fun:")){
            //cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorFun));
            tournamentName = tournamentName.replace("Fun: ", "" );
        }

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

        } else {
            Picasso.with(c).load(R.drawable.icon).into(tournyImage);
        }

        tournyMod.setText(moderator ? mod : "" );
        tournyStartHour.setText("Begint om " + tournaments.get(position).getUur());
        tournyName.setText(tournamentName);
        tournyDate.setText(tournaments.get(position).getDag() + " " + tournaments.get(position).getDatum());
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

        //@BindView(R.id.card_viewCH)
        //public CardView cardView;

        public RosterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}