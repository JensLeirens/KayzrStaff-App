package com.kayzr.kayzrstaff.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.domain.Tournament;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private int itemCount;
    private List<Tournament> tournaments;
    private Context c ;

    public HomeAdapter( List<Tournament> tournaments, Context c) {
        this.tournaments = tournaments;
        this.itemCount = tournaments.size();
        this.c = c ;

    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mod_day, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        TextView modDayDate = holder.modDayDate;
        TextView modDayTime = holder.modDayTime;
        TextView modDayTournament = holder.modDayTournament;
        CardView cardView = holder.cardView;


        modDayDate.setText(tournaments.get(position).getDag() + " " + tournaments.get(position).getDatum());
        modDayTime.setText(String.format("%s%s", "Tourney time: ",tournaments.get(position).getUur()));

        String tournamentName = tournaments.get(position).getNaam() ;
        if(tournamentName.contains("PS:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorPS));
            tournamentName = tournamentName.replace("PS:", "" );
        }else if(tournamentName.contains("Fun:")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(c,R.color.colorFun));
            tournamentName = tournamentName.replace("Fun:", "" );
        }

        modDayTournament.setText(tournamentName);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.modDayDate)
        public TextView modDayDate;

        @BindView(R.id.modDayTime)
        public TextView modDayTime;

        @BindView(R.id.modDayTournament)
        public TextView modDayTournament;

        @BindView(R.id.card_viewCH)
        public CardView cardView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}