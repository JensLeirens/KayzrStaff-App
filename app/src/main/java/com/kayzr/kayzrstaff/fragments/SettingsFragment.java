package com.kayzr.kayzrstaff.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.kayzr.kayzrstaff.R;
import com.kayzr.kayzrstaff.domain.KayzrApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingsFragment extends Fragment {

    private KayzrApp app;


    @BindView(R.id.settingsUsernameAndPass)
    CheckBox usernameAndPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);

        app = (KayzrApp) getActivity().getApplicationContext();
        usernameAndPass.setChecked(app.getCurrentUser().isRememberUsernameAndPass());

        return v;
    }


    @OnCheckedChanged(R.id.settingsUsernameAndPass)
    public void usernameAndPassSetting(){
        app.getCurrentUser().setRememberUsernameAndPass(usernameAndPass.isChecked());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
