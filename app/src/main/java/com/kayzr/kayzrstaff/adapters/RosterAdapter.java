package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView rosterCardHour = holder.rosterCardHour;
        TextView rosterCardMod = holder.rosterCardMod;
        TextView rosterCardName = holder.rosterCardName;
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
                    rosterCardMod.setTextColor(ContextCompat.getColor(c,R.color.colorMOD));
                } else {
                    rosterCardMod.setTextColor(ContextCompat.getColor(c, R.color.colorCM));
                }
            }
        }

        rosterCardMod.setText(mod);
        rosterCardHour.setText(tournaments.get(position).getUur());
        rosterCardName.setText(tournaments.get(position).getNaamkort());
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class RosterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rosterCardHour)
        public TextView rosterCardHour;

        @BindView(R.id.rosterCardMod)
        public TextView rosterCardMod;

        @BindView(R.id.rosterCardName)
        public TextView rosterCardName;


        public RosterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}