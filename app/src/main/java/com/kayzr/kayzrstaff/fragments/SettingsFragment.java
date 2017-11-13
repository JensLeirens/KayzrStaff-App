package com.kayzr.kayzrstaff.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.kayzr.kayzrstaff.MainActivity;
import com.kayzr.kayzrstaff.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settingsUsernameAndPass) CheckBox usernameAndPass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);

        usernameAndPass.setChecked(MainActivity.app.getCurrentUser().isRememberUsernameAndPass());

        return v;
    }

    @OnCheckedChanged(R.id.settingsUsernameAndPass)
    public void usernameAndPassSetting(){
        MainActivity.app.getCurrentUser().setRememberUsernameAndPass(usernameAndPass.isChecked());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
