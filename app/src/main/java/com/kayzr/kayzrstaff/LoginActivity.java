package com.kayzr.kayzrstaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kayzr.kayzrstaff.domain.KayzrApp;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AuthRequest;
import com.kayzr.kayzrstaff.domain.NetworkClasses.AuthResponse;
import com.kayzr.kayzrstaff.domain.User;
import com.kayzr.kayzrstaff.network.Calls;
import com.kayzr.kayzrstaff.network.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.LoginUsername) EditText mUsername;
    @BindView(R.id.Loginpassword) EditText mPassword;
    @BindView(R.id.signIn) Button signin ;
    @BindView(R.id.loading_spinner) ProgressBar spinner ;

    public User loginUser;
    public static boolean leave = false;
    private KayzrApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        app = (KayzrApp) getApplicationContext();

        //If the user wanted to remember the username and pas then fill it already in for him
        if(app.getCurrentUser().getRememberUsernameAndPass()){
            mUsername.setText(app.getCurrentUser().getUsername());
            mPassword.setText(app.getCurrentUser().getPassword());
            signin.requestFocus();
        }

    }

    @OnClick(R.id.signIn)
    public void signIn()  {
        spinner.setVisibility(View.VISIBLE);
        signin.setVisibility(View.INVISIBLE);
        //encrypt the password
        String encryptedPas = "" ;
        try{
             encryptedPas = sha256(mPassword.getText().toString()) ;
        }catch (Exception e){
            Log.e("Sha encrypt error", e.toString());
        }

        if(app.getCurrentUser().getRememberUsernameAndPass()){
            if(app.getCurrentUser().getPassword().equals(mPassword.getText().toString())){
                UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), mPassword.getText().toString());
                task.doInBackground();
            } else {
                UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), encryptedPas);
                task.doInBackground();
            }
        } else {
            UserLoginTask task = new UserLoginTask(mUsername.getText().toString(), encryptedPas);
            task.doInBackground();
        }
    }

    @Override
    public void onBackPressed() {
        // press the back button twice to leave the app
        Log.d("LoginActivity", "onBackPressed Called");
        if(leave){
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "if you want to exit press Back again!",Toast.LENGTH_LONG).show();
            leave = true ;
        }
    }

    private static String sha256(String input) throws NoSuchAlgorithmException {
        //encrypt the input string with sha256 algoritm and return it
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

     //Represents an asynchronous login task used to authenticate the user.

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // we make the call to get the userinfo and pas from the backend with the username
            Calls caller = Config.getRetrofit().create(Calls.class);
//            String body = "{\"username\": \"" + mUsername + "\", \"password\": \"" + mPassword +  "\"} ";
//
//            JSONObject json = null;
//            try {
//                json = new JSONObject(body);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.d("Backend Call", " call data (Post auth) body: " + json );


            Call <AuthResponse> call = caller.getAuth(new AuthRequest(mUsername,mPassword));
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                    try {

                        //Normaal verloop: de user uit backend halen
                        loginUser = response.body().getUser();
                        Log.d("Backend Call", " call successful (Post auth)");

                        //kijken of de user met succes is ingelogd
                        if (response.body().isSuccess()) {
                            Log.d("log in", " login succes");
                            loginUser.setLoggedOn(true);
                            loginUser.setRememberUsernameAndPass(app.getCurrentUser().getRememberUsernameAndPass());
                            app.setCurrentUser(loginUser);
                            app.getCurrentUser().setPassword(mPassword);
                            // de login activity is afgelopen en de user is ingelogd deze activity mag afgesloten worden
                            finish();

                        } else {
                            // user is niet ingelogd toon een popup
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            spinner.setVisibility(View.GONE);
                            signin.setVisibility(View.VISIBLE);
                        }
                    } catch (NullPointerException npe) {
                        // mocht de backend niet werken of de username is verkeerd dan krijgt men een nullpointer
                        // dit was dan de makkelijkste manier om deze op te vangen
                        Toast.makeText(getApplicationContext(), "There is no user like that or the service is currently down! " +
                                "please report this to Mafken", Toast.LENGTH_LONG).show();
                        spinner.setVisibility(View.GONE);
                        signin.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call <AuthResponse> call, Throwable t) {
                    //als er een error is in de call
                    Log.e("Backend CAll", "call failed (Post auth) " + t.getMessage() + " " + call.request().toString());
                    Toast.makeText(getApplicationContext(), "The service is currently down! " +
                            "please report this to Mafken", Toast.LENGTH_LONG).show();
                }
            });

            return true;
        }
    }
}

