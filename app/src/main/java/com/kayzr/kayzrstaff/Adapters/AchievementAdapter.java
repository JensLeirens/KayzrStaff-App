package com.kayzr.kayzrstaff.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayzr.kayzrstaff.R;

import butterknife.ButterKnife;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private int itemCount;


    public AchievementAdapter() {

        //this.itemCount = achievements.size();
    }

    @Override
    public AchievementAdapter.AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mod_day, parent, false);
        return new AchievementAdapter.AchievementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AchievementAdapter.AchievementViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {

        //@BindView(R.id.achievementCardImage)
        //public ImageView achievementImage;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}